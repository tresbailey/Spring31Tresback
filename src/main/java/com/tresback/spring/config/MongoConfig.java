package com.tresback.spring.config;

import java.util.Set;
import com.mongodb.Mongo;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 * @author rbailey
 */
@Configuration
@EnableMongoRepositories("com.tresback.repos")
public class MongoConfig extends AbstractMongoConfiguration {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    

    private MongoDbFactory mongoDbFact;
    private MongoMappingContext mappingContext;
    private CustomConversions allConversions;
    
    @Autowired
    DeployPropertiesConfig deployPropertiesConfig;
    
    @Override
    protected String getDatabaseName() {
        String dbName = deployPropertiesConfig.dbName();
        return dbName;
    }

    
    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new Mongo(deployPropertiesConfig.dbHost(), deployPropertiesConfig.dbPort());
    }
    
    @Override
    @Bean
    public CustomConversions customConversions() {
        if (this.allConversions == null) {
            loadCustomConversions();
        }
        return allConversions;
    }

    private void loadCustomConversions() {
        // Instantiate the various converter beans we want to use for MongoDB.
        // Must be setup before runtime, not configurable?
        List converters = new ArrayList();
        allConversions = new CustomConversions(converters);
    }

    @Override
    protected Set<Class<?>> getInitialEntitySet() throws ClassNotFoundException {
        return super.getInitialEntitySet();
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.tresback.mongos";
    }

    @Override
    protected org.springframework.data.authentication.UserCredentials getUserCredentials() {
        return super.getUserCredentials();
    }

    @Override
    @Bean
    public MappingMongoConverter mappingMongoConverter() throws Exception {
        MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory(), mongoMappingContext());
        converter.setCustomConversions(customConversions());
        return converter;
    }

    @Override
    @Bean
    public SimpleMongoDbFactory mongoDbFactory() throws Exception {
        if (this.mongoDbFact == null) {
            loadDbFactory();
        }
        return (SimpleMongoDbFactory) mongoDbFact;
    }

    private void loadDbFactory() throws Exception {
        UserCredentials credentials = getUserCredentials();
        if (credentials == null) {
            mongoDbFact = new SimpleMongoDbFactory(mongo(), getDatabaseName());
        } else {
            mongoDbFact = new SimpleMongoDbFactory(mongo(), getDatabaseName(), credentials);
        }

    }

    @Override
    @Bean
    public MongoMappingContext mongoMappingContext() throws ClassNotFoundException {
        if (mappingContext == null) {
            loadMappingContext();
        }
        return mappingContext;
    }

    private void loadMappingContext() throws ClassNotFoundException {
        mappingContext = new MongoMappingContext();
        mappingContext.setInitialEntitySet(getInitialEntitySet());
        mappingContext.setSimpleTypeHolder(customConversions().getSimpleTypeHolder());
        mappingContext.initialize();
    }

    @Override
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory(), mappingMongoConverter());
    }
}
