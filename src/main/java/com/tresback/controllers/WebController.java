package com.tresback.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author rbailey
 */
@Controller
public class WebController {
    
    @RequestMapping("/supersecret")
    public String getSecret() {
        return "Something protected";
    }
    
    @RequestMapping("/kindasecret")
    public String getKinda() {
        return "Something accessible";
    }
    
    @RequestMapping("/notsecret")
    public String getFree() {
        return "Not protected";
    }
    
}
