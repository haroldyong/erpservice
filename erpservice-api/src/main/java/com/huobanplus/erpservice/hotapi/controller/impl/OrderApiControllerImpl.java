/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.hotapi.controller.impl;

import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.*;
import com.huobanplus.erpservice.eventhandler.model.*;
import com.huobanplus.erpservice.hotapi.common.ERPApiBaseController;
import com.huobanplus.erpservice.hotapi.controller.OrderApiController;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Created by liual on 2015-10-17.
 */
@Controller
@RequestMapping("/hotApi/order")
public class OrderApiControllerImpl extends ERPApiBaseController implements OrderApiController {
    @Autowired
    private ERPRegister erpRegister;

    @Override
    @RequestMapping("/deliverInfo")
    @ResponseBody
    public ApiResult deliverInfo(
            String orderId,
            String logiName,
            String logiNo,
            @RequestParam(required = false, defaultValue = "0") int freight,
            @RequestParam(required = false) String remark,
            @RequestParam(required = false) String dicDeliverItemsStr,
            @RequestAttribute int customerId,
            @RequestAttribute ERPTypeEnum.UserType erpUserType
    ) throws IOException {

        //得到使用者事件处理器
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setErpUserType(erpUserType);
        erpUserInfo.setCustomerId(customerId);
        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (erpUserHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        DeliveryInfoEvent deliveryInfoEvent = new DeliveryInfoEvent();
        DeliveryInfo deliveryInfo = new DeliveryInfo();
        deliveryInfo.setOrderId(orderId);
        deliveryInfo.setLogiName(logiName);
        deliveryInfo.setLogiNo(logiNo);
        deliveryInfo.setFreight(freight);
        deliveryInfo.setRemark(remark);
        deliveryInfo.setDicDeliverItemsStr(dicDeliverItemsStr);
        deliveryInfoEvent.setDeliveryInfo(deliveryInfo);
        EventResult eventResult = erpUserHandler.handleEvent(deliveryInfoEvent);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            return ApiResult.resultWith(ResultCode.SUCCESS, eventResult.getData());
        } else {
            return ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST, eventResult.getResultMsg(), null);
        }
    }

    @Override
    @RequestMapping("/returnInfo")
    @ResponseBody
    public ApiResult returnInfo(
            String orderId,
            String logiName,
            String logiNo,
            String returnAddr,
            String returnMobile,
            String returnName,
            String returnZip,
            @RequestParam(required = false, defaultValue = "0") int freight,
            @RequestParam(required = false) String remark,
            @RequestParam(required = false) String dicDeliverItemsStr,
            @RequestAttribute int customerId,
            @RequestAttribute ERPTypeEnum.UserType erpUserType
    ) throws IOException {
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setErpUserType(erpUserType);
        erpUserInfo.setCustomerId(customerId);
        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (erpUserHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setOrderId(orderId);
        returnInfo.setLogiName(logiName);
        returnInfo.setLogiNo(logiNo);
        returnInfo.setReturnAddr(returnAddr);
        returnInfo.setReturnMobile(returnMobile);
        returnInfo.setReturnName(returnName);
        returnInfo.setReturnZip(returnZip);
        returnInfo.setFreight(freight);
        returnInfo.setRemark(remark);
        returnInfo.setDicDeliverItemsStr(dicDeliverItemsStr);
        ReturnInfoEvent returnInfoEvent = new ReturnInfoEvent();
        returnInfoEvent.setReturnInfo(returnInfo);
        EventResult eventResult = erpUserHandler.handleEvent(returnInfoEvent);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            return ApiResult.resultWith(ResultCode.SUCCESS, eventResult.getData());
        } else {
            return ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST, eventResult.getResultMsg(), null);
        }
    }

    @RequestMapping("/obtainOrders")
    @ResponseBody
    @Override
    public ApiResult obtainOrders(Pageable pageable, int orderStauts, int customerId, @RequestAttribute ERPTypeEnum.UserType erpUserType) throws IOException {
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setErpUserType(erpUserType);
        erpUserInfo.setCustomerId(customerId);
        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (erpUserHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }

        ObtainOrderListEvent orderListEvent = new ObtainOrderListEvent();
        orderListEvent.setErpUserInfo(erpUserInfo);
        OrderSearchInfo orderSearchInfo = new OrderSearchInfo();
        orderSearchInfo.setOrderStatus(orderStauts);
        orderSearchInfo.setPageIndex(pageable.getOffset());
        orderSearchInfo.setPageSize(pageable.getPageSize());
        orderListEvent.setOrderSearchInfo(orderSearchInfo);
        EventResult eventResult = erpUserHandler.handleEvent(orderListEvent);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            return ApiResult.resultWith(ResultCode.SUCCESS, eventResult.getData());
        } else {
            return ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST, eventResult.getResultMsg(), null);
        }
    }

    @RequestMapping("/obtainOrder")
    @ResponseBody
    @Override
    public ApiResult obtainOrder(String orderId, int customerId, @RequestAttribute ERPTypeEnum.UserType erpUserType) throws IOException {
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setErpUserType(erpUserType);
        erpUserInfo.setCustomerId(customerId);
        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (erpUserHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }

        ObtainOrderDetailEvent orderEvent = new ObtainOrderDetailEvent();
        orderEvent.setOrderId(orderId);
        EventResult eventResult = erpUserHandler.handleEvent(orderEvent);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            return ApiResult.resultWith(ResultCode.SUCCESS, eventResult.getData());
        } else {
            return ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST, eventResult.getResultMsg(), null);
        }
    }

    @Override
    public ApiResult syncInventory(String orderId, int customerId, String erpUserName) throws IOException {
        return null;
    }

    @Override
    public ApiResult index(@PathVariable("eventType") String eventType, @RequestAttribute ERPUserInfo erpUserInfo, HttpServletRequest request) {
        return null;
    }
}
