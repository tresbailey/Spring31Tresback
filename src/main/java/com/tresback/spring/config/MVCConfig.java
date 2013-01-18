/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.spring.config;

import com.invoker.ServiceInvoker;
import com.invoker.context.MongoServiceContextProvider;
import com.invoker.context.ServiceContextProvider;
import com.invoker.header.MongoHeaderExtensionProvider;
import com.invoker.modifier.ServiceDetailsService;
import com.invoker.service.ServiceMappingProperty;
import com.tresback.spring.AppRefreshListener;
import com.tresback.spring.MvcConfigurationPostProcessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
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
public class MVCConfig extends WebMvcConfigurerAdapter implements InitializingBean, ApplicationContextAware, HandlerExceptionResolver {
    
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
    
    @Bean
    public ServiceInvoker caller() {
        return new ServiceInvoker();
    }
    
    @Bean
    public ServiceDetailsService serviceDetailsService() {
        return new ServiceDetailsService();
    }
    @Bean
    public ServiceContextProvider serviceDetailsProvider() {
        return new MongoServiceContextProvider();
    }

    @Bean
    public ServiceMappingProperty serviceFieldsProvider() {
        return new ServiceMappingProperty();
    }
    
    @Bean
    public MongoHeaderExtensionProvider headerProvider() {
        return new MongoHeaderExtensionProvider();
    }
    
    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.appCtx = ac;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("After properties Set Method...");
        ((ConfigurableApplicationContext) appCtx).addApplicationListener(new AppRefreshListener());
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, Exception excptn) {
        if( excptn instanceof NoSuchBeanDefinitionException ) {
            ModelAndView mav = new ModelAndView();
            hsr1.setStatus(404);
            mav.setViewName("NoSuchBean");
            mav.addObject("name", excptn.getClass().getSimpleName());
            mav.addObject("message", excptn.getLocalizedMessage());
            return mav;
        }
        return null;
    }
}
