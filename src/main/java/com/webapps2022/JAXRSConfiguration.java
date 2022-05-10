package com.webapps2022;

import com.webapps2022.restservice.RSCurrencyExchange;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("/RESTService")
public class JAXRSConfiguration extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        //register resource
        classes.add(RSCurrencyExchange.class);
        return classes;
    }
}
