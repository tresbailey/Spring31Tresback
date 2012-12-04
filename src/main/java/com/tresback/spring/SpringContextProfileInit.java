/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.spring;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ConfigurableWebEnvironment;

/**
 *
 * @author rbailey
 */
public class SpringContextProfileInit implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

    private static final Logger log = LoggerFactory.getLogger(SpringContextProfileInit.class);
    
    @Override
     public void initialize(ConfigurableWebApplicationContext ctx) {
        ConfigurableWebEnvironment environ = ctx.getEnvironment();
        log.debug("Got the environment, no profiles should be set: "+ environ.getActiveProfiles());
        String profile = "dev";
        try {
            Properties appProperties = new Properties();
            appProperties.load(new FileInputStream("lib/application.properties"));
            log.debug("App props: "+ appProperties.stringPropertyNames());
            profile = appProperties.getProperty("prop.spring.profile");
        } catch (IOException ie) {
            log.error("Could not find application.properties in the classpath.  Exiting");
            ie.printStackTrace();
            System.exit(1);
        }
        environ.setActiveProfiles(profile);
        log.info("The spring profile is now set to: "+ environ.getActiveProfiles()[0]);
        
        ctx.refresh();
    }
    
}
