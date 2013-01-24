/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.services;

import com.tresback.mongos.ApplicationStaticProperty;
import com.tresback.repos.StaticPropertiesRepository;
import com.tresback.spring.StaticPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rbailey
 */
@Service
public class ApplicationPropertiesService {
    
    protected final Logger log = LoggerFactory.getLogger( getClass() );
    
    @Autowired
    StaticPropertiesRepository staticsRepo;
    @Autowired
    StaticPropertySource propertySource;

    public List<ApplicationStaticProperty> getAllStaticProperties() {
        log.info("Entering the method");
        return staticsRepo.findAll();
    }

    public ApplicationStaticProperty getOneStaticProperty( String key ) {
        log.info("Entering the method: " + key);
        return staticsRepo.findOne(key);
    }

    public ApplicationStaticProperty saveStaticProperty( String key, ApplicationStaticProperty property ) {
        log.info("Entering the method: " + key);
        property.setKey(key);
        property = staticsRepo.save(property);
        return property;
    }

    public List<ApplicationStaticProperty> saveStaticPropertyList( List<ApplicationStaticProperty> properties ) {
        log.info("Entering the method, list size: " + properties.size());
        List<ApplicationStaticProperty> returned = staticsRepo.save(properties);
        propertySource.loadProperties();
        log.info("Exiting the method");
        return returned;
    }

    public void deleteStaticProperty( String key ) {
        log.info("Entering the method: " + key);
        staticsRepo.delete(key);
        propertySource.loadProperties();
        log.info("Exiting the method");
    }

    public void deleteAllProperties() {
        log.info("Entering the method");
        staticsRepo.deleteAll();
        propertySource.loadProperties();
        log.info("Exiting the method");
    }
}
