/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.utils;

import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.datacenter.service.OrderOperatorService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by allan on 4/13/16.
 */
@Component
public class OrderProxyService {
    @Autowired
    private ERPRegister erpRegister;
    @Autowired
    private OrderOperatorService orderOperatorService;


    public ApiResult pushOrder(Order order, ERPInfo erpInfo) {
        return null;
    }
}
