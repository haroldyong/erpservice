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

package com.huobanplus.erpprovider.wangdianv2.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.wangdianv2.common.WangDianV2SysData;
import com.huobanplus.erpprovider.wangdianv2.formatdata.WangDianV2Order;
import com.huobanplus.erpprovider.wangdianv2.formatdata.WangDianV2OrderItem;
import com.huobanplus.erpprovider.wangdianv2.handler.WangDianV2OrderHandler;
import com.huobanplus.erpprovider.wangdianv2.search.WangDianV2OrderSearch;
import com.huobanplus.erpprovider.wangdianv2.util.WangDianV2SignBuilder;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.model.Order;
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
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by wuxiongliu on 2017-03-02.
 */
@Component
public class WangDianV2OrderHandlerImpl implements WangDianV2OrderHandler {

    private static final Log log = LogFactory.getLog(WangDianV2OrderHandlerImpl.class);

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        try {
            Order order = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
            log.info("wangdianv2 request order json:" + pushNewOrderEvent.getOrderInfoJson());
            ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
            ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
            WangDianV2SysData wangDianV2SysData = JSON.parseObject(erpInfo.getSysDataJson(), WangDianV2SysData.class);
            Date now = new Date();

            OrderDetailSyncLog orderDetailSyncLog = orderDetailSyncLogService.findByOrderId(order.getOrderId());
            if (orderDetailSyncLog == null) {
                orderDetailSyncLog = new OrderDetailSyncLog();
                orderDetailSyncLog.setCreateTime(now);
            }

            orderDetailSyncLog.setCustomerId(erpUserInfo.getCustomerId());
            orderDetailSyncLog.setProviderType(erpInfo.getErpType());
            orderDetailSyncLog.setUserType(erpUserInfo.getErpUserType());
            orderDetailSyncLog.setOrderId(order.getOrderId());
            orderDetailSyncLog.setOrderInfoJson(pushNewOrderEvent.getOrderInfoJson());
            orderDetailSyncLog.setErpSysData(erpInfo.getSysDataJson());
            orderDetailSyncLog.setSyncTime(now);

            WangDianV2Order wangDianV2Order = convert2ErpOrder(order, wangDianV2SysData);
            JSONArray orderArray = new JSONArray();
            orderArray.add(wangDianV2Order);

            // 构建请求参数
            Map<String, Object> requestMap = new TreeMap<>();

            // 系统级参数
            requestMap.put("sid", wangDianV2SysData.getWangdianv2Sid());
            requestMap.put("appkey", wangDianV2SysData.getAppKey());

            // 应用级参数
            requestMap.put("shop_no", wangDianV2SysData.getShopNo());
            requestMap.put("trade_list", JSON.toJSONString(orderArray));
            requestMap.put("timestamp", now.getTime() / 1000);

            String sign = WangDianV2SignBuilder.buildSign(requestMap, wangDianV2SysData.getAppSecret());
            requestMap.put("sign", sign);
            EventResult eventResult = orderPush(requestMap, wangDianV2SysData);
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
                log.info("wangdianv2 order push success");
            } else {
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                orderDetailSyncLog.setErrorMsg(eventResult.getResultMsg());
                log.info("wangdianv2 order push failed caused by " + eventResult.getResultMsg());

            }
            orderDetailSyncLogService.save(orderDetailSyncLog);
            log.info("wangdianv2 order push complete");
            return eventResult;
        } catch (Exception e) {
            log.info("wangdianv2 order push exception:" + e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    private WangDianV2Order convert2ErpOrder(Order order, WangDianV2SysData wangDianV2SysData) {
        WangDianV2Order wangDianV2Order = new WangDianV2Order();

        wangDianV2Order.setTid(order.getOrderId());
        wangDianV2Order.setTradeStatus(30);//已付款待发货
        wangDianV2Order.setPayStatus(2);// 已付款
        wangDianV2Order.setDeliveryTerm(1);// 款到发货
        wangDianV2Order.setTradeTime(order.getCreateTime());
        wangDianV2Order.setPayTime(order.getPayTime());
        wangDianV2Order.setBuyerNick(order.getUserNickname());
        wangDianV2Order.setBuyerEmail("");// TODO: 2017-02-24
        wangDianV2Order.setPayId(order.getPayNumber());
        wangDianV2Order.setPayAccount("");// TODO: 2017-02-24
        wangDianV2Order.setReceiverName(order.getShipName());
        wangDianV2Order.setReceiverCity(order.getCity());
        wangDianV2Order.setReceiverTelno(order.getShipTel());
        wangDianV2Order.setReceiverZip(order.getShipZip());
        wangDianV2Order.setLogisticsType(-1);
        wangDianV2Order.setInvoiceType(0);
        wangDianV2Order.setInvoiceTitle("");
        wangDianV2Order.setBuyerMessage(order.getMemo());
        wangDianV2Order.setSellerMemo(order.getRemark());
        wangDianV2Order.setSellerFlag(0);// TODO: 2017-02-24
        wangDianV2Order.setPostAmount(order.getCostFreight());
        wangDianV2Order.setCodeAmount(0.0);
//        wangDianV2Order.setExtCodFee("");
//        wangDianV2Order.setOtherAmount("");
        wangDianV2Order.setPaid(order.getFinalAmount());
//        wangDianV2Order.setIdCardType("1");
//        wangDianV2Order.setIdCard("");
//        wangDianV2Order.setCustData("");
        wangDianV2Order.setReceiverProvince(order.getProvince());
        wangDianV2Order.setReceiverDistrict(order.getDistrict());
        wangDianV2Order.setReceiverMobile(order.getShipMobile());
        wangDianV2Order.setReceiverAddress(order.getShipAddr());

        List<OrderItem> orderItems = order.getOrderItems();
        List<WangDianV2OrderItem> wangDianV2OrderItems = new ArrayList<>();

        int count = 0;
        for (OrderItem orderItem : orderItems) {

            WangDianV2OrderItem wangDianV2OrderItem = new WangDianV2OrderItem();
            wangDianV2OrderItem.setOid(orderItem.getOrderId() + count);
            wangDianV2OrderItem.setNum(orderItem.getNum());
            wangDianV2OrderItem.setPrice(orderItem.getPrice());
            wangDianV2OrderItem.setStatus(30);// 代发货
            wangDianV2OrderItem.setRefundStatus(0);//无退款
            wangDianV2OrderItem.setGoodsId(orderItem.getGoodBn());// TODO: 2017-02-24
            wangDianV2OrderItem.setSpecId(orderItem.getProductBn());// TODO: 2017-02-24
            wangDianV2OrderItem.setGoodsNo(orderItem.getGoodBn());
            wangDianV2OrderItem.setSpecNo(orderItem.getProductBn());
            wangDianV2OrderItem.setGoodsName(orderItem.getName());// TODO: 2017-02-24
            wangDianV2OrderItem.setSpecName(orderItem.getName());
            wangDianV2OrderItem.setAdjustAmount("0");
            wangDianV2OrderItem.setDiscount("0");
            wangDianV2OrderItem.setShareDiscount("0");
            wangDianV2OrderItem.setCid("");

            wangDianV2OrderItems.add(wangDianV2OrderItem);
        }
        ;

        wangDianV2Order.setOrderList(wangDianV2OrderItems);
        return wangDianV2Order;
    }

    private EventResult orderPush(Map<String, Object> requestMap, WangDianV2SysData wangDianV2SysData) throws UnsupportedEncodingException {

        HttpResult httpResult = HttpClientUtil.getInstance().post(wangDianV2SysData.getRequestUrl() + "/openapi2/trade_push.php", requestMap);
        if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
            JSONObject resp = JSON.parseObject(httpResult.getHttpContent());
            if (resp.getString("code").equals("0")) {
                return EventResult.resultWith(EventResultEnum.SUCCESS);
            } else {
                JSONArray jsonArray = JSON.parseArray(resp.getString("message"));
                JSONArray resultMsg = new JSONArray();

                for (Object o : jsonArray) {
                    JSONObject obj = JSON.parseObject(o.toString());
                    JSONObject resultObj = new JSONObject();
                    resultObj.put("tid", obj.getString("tid"));
                    resultObj.put("error", URLDecoder.decode(obj.getString("error"), "utf-8"));
                    resultMsg.add(resultObj);
                }
                return EventResult.resultWith(EventResultEnum.ERROR, resultMsg.toJSONString(), null);
            }
        } else {
            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
        }
    }

    @Override
    public EventResult queryOrder(WangDianV2OrderSearch wangDianV2OrderSearch, WangDianV2SysData wangDianV2SysData) {
        try {
            Date now = new Date();
            // 构建请求参数
            Map<String, Object> requestMap = new TreeMap<>();

            // 系统级参数
            requestMap.put("sid", wangDianV2SysData.getWangdianv2Sid());
            requestMap.put("appkey", wangDianV2SysData.getAppKey());
            requestMap.put("shop_no", wangDianV2SysData.getShopNo());
            requestMap.put("timestamp", now.getTime() / 1000);

            // 应用级参数
            requestMap.put("status", wangDianV2OrderSearch.getStatus());
            requestMap.put("start_time", wangDianV2OrderSearch.getStartTime());
            requestMap.put("end_time", wangDianV2OrderSearch.getEndTime());
            requestMap.put("page_no", wangDianV2OrderSearch.getPageNo());
            requestMap.put("page_size", wangDianV2OrderSearch.getPageSize());
            requestMap.put("img_url", wangDianV2OrderSearch.getImgUrl());
            requestMap.put("trade_no", wangDianV2OrderSearch.getTradeNo());
            requestMap.put("warehouse_no", wangDianV2OrderSearch.getWarehouseNo());
            requestMap.put("goodstax", wangDianV2OrderSearch.getGoodstax());
            requestMap.put("has_logistics_no", wangDianV2OrderSearch.getHasLogisticsNo());
            requestMap.put("src", wangDianV2OrderSearch.getSrc());
            requestMap.put("src_tid", wangDianV2OrderSearch.getSrcTid());

            String sign = WangDianV2SignBuilder.buildSign(requestMap, wangDianV2SysData.getAppSecret());
            requestMap.put("sign", sign);
            log.info("order query request sign:" + sign);

            HttpResult httpResult = HttpClientUtil.getInstance().post(wangDianV2SysData.getRequestUrl() + "/openapi2/trade_query.php", requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject resp = JSON.parseObject(httpResult.getHttpContent());
                if (resp.getString("code").equals("0")) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS, resp);
                } else {
                    return EventResult.resultWith(EventResultEnum.ERROR, resp.getString("message"), null);
                }
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }

        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }
}
