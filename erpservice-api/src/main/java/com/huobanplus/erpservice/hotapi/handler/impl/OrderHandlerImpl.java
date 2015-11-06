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
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.DeliveryInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.ObtainOrderDetailEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.ObtainOrderListEvent;
import com.huobanplus.erpservice.eventhandler.model.DeliveryInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.OrderSearchInfo;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import com.huobanplus.erpservice.hotapi.handler.OrderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.EventHandler;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by liual on 2015-11-05.
 */
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
        String dicDeliverItemsStr = request.getParameter("dicDeliverItemsStr");

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
            return ApiResult.resultWith(ResultCode.SUCCESS);
        }
        //todo 推送失败是否需要保存到本地等待下次推送
        return ApiResult.resultWith(ResultCode.ERPUSER_BAD_REQUEST, eventResult.getResultMsg(), null);
    }

    @Override
    public ApiResult returnInfo(HttpServletRequest request, ERPUserInfo erpUserInfo) {
        return null;
    }

    @Override
    public ApiResult obtainOrderList(HttpServletRequest request, ERPUserInfo erpUserInfo) {
        Integer pageIndex = StringUtil.getWithDefault(request.getParameter("pageIndex"), (Integer) null);
        Integer pageSize = StringUtil.getWithDefault(request.getParameter("pageSize"), 20);
        Integer orderStatus = StringUtil.getWithDefault(request.getParameter("orderStatus"), (Integer) null);
        Integer shipStatus = StringUtil.getWithDefault(request.getParameter("shipStatus"), (Integer) null);
        Integer payStatus = StringUtil.getWithDefault(request.getParameter("payStatus"), (Integer) null);
        String beginTime = request.getParameter("beginTime");
        if (StringUtils.isEmpty(beginTime)) {
            return ApiResult.resultWith(ResultCode.BAD_REQUEST_PARAM, "开始时间未传", null);
        }
        String endTime = request.getParameter("endTime");
        if (StringUtil.isEmpty(endTime)) {
            return ApiResult.resultWith(ResultCode.BAD_REQUEST_PARAM, "结束时间未传", null);
        }

        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (erpUserHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }

        ObtainOrderListEvent obtainOrderListEvent = new ObtainOrderListEvent();
        OrderSearchInfo orderSearchInfo = new OrderSearchInfo();
        orderSearchInfo.setPageIndex(pageIndex);
        orderSearchInfo.setPageSize(pageSize);
        orderSearchInfo.setOrderStatus(orderStatus);
        orderSearchInfo.setShipStatus(shipStatus);
        orderSearchInfo.setPayStatus(payStatus);
        orderSearchInfo.setBeginTime(beginTime);
        orderSearchInfo.setEndTime(endTime);
        obtainOrderListEvent.setOrderSearchInfo(orderSearchInfo);
        EventResult eventResult = erpUserHandler.handleEvent(obtainOrderListEvent);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            return ApiResult.resultWith(ResultCode.SUCCESS, eventResult.getData());
        }

        return ApiResult.resultWith(ResultCode.ERPUSER_BAD_REQUEST, eventResult.getResultMsg(), null);
    }

    @Override
    public ApiResult obtainOrderDetail(HttpServletRequest request, ERPUserInfo erpUserInfo) {
        String orderId = request.getParameter("orderId");
        if (StringUtils.isEmpty(orderId)) {
            return ApiResult.resultWith(ResultCode.BAD_REQUEST_PARAM, "未传入有效的orderId", null);
        }
        ObtainOrderDetailEvent obtainOrderDetailEvent = new ObtainOrderDetailEvent();
        obtainOrderDetailEvent.setErpUserInfo(erpUserInfo);
        obtainOrderDetailEvent.setOrderId(orderId);
        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (erpUserHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        EventResult eventResult = erpUserHandler.handleEvent(obtainOrderDetailEvent);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            return ApiResult.resultWith(ResultCode.SUCCESS, eventResult.getData());
        }

        return ApiResult.resultWith(ResultCode.ERPUSER_BAD_REQUEST, eventResult.getResultMsg(), null);
    }
}
