package com.tresback.converters;

import com.mongodb.DBObject;
import com.tresback.mongos.TBBean;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author rbailey
 */
public class MongoToTBBeanConverter implements Converter<DBObject, TBBean> {

    @Override
    public TBBean convert(DBObject s) {
        TBBean bean = new TBBean();
        bean.setActive((Boolean)s.get("active"));
        bean.setBeanName((String) s.get("beanName"));
        try {
            bean.setImplementationClass(Class.forName((String) s.get("implementationClass")));
        } catch( ClassNotFoundException cnfe ) {
            cnfe.printStackTrace();
        }
        bean.setComponentType(TBBean.Components.valueOf((String) s.get("componentType")));
        return bean;
    }
    
}
