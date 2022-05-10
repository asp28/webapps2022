/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author ankeet
 */
@NamedQuery(name = "Notification.getAllNotifcationsForUser", query = "SELECT u from Notification u WHERE u.user.email = :email")
@NamedQuery(name = "Notification.getById", query = "SELECT u from Notification u WHERE u.id = :id")
@NamedQuery(name = "Notification.getRequestId", query = "SELECT u from Notification u WHERE u.requester.id = :id")
@Entity
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    
    @OneToOne
    private SystemUser user;
    
    @OneToOne
    private SystemUser requester;
    
    private String notificationType;
    
    private Double amount;
    
    private Currency currency;

    public Notification() {
    }

    public Notification(SystemUser user, String notificationType, Double amount, Currency currency) {
        this.user = user;
        this.notificationType = notificationType;
        this.amount = amount;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SystemUser getUser() {
        return user;
    }

    public void setUser(SystemUser user) {
        this.user = user;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public SystemUser getRequester() {
        return requester;
    }

    public void setRequester(SystemUser requester) {
        this.requester = requester;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.user);
        hash = 67 * hash + Objects.hashCode(this.notificationType);
        hash = 67 * hash + Objects.hashCode(this.amount);
        hash = 67 * hash + Objects.hashCode(this.currency);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Notification other = (Notification) obj;
        if (!Objects.equals(this.notificationType, other.notificationType)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        return this.currency == other.currency;
    }

    
    
}
