/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.repos;

import com.tresback.mongos.ApplicationStaticProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rbailey
 */
@Repository
public interface StaticPropertiesRepository extends MongoRepository<ApplicationStaticProperty, String> {
    
}
