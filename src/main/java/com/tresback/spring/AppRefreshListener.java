package com.tresback.spring;

import com.tresback.mongos.TBBean;
import com.tresback.services.TBBeanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author rbailey
 */
@Component
public class AppRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired(required=true)
    TBBeanService tBBeanService;    
    
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
        AutowireCapableBeanFactory registry = e.getApplicationContext().getAutowireCapableBeanFactory();
        log.info("Got a context refresh event: "+ tBBeanService);
        if ( tBBeanService != null ) {
            log.debug("Calling all beans!!!");
            for( TBBean bean : tBBeanService.getAllGSBeans() ) {
                if ( !bean.isActive() && ((DefaultListableBeanFactory) registry).containsBean(bean.getBeanName()) ) {
                    log.info("Removing inactive bean: "+ bean.getBeanName());
                    ((DefaultListableBeanFactory) registry).removeBeanDefinition(bean.getBeanName());
                } else if ( bean.isActive() && !((DefaultListableBeanFactory) registry).containsBean(bean.getBeanName()) ) {
                    log.info("Re-implementing inactive bean: "+ bean.getBeanName());
                    Object beanImpl = null;
                    try {
                        beanImpl = bean.getImplementationClass().newInstance();
                    } catch( Exception ie ) {
                        log.error("Couldn't instantiate bean object: "+ bean.getBeanName());
                        ie.printStackTrace();
                    }
                    ((DefaultListableBeanFactory) registry).registerSingleton(bean.getBeanName(), beanImpl);
                } else if( bean.isActive() && 
                        ((DefaultListableBeanFactory) registry).containsBean(bean.getBeanName()) &&
                        ((DefaultListableBeanFactory) registry).getBean(bean.getBeanName()).getClass().getSuperclass()  != bean.getImplementationClass() ) {
                    log.info("Reconfiguring existing bean to use a different bean implementation");
                    Object beanImpl = null;
                    try {
                        beanImpl = bean.getImplementationClass().newInstance();
                    } catch( Exception ie ) {
                        log.error("Couldn't instantiate bean object: "+ bean.getBeanName());
                        ie.printStackTrace();
                    }
                    ((DefaultListableBeanFactory) registry).removeBeanDefinition(bean.getBeanName());
                    ((DefaultListableBeanFactory) registry).registerSingleton(bean.getBeanName(), beanImpl);
                    
                }
            }
        }
    }
    
}
