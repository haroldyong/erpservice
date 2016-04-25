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
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderShipSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.ShipSyncFailureOrder;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.searchbean.OrderDetailSyncSearch;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.datacenter.service.logs.OrderShipSyncLogService;
import com.huobanplus.erpservice.datacenter.service.logs.ShipSyncFailureOrderService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushDeliveryInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
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
public class OrderLogController {
    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;
    @Autowired
    private OrderProxyService orderProxyService;
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ShipSyncFailureOrderService shipSyncFailureOrderService;
    @Autowired
    private ERPRegister erpRegister;


    @RequestMapping(value = "/orderDetailSyncs", method = RequestMethod.GET)
    private String orderDetailSyncs(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            OrderDetailSyncSearch orderDetailSyncSearch,
            @RequestAttribute int customerId,
            int erpUserType,
            @RequestParam(required = false, defaultValue = "-1") int syncStatus,
            Model model
    ) {
        orderDetailSyncSearch.setSyncStatus(syncStatus);
        Page<OrderDetailSyncLog> orderDetailSyncLogs = orderDetailSyncLogService.findAll(pageIndex, SysConstant.DEFALUT_PAGE_SIZE, customerId, orderDetailSyncSearch);
        model.addAttribute("orderDetailSyncLogs", orderDetailSyncLogs);
        model.addAttribute("orderDetailSyncSearch", orderDetailSyncSearch);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("pageSize", SysConstant.DEFALUT_PAGE_SIZE);
        model.addAttribute("erpUserType", erpUserType);
        return "logs/order_detail_sync_list";
    }

    @RequestMapping(value = "/orderShipSyncs", method = RequestMethod.GET)
    private String orderShipSyncs(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            String beginTime, String endTime,
            @RequestAttribute int customerId,
            int erpUserType,
            Model model
    ) {
        Page<OrderShipSyncLog> orderShipSyncLogs = orderShipSyncLogService.findAll(pageIndex, SysConstant.DEFALUT_PAGE_SIZE, beginTime, endTime, customerId);
        model.addAttribute("orderShipSyncLogs", orderShipSyncLogs);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("pageSize", SysConstant.DEFALUT_PAGE_SIZE);
        model.addAttribute("erpUserType", erpUserType);
        model.addAttribute("beginTime", beginTime);
        model.addAttribute("endTime", endTime);

        return "logs/order_ship_sync_list";
    }

    @RequestMapping(value = "/shipSyncFailureOrders", method = RequestMethod.GET)
    private String shipSyncFailureOrders(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            String orderId, long shipSyncId,
            int erpUserType,
            Model model
    ) {
        Page<ShipSyncFailureOrder> shipSyncFailureOrders = shipSyncFailureOrderService.findAll(pageIndex, SysConstant.DEFALUT_PAGE_SIZE, shipSyncId, orderId);
        model.addAttribute("shipSyncFailureOrders", shipSyncFailureOrders);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("pageSize", SysConstant.DEFALUT_PAGE_SIZE);
        model.addAttribute("erpUserType", erpUserType);
        model.addAttribute("orderId", orderId);
        model.addAttribute("shipSyncId", shipSyncId);

        return "logs/sync_failure_order_list";
    }

//    @RequestMapping("/orderSyncs")
//    private String OrderSyncs(
//            @RequestParam(required = false, defaultValue = "1") int pageIndex,
//            OrderSyncSearch orderSyncSearch,
//            @RequestAttribute int customerId,
//            Model model
//    ) {
//        Page<OrderSync> orderSyncs = orderSyncService.findAll(pageIndex, SysConstant.DEFALUT_PAGE_SIZE, customerId, orderSyncSearch);
//        model.addAttribute("orderSyncs", orderSyncs);
//        model.addAttribute("orderSyncSearch", orderSyncSearch);
//        model.addAttribute("pageIndex", pageIndex);
//        model.addAttribute("pageSize", SysConstant.DEFALUT_PAGE_SIZE);
//        return "order_sync_list";
//    }
//
//    @RequestMapping("/orderOperatorLogs")
//    private String orderOperatorLogs(
//            String orderId,
//            Model model
//    ) {
//        List<OrderOperatorLog> orderOperatorLogs = orderOperatorService.findByOrderId(orderId);
//        model.addAttribute("orderOperatorLogs", orderOperatorLogs);
//        return "push_order_log";
//    }

    @RequestMapping(value = "/rePushOrder", method = RequestMethod.POST)
    @ResponseBody
    private ApiResult rePushOrder(long id) {
        OrderDetailSyncLog orderDetailSyncLog = orderDetailSyncLogService.findById(id);
        ERPInfo erpInfo = new ERPInfo(orderDetailSyncLog.getProviderType(), orderDetailSyncLog.getErpSysData());
        ERPUserInfo userInfo = new ERPUserInfo(orderDetailSyncLog.getUserType(), orderDetailSyncLog.getCustomerId());
        PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
        pushNewOrderEvent.setErpInfo(erpInfo);
        pushNewOrderEvent.setErpUserInfo(userInfo);
        pushNewOrderEvent.setOrderInfoJson(orderDetailSyncLog.getOrderInfoJson());

        return orderProxyService.pushOrder(pushNewOrderEvent);
    }

    @RequestMapping(value = "/syncOrderShip", method = RequestMethod.POST)
    private ApiResult syncOrderShip(long id) {
        ShipSyncFailureOrder shipSyncFailureOrder = shipSyncFailureOrderService.findById(id);
        ERPUserInfo erpUserInfo = new ERPUserInfo(shipSyncFailureOrder.getOrderShipSyncLog().getUserType(), shipSyncFailureOrder.getOrderShipSyncLog().getCustomerId());
        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        PushDeliveryInfoEvent pushDeliveryInfoEvent = new PushDeliveryInfoEvent();
        pushDeliveryInfoEvent.setErpUserInfo(erpUserInfo);
        OrderDeliveryInfo orderDeliveryInfo = new OrderDeliveryInfo();
        orderDeliveryInfo.setOrderId(shipSyncFailureOrder.getOrderId());
        orderDeliveryInfo.setLogiName(shipSyncFailureOrder.getLogiName());
        orderDeliveryInfo.setLogiNo(shipSyncFailureOrder.getLogiNo());
        pushDeliveryInfoEvent.setDeliveryInfo(orderDeliveryInfo);
        EventResult eventResult = erpUserHandler.handleEvent(pushDeliveryInfoEvent);

        return null;
    }
}
