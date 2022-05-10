/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.restservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author ankeet
 */

// The Singleton annotaion denotes that there will be a single object of the RSEmployee class - don't change that
@Singleton

/*** Annotate the class so that it exports Employee resources under the relative path /employee ***/
@Path("/conversion")
public class RSCurrencyExchange {
    
    HashMap<String, Currency> currencyRates = new HashMap<>();

    public RSCurrencyExchange() {
        HashMap<String, ExchangeRate> gbpRates = new HashMap<>();
        ExchangeRate usd = generateExchangeRate("USD", 1.31);
        ExchangeRate euro = generateExchangeRate("EUR", 1.19);
        ExchangeRate gbp = generateExchangeRate("GBP", 1.0);
        gbpRates.put("EUR", euro);
        gbpRates.put("USD", usd);
        gbpRates.put("GBP", gbp);
        Currency currency = new Currency("GBP", gbpRates);
        
        HashMap<String, ExchangeRate> usdRates = new HashMap<>();
        ExchangeRate usdToGBP = generateExchangeRate("GBP", 0.76);
        ExchangeRate usdToEUR = generateExchangeRate("EUR", 0.91);
        ExchangeRate usdToUSD = generateExchangeRate("USD", 1.0);
        usdRates.put("EUR", usdToEUR);
        usdRates.put("GBP", usdToGBP);
        usdRates.put("USD", usdToUSD);
        Currency currencyUSD = new Currency("USD", usdRates);
        
        HashMap<String, ExchangeRate> eurRates = new HashMap<>();
        ExchangeRate eurToUSD = generateExchangeRate("USD", 1.10);
        ExchangeRate eurToGBP = generateExchangeRate("GBP", 0.84);
        ExchangeRate eurToEUR = generateExchangeRate("EUR", 1.0);
        eurRates.put("GBP", eurToGBP);
        eurRates.put("USD", eurToUSD);
        eurRates.put("EUR", eurToEUR);
        Currency currencyEUR = new Currency("EUR", eurRates);
        
        currencyRates.put("GBP", currency);
        currencyRates.put("USD", currencyUSD);
        currencyRates.put("EUR", currencyEUR);
        
        System.out.println(currencyRates.toString());
    }

    private ExchangeRate generateExchangeRate(String currency, Double value) {
        ExchangeRate rate = new ExchangeRate();
        rate.setCurrency(currency);
        rate.setRate(value);
        return rate;
    }

    @GET
    @Path("/{currency1}/{currency2}/{amount_of_currency1}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getEmployee(@PathParam("currency1") String currency1, @PathParam("currency2") String currency2, @PathParam("amount_of_currency1") Double amount_of_currency1) {
        Currency currency = currencyRates.get(currency1);
        if (currency == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            ExchangeRate exchangeRate = currency.getRates().get(currency2);
            if (exchangeRate == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            ConversionResponse response = new ConversionResponse();
            response.setValue(exchangeRate.rate * amount_of_currency1);
            return Response.ok(response).build();
        }
    }
    
    @PostConstruct
    public void init() {
        System.out.println("Singleton Object for this RESTfull Web Service has been created!!");
    }

    @PreDestroy
    public void clean() {
        System.out.println("Singleton Object for this RESTfull Web Service has been cleaned!!");
    }
}

