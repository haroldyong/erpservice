/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.platform.controller;

import com.huobanplus.erpservice.common.SysConstant;
import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.datacenter.entity.OrderOperatorLog;
import com.huobanplus.erpservice.datacenter.entity.OrderSync;
import com.huobanplus.erpservice.datacenter.searchbean.OrderPushSearch;
import com.huobanplus.erpservice.datacenter.searchbean.OrderSyncSearch;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.OrderOperatorService;
import com.huobanplus.erpservice.datacenter.service.OrderSyncService;
import com.huobanplus.erpservice.proxy.utils.OrderProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by allan on 4/13/16.
 */
@Controller
@RequestMapping("/erpService/platform")
public class OrderController {
    @Autowired
    private OrderOperatorService orderOperatorService;
    @Autowired
    private OrderSyncService orderSyncService;
    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderProxyService orderProxyService;

    @RequestMapping("/orderSyncs")
    private String OrderSyncs(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            OrderSyncSearch orderSyncSearch,
            @RequestAttribute int customerId,
            Model model
    ) {
        Page<OrderSync> orderSyncs = orderSyncService.findAll(pageIndex, SysConstant.DEFALUT_PAGE_SIZE, customerId, orderSyncSearch);
        model.addAttribute("orderSyncs", orderSyncs);
        model.addAttribute("orderSyncSearch", orderSyncSearch);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("pageSize", SysConstant.DEFALUT_PAGE_SIZE);
        return "order_sync_list";
    }

    @RequestMapping("/orderPushLogs")
    private String orderPushLogs(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            OrderPushSearch orderPushSearch,
            @RequestAttribute int customerId,
            @RequestParam(required = false, defaultValue = "-1") int resultStatus,
            Model model
    ) {
        orderPushSearch.setResultStatus(resultStatus);
        Page<OrderOperatorLog> orderPushLogs = orderOperatorService.findAll(pageIndex, 20, orderPushSearch, customerId);
        model.addAttribute("orderPushLogs", orderPushLogs);
        model.addAttribute("orderPushSearch", orderPushSearch);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("pageSize", 20);
        model.addAttribute("resultStatus", resultStatus);
        return "push_order_log";
    }

    @RequestMapping(value = "/rePushOrder", method = RequestMethod.POST)
    @ResponseBody
    private ApiResult rePushOrder(long id) {
//        OrderOperatorLog orderOperatorLog = orderOperatorService.findById(id);
//        ERPInfo erpInfo = JSON.parseObject(orderOperatorLog.getErpInfo(), ERPInfo.class);
//        return orderProxyService.pushOrder(orderOperatorLog.getOrderJsonData(), erpInfo);
        return null;
    }
}
