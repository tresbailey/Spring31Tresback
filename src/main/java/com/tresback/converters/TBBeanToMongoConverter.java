package com.tresback.converters;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.tresback.mongos.TBBean;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author rbailey
 */
public class TBBeanToMongoConverter implements Converter<TBBean, DBObject > {

    @Override
    public DBObject convert(TBBean s) {
        DBObject dbo = new BasicDBObject();
        dbo.put("implementationClass", s.getImplementationClass().getCanonicalName());
        dbo.put("_class", s.getClass().getCanonicalName());
        dbo.put("beanName", s.getBeanName());
        dbo.put("componentType", s.getComponentType().name());
        dbo.put("active", s.isActive());
        dbo.put("_id", s.getBeanName());
        return dbo;
    }
    
}
