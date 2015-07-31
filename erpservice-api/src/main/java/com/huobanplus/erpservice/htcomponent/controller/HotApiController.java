package com.huobanplus.erpservice.htcomponent.controller;

import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPRegister;
import com.huobanplus.erpservice.event.model.ERPInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by allan on 2015/7/31.
 */
@Controller
public class HotApiController {
    @Autowired
    private ERPRegister erpRegister;

    @RequestMapping("/createOrder/{erpInfo}")
    public void createOrder(@PathVariable("erpInfo") String info, HttpServletRequest request) {
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(info);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
    }
}
