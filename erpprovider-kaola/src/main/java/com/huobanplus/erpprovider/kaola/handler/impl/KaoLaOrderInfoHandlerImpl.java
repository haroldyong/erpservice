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
import com.huobanplus.erpservice.common.util.SignBuilder;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private static final Log log = LogFactory.getLog(KaoLaOrderInfoHandlerImpl.class);

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
                    if (result.getString("recCode").equals("200")) {

                        if(result.containsKey("result")){
                            JSONArray jsonArray = result.getJSONArray("result");

                            jsonArray.forEach(item -> {

                                double freight = 0.0;
                                if(result.containsKey("totalChinaLogisticsAmount")){
                                    freight  = result.getDouble("totalChinaLogisticsAmount");
                                }
                                JSONObject jsonObject = JSON.parseObject(item.toString());
                                int status = jsonObject.getInteger("status");
                                if (status == 4) {// 订单已发货
                                    orderDeliveryInfo.setFreight(freight);
                                    String deliverNo = jsonObject.getString("deliverNo");
                                    String deliverName = jsonObject.getString("deliverName");
                                    orderDeliveryInfo.setLogiName(deliverName);
                                    orderDeliveryInfo.setLogiNo(deliverNo);
                                    orderDeliveryInfoList.add(orderDeliveryInfo);
                                }
                            });
                        }

                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
        return EventResult.resultWith(EventResultEnum.SUCCESS, orderDeliveryInfoList);
    }

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {

        try {

            Order orderInfo = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);

            ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
            KaoLaSysData kaoLaSysData = JSON.parseObject(erpInfo.getSysDataJson(), KaoLaSysData.class);
            ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();

            List<OrderItem> orderItems = orderInfo.getOrderItems();
            List<KaoLaOrderItem> kaoLaOrderItems = new ArrayList<KaoLaOrderItem>();

            for (OrderItem item : orderItems) {
                KaoLaOrderItem kaoLaOrderItem = new KaoLaOrderItem();
                String goodsId = queryGoodsId(item.getProductBn(), kaoLaSysData);
                if(goodsId == null) {
                    return EventResult.resultWith(EventResultEnum.ERROR, "考拉中无此商品", null);
                }
                kaoLaOrderItem.setGoodsId(goodsId);
                kaoLaOrderItem.setSkuId(item.getProductBn());
                kaoLaOrderItem.setBuyAmount(item.getNum());
                kaoLaOrderItems.add(kaoLaOrderItem);
            }

            KaoLaUserInfo userInfo = new KaoLaUserInfo();
            userInfo.setAccountId(String.valueOf(orderInfo.getMemberId()));
            userInfo.setName(orderInfo.getBuyerName());
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
            userInfo.setIdentityId(orderInfo.getBuyerPid());


            Map<String, Object> parameterMap = new TreeMap<String, Object>();

            parameterMap.put("source", kaoLaSysData.getChannelId());
            parameterMap.put("thirdPartOrderId", orderInfo.getOrderId());
            parameterMap.put("timestamp", StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN));
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
        }catch (Exception ex){
            return EventResult.resultWith(EventResultEnum.ERROR, ex.getMessage(), null);
        }
    }

    private EventResult orderPush(Map<String, Object> parameterMap, KaoLaSysData kaoLaSysData) throws UnsupportedEncodingException {

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

    @Override
    public String queryGoodsId(String skuId,KaoLaSysData kaoLaSysData) throws UnsupportedEncodingException {
        Map<String, Object> requestData = new TreeMap<>();
        requestData.put("channelId", kaoLaSysData.getChannelId());
        requestData.put("timestamp", StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN));
        requestData.put("v", kaoLaSysData.getV());
        requestData.put("sign_method", "md5");
        requestData.put("app_key", kaoLaSysData.getAppKey());
        requestData.put("skuId", skuId);
        requestData.put("queryType", 1);
        requestData.put("sign", SignBuilder.buildSign(requestData, kaoLaSysData.getAppSecret(), kaoLaSysData.getAppSecret()));
        HttpResult httpResult = HttpClientUtil.getInstance().post(kaoLaSysData.getRequestUrl()+"/queryGoodsInfoById", requestData);
        if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
            JSONObject jsonObject = JSON.parseObject(httpResult.getHttpContent());
            if(jsonObject.getInteger("recCode") == 200){
                JSONObject goodInfoJson = jsonObject.getJSONObject("goodsInfo");
                return goodInfoJson.getString("goodsId");
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
}
