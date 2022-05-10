/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.thrift;

import datetime.dateTimeService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author ankeet
 */
@Stateless
public class DateTimeClient {
    
    TTransport transport;
    
    @PostConstruct
    public void buildClient(){
        try {
            transport = new TSocket("localhost", 9090);
            transport.open();
        } catch (TTransportException ex) {
            Logger.getLogger(DateTimeClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @PreDestroy
    public void closeClient() {
        transport.close();
    }
    
    public Long getDateTime() {
        try {
            //instantiate a TProtocol using the TTransport instantiated above
            TProtocol protocol = new TBinaryProtocol(transport);
            //Finally, instantiate a synchronous client using the TProtocol instantiated above
            dateTimeService.Client client = new dateTimeService.Client(protocol);
            // use the stub method to call the RPC Server - this is a blocking call
            return client.getDateTime();
        } catch (TException ex) {
            Logger.getLogger(DateTimeClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
