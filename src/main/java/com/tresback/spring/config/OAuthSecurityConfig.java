/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.spring.config;

import com.tresback.oauth.GSConsumerDetailsService;
import com.tresback.oauth.GSProtectedResourceProcessingFilter;
import com.tresback.spring.filters.CORSAwareFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import javax.ws.rs.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.oauth.provider.OAuthProcessingFilterEntryPoint;
import org.springframework.security.oauth.provider.filter.CoreOAuthProviderSupport;
import org.springframework.security.oauth.provider.filter.UserAuthorizationProcessingFilter;
import org.springframework.security.oauth.provider.token.InMemorySelfCleaningProviderTokenServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.ExpressionBasedFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RegexRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

/**
 *
 * @author rbailey
 */
@Configuration
public class OAuthSecurityConfig {

    @Autowired
    Environment env;

    @Bean
    List filterChains() {
        List filterChains = new ArrayList();
        List simpleFilters = new ArrayList();
        CORSAwareFilter corsFilter = new CORSAwareFilter();
        simpleFilters.add(corsFilter);
        UserAuthorizationProcessingFilter userAuthFilter = new UserAuthorizationProcessingFilter();
        userAuthFilter.setAuthenticationManager(authMgr());
        simpleFilters.add(exceptionTranslationFilter());
        simpleFilters.add(filterSecurityInterceptor());
        SecurityFilterChain simpleChainGET = new DefaultSecurityFilterChain(new AntPathRequestMatcher("/**", HttpMethod.GET), simpleFilters);
        filterChains.add(simpleChainGET);
        SecurityFilterChain simpleChainPUT = new DefaultSecurityFilterChain(new AntPathRequestMatcher("/**", HttpMethod.PUT), simpleFilters);
        filterChains.add(simpleChainPUT);
        SecurityFilterChain simpleChainPOST = new DefaultSecurityFilterChain(new AntPathRequestMatcher("/**", HttpMethod.POST), simpleFilters);
        filterChains.add(simpleChainPOST);
        SecurityFilterChain simpleChainDELETE = new DefaultSecurityFilterChain(new AntPathRequestMatcher("/**", HttpMethod.DELETE), simpleFilters);
        filterChains.add(simpleChainDELETE);

        return filterChains;
    }
    
    @Bean
    public FilterSecurityInterceptor filterSecurityInterceptor() {
        // FilterSecurityInterceptor
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setAuthenticationManager(authMgr());
        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
        // SecurityExpressionHandler
        SecurityExpressionHandler<FilterInvocation> securityExpressionHandler = new DefaultWebSecurityExpressionHandler();

        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> map = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
        map.put(new AntPathRequestMatcher("/supersecret/**"), Arrays.<ConfigAttribute>asList(new SecurityConfig("hasRole('ROLE_SUPER')")));
        map.put(new AntPathRequestMatcher("/notsecret/**"), Arrays.<ConfigAttribute>asList(new SecurityConfig("permitAll")));
        map.put(new AntPathRequestMatcher("/kindasecret/**"), Arrays.<ConfigAttribute>asList(new SecurityConfig("hasRole('ROLE_META')")));
        ExpressionBasedFilterInvocationSecurityMetadataSource ms = new ExpressionBasedFilterInvocationSecurityMetadataSource(map, securityExpressionHandler);
        filterSecurityInterceptor.setSecurityMetadataSource(ms);
        try {
            filterSecurityInterceptor.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filterSecurityInterceptor;
    }
    
    @Bean
    public ExceptionTranslationFilter exceptionTranslationFilter() {
        ExceptionTranslationFilter exceptionTranslationFilter = new ExceptionTranslationFilter(oauthProcessingFilterEntryPoint());
        exceptionTranslationFilter.setAccessDeniedHandler(new AccessDeniedHandlerImpl());
        exceptionTranslationFilter.afterPropertiesSet();
        return exceptionTranslationFilter;
    }
    
    @Bean
    public AuthenticationManager authMgr() {
        List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();
        DaoAuthenticationProvider daoAP = new DaoAuthenticationProvider();
        daoAP.setUserDetailsService(customConsumerDetails());
        providers.add(daoAP);
        return new ProviderManager(providers);
    }
    
    @Bean 
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter> voters = Arrays.<AccessDecisionVoter>asList(new RoleVoter(), new WebExpressionVoter());
        return new AffirmativeBased(voters);
    }

    @Bean
    public FilterChainProxy springSecurityFilterChain() {
        FilterChainProxy chainProxy = new FilterChainProxy(filterChains());
        return chainProxy;
    }

    @Bean
    public OAuthProviderTokenServices tokenServices() {
        OAuthProviderTokenServices tokenServices = new InMemorySelfCleaningProviderTokenServices();
        return tokenServices;
    }

    @Bean
    public OAuthProcessingFilterEntryPoint oauthProcessingFilterEntryPoint() {
        OAuthProcessingFilterEntryPoint bean = new OAuthProcessingFilterEntryPoint();
        bean.setRealmName(env.getProperty("realm"));
        return bean;
    }

    @Bean
    public GSProtectedResourceProcessingFilter oauthFilter() {
        return new GSProtectedResourceProcessingFilter();
    }

    @Bean
    public CoreOAuthProviderSupport providerSupport() {
        CoreOAuthProviderSupport bean = new CoreOAuthProviderSupport();
        bean.setBaseUrl(env.getRequiredProperty("prop.service.host"));
        return bean;
    }

    @Bean
    public GSConsumerDetailsService customConsumerDetails() {
        return new GSConsumerDetailsService();
    }
}
