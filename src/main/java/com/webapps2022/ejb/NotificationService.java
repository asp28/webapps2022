/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.ejb;

import com.webapps2022.entity.Currency;
import com.webapps2022.entity.Notification;
import com.webapps2022.entity.SystemUser;
import com.webapps2022.restservice.RestClient;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 198758
 */
@Stateless
public class NotificationService {
    @PersistenceContext
    EntityManager em;
    
    @Inject
    RestClient restClient;
    
    @Resource
    SessionContext context;
    
    public void moneyReceivedNotification(SystemUser user, Double amount, Currency currency) {
        Notification notification = new Notification(user, "RECEIVED", amount, currency);
        em.persist(notification);
    }
    
    public void moneyRequestedNotification(SystemUser user, Double amount, Currency currency) {
        String email = context.getCallerPrincipal().getName();
        SystemUser requester = (SystemUser) em.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();
        Notification notification = new Notification(user, "REQUESTED", amount, currency);
        notification.setRequester(requester);
        em.persist(notification);
    }
    
    public void moneySentNotification(SystemUser user, Double amount, Currency currency) {
        Notification notification = new Notification(user, "SENT", amount, currency);
        em.persist(notification);
    }
    
    public void senderRequestedNotification(SystemUser user, Double amount, Currency currency) {
        Notification notification = new Notification(user, "SENDERREQUESTED", amount, currency);
        em.persist(notification);
    }
    
    public List<Notification> getAllNotificationsForUser() {
        String email = context.getCallerPrincipal().getName();
        return em.createNamedQuery("Notification.getAllNotifcationsForUser").setParameter("email", email).getResultList();
    }
    
    public void denyRequest(Long id, Long requester) {
        Notification notification = (Notification) em.createNamedQuery("Notification.getById").setParameter("id", id).getSingleResult();
        notification.setNotificationType("DENIED");
        em.persist(notification);
        Notification senderNotification = (Notification) em.createNamedQuery("Notification.getRequestId").setParameter("id", requester).getSingleResult();
        notification.setNotificationType("DENIED");
        em.persist(senderNotification);
        
    }
    
}
