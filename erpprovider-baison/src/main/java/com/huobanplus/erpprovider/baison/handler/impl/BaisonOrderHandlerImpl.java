/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpprovider.baison.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.baison.common.BaisonConstant;
import com.huobanplus.erpprovider.baison.common.BaisonSysData;
import com.huobanplus.erpprovider.baison.formatdata.BaisonOrder;
import com.huobanplus.erpprovider.baison.formatdata.BaisonOrderItem;
import com.huobanplus.erpprovider.baison.handler.BaisonOrderHandler;
import com.huobanplus.erpprovider.baison.search.BaisonOrderSearch;
import com.huobanplus.erpprovider.baison.util.BaisonUtil;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class BaisonOrderHandlerImpl implements BaisonOrderHandler {

    private static final Log log = LogFactory.getLog(BaisonOrderHandlerImpl.class);

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;
    @Autowired
    private ERPRegister erpRegister;


    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        log.info("order:" + pushNewOrderEvent.getOrderInfoJson());

        Date now = new Date();
        Order orderInfo = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
        BaisonSysData baisonSysData = JSON.parseObject(erpInfo.getSysDataJson(), BaisonSysData.class);
        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();

        OrderDetailSyncLog orderDetailSyncLog = orderDetailSyncLogService.findByOrderId(orderInfo.getOrderId());
        if (orderDetailSyncLog == null) {
            orderDetailSyncLog = new OrderDetailSyncLog();
            orderDetailSyncLog.setCreateTime(now);
        }

        orderDetailSyncLog.setCustomerId(erpUserInfo.getCustomerId());
        orderDetailSyncLog.setProviderType(erpInfo.getErpType());
        orderDetailSyncLog.setUserType(erpUserInfo.getErpUserType());
        orderDetailSyncLog.setOrderId(orderInfo.getOrderId());
        orderDetailSyncLog.setOrderInfoJson(pushNewOrderEvent.getOrderInfoJson());
        orderDetailSyncLog.setErpSysData(erpInfo.getSysDataJson());
        orderDetailSyncLog.setSyncTime(now);

        try {
            BaisonOrder baisonOrder = convert2ErpOrder(orderInfo);
            EventResult eventResult = orderPush(baisonOrder, baisonSysData);
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
            } else {
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                orderDetailSyncLog.setErrorMsg(eventResult.getResultMsg());
            }
            orderDetailSyncLogService.save(orderDetailSyncLog);
            log.info("BaisonOrderHandlerImpl-pushOrder complete");
            return eventResult;
        } catch (Exception e) {
            log.info("BaisonOrderHandlerImpl-pushOrder exception:" + e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    private EventResult orderPush(BaisonOrder baisonOrder, BaisonSysData baisonSysData) {

        try {
            Map<String, Object> requestMap = BaisonUtil.buildRequestMap(baisonSysData, BaisonConstant.ADD_ORDER, JSON.toJSONString(baisonOrder));
            HttpResult httpResult = HttpClientUtil.getInstance().post(baisonSysData.getRequestUrl(), requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject respData = JSON.parseObject(httpResult.getHttpContent());
                if (respData.getString("status").equals("api-success")) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                } else {
                    return EventResult.resultWith(EventResultEnum.ERROR, respData.getString("message"), null);
                }
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    private BaisonOrder convert2ErpOrder(Order order) {

        BaisonOrder baisonOrder = new BaisonOrder();

        baisonOrder.setAddress("");
        baisonOrder.setAddTime("");
        baisonOrder.setOrderSn("");
        baisonOrder.setSdCode("");
        baisonOrder.setOrderStatus(1);
        baisonOrder.setPayStatus(1);
        baisonOrder.setConsignee("");
        baisonOrder.setProvinceName("");
        baisonOrder.setCityName("");
        baisonOrder.setDistrictName("");
        baisonOrder.setUserName("");
        baisonOrder.setEmail("");
        baisonOrder.setMobile("");
        baisonOrder.setTel("");
        baisonOrder.setZipcode("");
        baisonOrder.setPosCode("");
        baisonOrder.setSaleShopCode("");
        baisonOrder.setPayCode("");
        baisonOrder.setVipNo("");
        baisonOrder.setShippingCode("");
        baisonOrder.setShippingFee(1);
        baisonOrder.setOrderAmount(1);
        baisonOrder.setPayment(1);
        baisonOrder.setSalerEmployeeNo("");

        List<BaisonOrderItem> baisonOrderItems = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            BaisonOrderItem baisonOrderItem = new BaisonOrderItem();
            baisonOrderItem.setTransactionPrice(1);
            baisonOrderItem.setSkuSn("");
            baisonOrderItem.setGoodsPrice(1);
            baisonOrderItem.setDiscount(1);
            baisonOrderItem.setGoodsNumber(1);
            baisonOrderItem.setIsGift(1);
            baisonOrderItems.add(baisonOrderItem);
        }
        baisonOrder.setOrderItems(baisonOrderItems);
        return baisonOrder;
    }

    @Override
    public EventResult orderQuery(BaisonOrderSearch baisonOrderSearch, BaisonSysData baisonSysData) {

        try {
            Map<String, Object> requestMap = BaisonUtil.buildRequestMap(baisonSysData, BaisonConstant.QUERY_ORDER, JSON.toJSONString(baisonOrderSearch));
            String sign = BaisonUtil.buildSign(requestMap);
            requestMap.put("sign", sign);

            HttpResult httpResult = HttpClientUtil.getInstance().get(baisonSysData.getRequestUrl(), requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject jsonObj = JSON.parseObject(httpResult.getHttpContent());
                String status = jsonObj.getString("status");
                if (status.equals("api-success")) {
                    log.info("BaisonOrderHandlerImpl-orderQuery: get order detail success");
                    return EventResult.resultWith(EventResultEnum.SUCCESS, jsonObj.getJSONObject("data"));
                } else {
                    log.info("BaisonOrderHandlerImpl-orderQuery: get order detail failed,error message :" + jsonObj.getString("message"));
                    return EventResult.resultWith(EventResultEnum.ERROR, jsonObj.getString("message"), null);
                }
            } else {
                log.info("BaisonOrderHandlerImpl-orderQuery request server error: " + httpResult.getHttpContent());
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }
        } catch (Exception e) {
            log.info("BaisonOrderHandlerImpl-orderQuery: exception " + e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }


}