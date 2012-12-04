package com.tresback.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author rbailey
 */
@Controller
public class WebController {
    
    class ResponseObj {
        String message;
        public ResponseObj(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    }
    
    @RequestMapping("/supersecret")
    public @ResponseBody ResponseObj getSecret() {
        return new ResponseObj("Something protected");
    }
    
    @RequestMapping("/kindasecret")
    public @ResponseBody ResponseObj getKinda() {
        return new ResponseObj("Something accessible");
    }
    
    @RequestMapping("/notsecret")
    public @ResponseBody ResponseObj getUserFree() {
        return new ResponseObj("Not role protected");
    }
    
    @RequestMapping("/totallyFree")
    public @ResponseBody ResponseObj getFree() {
        return new ResponseObj("Not protected");
    }
    
}
