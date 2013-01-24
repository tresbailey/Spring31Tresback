/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.mongos;

import org.springframework.data.annotation.Id;

/**
 *
 * @author rbailey
 */
public class ApplicationStaticProperty {
    
    @Id
    String key;
    String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "key: "+ this.key +", value: "+ this.value;
    }

}
