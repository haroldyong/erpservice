/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.kaola.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.kaola.common.KaoLaSysData;
import com.huobanplus.erpprovider.kaola.formatkaola.KaoLaOrderItem;
import com.huobanplus.erpprovider.kaola.formatkaola.KaoLaUserInfo;
import com.huobanplus.erpprovider.kaola.handler.KaoLaBaseHandler;
import com.huobanplus.erpprovider.kaola.handler.KaoLaOrderInfoHandler;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by wuxiongliu on 2016/5/10.
 */
@Component
public class KaoLaOrderInfoHandlerImpl extends KaoLaBaseHandler implements KaoLaOrderInfoHandler {

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    @Override
    public EventResult queryOrderStatusInfo(List<Order> orderList,KaoLaSysData kaoLaSysData) {

        List<OrderDeliveryInfo> orderDeliveryInfoList = new ArrayList<>();

        for(Order order: orderList) {

            OrderDeliveryInfo orderDeliveryInfo = new OrderDeliveryInfo();
            orderDeliveryInfo.setOrderId(order.getOrderId());

            Map<String, Object> parameterMap = new TreeMap<String, Object>();
            parameterMap.put("channelId", kaoLaSysData.getChannelId());
            parameterMap.put("thirdPartOrderId", order.getOrderId());
            parameterMap.put("timestamp", StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));
            parameterMap.put("v", kaoLaSysData.getV());
            parameterMap.put("sign_method", "md5");
            parameterMap.put("app_key", kaoLaSysData.getAppKey());

            try {
                Map<String, Object> requestData = getRequestData(kaoLaSysData, parameterMap);
                HttpResult httpResult = HttpClientUtil.getInstance().post(kaoLaSysData.getRequestUrl() + "/queryOrderStatus", requestData);
                if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                    JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                    System.out.println("*******************");
                    System.out.println(result);
                    System.out.println("*******************");
                    if (result.getString("recCode").equals("200")) {

                        double freight = result.getDouble("totalChinaLogisticsAmount");
                        orderDeliveryInfo.setFreight(freight);
                        JSONArray jsonArray = result.getJSONArray("result");

                        jsonArray.forEach(item -> {

                            JSONObject jsonObject = JSON.parseObject(item.toString());
//                            String orderId = jsonObject.getString("orderId");
                            String deliverNo = jsonObject.getString("deliverNo");
                            String deliverName = jsonObject.getString("deliverName");
//                            orderDeliveryInfo.setOrderId(orderId);
                            orderDeliveryInfo.setLogiName(deliverName);
                            orderDeliveryInfo.setLogiNo(deliverNo);

                            orderDeliveryInfoList.add(orderDeliveryInfo);
                        });

                        // 返回订单发货信息列表

                    } else {

                        orderDeliveryInfo.setLogiName("");
                        orderDeliveryInfo.setLogiName("");
                        orderDeliveryInfoList.add(orderDeliveryInfo);

//                        return EventResult.resultWith(EventResultEnum.ERROR, result.get("recMeg").toString(), null);
                    }

                } else {
                    orderDeliveryInfo.setLogiName("");
                    orderDeliveryInfo.setLogiName("");
                    orderDeliveryInfoList.add(orderDeliveryInfo);
//                    return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
//                return EventResult.resultWith(EventResultEnum.ERROR);
            }
        }
        return EventResult.resultWith(EventResultEnum.SUCCESS, orderDeliveryInfoList);
    }

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {

        Order orderInfo = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);

        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
        KaoLaSysData kaoLaSysData = JSON.parseObject(erpInfo.getSysDataJson(), KaoLaSysData.class);
        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();

        List<OrderItem> orderItems = orderInfo.getOrderItems();
        List<KaoLaOrderItem> kaoLaOrderItems = new ArrayList<KaoLaOrderItem>();

        orderItems.forEach(item -> {
            KaoLaOrderItem kaoLaOrderItem = new KaoLaOrderItem();
            kaoLaOrderItem.setGoodsId(String.valueOf(item.getItemId()));
            kaoLaOrderItem.setSkuId(item.getProductBn());
            kaoLaOrderItem.setBuyAmount(item.getNum());
            kaoLaOrderItems.add(kaoLaOrderItem);
        });

        KaoLaUserInfo userInfo = new KaoLaUserInfo();
        userInfo.setAccountId(String.valueOf(orderInfo.getMemberId()));
        userInfo.setName(orderInfo.getShipName());
        userInfo.setMobile(orderInfo.getShipMobile());
        userInfo.setEmail(orderInfo.getShipEmail());
        userInfo.setProvinceName(orderInfo.getProvince());
        //userInfo.setProvinceCode("");
        userInfo.setCityName(orderInfo.getCity());
//        userInfo.setCityCode("");
        userInfo.setDistrictName(orderInfo.getDistrict());
//        userInfo.setDistrictCode("");
        userInfo.setAddress(orderInfo.getShipAddr());
//        userInfo.setPostCode(orderInfo.getShipZip());
//        userInfo.setPhoneNum("");
//        userInfo.setPhoneAreaNum("");
//        userInfo.setPhoneExtNum("");
        userInfo.setIdentityId("362322199411050053");// TODO: 2016/5/11


        Map<String, Object> parameterMap = new TreeMap<String, Object>();

        parameterMap.put("source", kaoLaSysData.getChannelId());
        parameterMap.put("thirdPartOrderId", orderInfo.getOrderId());
        parameterMap.put("timestamp", orderInfo.getPayTime());
        parameterMap.put("v", kaoLaSysData.getV());
        parameterMap.put("sign_method", "md5");
        parameterMap.put("app_key", kaoLaSysData.getAppKey());
        JSONObject orderItemsJson = new JSONObject();
        orderItemsJson.put("orderItemList", kaoLaOrderItems);
        parameterMap.put("orderItemList", orderItemsJson.toJSONString());


        JSONObject userInfoJson = new JSONObject();
        userInfoJson.put("userInfo", userInfo);
        parameterMap.put("userInfo", userInfoJson.toJSONString());

        Date now = new Date();
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

        EventResult eventResult = orderPush(parameterMap, kaoLaSysData);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
        } else {
            orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
            orderDetailSyncLog.setErrorMsg(eventResult.getResultMsg());
        }
        orderDetailSyncLogService.save(orderDetailSyncLog);
        return eventResult;
    }

    private EventResult orderPush(Map<String, Object> parameterMap, KaoLaSysData kaoLaSysData) {
        try {
            Map<String, Object> requestData = getRequestData(kaoLaSysData, parameterMap);
            HttpResult httpResult = HttpClientUtil.getInstance().post(kaoLaSysData.getRequestUrl() + "/bookpayorder", requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                if (result.getString("recCode").equals("200")) {
                    // TODO: 2016/5/9
                    return EventResult.resultWith(EventResultEnum.SUCCESS, result.getString("gorder"));
                } else {
                    return EventResult.resultWith(EventResultEnum.ERROR, result.get("recMeg").toString(), null);
                }

            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, "服务器请求失败", null);
            }
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    /**
     * 记录日志
     *
     * @param orderInfo
     * @param erpUserInfo
     * @param erpInfo
     * @param pushNewOrderEvent
     * @param isSuccess
     */
    private void saveLog(Order orderInfo, ERPUserInfo erpUserInfo, ERPInfo erpInfo, PushNewOrderEvent pushNewOrderEvent, boolean isSuccess) {
        OrderDetailSyncLog orderDetailSyncLog = orderDetailSyncLogService.findByOrderId(orderInfo.getOrderId());
        Date now = new Date();
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

        if (isSuccess) {
            orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
        } else {
            orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
        }

        orderDetailSyncLogService.save(orderDetailSyncLog);
    }
}
