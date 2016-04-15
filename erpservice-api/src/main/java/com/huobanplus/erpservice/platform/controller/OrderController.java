/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.platform.controller;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.datacenter.entity.OrderOperatorLog;
import com.huobanplus.erpservice.datacenter.searchbean.OrderPushSearch;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.OrderPushLogService;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
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
    private OrderPushLogService orderPushLogService;
    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderProxyService orderProxyService;

    @RequestMapping("/orderPushLogs")
    private String orderPushLogs(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            OrderPushSearch orderPushSearch,
            @RequestAttribute int customerId,
            @RequestParam(required = false, defaultValue = "-1") int resultStatus,
            Model model
    ) {
        orderPushSearch.setResultStatus(resultStatus);
        Page<OrderOperatorLog> orderPushLogs = orderPushLogService.findAll(pageIndex, 20, orderPushSearch, customerId);
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
        OrderOperatorLog orderOperatorLog = orderPushLogService.findById(id);
        ERPInfo erpInfo = JSON.parseObject(orderOperatorLog.getErpInfo(), ERPInfo.class);
        return orderProxyService.pushOrder(orderOperatorLog.getOrderJsonData(), erpInfo);
    }
}
