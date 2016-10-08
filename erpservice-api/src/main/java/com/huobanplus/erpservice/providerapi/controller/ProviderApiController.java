/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.providerapi.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liual on 2015-10-21.
 */
@RequestMapping("/providerApi")
public interface ProviderApiController {
    @RequestMapping(value = "/rest/{erpProviderType}/{erpUserType}", method = RequestMethod.POST)
    @ResponseBody
    void index(
            @PathVariable("erpProviderType") int providerType,
            @PathVariable("erpUserType") int erpUserType,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException;

}
