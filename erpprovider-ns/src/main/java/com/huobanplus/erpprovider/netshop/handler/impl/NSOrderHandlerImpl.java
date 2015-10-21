/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.netshop.bean.NSOrderDetailResult;
import com.huobanplus.erpprovider.netshop.bean.NSOrderItemResult;
import com.huobanplus.erpprovider.netshop.bean.NSOrderListResult;
import com.huobanplus.erpprovider.netshop.exceptionhandler.NSExceptionHandler;
import com.huobanplus.erpprovider.netshop.handler.NSOrderHandler;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.DeliveryInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.ObtainOrderDetailEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.ObtainOrderListEvent;
import com.huobanplus.erpservice.eventhandler.model.DeliveryInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

/**
 * 订单信息处理实现类
 */
@Service
public class NSOrderHandlerImpl implements NSOrderHandler {
    @Autowired
    private ERPRegister erpRegister;

    @Override
    public EventResult obtainOrderInfoList(int orderStatus, int pageSize, Integer pageIndex, ERPUserInfo erpUserInfo, String mType) {
        try {
            //得到erp使用者的事件处理器
            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
            if (erpUserHandler == null) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.NO_DATA, "未找到数据源信息");
            }
            ObtainOrderListEvent orderListEvent = new ObtainOrderListEvent();
            orderListEvent.setErpUserInfo(erpUserInfo);
            orderListEvent.setOrderStatus(orderStatus);
            orderListEvent.setPageSize(pageSize);
            orderListEvent.setPageIndex(pageIndex);
            //todo 调用相关使用者获得订单数据
            EventResult eventResult = erpUserHandler.handleEvent(orderListEvent);
            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.ERROR, eventResult.getResultMsg());
            }
            List<MallOrderBean> orderList = (List<MallOrderBean>) eventResult.getData();
            List<String> orderIdList = new ArrayList<>();
            orderList.forEach(order -> {
                orderIdList.add(order.getOrderId());
            });

            NSOrderListResult orderListResult = new NSOrderListResult();
            orderListResult.setOrderCount(String.valueOf(orderList.size()));
            orderListResult.setPage(pageIndex.toString());
            orderListResult.setResult("1");
            orderListResult.setOrderNo(orderIdList);

            String orderResultXml = new XmlMapper().writeValueAsString(orderListResult);
            int firstIndex = orderResultXml.indexOf("<OrderNO>");
            int lastIndex = orderResultXml.lastIndexOf("</OrderNO>");
            String firstPanel = orderResultXml.substring(0, firstIndex);
            String orderPanel = orderResultXml.substring(firstIndex + 9, lastIndex);
            String lastPanel = orderResultXml.substring(lastIndex + 10);
            String xmlResult = firstPanel + "<OrderList>" + orderPanel + "</OrderList>" + lastPanel;
            return EventResult.resultWith(EventResultEnum.SUCCESS, xmlResult);
        } catch (Exception e) {
            return NSExceptionHandler.handleException(mType, EventResultEnum.ERROR, "服务器错误--" + e.getMessage());
        }
    }

    @Override
    public EventResult obtainOrderInfo(String orderId, ERPUserInfo erpUserInfo, String mType) {
        try {
            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
            if (erpUserHandler == null) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.NO_DATA, "未找到数据源信息");
            }
            ObtainOrderDetailEvent orderDetailEvent = new ObtainOrderDetailEvent();
            orderDetailEvent.setErpUserInfo(erpUserInfo);
            orderDetailEvent.setOrderId(orderId);
            EventResult eventResult = erpUserHandler.handleEvent(orderDetailEvent);
            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.ERROR, eventResult.getResultMsg());
            }
            //todo 调用相关使用者获得订单详情
            MallOrderBean orderBean = (MallOrderBean) eventResult.getData();

            NSOrderDetailResult orderDetailResult = new NSOrderDetailResult();
            orderDetailResult.setOrderNo(orderBean.getOrderId());
            orderDetailResult.setResult(1);
            orderDetailResult.setDateTime(StringUtil.DateFormat(orderBean.getPayTime(), Constant.TIME_FORMAT_ONE));
            orderDetailResult.setBuyerId(orderBean.getBuyerId());
            orderDetailResult.setBuyerName(orderBean.getReceiverName());
            orderDetailResult.setProvince(orderBean.getProvince());
            orderDetailResult.setCity(orderBean.getCity());
            orderDetailResult.setTown(orderBean.getDistrict());
            orderDetailResult.setAdr(orderBean.getAddress());
            orderDetailResult.setZip(orderBean.getPost());
            orderDetailResult.setEmail(orderBean.getEmail());
            orderDetailResult.setPhone(orderBean.getReceiverMobile());
            orderDetailResult.setTotal(orderBean.getOrderTotalFee());
            orderDetailResult.setPostage(orderBean.getRealPayFreight());
            orderDetailResult.setPayAccount(orderBean.getPayMothed());
            orderDetailResult.setPayID(orderBean.getPayId());
            orderDetailResult.setLogisticsName(orderBean.getSendingType());
            orderDetailResult.setChargetype(orderBean.getChargeType());
            orderDetailResult.setCustomerRemark(orderBean.getBuyerMessage());
            orderDetailResult.setInvoiceTitle(orderBean.getInvoiceTitle());
            orderDetailResult.setRemark(orderBean.getServiceRemarks());
            List<NSOrderItemResult> orderItemResults = new ArrayList<>();
            orderBean.getOrderItems().forEach(item -> {
                NSOrderItemResult orderItemResult = new NSOrderItemResult();
                orderItemResult.setGoodsID(item.getProductNo());
                orderItemResult.setGoodsName(item.getProName());
                orderItemResult.setGoodsSpec(item.getSpecification());
                orderItemResult.setCount(item.getProNum());
                orderItemResult.setPrice(item.getSellPrice());
                orderItemResults.add(orderItemResult);
            });
            String resultXml = new XmlMapper().writeValueAsString(orderDetailResult);

            return EventResult.resultWith(EventResultEnum.SUCCESS, resultXml);
        } catch (Exception e) {
            return NSExceptionHandler.handleException(mType, EventResultEnum.ERROR, "服务器错误--" + e.getMessage());
        }
    }

    @Override
    public EventResult deliverOrder(String orderId, String logiName, String logiNo, ERPUserInfo erpUserInfo, String mType) {
        try {
            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
            if (erpUserHandler == null) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.NO_DATA, "未找到数据源信息");
            }
            DeliveryInfoEvent deliveryInfoEvent = new DeliveryInfoEvent();
            deliveryInfoEvent.setErpUserInfo(erpUserInfo);
            DeliveryInfo deliveryInfo = new DeliveryInfo();
            deliveryInfo.setOrderId(orderId);
            deliveryInfo.setLogiName(logiName);
            deliveryInfo.setLogiNo(logiNo);
            deliveryInfoEvent.setDeliveryInfo(deliveryInfo);
            EventResult eventResult = erpUserHandler.handleEvent(deliveryInfoEvent);
            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.ERROR, eventResult.getResultMsg());
            }

            return EventResult.resultWith(EventResultEnum.SUCCESS, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</Result></Rsp>");
        } catch (Exception e) {
            return NSExceptionHandler.handleException(mType, EventResultEnum.ERROR, "服务器错误--" + e.getMessage());
        }
    }
}
