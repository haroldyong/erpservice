/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.controller.impl;

import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.model.OrderRefundStatusInfo;
import com.huobanplus.erpservice.datacenter.model.OrderRemarkUpdateInfo;
import com.huobanplus.erpservice.eventhandler.erpevent.push.*;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.proxy.common.ProxyBaseController;
import com.huobanplus.erpservice.proxy.controller.OrderProxyController;
import com.huobanplus.erpservice.proxy.utils.OrderProxyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liual on 2015-10-19.
 */
@Controller
@RequestMapping(value = "/hotProxy/order", method = RequestMethod.POST)
public class OrderProxyControllerImpl extends ProxyBaseController implements OrderProxyController {
    private static final Log log = LogFactory.getLog(OrderProxyControllerImpl.class);

    @Autowired
    private OrderProxyService orderProxyService;

    @Override
    @RequestMapping(value = "/createOrder")
    @ResponseBody
    public ApiResult createOrder(
            String orderInfoJson,
            @RequestAttribute ERPInfo erpInfo,
            @RequestAttribute ERPUserInfo erpUserInfo
    ) throws Exception {
        PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
        pushNewOrderEvent.setErpInfo(erpInfo);
        pushNewOrderEvent.setOrderInfoJson(orderInfoJson);
        pushNewOrderEvent.setErpUserInfo(erpUserInfo);

        return orderProxyService.handleEvent(pushNewOrderEvent);
    }

    @Override
    @RequestMapping("/updateOrder")
    @ResponseBody
    public ApiResult updateOrder(String orderInfoJson, @RequestAttribute ERPInfo erpInfo) throws Exception {
        return null;
    }

    @Override
    @RequestMapping("/orderDeliver")
    @ResponseBody
    public ApiResult orderDeliver(
            @RequestAttribute ERPInfo erpInfo,
            @RequestAttribute ERPUserInfo erpUserInfo,
            OrderDeliveryInfo deliveryInfo
    ) throws Exception {
        PushDeliveryInfoEvent pushDeliveryInfoEvent = new PushDeliveryInfoEvent();
        pushDeliveryInfoEvent.setErpUserInfo(erpUserInfo);
        pushDeliveryInfoEvent.setErpInfo(erpInfo);
        pushDeliveryInfoEvent.setDeliveryInfo(deliveryInfo);

        return orderProxyService.handleEvent(pushDeliveryInfoEvent);
    }

    @Override
    @RequestMapping("/getOrderDetail")
    @ResponseBody
    public ApiResult getOrderDetail(String orderId, @RequestAttribute ERPInfo erpInfo) throws Exception {
        return null;
    }

    @Override
    @RequestMapping("/orderRefundStatus")
    @ResponseBody
    public ApiResult orderRefundStatus(@RequestAttribute ERPInfo erpInfo, @RequestAttribute ERPUserInfo erpUserInfo, OrderRefundStatusInfo orderRefundStatusInfo) {
        OrderRefundStatusUpdate orderRefundStatusUpdate = new OrderRefundStatusUpdate();
        orderRefundStatusUpdate.setErpInfo(erpInfo);
        orderRefundStatusUpdate.setErpUserInfo(erpUserInfo);
        orderRefundStatusUpdate.setOrderRefundStatusInfo(orderRefundStatusInfo);

        return orderProxyService.handleEvent(orderRefundStatusUpdate);
    }

    @Override
    @RequestMapping("/orderRemarkUpdate")
    @ResponseBody
    public ApiResult orderRemarkUpdate(@RequestAttribute ERPInfo erpInfo, @RequestAttribute ERPUserInfo erpUserInfo, OrderRemarkUpdateInfo orderRemarkUpdateInfo) {
        OrderRemarkUpdate orderRemarkUpdate = new OrderRemarkUpdate();
        orderRemarkUpdate.setErpInfo(erpInfo);
        orderRemarkUpdate.setErpUserInfo(erpUserInfo);
        orderRemarkUpdate.setOrderRemarkUpdateInfo(orderRemarkUpdateInfo);

        return orderProxyService.handleEvent(orderRemarkUpdate);
    }

    @Override
    @RequestMapping("/cancelOrder")
    @ResponseBody
    public ApiResult cancelOrder(@RequestAttribute ERPInfo erpInfo, @RequestAttribute ERPUserInfo erpUserInfo, String orderId) {
        CancelOrderEvent cancelOrderEvent = new CancelOrderEvent();
        cancelOrderEvent.setErpInfo(erpInfo);
        cancelOrderEvent.setErpUserInfo(erpUserInfo);
        cancelOrderEvent.setOrderId(orderId);
        return orderProxyService.handleEvent(cancelOrderEvent);
    }

    @Override
    @RequestMapping("/returnRefund")
    @ResponseBody
    public ApiResult returnRefund(@RequestAttribute ERPInfo erpInfo, @RequestAttribute ERPUserInfo erpUserInfo, String afterSaleJson) {

        log.info("AfterSaleJson:" + afterSaleJson);
        PushAfterSaleEvent pushAfterSaleEvent = new PushAfterSaleEvent();
        pushAfterSaleEvent.setErpInfo(erpInfo);
        pushAfterSaleEvent.setErpUserInfo(erpUserInfo);
        pushAfterSaleEvent.setAfterSaleInfo(afterSaleJson);
        return orderProxyService.handleEvent(pushAfterSaleEvent);
    }


    @Override
    @RequestMapping("/pushRemark")
    @ResponseBody
    public ApiResult pushRemark(@RequestAttribute ERPInfo erpInfo, @RequestAttribute ERPUserInfo erpUserInfo, String orderId, String remark) {

        log.info("orderId:" + orderId);
        log.info("remark:" + remark);
        PushRemarkEvent pushRemarkEvent = new PushRemarkEvent();
        pushRemarkEvent.setErpInfo(erpInfo);
        pushRemarkEvent.setErpUserInfo(erpUserInfo);
        pushRemarkEvent.setOrderId(orderId);
        pushRemarkEvent.setRemark(remark);
        return orderProxyService.handleEvent(pushRemarkEvent);
    }
}
