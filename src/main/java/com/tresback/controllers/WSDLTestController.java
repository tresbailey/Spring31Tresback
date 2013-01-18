/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.controllers;

import com.invoker.ServiceDetails;
import com.invoker.ServiceInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This is ONLY a test client existing for a short time.  
 * It is used to interact with the ServiceInvoker tool to test calling specific 
 * downstream endpoints.
 * @author rbailey
 */
@Controller
public class WSDLTestController {
    
    @Autowired
    ServiceInvoker caller;
    
    class SimpleClass {
        String val1;
        String val2;

        public String getVal1() {
            return val1;
        }

        public void setVal1( String val1 ) {
            this.val1 = val1;
        }

        public String getVal2() {
            return val2;
        }

        public void setVal2( String val2 ) {
            this.val2 = val2;
        }
        
    }
    
    @RequestMapping("/testWsdl/{client}/{service}/{action}/{mainGUID}")
    public @ResponseBody Object testWsdl(@PathVariable String client,
            @PathVariable String service,
            @PathVariable String action,
            @PathVariable String mainGUID
            ) throws Exception {
//        QuoteCensusAdapter adapter = new QuoteCensusAdapter();
//        adapter.setMarketSegment("B49C652AADA072CDE040C80A49114324");
//        adapter.setMarketSegment("203771702995922");
////        adapter.setCarrierName("5DE53F0C768A393DE040640A6E11267C");
//        adapter.setCarrierName("BCNEPA");
        SimpleClass simple = new SimpleClass();
        simple.setVal1( "Value 1");
        simple.setVal2(mainGUID);
        caller.invokeServiceSet( simple, client, service, action );
        return null;
    }
}
