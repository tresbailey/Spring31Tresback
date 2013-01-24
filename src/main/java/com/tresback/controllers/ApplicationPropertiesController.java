/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.controllers;

import com.tresback.mongos.ApplicationStaticProperty;
import com.tresback.services.ApplicationPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rbailey
 */
@Controller
public class ApplicationPropertiesController {

    protected final Logger log = LoggerFactory.getLogger( getClass() );
    
    @Autowired
    ApplicationPropertiesService propertiesService;


    @RequestMapping( value = "/staticproperties", 
            method = RequestMethod.GET )
    public
    @ResponseBody
    List<ApplicationStaticProperty> getAllProperties() {
        log.info("Entering the method");
        return propertiesService.getAllStaticProperties();
    }

    @RequestMapping( value = "/staticproperties/{key}", 
            method = RequestMethod.GET )
    public
    @ResponseBody
    ApplicationStaticProperty getOneProperty( 
            @PathVariable String key ) {
        log.info("Entering the method");
        return propertiesService.getOneStaticProperty(key);
    }

    @RequestMapping( value = "/staticproperties", 
            method = RequestMethod.POST, 
            consumes = "application/json" )
    public
    @ResponseBody
    List<ApplicationStaticProperty> saveManyProperties(
            @RequestBody List properties
    ) {
        log.info("Entering the method");
        for ( Object property : properties ) {
            log.debug(property.toString());
        }
        return propertiesService.saveStaticPropertyList(properties);
    }

    @RequestMapping( value = "/staticproperties/{key}", 
            method = RequestMethod.PUT )
    public
    @ResponseBody
    ApplicationStaticProperty saveOneProperty(
            @PathVariable String key,
            @RequestBody ApplicationStaticProperty property
    ) {
        log.info("Entering the method");
        return propertiesService.saveStaticProperty(key, property);
    }

    @RequestMapping( value = "/staticproperties/{key}",
            method = RequestMethod.DELETE )
    public
    @ResponseBody
    void deleteOneProperty( 
            @PathVariable String key ) {
        log.info("entering the method");
        propertiesService.deleteStaticProperty(key);
        log.info("Exiting the method");
    }

    @RequestMapping( value = "/staticproperties", 
            method = RequestMethod.DELETE )
    public
    @ResponseBody
    void deleteAllProperties() {
        log.info("entering the method");
        propertiesService.deleteAllProperties();
        log.info("Exiting the method");
    }
}
