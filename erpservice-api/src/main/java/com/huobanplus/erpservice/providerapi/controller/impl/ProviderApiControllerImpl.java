/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.providerapi.controller.impl;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.providerapi.controller.ProviderApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liual on 2015-10-21.
 */
@Controller
@RequestMapping("/providerApi")
public class ProviderApiControllerImpl implements ProviderApiController {
    @Autowired
    private ERPRegister erpRegister;

    @Override
    @RequestMapping(value = "/{erpName}/obtainOrderInfo", method = RequestMethod.POST)
    public String obtainOrderInfo(@PathVariable("erpName") String erpName, HttpServletRequest request) {
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setErpName(erpName);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            return "未找到相关erp处理器";
        }
        EventResult eventResult = erpHandler.handleRequest(request);
        return eventResult.getData().toString();
    }
}