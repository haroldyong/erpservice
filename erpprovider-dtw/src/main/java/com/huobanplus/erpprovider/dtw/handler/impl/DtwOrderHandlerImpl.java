package com.huobanplus.erpprovider.dtw.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwOrder;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwOrderItem;
import com.huobanplus.erpprovider.dtw.handler.DtwOrderHandler;
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

import java.util.*;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Component
public class DtwOrderHandlerImpl implements DtwOrderHandler {

    private static final Log log = LogFactory.getLog(DtwOrderHandlerImpl.class);

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        try {
            Order order = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
            ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
            DtwSysData dtwSysData = JSON.parseObject(erpInfo.getSysDataJson(), DtwSysData.class);
            ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
            Map<String, Object> requestMap = new HashMap<>();
            DtwOrder dtwOrder = new DtwOrder();
            dtwOrder.setPassKey(dtwSysData.getPassKey());
            dtwOrder.setMsgid("1");
            dtwOrder.setPayCompanyCode("001");
            dtwOrder.setPayNumber("100001");
            dtwOrder.setOrderNo("201625621535");
            dtwOrder.setLogisCompanyName("tefaffasdf");
            dtwOrder.setLogisCompanyCode("logisCompanyCode");
            dtwOrder.setPurchaserId("123");
            dtwOrder.setShipper("wuxiongliu");
            dtwOrder.setShipperAddress("zhejiang");
            dtwOrder.setShipperCountry("china");
            dtwOrder.setConsignee("wuxiongliu2");
            dtwOrder.setConsigneePro("zhejiang");
            dtwOrder.setConsigneeCity("hangzhou");
            dtwOrder.setConsigneeDistrict("binjiangqu");
            dtwOrder.setConsigneeAdd("testaddress");
            dtwOrder.setConsigneeCountry("china");
            dtwOrder.setECommerceCode(dtwSysData.getECommerceCode());
            dtwOrder.setECommerceName(dtwSysData.getECommerceName());
            dtwOrder.setConsigneeMobile("18705153967");
            dtwOrder.setConsigneeTel("18705153967");


            List<DtwOrderItem> dtwOrderItemList = new ArrayList<>();
            List<OrderItem> orderItemList = order.getOrderItems();
            orderItemList.forEach(orderItem -> {
                DtwOrderItem dtwOrderItem = new DtwOrderItem();
                dtwOrderItem.setPartno("test");
                orderItem.getProductBn();
                dtwOrderItem.setPartName("testtt");
                orderItem.getName();
                dtwOrderItem.setUnit("test");
                dtwOrderItem.setCurrency("test");

                dtwOrderItemList.add(dtwOrderItem);
            });

            dtwOrder.setDtwOrderItems(dtwOrderItemList);
            requestMap.put("data", JSON.toJSONString(dtwOrder));

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

            EventResult eventResult = orderPush(requestMap, dtwSysData);
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
            } else {
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                orderDetailSyncLog.setErrorMsg(eventResult.getResultMsg());
            }
            orderDetailSyncLogService.save(orderDetailSyncLog);
            return eventResult;

        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    private EventResult orderPush(Map<String, Object> requestMap, DtwSysData dtwSysData) {

        HttpResult httpResult = HttpClientUtil.getInstance().post(dtwSysData.getRequestUrl() + "/QBIntegratedOrder", requestMap);
        System.out.println("\n********************************");
        System.out.println(httpResult.getHttpContent());
        System.out.println("********************************");
        if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
            JSONObject result = JSON.parseObject(httpResult.getHttpContent());
            if (result.getString("ErrCode").equals("000")) {
                return EventResult.resultWith(EventResultEnum.SUCCESS);
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, result.getString("ErrMsg"), null);
            }
        } else {
            return EventResult.resultWith(EventResultEnum.ERROR, "服务器请求失败", null);
        }
    }

}
