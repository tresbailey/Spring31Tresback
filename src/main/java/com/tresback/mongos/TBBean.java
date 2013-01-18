/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.mongos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author rbailey
 */
@Document
public class TBBean {
    
    Components componentType;
    @Id
    String beanName;
    boolean active;
    Class implementationClass;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Components getComponentType() {
        return componentType;
    }

    public void setComponentType(Components componentType) {
        this.componentType = componentType;
    }

    public Class getImplementationClass() {
        return implementationClass;
    }

    public void setImplementationClass(Class implementationClass) {
        this.implementationClass = implementationClass;
    }
    
    public static enum Components {
        CONTROLLER,
        SERVICE,
        REPOSITORY,
        COMPONENT,
        CONFIGURATION
    };
    
}
