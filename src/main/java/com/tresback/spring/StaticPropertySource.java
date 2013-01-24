/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.spring;

import com.tresback.mongos.ApplicationStaticProperty;
import com.tresback.repos.StaticPropertiesRepository;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author rbailey
 */
@Component
public class StaticPropertySource extends PropertySource<ApplicationStaticProperty> {
    
    protected final Logger log = LoggerFactory.getLogger( getClass() );
    
    public static final String MONGO_PROPERTY_SOURCE = "mongo_properties";
    
    @Autowired(required=true)
    StaticPropertiesRepository propertiesRepo;
    
    private final Map<String, ApplicationStaticProperty> propertyMap;
    
    public StaticPropertySource( ) {
        super(MONGO_PROPERTY_SOURCE);
        propertyMap = new HashMap<String, ApplicationStaticProperty>();
    }
    
    @PostConstruct
    public void loadProperties() {
        for ( ApplicationStaticProperty staticProp : propertiesRepo.findAll() ) {
            propertyMap.put( staticProp.getKey(), staticProp );
        }
        log.info("Loaded properties: "+ propertyMap);
    }
    
    @Override
    public String getProperty(String string) {
        log.debug("Searching for string: "+ string);
        return ( (ApplicationStaticProperty) propertyMap.get(string)).getValue();
    }
}
