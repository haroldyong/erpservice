/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.controller.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpservice.common.util.ClassUtil;
import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.datacenter.entity.MallOrderBean;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.CancelOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.ObtainOrderDetailEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.OrderDeliverEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.OrderUpdateEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.OrderDeliverInfo;
import com.huobanplus.erpservice.proxy.common.ProxyBaseController;
import com.huobanplus.erpservice.proxy.controller.OrderProxyController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by liual on 2015-10-19.
 */
@Controller
@RequestMapping(value = "/hotProxy/order", method = RequestMethod.POST)
public class OrderProxyControllerImpl extends ProxyBaseController implements OrderProxyController {
    @Autowired
    private ERPRegister erpRegister;
    @Autowired
    private MallOrderService orderService;

    @Override
    @RequestMapping(value = "/createOrder")
    @ResponseBody
    public ApiResult createOrder(String orderInfoJson, @RequestAttribute ERPInfo erpInfo) throws Exception {

        //如果开通了erp，交由erp处理器推送到指定erp
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        if (erpHandler.eventSupported(PushNewOrderEvent.class)) {
            Order order = JSON.parseObject(orderInfoJson, Order.class);
            PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
            pushNewOrderEvent.setErpInfo(erpInfo);
            pushNewOrderEvent.setOrderInfo(order);
            EventResult eventResult = erpHandler.handleEvent(pushNewOrderEvent);
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                return ApiResult.resultWith(ResultCode.SUCCESS);
            } else {
                //如果未推送成功，则保存数据到erp数据服务平台，交由相关处理器处理
                MallOrderBean orderBean = new MallOrderBean();

                ClassUtil.cloneClass(order, orderBean);
                orderBean.setErpInfo(JSON.toJSONString(erpInfo));
                orderService.save(orderBean);
                return ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST, "推送给erp时失败，将交给相关处理进行第二次尝试", null);
            }
        } else {
            return ApiResult.resultWith(ResultCode.EVENT_NOT_SUPPORT);
        }
    }

    @Override
    @RequestMapping("/updateOrder")
    @ResponseBody
    public ApiResult updateOrder(String orderInfoJson, @RequestAttribute ERPInfo erpInfo) throws Exception {

        //如果开通了erp，交由erp处理器推送到指定erp
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        if (erpHandler.eventSupported(OrderUpdateEvent.class)) {
            MallOrderBean orderInfo = new ObjectMapper().readValue(orderInfoJson, MallOrderBean.class);
            OrderUpdateEvent orderUpdateEvent = new OrderUpdateEvent();
            orderUpdateEvent.setErpInfo(erpInfo);
            orderUpdateEvent.setOrderInfo(orderInfo);
            EventResult eventResult = erpHandler.handleEvent(orderUpdateEvent);
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                return ApiResult.resultWith(ResultCode.SUCCESS);
            } else {
                orderInfo.setErpInfo(JSON.toJSONString(erpInfo));
                //如果不成功，保存本地数据，由相关处理器处理
                MallOrderBean preOrder = orderService.findByOrderId(orderInfo.getOrderId());
                if (preOrder == null) {
                    orderService.save(orderInfo);
                } else {
                    preOrder.setLogiName(orderInfo.getLogiName());
                    preOrder.setLogiNo(orderInfo.getLogiNo());
//                    preOrder.setDeliverTime(orderInfo.getDeliverTime());
                    preOrder.setWeight(orderInfo.getWeight());
                    orderService.save(orderInfo);
                }
                return ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST, "推送给erp时失败，将交给相关处理进行第二次尝试", null);
            }
        } else {
            return ApiResult.resultWith(ResultCode.EVENT_NOT_SUPPORT);
        }
    }

    @Override
    @RequestMapping("/orderDeliver")
    @ResponseBody
    public ApiResult orderDeliver(
            String orderId,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date deliverTime,
            String logiName,
            String logiNo,
            String weight,
            @RequestAttribute ERPInfo erpInfo
    ) throws Exception {
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        if (erpHandler.eventSupported(OrderDeliverEvent.class)) {
            OrderDeliverInfo orderDeliverInfo = new OrderDeliverInfo();
            orderDeliverInfo.setOrderId(orderId);
            orderDeliverInfo.setLogiName(logiName);
            orderDeliverInfo.setLogiNo(logiNo);
            orderDeliverInfo.setDeliverTime(deliverTime);
            orderDeliverInfo.setWeight(weight);
            OrderDeliverEvent orderDeliverEvent = new OrderDeliverEvent();
            orderDeliverEvent.setErpInfo(erpInfo);
            orderDeliverEvent.setOrderDeliverInfo(orderDeliverInfo);
            EventResult eventResult = erpHandler.handleEvent(orderDeliverEvent);
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                return ApiResult.resultWith(ResultCode.SUCCESS);
            } else {
                return ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST, eventResult.getResultMsg(), null);
            }
        } else {
            return ApiResult.resultWith(ResultCode.EVENT_NOT_SUPPORT);
        }
    }

    @Override
    @RequestMapping("/cancelOrder")
    @ResponseBody
    public ApiResult cancelOrder(String orderId, @RequestAttribute ERPInfo erpInfo) throws Exception {
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        if (erpHandler.eventSupported(CancelOrderEvent.class)) {
            CancelOrderEvent cancelOrderEvent = new CancelOrderEvent();
            cancelOrderEvent.setOrderId(orderId);
            cancelOrderEvent.setErpInfo(erpInfo);
            EventResult eventResult = erpHandler.handleEvent(cancelOrderEvent);
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                return ApiResult.resultWith(ResultCode.SUCCESS);
            } else {
                return ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST, eventResult.getResultMsg(), null);
            }
        } else {
            return ApiResult.resultWith(ResultCode.EVENT_NOT_SUPPORT);
        }
    }

    @Override
    @RequestMapping("/getOrderDetail")
    public ApiResult getOrderDetail(String orderId, @RequestAttribute ERPInfo erpInfo) throws Exception {
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        if (erpHandler.eventSupported(ObtainOrderDetailEvent.class)) {
            ObtainOrderDetailEvent obtainOrderDetailEvent = new ObtainOrderDetailEvent();
            obtainOrderDetailEvent.setOrderId(orderId);
            obtainOrderDetailEvent.setErpInfo(erpInfo);
            EventResult eventResult = erpHandler.handleEvent(obtainOrderDetailEvent);
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                return ApiResult.resultWith(ResultCode.SUCCESS, eventResult.getData());
            } else {
                return ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST, eventResult.getResultMsg(), null);
            }
        } else {
            return ApiResult.resultWith(ResultCode.EVENT_NOT_SUPPORT);
        }
    }
}
