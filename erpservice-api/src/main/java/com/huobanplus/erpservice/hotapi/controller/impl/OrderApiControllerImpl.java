/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.hotapi.controller.impl;

import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.hotapi.common.ERPApiBaseController;
import com.huobanplus.erpservice.hotapi.controller.OrderApiController;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.DeliveryInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.InventoryEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.ReturnInfoEvent;
import com.huobanplus.erpservice.eventhandler.model.DeliveryInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.ReturnInfo;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

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
            @RequestParam(required = false) String dicDeliverItemsStr) throws IOException {

        //得到使用者事件处理器
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setERPUserName(erpUserName);
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
        EventResult eventResult = erpUserHandler.handleEvent(deliveryInfoEvent, null);
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
            @RequestParam(required = false) String dicDeliverItemsStr
    ) throws IOException {
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setERPUserName(erpUserName);
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
        EventResult eventResult = erpUserHandler.handleEvent(returnInfoEvent, null);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            return ApiResult.resultWith(ResultCode.SUCCESS, eventResult.getData());
        } else {
            return ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST, eventResult.getResultMsg(), null);
        }
    }

    @Override
    @RequestMapping("/syncInventory")
    @ResponseBody
    public ApiResult syncInventory(int goodId, int productId, String bn, int stock) throws IOException {
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setERPUserName(erpUserName);
        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (erpUserHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        InventoryEvent inventoryEvent = new InventoryEvent();
        inventoryEvent.setGoodId(goodId);
        inventoryEvent.setProductId(productId);
        inventoryEvent.setBn(bn);
        inventoryEvent.setStock(stock);
        EventResult eventResult = erpUserHandler.handleEvent(inventoryEvent, null);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            return ApiResult.resultWith(ResultCode.SUCCESS, eventResult.getData());
        } else {
            return ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST, eventResult.getResultMsg(), null);
        }
    }
}
