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

package com.huobanplus.erpprovider.efast.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.efast.common.EFastConstant;
import com.huobanplus.erpprovider.efast.common.EFastSysData;
import com.huobanplus.erpprovider.efast.formatdata.EFastOrder;
import com.huobanplus.erpprovider.efast.formatdata.EFastOrderItem;
import com.huobanplus.erpprovider.efast.handler.EFastOrderHandler;
import com.huobanplus.erpprovider.efast.util.EFastUtil;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.model.Order;
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

@Component
public class EFastOrderHandlerImpl implements EFastOrderHandler {

    private static final Log log = LogFactory.getLog(EFastOrderHandlerImpl.class);

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        log.info("efast order:" + pushNewOrderEvent.getOrderInfoJson());

        Date now = new Date();
        Order order = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
        EFastSysData eFastSysData = JSON.parseObject(erpInfo.getSysDataJson(), EFastSysData.class);
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


        EFastOrder eFastOrder = convert2ErpOrder(order, eFastSysData);

        try {
            EventResult eventResult = orderPush(eFastOrder, eFastSysData, now);
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
            } else {
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                orderDetailSyncLog.setErrorMsg(eventResult.getResultMsg());
            }
            orderDetailSyncLogService.save(orderDetailSyncLog);
            log.info("EFastOrderHandlerImpl-pushOrder complete");
            return eventResult;
        } catch (Exception e) {
            log.info("EFastOrderHandlerImpl-pushOrder exception:" + e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    public EFastOrder convert2ErpOrder(Order order, EFastSysData eFastSysData) {
        EFastOrder eFastOrder = new EFastOrder();
        List<EFastOrderItem> eFastOrderItemList = new ArrayList<>();

        eFastOrder.setEFastOrderItemList(eFastOrderItemList);
        return eFastOrder;
    }

    private EventResult orderPush(EFastOrder eFastOrder, EFastSysData eFastSysData, Date now) throws UnsupportedEncodingException {
        Map<String, Object> requestMap = buildSystemRequestMap(EFastConstant.ADD_ORDER, eFastSysData, now);
        requestMap.put("info", JSON.toJSONString(eFastOrder));
        String sign = EFastUtil.buildSign(requestMap, eFastSysData.getAppSecret(), eFastSysData.getAppSecret());
        requestMap.put("sign", sign);

        HttpResult httpResult = HttpClientUtil.getInstance().get(eFastSysData.getRequestUrl(), requestMap);
        if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
            JSONObject resp = JSON.parseObject(httpResult.getHttpContent());
            if (resp.getInteger("code") == 0) {// 业务数据请求成功
                return EventResult.resultWith(EventResultEnum.SUCCESS);
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, resp.getString("msg"), null);
            }
        } else {
            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
        }
    }

    /**
     * 构建系统请求参数map
     *
     * @param appAct
     * @param eFastSysData
     * @param now
     * @return
     */
    private Map<String, Object> buildSystemRequestMap(String appAct, EFastSysData eFastSysData, Date now) {
        Map<String, Object> requestMap = new TreeMap<>();
        requestMap.put("app_act", appAct);
        requestMap.put("app_nick", eFastSysData.getAppNick());
        requestMap.put("app_key ", eFastSysData.getAppKey());
        requestMap.put("timestamp", StringUtil.DateFormat(now, StringUtil.TIME_PATTERN));
        requestMap.put("format", "json");
        requestMap.put("version", "2.0");
        requestMap.put("app_secret", eFastSysData.getAppSecret());

        return requestMap;
    }

}