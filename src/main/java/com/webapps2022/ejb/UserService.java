package com.webapps2022.ejb;

import com.webapps2022.entity.SystemUserGroup;
import com.webapps2022.entity.SystemUser;
import com.webapps2022.restservice.RestClient;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author 198758
 */
@Stateless
public class UserService {

    @PersistenceContext
    EntityManager em;
    
    @Inject
    RestClient restClient;

    public UserService() {
    }

    public void registerUser(String username, String userpassword, String name, String surname, String currency) {
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String paswdToStoreInDB = sb.toString();

            // apart from the default constructor which is required by JPA
            // you need to also implement a constructor that will make the following code succeed
            sys_user = new SystemUser(username, paswdToStoreInDB, name, surname, currency, restClient.getCurrentFx("GBP", currency, 1000).getValue());
            sys_user_group = new SystemUserGroup(username, "users");

            em.persist(sys_user);
            em.persist(sys_user_group);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @RolesAllowed(value = "admins")
    public void registerAdmin(String username, String userpassword, String name, String surname, String currency) {
        registerAdminMethod(username, userpassword, name, surname, currency, false);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage("New admin registered with username " + username));
    }
    
    public void registerAdminOnStartup(String username, String userpassword, String name, String surname, String currency) {
        registerAdminMethod(username, userpassword, name, surname, currency, true);
    }
    
    private void registerAdminMethod(String username, String userpassword, String name, String surname, String currency, Boolean isOnStartup) {
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String paswdToStoreInDB = sb.toString();

            // apart from the default constructor which is required by JPA
            // you need to also implement a constructor that will make the following code succeed
            double balance;
            if (isOnStartup) {
                balance = 1000;
            } else {
                balance = restClient.getCurrentFx("GBP", currency, 1000).getValue();
            }
            sys_user = new SystemUser(username, paswdToStoreInDB, name, surname, currency, balance);
            sys_user_group = new SystemUserGroup(username, "admins");

            em.persist(sys_user);
            em.persist(sys_user_group);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConstraintViolationException e) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE,"Exception: ");
            e.getConstraintViolations().forEach(err->Logger.getLogger(UserService.class.getName()).log(Level.SEVERE,err.toString()));
        }
    }
    
    public Boolean existsByEmail(String email) {
        try {
            em.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();
            return true;
        } catch(NoResultException ex) {
            return false;
        }
        
    }

    public SystemUser findByEmail(String email) {
        try {
            return (SystemUser) em.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }

    @RolesAllowed(value = "admins")
    public List<SystemUser> getAllUsers() {
        return em.createNamedQuery("User.getAll").getResultList();
    }
}
