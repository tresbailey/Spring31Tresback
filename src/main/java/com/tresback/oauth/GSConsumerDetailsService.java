/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.oauth;

import com.tresback.mongos.OAuthConsumerDetails;
import com.tresback.repos.OAuthConsumerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.security.oauth.provider.InvalidOAuthParametersException;

/**
 *
 * @author rbailey
 */
public class GSConsumerDetailsService implements ConsumerDetailsService, UserDetailsService {
    
    
    private static final Logger log = LoggerFactory.getLogger(GSConsumerDetailsService.class);
    
    @Autowired
    private OAuthConsumerRepository consumerRepo;

    @Override
    public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) throws OAuthException {
        OAuthConsumerDetails details = consumerRepo.findOneByConsumerKey(consumerKey);
        log.debug("got the consumer: "+ details);
        if (details == null) {
            throw new InvalidOAuthParametersException("Consumer not found: " + consumerKey);
        }
        return details;
    }
    
    
    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        log.info("Entering the method");
        throw new UnsupportedOperationException();
    }
}
