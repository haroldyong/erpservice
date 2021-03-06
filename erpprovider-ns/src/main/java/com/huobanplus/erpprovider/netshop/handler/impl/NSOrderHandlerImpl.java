/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.netshop.bean.NSOrderDetailResult;
import com.huobanplus.erpprovider.netshop.bean.NSOrderItemResult;
import com.huobanplus.erpprovider.netshop.bean.NSOrderListResult;
import com.huobanplus.erpprovider.netshop.exceptionhandler.NSExceptionHandler;
import com.huobanplus.erpprovider.netshop.handler.NSOrderHandler;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.model.OrderListInfo;
import com.huobanplus.erpservice.datacenter.model.OrderSearchInfo;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetOrderDetailEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetOrderDetailListEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushDeliveryInfoEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单信息处理实现类
 */
@Service
public class NSOrderHandlerImpl implements NSOrderHandler {
    @Autowired
    private ERPRegister erpRegister;

    @Override
    public EventResult obtainOrderInfoList(int orderStatus, int pageSize, Integer pageIndex, ERPUserInfo erpUserInfo, String mType, String startUpdateTime, String endUpdateTime) {
        try {
            //得到erp使用者的事件处理器
            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
            if (erpUserHandler == null) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.NO_DATA, "未找到数据源信息");
            }
            GetOrderDetailListEvent orderListEvent = new GetOrderDetailListEvent();
            OrderSearchInfo orderSearchInfo = new OrderSearchInfo();
            orderListEvent.setErpUserInfo(erpUserInfo);
            orderSearchInfo.setPayStatus(orderStatus);
            orderSearchInfo.setShipStatus(OrderEnum.ShipStatus.NOT_DELIVER.getCode());// 未发货
            orderSearchInfo.setPageSize(pageSize);
            orderSearchInfo.setPageIndex(pageIndex);

            if (StringUtil.isEmpty(startUpdateTime) || StringUtil.isEmpty(endUpdateTime)) {
                Date beginTime = Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusMonths(2));
                Date endTime = new Date();
                orderSearchInfo.setBeginUpdateTime(StringUtil.DateFormat(beginTime, StringUtil.TIME_PATTERN));
                orderSearchInfo.setEndUpdateTime(StringUtil.DateFormat(endTime, StringUtil.TIME_PATTERN));
            } else {
                orderSearchInfo.setBeginUpdateTime(startUpdateTime);
                orderSearchInfo.setEndUpdateTime(endUpdateTime);
            }

            orderListEvent.setOrderSearchInfo(orderSearchInfo);
            //todo 调用相关使用者获得订单数据
            EventResult eventResult = erpUserHandler.handleEvent(orderListEvent);

            if (eventResult == null) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.UNSUPPORT_EVENT, "不支持的ERP事件");
            }

            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.ERROR, eventResult.getResultMsg());
            }
            OrderListInfo orderListInfo = (OrderListInfo) eventResult.getData();
            List<Order> orderList = orderListInfo.getOrders();
            List<String> orderIdList = new ArrayList<>();

            orderList.forEach(order -> {
                orderIdList.add(order.getOrderId());
            });

            NSOrderListResult orderListResult = new NSOrderListResult();
            orderListResult.setOrderCount(String.valueOf(orderListInfo.getRecordCount()));
            orderListResult.setPage(pageIndex.toString());
            orderListResult.setResult("1");
            orderListResult.setOrderNo(orderIdList);

            String orderResultXml = new XmlMapper().writeValueAsString(orderListResult);
            int firstIndex = orderResultXml.indexOf("<OrderNO>");
            int lastIndex = orderResultXml.lastIndexOf("</OrderNO>");
            String firstPanel = orderResultXml.substring(0, firstIndex);
            String orderPanel = orderResultXml.substring(firstIndex + 9, lastIndex);
            String lastPanel = orderResultXml.substring(lastIndex + 10);
            String xmlResult = "<?xml version='1.0' encoding='UTF-8'?>" + firstPanel.replaceFirst("<Order xmlns=\"\">", "<Order>")
                    + "<OrderList>" + orderPanel + "</OrderList>" + lastPanel;
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

            GetOrderDetailEvent orderDetailEvent = new GetOrderDetailEvent();
            orderDetailEvent.setErpUserInfo(erpUserInfo);
            orderDetailEvent.setOrderId(orderId);
            EventResult eventResult = erpUserHandler.handleEvent(orderDetailEvent);

            if (eventResult == null) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.UNSUPPORT_EVENT, "不支持的ERP事件");
            }

            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.ERROR, eventResult.getResultMsg());
            }
            //todo 调用相关使用者获得订单详情
            Order orderBean = (Order) eventResult.getData();

            NSOrderDetailResult orderDetailResult = new NSOrderDetailResult();
            orderDetailResult.setOrderNo(orderBean.getOrderId());
            orderDetailResult.setResult(1);
            orderDetailResult.setDateTime(orderBean.getPayTime());
            if (StringUtil.isEmpty(orderBean.getUserLoginName())) {// 保证buyerId不为空
                orderDetailResult.setBuyerId(orderBean.getShipMobile());
            } else {
                orderDetailResult.setBuyerId(orderBean.getUserLoginName());
            }
            orderDetailResult.setBuyerName(orderBean.getShipName());
            if (StringUtil.isNotEmpty(orderBean.getBuyerPid())) {
                orderDetailResult.setCardType(1);
                orderDetailResult.setIdCard(orderBean.getBuyerPid());
            }
            orderDetailResult.setCountry("中国");
            orderDetailResult.setProvince(orderBean.getProvince());
            orderDetailResult.setCity(orderBean.getCity());
            orderDetailResult.setTown(orderBean.getDistrict());
            orderDetailResult.setAdr(orderBean.getShipAddr());
            orderDetailResult.setZip(orderBean.getShipZip());
            orderDetailResult.setEmail(orderBean.getShipEmail());
            orderDetailResult.setPhone(orderBean.getShipMobile());
            orderDetailResult.setTotal(orderBean.getFinalAmount());
            orderDetailResult.setCurrency("CNY");
            orderDetailResult.setPostage(orderBean.getCostFreight());
            orderDetailResult.setPayAccount(orderBean.getPaymentName());
//            orderDetailResult.setPayID(orderBean);
            orderDetailResult.setLogisticsName(orderBean.getLogiName());
            orderDetailResult.setChargetype("担保交易"); // FIXME: 2016/7/4  针对支付宝，微信支付这类
            orderDetailResult.setCustomerRemark(orderBean.getMemo());
//            orderDetailResult.setInvoiceTitle(orderBean.getInvoiceTitle());
            orderDetailResult.setRemark(orderBean.getRemark());
            List<NSOrderItemResult> orderItemResults = new ArrayList<>();
            orderBean.getOrderItems().forEach(item -> {
                NSOrderItemResult orderItemResult = new NSOrderItemResult();
                orderItemResult.setGoodsID(item.getProductBn());
                orderItemResult.setGoodsName(item.getName());
                orderItemResult.setGoodsSpec(item.getStandard());
                orderItemResult.setCount(item.getNum());
                orderItemResult.setPrice(item.getAmount());
                orderItemResults.add(orderItemResult);
            });

            orderDetailResult.setOrderItemResults(orderItemResults);
            String resultXml = new XmlMapper().writeValueAsString(orderDetailResult);
            int firstIndex = resultXml.indexOf("<Item>");
            int lastIndex = resultXml.lastIndexOf("</Item>");
            String firstPanel = resultXml.substring(0, firstIndex);
            String orderItemPanel = resultXml.substring(firstIndex + 6, lastIndex);
            String lastPanel = resultXml.substring(lastIndex + 7);
            String xmlResult = "<?xml version='1.0' encoding='UTF-8'?>" + firstPanel.replaceFirst("<Order xmlns=\"\">", "<Order>")
                    + orderItemPanel + lastPanel;
            return EventResult.resultWith(EventResultEnum.SUCCESS, xmlResult);
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

            PushDeliveryInfoEvent pushDeliveryInfoEvent = new PushDeliveryInfoEvent();
            OrderDeliveryInfo orderDeliveryInfo = new OrderDeliveryInfo();
            orderDeliveryInfo.setOrderId(orderId);
            orderDeliveryInfo.setLogiName(logiName);
            orderDeliveryInfo.setLogiNo(logiNo);
            pushDeliveryInfoEvent.setDeliveryInfo(orderDeliveryInfo);
            pushDeliveryInfoEvent.setErpUserInfo(erpUserInfo);

            EventResult eventResult = erpUserHandler.handleEvent(pushDeliveryInfoEvent);
            if (eventResult == null) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.UNSUPPORT_EVENT, "不支持的ERP事件");
            }
            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.ERROR, eventResult.getResultMsg());
            }

            return EventResult.resultWith(EventResultEnum.SUCCESS, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</Result></Rsp>");
        } catch (Exception e) {
            return NSExceptionHandler.handleException(mType, EventResultEnum.ERROR, "服务器错误--" + e.getMessage());
        }
    }
}
