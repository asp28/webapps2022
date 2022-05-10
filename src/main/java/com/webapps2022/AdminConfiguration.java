/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022;

import com.webapps2022.ejb.UserService;
import com.webapps2022.entity.SystemUserGroup;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 198758
 */
@Startup
@Singleton
public class AdminConfiguration {
    
    @EJB
    UserService usrSrv;
    
    @PersistenceContext
    EntityManager em;
    
    @PostConstruct
    public void getAdmins() {
        List<SystemUserGroup> admins = em.createNamedQuery("doesAnAdminExist").getResultList();
        System.out.println("admins " + admins);
        if (admins == null || admins.isEmpty()) {
            usrSrv.registerAdminOnStartup("admin1", "admin1", "admin", "admin", "GBP");
        }
    }
    
}
