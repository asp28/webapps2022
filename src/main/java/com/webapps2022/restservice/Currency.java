/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.restservice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ankeet
 */
public class Currency {
    
    private String currency;
    private HashMap<String, ExchangeRate> rates;

    public Currency(String currency, HashMap<String, ExchangeRate> rates) {
        this.currency = currency;
        this.rates = rates;
    }

    public Currency() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public HashMap<String, ExchangeRate> getRates() {
        return rates;
    }

    public void setRates(HashMap<String, ExchangeRate> rates) {
        this.rates = rates;
    }
    
    
    
}
