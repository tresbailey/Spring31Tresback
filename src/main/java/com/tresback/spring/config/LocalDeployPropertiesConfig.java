/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 *
 * @author rbailey
 */
@Configuration
@Profile("local")
@PropertySource("classpath:local.properties")
public class LocalDeployPropertiesConfig implements DeployPropertiesConfig {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    Environment env;

    @Override
    @Bean
    public String dbHost() {
        return env.getProperty("prop.mongo.host");
    }

    @Override
    @Bean
    public String dbName() {
        return env.getProperty("prop.mongo.dbname");
    }

    @Override
    @Bean
    public int dbPort() {
        return Integer.parseInt(env.getProperty("prop.mongo.port"));
    }
    
}
