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
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.OrderStatusInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.OrderInfo;
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
    public EventResult queryOrderStatusInfo(OrderStatusInfoEvent orderStatusInfoEvent) {

        OrderInfo orderInfo = orderStatusInfoEvent.getOrderInfo();
        ERPInfo erpInfo = orderStatusInfoEvent.getErpInfo();
        KaoLaSysData kaoLaSysData = JSON.parseObject(erpInfo.getSysDataJson(), KaoLaSysData.class);


        Map<String, Object> parameterMap = new TreeMap<String, Object>();
        parameterMap.put("channelId", kaoLaSysData.getChannelId());
        parameterMap.put("thirdPartOrderId", orderInfo.getOrderCode());
        parameterMap.put("timestamp", StringUtil.DateFormat(orderInfo.getPayTime(), StringUtil.TIME_PATTERN));
        parameterMap.put("v", kaoLaSysData.getV());
        parameterMap.put("sign_method", "md5");
        parameterMap.put("app_key", kaoLaSysData.getAppKey());


        try {
            Map<String, Object> requestData = getRequestData(kaoLaSysData, parameterMap);
            HttpResult httpResult = HttpClientUtil.getInstance().post(kaoLaSysData.getRequestUrl() + "/queryOrderStatus", requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                if (result.getString("recCode").equals("200")) {
                    // TODO: 2016/5/9

                    List<Order> orderList = new ArrayList<>();
                    JSONArray jsonArray = result.getJSONArray("result");

                    jsonArray.forEach(order -> {

                        List<OrderItem> orderItemList = new ArrayList<>();
                        Order resultOrder = new Order();
                        JSONObject jsonObject = JSON.parseObject(order.toString());
                        resultOrder.setOrderId(jsonObject.getString("orderId"));
                        Integer status = jsonObject.getInteger("status");
                        if (status == 0 || status == 1) {
                            resultOrder.setOrderStatus(status);
                        } else if (status == 2) {
                            resultOrder.setPayStatus(OrderEnum.PayStatus.PAYED.getCode());
                        } else if (status == 3) {
                            resultOrder.setPayStatus(OrderEnum.PayStatus.NOT_PAYED.getCode());
                        } else if (status == 4) {
                            resultOrder.setShipStatus(OrderEnum.ShipStatus.DELIVERED.getCode());
                        } else if (status == 5) {
                            resultOrder.setShipStatus(OrderEnum.ShipStatus.NOT_DELIVER.getCode());
                        }

//                        String desc = jsonObject.getString("desc");
                        resultOrder.setLogiName(jsonObject.getString("deliverName"));

                        JSONArray orderItems = jsonObject.getJSONArray("skuList");
                        if (orderItems != null) {
                            orderItems.forEach(item -> {
                                OrderItem orderItem = new OrderItem();
                                JSONObject itemJson = JSON.parseObject(item.toString());
                                orderItem.setItemId(Integer.parseInt(itemJson.getString("skuid")));
                                orderItem.setNum(itemJson.getInteger("buyCnt"));
                                orderItemList.add(orderItem);
                            });
                        }

                        resultOrder.setOrderItems(orderItemList);
                        orderList.add(resultOrder);
                    });

                    // 返回订单list
                    return EventResult.resultWith(EventResultEnum.SUCCESS, orderList);
                } else {
                    return EventResult.resultWith(EventResultEnum.ERROR, result.get("recMeg").toString(), null);
                }

            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return EventResult.resultWith(EventResultEnum.ERROR);
        }
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
