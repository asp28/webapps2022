package com.webapps2022.thrift;

import datetime.dateTimeService;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Startup;
import javax.ejb.Singleton;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

@Startup
@Singleton
public class DateTimeServer {

    public static TServer server;

    @PostConstruct
    public void startServer() {
        try {
            DateTimeHandler handler = new DateTimeHandler();
            dateTimeService.Processor processor = new dateTimeService.Processor(handler);

            Runnable simple = new Runnable() {
                @Override
                public void run() {
                    simple(processor);
                }
            };

            new Thread(simple).start();

        } catch (Exception x) {
            System.err.println(x);
        }
    }

    public static void simple(dateTimeService.Processor processor) {
        try {
            TServerSocket serverTransport = new TServerSocket(9090);
            server = new TSimpleServer(new Args(serverTransport).processor(processor));

            System.out.println("Starting the simple server in Thread " + Thread.currentThread().getId());
            server.serve();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    @PreDestroy
    public void stopServer() {
        server.stop();
    }

}
