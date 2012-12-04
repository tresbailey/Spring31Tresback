
package com.tresback.mongos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth.provider.BaseConsumerDetails;
import org.springframework.security.oauth.provider.ExtraTrustConsumerDetails;

/**
 *
 * @author rbailey
 */
@Document
public class OAuthConsumerDetails extends BaseConsumerDetails implements ExtraTrustConsumerDetails {
    
    @Id
    private String id;
    
    @Override
    public boolean isRequiredToObtainAuthenticatedToken() {
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public void setConsumerKey(String consumerKey) {
        super.setConsumerKey(consumerKey);
        this.id = consumerKey;
    }
}
