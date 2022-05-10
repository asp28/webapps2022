/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.restservice;

import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ankeet
 */
@Singleton
public class RestClient {
    
        public ConversionResponse getCurrentFx(String currency1, String currency2, double amount) {
        Client client = ClientBuilder.newClient();
        WebTarget conversionResource = client.target("http://localhost:10000/webapps2022/RESTService/conversion")
                .path("{currency1}")
                .resolveTemplate("currency1", currency1)
                .path("{currency2}")
                .resolveTemplate("currency2", currency2)
                .path("{amount_of_currency1}")
                .resolveTemplate("amount_of_currency1", String.valueOf(amount));
            System.out.println(conversionResource.getUri());
        Invocation.Builder builder = conversionResource.request(MediaType.APPLICATION_JSON);
        ConversionResponse response = builder.get(ConversionResponse.class);
        client.close();
        return response;
    }
    
}
