/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.hotapi.handler.impl;

import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetOrderDetailEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetOrderDetailListEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushDeliveryInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushReturnInfoEvent;
import com.huobanplus.erpservice.eventhandler.model.*;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import com.huobanplus.erpservice.hotapi.handler.OrderHandler;
import com.huobanplus.erpservice.hotapi.jsonmodel.OrderList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liual on 2015-11-05.
 */
@Service
public class OrderHandlerImpl implements OrderHandler {
    @Autowired
    private ERPRegister erpRegister;

    @Override
    public ApiResult deliveryInfo(HttpServletRequest request, ERPUserInfo erpUserInfo) {
        String orderId = request.getParameter("orderId");
        if (StringUtils.isEmpty(orderId)) {
            return ApiResult.resultWith(ResultCode.BAD_REQUEST_PARAM, "未传入有效的orderId", null);
        }
        String logiName = request.getParameter("logiName");
        if (StringUtils.isEmpty(logiName)) {
            return ApiResult.resultWith(ResultCode.BAD_REQUEST_PARAM, "未传入快递公司名称", null);
        }
        String logiNo = request.getParameter("logiNo");
        if (StringUtils.isEmpty(logiNo)) {
            return ApiResult.resultWith(ResultCode.BAD_REQUEST_PARAM, "未传入快递单号", null);
        }
        double freight = StringUtil.getWithDefault(request.getParameter("freight"), 0);
        String remark = request.getParameter("remark");
        String deliverItemsStr = request.getParameter("deliverItemsStr");

        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (erpUserHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        PushDeliveryInfoEvent pushDeliveryInfoEvent = new PushDeliveryInfoEvent();
        DeliveryInfo deliveryInfo = new DeliveryInfo();
        deliveryInfo.setOrderId(orderId);
        deliveryInfo.setLogiName(logiName);
        deliveryInfo.setLogiNo(logiNo);
        deliveryInfo.setFreight(freight);
        deliveryInfo.setRemark(remark);
        deliveryInfo.setDeliverItemsStr(deliverItemsStr);
        pushDeliveryInfoEvent.setDeliveryInfo(deliveryInfo);
        EventResult eventResult = erpUserHandler.handleEvent(pushDeliveryInfoEvent);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            return ApiResult.resultWith(ResultCode.SUCCESS);
        }
        //todo 推送失败是否需要保存到本地等待下次推送
        return ApiResult.resultWith(ResultCode.ERPUSER_BAD_REQUEST, eventResult.getResultMsg(), null);
    }

    @Override
    public ApiResult returnInfo(HttpServletRequest request, ERPUserInfo erpUserInfo) {
        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (erpUserHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }

        String orderId = request.getParameter("orderId");
        if (StringUtils.isEmpty(orderId)) {
            return ApiResult.resultWith(ResultCode.BAD_REQUEST_PARAM, "未传入有效的orderId", null);
        }
        String logiName = request.getParameter("logiName");
        String logiNo = request.getParameter("logiNo");
        String returnAddr = request.getParameter("returnAddr");
        String returnMobile = request.getParameter("returnMobile");
        String returnName = request.getParameter("returnName");
        String returnZip = request.getParameter("returnZip");
        double freight = StringUtil.getWithDefault(request.getParameter("freight"), 0);
        String remark = request.getParameter("remark");
        String returnItemsStr = request.getParameter("returnItemsStr");
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
        returnInfo.setReturnItemStr(returnItemsStr);
        PushReturnInfoEvent returnInfoEvent = new PushReturnInfoEvent();
        returnInfoEvent.setReturnInfo(returnInfo);
        EventResult eventResult = erpUserHandler.handleEvent(returnInfoEvent);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            return ApiResult.resultWith(ResultCode.SUCCESS);
        }
        //todo 推送失败是否需要保存到本地等待下次推送
        return ApiResult.resultWith(ResultCode.ERPUSER_BAD_REQUEST, eventResult.getResultMsg(), null);
    }

    @Override
    public ApiResult obtainOrderList(HttpServletRequest request, ERPUserInfo erpUserInfo) {
        Integer pageIndex = StringUtil.getWithDefault(request.getParameter("pageIndex"), 1);
        Integer pageSize = StringUtil.getWithDefault(request.getParameter("pageSize"), 10);
        Integer orderStatus = StringUtil.getWithDefault(request.getParameter("orderStatus"), (Integer) null);
        Integer shipStatus = StringUtil.getWithDefault(request.getParameter("shipStatus"), -1);
        Integer payStatus = StringUtil.getWithDefault(request.getParameter("payStatus"), -1);
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String beginPayTime = request.getParameter("beginPayTime");
        String endPayTime = request.getParameter("endPayTime");
        String beginUpdateTime = request.getParameter("beginUpdateTime");
        String endUpdateTime = request.getParameter("endUpdateTime");
        String orderBy = StringUtil.getWithDefault(request.getParameter("orderBy"), "createTime");
        String orderType = StringUtil.getWithDefault(request.getParameter("orderType"), "desc");

        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (erpUserHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }

        GetOrderDetailListEvent getOrderDetailListEvent = new GetOrderDetailListEvent();
        OrderSearchInfo orderSearchInfo = new OrderSearchInfo();
        orderSearchInfo.setPageIndex(pageIndex);
        orderSearchInfo.setPageSize(pageSize);
        orderSearchInfo.setOrderStatus(orderStatus);
        orderSearchInfo.setShipStatus(shipStatus);
        orderSearchInfo.setPayStatus(payStatus);
        orderSearchInfo.setBeginTime(beginTime);
        orderSearchInfo.setEndTime(endTime);
        orderSearchInfo.setBeginPayTime(beginPayTime);
        orderSearchInfo.setEndPayTime(endPayTime);
        orderSearchInfo.setBeginUpdateTime(beginUpdateTime);
        orderSearchInfo.setEndUpdateTime(endUpdateTime);
        orderSearchInfo.setOrderBy(orderBy);
        orderSearchInfo.setOrderType(orderType);

        getOrderDetailListEvent.setOrderSearchInfo(orderSearchInfo);
        EventResult eventResult = erpUserHandler.handleEvent(getOrderDetailListEvent);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            OrderList orderList = new OrderList();
            List<Order> orders = (List<Order>) eventResult.getData();
            orderList.setRecordCount(orders.size());
            orderList.setPageIndex(pageIndex);
            orderList.setPageSize(pageSize);
            orderList.setOrderBy(orderBy);
            orderList.setOrderType(orderType);
            orderList.setOrders(orders);
            return ApiResult.resultWith(ResultCode.SUCCESS, orderList);
        }

        return ApiResult.resultWith(ResultCode.ERPUSER_BAD_REQUEST, eventResult.getResultMsg(), null);
    }

    @Override
    public ApiResult obtainOrderDetail(HttpServletRequest request, ERPUserInfo erpUserInfo) {
        String orderId = request.getParameter("orderId");
        if (StringUtils.isEmpty(orderId)) {
            return ApiResult.resultWith(ResultCode.BAD_REQUEST_PARAM, "未传入有效的orderId", null);
        }
        GetOrderDetailEvent getOrderDetailEvent = new GetOrderDetailEvent();
        getOrderDetailEvent.setErpUserInfo(erpUserInfo);
        getOrderDetailEvent.setOrderId(orderId);
        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (erpUserHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        getOrderDetailEvent.setOrderId(orderId);
        EventResult eventResult = erpUserHandler.handleEvent(getOrderDetailEvent);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            return ApiResult.resultWith(ResultCode.SUCCESS, eventResult.getData());
        }

        return ApiResult.resultWith(ResultCode.ERPUSER_BAD_REQUEST, eventResult.getResultMsg(), null);
    }
}
