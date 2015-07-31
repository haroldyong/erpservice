package com.huobanplus.erpservice.htcomponent.controller.impl;

import com.huobanplus.erpservice.event.erpevent.CreateOrderEvent;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPRegister;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.htcomponent.controller.HotApiController;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by allan on 2015/7/31.
 */
@Controller
public class HotApiControllerImpl implements HotApiController {
    @Autowired
    private ERPRegister erpRegister;

    @Override
    public void createOrder(@PathVariable("erpInfo") String info, HttpServletRequest request) {

        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(info);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if(erpHandler.eventSupported(CreateOrderEvent.class))
        {

        }
        else
        {

        }
    }

    @Override
    public void getProInventory(@PathVariable("erpInfo") String info, HttpServletRequest request) throws IOException, IllegalAccessException {

    }

    @Override
    public void getOrderInfo(@PathVariable("erpInfo") String info, HttpServletRequest request) {

    }
}
