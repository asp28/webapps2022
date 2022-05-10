/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.ejb;

import com.webapps2022.entity.Notification;
import com.webapps2022.entity.Payment;
import com.webapps2022.entity.SystemUser;
import com.webapps2022.restservice.RestClient;
import com.webapps2022.thrift.DateTimeClient;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author ankeet
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TransactionService {
    @PersistenceContext
    EntityManager em;
    
    @EJB
    UserService userService;
    
    @EJB
    DateTimeClient datetimeClient;
    
    @EJB
    NotificationService notificationService;
    
    @Inject
    RestClient restClient;
    
    @Resource 
    private SessionContext context;

    public TransactionService() {
    }
    
    public void sendMoney(String email, Double amount) {
        if (userService.existsByEmail(email)) {
            SystemUser sender = userService.findByEmail(context.getCallerPrincipal().getName());
            SystemUser receiver = userService.findByEmail(email);
            if (sender.getBalance() >= amount) {
                sender.setBalance(sender.getBalance() - amount);
                Double amountToSend = restClient.getCurrentFx(sender.getCurrency().toString(), receiver.getCurrency().toString(), amount).getValue();
                receiver.setBalance(receiver.getBalance() + amountToSend);
                em.persist(sender);
                em.persist(receiver);
                Payment paymentSender = new Payment(sender, amount, sender.getCurrency(), true, new Date(datetimeClient.getDateTime()));
                Payment paymentReceiver = new Payment(receiver, amountToSend, receiver.getCurrency(), false, new Date(datetimeClient.getDateTime()));
                try {
                    em.persist(paymentReceiver);
                    em.persist(paymentSender);
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    facesContext.addMessage(null, new FacesMessage(amount + " sent to user with username " + email));
                } catch (ConstraintViolationException e) {
                    e.getConstraintViolations().forEach(err-> System.err.println(err.getMessage()));
                }
                notificationService.moneySentNotification(sender, amount, sender.getCurrency());
                notificationService.moneyReceivedNotification(receiver, amountToSend, receiver.getCurrency());
            } else {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.addMessage(null, new FacesMessage("Not enough money in balance"));
            }
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage("User not found with email " + email));
        }
    }

    public void requestMoney(String emailToSendTo, Double amount) {
        if (userService.existsByEmail(emailToSendTo)) {
            SystemUser sender = userService.findByEmail(context.getCallerPrincipal().getName());
            SystemUser receiver = userService.findByEmail(emailToSendTo);
            Double requestedAmount = restClient.getCurrentFx(sender.getCurrency().toString(), receiver.getCurrency().toString(), amount).getValue();
            
            notificationService.senderRequestedNotification(sender, amount, sender.getCurrency());
            notificationService.moneyRequestedNotification(receiver, requestedAmount, receiver.getCurrency());
        }
    }

    public List<Payment> getAllPaymentsForUser() {
        return em.createNamedQuery("getAllPaymentsForUser").setParameter("email", context.getCallerPrincipal().getName()).getResultList();
    }
    
    @RolesAllowed(value = "admins")
    public List<Payment> getAllPayments() {
        return em.createNamedQuery("getAllPayments").getResultList();
    }
    
    public void acceptMoneyRequest(Long id) {
        Notification notification = (Notification) em.createNamedQuery("Notification.getById").setParameter("id", id).getSingleResult();
        this.sendMoney(notification.getRequester().getEmail(), notification.getAmount());
        notification.setNotificationType("SENT");
        em.persist(notification);
    }

}
