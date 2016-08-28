/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.providerapi.controller.impl;

import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by liual on 2015-10-21.
 */
@Controller
@RequestMapping("/providerApi")
public class ProviderApiControllerImpl implements ProviderApiController {
    @Autowired
    private ERPRegister erpRegister;

    @RequestMapping(value = "/rest/{erpProviderType}/{erpUserType}", method = RequestMethod.POST)
    @ResponseBody
    public String index(
            @PathVariable("erpProviderType") int providerType,
            @PathVariable("erpUserType") int erpUserType,
            HttpServletRequest request) {
        ERPTypeEnum.ProviderType providerTypeEnum = EnumHelper.getEnumType(ERPTypeEnum.ProviderType.class, providerType);
        ERPTypeEnum.UserType userTypeEnum = EnumHelper.getEnumType(ERPTypeEnum.UserType.class, erpUserType);
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setErpType(providerTypeEnum);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            return "未找到相关erp处理器";
        }

        EventResult eventResult = erpHandler.handleRequest(request, providerTypeEnum, userTypeEnum);
        return eventResult.getData().toString();
    }

    @RequestMapping(value = "/rest/{erpProviderType}/{erpUserType}/{backType}", method = RequestMethod.POST)
    @ResponseBody
    public void index(
            @PathVariable("erpProviderType") int providerType,
            @PathVariable("erpUserType") int erpUserType,
            @PathVariable("backType") int backType,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        ERPTypeEnum.ProviderType providerTypeEnum = EnumHelper.getEnumType(ERPTypeEnum.ProviderType.class, providerType);
        ERPTypeEnum.UserType userTypeEnum = EnumHelper.getEnumType(ERPTypeEnum.UserType.class, erpUserType);
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setErpType(providerTypeEnum);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();

        if (erpHandler == null) {
            writer.write("未找到相关erp处理器");
        }
        request.setAttribute("backType", backType);
        EventResult eventResult = erpHandler.handleRequest(request, providerTypeEnum, userTypeEnum);
        String resultData = (String) eventResult.getData();
        writer.write(resultData);
//        return new String(resultData.getBytes("utf-8"),"utf-8");
    }
}
