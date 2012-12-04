/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.spring.config;

import org.springframework.context.annotation.Bean;

/**
 *
 * @author rbailey
 */
public interface DeployPropertiesConfig {
    @Bean
    String dbName();
    
    @Bean
    String dbHost();
    
    @Bean 
    int dbPort();
}
