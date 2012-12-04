/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.oauth;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.ProtectedResourceDetailsService;

/**
 *
 * @author rbailey
 */
public class GSProtectedResourceDetailsService implements ProtectedResourceDetailsService {

    @Override
    public ProtectedResourceDetails loadProtectedResourceDetailsById(String id) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private static final Logger log = LoggerFactory.getLogger(GSProtectedResourceDetailsService.class);
    

    
    
}
