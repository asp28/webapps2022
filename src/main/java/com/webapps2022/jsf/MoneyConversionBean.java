/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.jsf;

import com.webapps2022.ejb.TransactionService;
import com.webapps2022.restservice.RestClient;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ankeet
 */
@Named
@RequestScoped
public class MoneyConversionBean {
    
    @Inject
    RestClient restClient;
    
    @EJB
    TransactionService transactionService;
    
    String emailToSendTo;
    Double amount;
    
    String response;

    public MoneyConversionBean() {
    }

    public String getEmailToSendTo() {
        return emailToSendTo;
    }

    public void setEmailToSendTo(String emailToSendTo) {
        this.emailToSendTo = emailToSendTo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
    public String sendMoney() {
        transactionService.sendMoney(emailToSendTo, amount);
        return "user";
    }
    public String requestMoney() {
        transactionService.requestMoney(emailToSendTo, amount);
        return "user";
    }
    
    public String acceptRequest(Long id) {
        transactionService.acceptMoneyRequest(id);
        return "user";
    }
}
