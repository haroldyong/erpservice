/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.providerapi.controller;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liual on 2015-10-21.
 */
@RequestMapping("/providerApi")
public interface ProviderApiController {

    /**
     * 获取订单列表
     * @param providerType
     * @param erpUserType
     * @param request
     * @return
     */
    @RequestMapping(value = "/rest/{erpProviderType}/{erpUserType}/obtainOrders", method = RequestMethod.POST)
    String obtainOrderInfos(
            @PathVariable("erpProviderType") int providerType,
            @PathVariable("erpUserType") int erpUserType,
            HttpServletRequest request);

    /**
     * 获取订单详情
     * @param providerType
     * @param erpUserType
     * @param request
     * @return
     */
    @RequestMapping(value = "/rest/{erpProviderType}/{erpUserType}/obtainOrder", method = RequestMethod.POST)
    String obtainOrderInfo(@PathVariable("erpProviderType") int providerType,
                           @PathVariable("erpUserType") int erpUserType,
                           HttpServletRequest request);
}
