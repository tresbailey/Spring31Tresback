package com.tresback.repos;

import com.tresback.mongos.TBBean;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author rbailey
 */
public interface TBBeanRepository extends MongoRepository<TBBean, String> {
    
}
