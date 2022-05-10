/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ankeet
 */
@NamedQuery(name = "getAllPaymentsForUser", query = "SELECT u from Payment u WHERE u.user.email = :email")
@NamedQuery(name = "getAllPayments", query = "SELECT u from Payment u ORDER BY u.dateOfTransaction DESC")
@Entity
public class Payment implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    SystemUser user;
    
    Double amount;
    
    Currency currency;
    
    Boolean sent;
    
    @Temporal(TemporalType.TIMESTAMP)
    Date dateOfTransaction;

    public Payment() {
    }

    public Payment(SystemUser user, Double amount, Currency currency, Boolean sent, Date date) {
        this.user = user;
        this.amount = amount;
        this.currency = currency;
        this.sent = sent;
        this.dateOfTransaction = date;
    }
    
    

    public SystemUser getUser() {
        return user;
    }

    public void setUser(SystemUser userEmail) {
        this.user = user;
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

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public Date getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(Date dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.user);
        hash = 67 * hash + Objects.hashCode(this.amount);
        hash = 67 * hash + Objects.hashCode(this.currency);
        hash = 67 * hash + Objects.hashCode(this.sent);
        hash = 67 * hash + Objects.hashCode(this.dateOfTransaction);
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
        final Payment other = (Payment) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        if (this.currency != other.currency) {
            return false;
        }
        if (!Objects.equals(this.sent, other.sent)) {
            return false;
        }
        return Objects.equals(this.dateOfTransaction, other.dateOfTransaction);
    }

    
    
}
