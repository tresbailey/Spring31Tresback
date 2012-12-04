
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.spring.filters;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author rbailey
 */
@Component
public class CORSAwareFilter extends OncePerRequestFilter {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Open-Token");
        response.addHeader("Access-Control-Expose-Headers", "Open-Token");
        
        filterChain.doFilter(request, response);
    }
}
