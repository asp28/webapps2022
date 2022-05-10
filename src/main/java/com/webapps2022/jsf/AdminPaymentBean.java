/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.jsf;

import com.webapps2022.ejb.TransactionService;
import com.webapps2022.entity.Currency;
import com.webapps2022.entity.Payment;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author ankee
 */

@Named
@RequestScoped
public class AdminPaymentBean implements Serializable {
    
    @EJB
    TransactionService transactionService;
    
    Long id;
    
    String userID;
    
    Double amount;
    
    Currency currency;
    
    Boolean sent;
    
    Date dateOfTransaction;
    
    public List<Payment> getAllPayments() {
        return transactionService.getAllPayments();
    }
    
    public String getCurrencyCode() {
        return this.currency.name();
    }
}
