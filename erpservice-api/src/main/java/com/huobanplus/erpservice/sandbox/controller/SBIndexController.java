package com.huobanplus.erpservice.sandbox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liuqucheng on 12/10/15.
 */
@RequestMapping("/erpService/sandbox")
@Controller
public class SBIndexController {

    @RequestMapping({"", "/", "/instruction"})
    public String instruction() {

        return "sandbox/instruction";
    }

    @RequestMapping("/index")
    public String index(){
        return "sandbox/index";
    }
}
