package com.tresback.controllers;

import com.tresback.mongos.TBBean;
import com.tresback.services.TBBeanService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Beans that can be reset use different DI'ed objects
 * @author rbailey
 */
@Controller
public class TBBeanController {
    
    @Autowired
    TBBeanService beanService;
    
    @RequestMapping( value="/beans", method=RequestMethod.GET )
    public @ResponseBody List<TBBean> getAllBeans() {
        return beanService.getAllGSBeans();
    }
    
    @RequestMapping( value="/beans/{beanName}", method=RequestMethod.GET )
    public @ResponseBody TBBean getOneBean( @PathVariable String beanName ) {
        return beanService.getOneGSBean(beanName);
    }
    
    @RequestMapping( value="/beans/{beanName}", method=RequestMethod.PUT )
    public @ResponseBody TBBean saveBean( @PathVariable String beanName,
                                                        @RequestBody TBBean bean ) {
        return beanService.saveGSBean(beanName, bean);
    }
    
}
