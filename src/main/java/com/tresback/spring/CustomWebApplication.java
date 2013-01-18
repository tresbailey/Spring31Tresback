/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.FilterRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author rbailey
 */
public class CustomWebApplication implements WebApplicationInitializer {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        log.debug("Initialize the servlet now");
        
        servletContext.setInitParameter("contextInitializerClasses", "com.tresback.spring.SpringContextProfileInit");
        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        log.debug("Registering the Config class");
        mvcContext.scan("com.tresback.spring");
        dispatcherContext.scan("com.tresback.spring");
        log.debug("Done");
        
        servletContext.addListener(new ContextLoaderListener(mvcContext));
        
        
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("appServlet", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        
        FilterRegistration.Dynamic springFilter = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
        springFilter.addMappingForUrlPatterns(null, true, "/*");
        
        log.debug("Done");
    }
    
}