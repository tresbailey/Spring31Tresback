package com.tresback.repos;

import com.tresback.mongos.OAuthConsumerDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rbailey
 */
@Repository
public interface OAuthConsumerRepository  extends MongoRepository<OAuthConsumerDetails, String> {
    
    OAuthConsumerDetails findOneByConsumerKey(String consumerName);
    
}
