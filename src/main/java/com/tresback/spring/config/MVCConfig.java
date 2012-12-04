/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.spring.config;

import com.tresback.spring.MvcConfigurationPostProcessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 *
 * @author rbailey
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.tresback")
@Import({MongoConfig.class, OAuthSecurityConfig.class})
@EnableAspectJAutoProxy
public class MVCConfig extends WebMvcConfigurerAdapter {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    ApplicationContext appCtx;
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJacksonHttpMessageConverter());
    }
    
    @Bean
    public ViewResolver viewResolver() {
        log.debug("Adding views");
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setDefaultContentType(MediaType.APPLICATION_JSON);
        Map<String, String> mediaTypes = new HashMap<String, String>();
        mediaTypes.put("json", MediaType.APPLICATION_JSON_VALUE);
        resolver.setMediaTypes(mediaTypes);
        List<View> defaultViews = new ArrayList<View>();
        defaultViews.add(new MappingJacksonJsonView());
        resolver.setDefaultViews(defaultViews);
        resolver.setIgnoreAcceptHeader(true);
        UrlBasedViewResolver ubvr = new UrlBasedViewResolver();
        ubvr.setViewClass(JstlView.class);
        ubvr.setPrefix("/");
        ubvr.setSuffix(".jsp");
        List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
        viewResolvers.add(ubvr);
        resolver.setViewResolvers(viewResolvers);
        return resolver;
    }
    
    @Bean
    public LoggingEventListener loggingEventListener() {
        return new LoggingEventListener();
    }
    
    @Bean
    public MvcConfigurationPostProcessor mvcPostProcessor() {
        return new MvcConfigurationPostProcessor();
    }
    
    
    
}
