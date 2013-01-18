package com.tresback.services;

import com.tresback.mongos.TBBean;
import com.tresback.repos.TBBeanRepository;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author rbailey
 */
@Service
public class TBBeanService implements ApplicationContextAware {
    
    ApplicationContext appCtx;
    
    @Autowired
    TBBeanRepository beanRepo;
    
    public List<TBBean> getAllGSBeans() {
        List<TBBean> allBeans = beanRepo.findAll();
        return allBeans;
    }
    
    public TBBean getOneGSBean( String beanName ) {
        TBBean bean = beanRepo.findOne(beanName);
        return bean;
    }
    
    public TBBean saveGSBean( String beanName, TBBean bean ) {
        bean.setBeanName(beanName);
        beanRepo.save(bean);
        // Saved the updated bean repo, now call refresh on context so that the 
        // Configuration class can reset its bean registry
        ((ConfigurableApplicationContext) this.appCtx).refresh();
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.appCtx = ac;
    }
    
    
}
