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
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
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

import java.util.*;

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

        BaisonOrder baisonOrder = convert2ErpOrder(orderInfo);




        return null;
    }

    private EventResult orderPush(BaisonOrder baisonOrder, BaisonSysData baisonSysData) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("app_act", BaisonConstant.ADD_ORDER);
        requestMap.put("app_key", "");
        requestMap.put("app_nick", "");
        requestMap.put("app_secret", "");
        requestMap.put("timestamp", "");
        requestMap.put("sign", "");
        requestMap.put("format", "");
        requestMap.put("version", "");
        requestMap.put("info", JSON.toJSONString(baisonOrder));

        try {
            HttpResult httpResult = HttpClientUtil.getInstance().get(baisonSysData.getRequestUrl(), requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                String content = httpResult.getHttpContent();

                JSONObject respData = JSON.parseObject(content);
                JSONObject resultData = respData.getJSONObject("resp_data");

                if (resultData.getString("code").equals("0")) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                } else {
                    return EventResult.resultWith(EventResultEnum.ERROR, resultData.getString("ResultMsg"), null);
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
        baisonOrder.setPayTime("");
        baisonOrder.setCityName("");
        baisonOrder.setGoodsCount("");
        baisonOrder.setConsignee("");
        baisonOrder.setDistrictName("");
        baisonOrder.setEmail("");
        baisonOrder.setGoodsAmount("");
        baisonOrder.setMobile("");
        baisonOrder.setOid("");
        baisonOrder.setMoneyPaid("");
        baisonOrder.setOrderAmount("");
        baisonOrder.setPayName("");
        baisonOrder.setTel("");
        baisonOrder.setPostscript("");
        baisonOrder.setToBuyer("");
        baisonOrder.setSdId("");
        baisonOrder.setUserName("");
        baisonOrder.setZipcode("");
        baisonOrder.setProvinceName("");
        baisonOrder.setShippingName("");
        baisonOrder.setTotalAmount("");
        baisonOrder.setShippingFee("");
        baisonOrder.setAddTime("");


        List<BaisonOrderItem> baisonOrderItems = new ArrayList<>();

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.forEach(item -> {
            BaisonOrderItem baisonOrderItem = new BaisonOrderItem();
            baisonOrderItem.setGoodsName("");
            baisonOrderItem.setGoodsNumber("");
            baisonOrderItem.setGoodsPrice("");
            baisonOrderItem.setOuterSku("");
            baisonOrderItem.setPaymentFt("");
            baisonOrderItems.add(baisonOrderItem);
        });

        baisonOrder.setOrderItems(baisonOrderItems);
        return baisonOrder;
    }
}