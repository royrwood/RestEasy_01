package org.roy;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;


// Info here on setting up to automatically discover JAX-RS resources: https://docs.jboss.org/author/display/AS7/JAX-RS+Reference+Guide

//@ApplicationPath("/resteasy")
public class RestApplication extends Application {

    public void doStuff() {
        System.out.println("RestApplication.doStuff() called");
    }

    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(JOOQService.class, HelloWorld.class));
    }


//    private Set<Object> singletons = new HashSet<Object>();
//
//    public RestApplication() {
//
//        singletons.add(new HelloWorld());
//        singletons.add(new JOOQService());
//    }
//
//    @Override
//    public Set<Object> getSingletons() {
//        return singletons;
//    }
}
