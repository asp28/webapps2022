/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.jsf;

import com.webapps2022.ejb.NotificationService;
import com.webapps2022.entity.Notification;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ankeet
 */
@Named
@RequestScoped
public class NotificationsBean implements Serializable {
    
    @Inject
    NotificationService notificationService;
    
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotificationsForUser();
    }
    
    public void denyRequest(Long id, Long requesterID) {
        notificationService.denyRequest(id, requesterID);
    }
    
}
