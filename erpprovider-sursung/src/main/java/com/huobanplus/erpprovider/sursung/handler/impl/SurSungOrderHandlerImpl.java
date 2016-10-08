/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.sursung.common.SurSungConstant;
import com.huobanplus.erpprovider.sursung.common.SurSungEnum;
import com.huobanplus.erpprovider.sursung.common.SurSungSysData;
import com.huobanplus.erpprovider.sursung.exceptionhandler.SurSungExceptionHandler;
import com.huobanplus.erpprovider.sursung.formatdata.*;
import com.huobanplus.erpprovider.sursung.handler.SurSungOrderHandler;
import com.huobanplus.erpprovider.sursung.search.SurSungLogisticSearch;
import com.huobanplus.erpprovider.sursung.search.SurSungOrderSearch;
import com.huobanplus.erpprovider.sursung.search.SurSungOrderSearchResult;
import com.huobanplus.erpprovider.sursung.util.SurSungUtil;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.model.ProInventoryInfo;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.BatchDeliverEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.sync.SyncInventoryEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Component
public class SurSungOrderHandlerImpl implements SurSungOrderHandler {

    private static final Log log = LogFactory.getLog(SurSungOrderHandlerImpl.class);

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;
    @Autowired
    private ERPRegister erpRegister;

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {

        Order orderInfo = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
        log.info("order:" + pushNewOrderEvent.getOrderInfoJson());
        Date now = new Date();
        int time = (int) (now.getTime() / 1000);

        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
        SurSungSysData surSungSysData = JSON.parseObject(erpInfo.getSysDataJson(), SurSungSysData.class);
        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();

        SurSungOrder surSungOrder = convert2ErpOrder(orderInfo, surSungSysData.getShopId());
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(surSungOrder);


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

            String requestData = JSON.toJSONString(jsonArray);
            String requestUrl = SurSungUtil.createRequestUrl(SurSungConstant.ORDER_PUSH, time, surSungSysData);
            EventResult eventResult = orderPush(requestUrl, requestData);
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
            } else {
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                orderDetailSyncLog.setErrorMsg(eventResult.getResultMsg());
            }
            orderDetailSyncLogService.save(orderDetailSyncLog);
            log.info("SurSungOrderHandlerImpl-pushOrder: 推送订单完成");
            return eventResult;

        } catch (Exception e) {
            log.error("SurSungOrderHandlerImpl-pushOrder:" + e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    private SurSungOrder convert2ErpOrder(com.huobanplus.erpservice.datacenter.model.Order order, int shopId) {
        SurSungOrder surSungOrder = new SurSungOrder();
        surSungOrder.setShopId(shopId);
        surSungOrder.setSoId(order.getOrderId());
        surSungOrder.setOrderDate(order.getPayTime());
        surSungOrder.setShopStatus(SurSungEnum.OrderStatus.WAIT_SELLER_SEND_GOODS.toString());
        surSungOrder.setShopBuyerId(order.getUserLoginName());
        surSungOrder.setReceiverState(order.getProvince());
        surSungOrder.setReceiverCity(order.getCity());
        surSungOrder.setReceiverDistrict(order.getDistrict());
        surSungOrder.setReceiverAddress(order.getShipAddr());
        surSungOrder.setReceiverName(order.getShipName());
        surSungOrder.setReceiverPhone(order.getShipTel());
        surSungOrder.setReceiverMobile(order.getShipMobile());
//        surSungOrder.setSendDate();
        surSungOrder.setPayAmount(order.getFinalAmount());
        surSungOrder.setFreight(order.getCostFreight());
        surSungOrder.setBuyerMessage(order.getMemo());
        surSungOrder.setRemark(order.getRemark());
//        surSungOrder.setInvoiceTitle(order.getTaxCompany());
        surSungOrder.setCod(false);
        surSungOrder.setLogiNo(order.getLogiNo());
        surSungOrder.setLogiCompany(order.getLogiName());
//        surSungOrder.setQuestionDesc("");
//        surSungOrder.setTag(1);
//        surSungOrder.setSellerFlag(1);

        List<SurSungOrderItem> surSungOrderItems = new ArrayList<>();
        order.getOrderItems().forEach(item -> {
            SurSungOrderItem surSungOrderItem = new SurSungOrderItem();
            surSungOrderItem.setSkuId(item.getProductBn());
//            surSungOrderItem.setShopSkuId("");
            surSungOrderItem.setPropertiesValue(item.getStandard());
            surSungOrderItem.setAmount(item.getAmount());
            surSungOrderItem.setBasePrice(item.getPrice());
            surSungOrderItem.setQty(item.getNum());
            surSungOrderItem.setName(item.getName());
            surSungOrderItem.setOuterOiId(item.getOrderId());
            surSungOrderItems.add(surSungOrderItem);
        });

        SursungPay sursungPay = new SursungPay();
        sursungPay.setAmount(order.getFinalAmount());
        sursungPay.setOuterPayId(order.getPayNumber());
        sursungPay.setPayDate(order.getPayTime());
        sursungPay.setPayment(order.getPaymentName());
//        sursungPay.setSellerAccount("wuxiongliu");
//        sursungPay.setBuyerAccount("wuxiongliu2");
        surSungOrder.setPay(sursungPay);

        surSungOrder.setSurSungOrderItems(surSungOrderItems);
        return surSungOrder;
    }

    private EventResult orderPush(String requestUrl, String requestData) {
        HttpResult httpResult = HttpClientUtil.getInstance().post(requestUrl, requestData);
        if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
            JSONObject respJson = JSONObject.parseObject(httpResult.getHttpContent());
            if (respJson.getBoolean("issuccess")) {
                log.info("SurSungOrderHandlerImpl-orderPush:推送订单成功");
                return EventResult.resultWith(EventResultEnum.SUCCESS);
            } else {
                log.info("SurSungOrderHandlerImpl-orderPush:推送订单失败 " + respJson.getString("msg"));
                return EventResult.resultWith(EventResultEnum.ERROR, respJson.getString("msg"), null);
            }

        } else {
            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
        }
    }

    @Override
    public EventResult logisticSearch(SurSungLogisticSearch surSungDeliverySearch, SurSungSysData surSungSysData) {
        try {

            int time = (int) (new Date().getTime() / 1000);
            String requestData = JSON.toJSONString(surSungDeliverySearch);
            String requestUrl = SurSungUtil.createRequestUrl(SurSungConstant.LOGISTIC_QUERY, time, surSungSysData);

            HttpResult httpResult = HttpClientUtil.getInstance().post(requestUrl, requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                String result = httpResult.getHttpContent();
                SurSungLogisticSearchResult surSungLogistic = JSON.parseObject(result, SurSungLogisticSearchResult.class);
                return EventResult.resultWith(EventResultEnum.SUCCESS, surSungLogistic);
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }

        } catch (Exception e) {
            log.error("SurSungOrderHandlerImpl-logisticSearch: " + e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    @Override
    public EventResult logisticUpload(SurSungLogistic surSungLogistic, ERPUserInfo erpUserInfo, ERPInfo erpInfo) {
        try {
            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
            if (erpUserHandler == null) {
                return SurSungExceptionHandler.handleException(false, "{\"code\":0,\"msg\":\"未找到数据源信息\" }");
            }

            List<OrderDeliveryInfo> orderDeliveryInfoList = new ArrayList<>();

            OrderDeliveryInfo orderDeliveryInfo = new OrderDeliveryInfo();
            orderDeliveryInfo.setOrderId(surSungLogistic.getSoId());
//            orderDeliveryInfo.setLogiCode(surSungLogistic.getLogiNo());
            orderDeliveryInfo.setLogiName(surSungLogistic.getLogisticsCompany());
//            orderDeliveryInfo.setFreight(0.0);
            orderDeliveryInfo.setLogiNo(surSungLogistic.getLogiNo());

            List<SurSungLogisticItem> items = surSungLogistic.getItems();
            StringBuilder sb = new StringBuilder();
            items.forEach(item -> {
                sb.append(item.getSku_id()).append(",").append(item.getQty()).append("|");
            });
            orderDeliveryInfo.setDeliverItemsStr(sb.toString());
            orderDeliveryInfoList.add(orderDeliveryInfo);

            BatchDeliverEvent batchDeliverEvent = new BatchDeliverEvent();
            batchDeliverEvent.setErpInfo(erpInfo);
            batchDeliverEvent.setErpUserInfo(erpUserInfo);
            batchDeliverEvent.setOrderDeliveryInfoList(orderDeliveryInfoList);
            EventResult eventResult = erpUserHandler.handleEvent(batchDeliverEvent);

            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {

                log.info("SurSungOrderHandlerImpl-logisticUpload: 发货同步失败 " + eventResult.getResultMsg());
                return SurSungExceptionHandler.handleException(false, eventResult.getResultMsg());
            }

            log.info("SurSungOrderHandlerImpl-logisticUpload: 发货同步成功");
            return SurSungExceptionHandler.handleException(true, null);

        } catch (Exception e) {
            log.error("SurSungOrderHandlerImpl-logisticUpload: " + e.getMessage());
            return SurSungExceptionHandler.handleException(false, e.getMessage());
        }

    }

    @Override
    public EventResult inventoryUpload(List<SurSungInventory> surSungInventoryList, ERPUserInfo erpUserInfo, ERPInfo erpInfo) {
        try {
            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
            if (erpUserHandler == null) {
                return SurSungExceptionHandler.handleException(false, "{\"code\":0,\"msg\":\"未找到数据源信息\" }");
            }

            SyncInventoryEvent syncInventoryEvent = new SyncInventoryEvent();
            List<ProInventoryInfo> inventoryInfoList = new ArrayList<>();
            surSungInventoryList.forEach(surSungInventory -> {
                ProInventoryInfo proInventoryInfo = new ProInventoryInfo();
                proInventoryInfo.setProductBn(surSungInventory.getSkuId());
                proInventoryInfo.setInventory(surSungInventory.getQty());
                inventoryInfoList.add(proInventoryInfo);
            });
            syncInventoryEvent.setErpUserInfo(erpUserInfo);
            syncInventoryEvent.setErpInfo(erpInfo);
            syncInventoryEvent.setInventoryInfoList(inventoryInfoList);
            EventResult eventResult = erpUserHandler.handleEvent(syncInventoryEvent);


            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {

                log.info("SurSungOrderHandlerImpl-inventoryUpload: 库存同步失败" + eventResult.getResultMsg());
                return SurSungExceptionHandler.handleException(false, eventResult.getResultMsg());
            }

            log.info("SurSungOrderHandlerImpl-inventoryUpload: 库存同步成功");
            return SurSungExceptionHandler.handleException(true, null);
        } catch (Exception e) {

            log.error("SurSungOrderHandlerImpl-inventoryUpload: " + e.getMessage());
            return SurSungExceptionHandler.handleException(false, e.getMessage());
        }
    }

    @Override
    public EventResult returnRefundUpload(SurSungReturnRefund surSungReturnRefund, SurSungSysData surSungSysData) {
        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(surSungReturnRefund);
            Date now = new Date();
            int time = (int) (now.getTime() / 1000);

            String requestData = JSON.toJSONString(jsonArray);
            String requestUrl = SurSungUtil.createRequestUrl(SurSungConstant.AFTERSALE_UPLOAD, time, surSungSysData);
            HttpResult httpResult = HttpClientUtil.getInstance().post(requestUrl, requestData);
            if (HttpStatus.SC_OK == httpResult.getHttpStatus()) {
                JSONObject respJson = JSONObject.parseObject(httpResult.getHttpContent());
                if (respJson.getBoolean("issuccess")) {
                    log.info("SurSungOrderHandlerImpl-returnRefundUpload: 退货退款成功");
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                } else {
                    log.info("SurSungOrderHandlerImpl-returnRefundUpload: 退货退款失败" + respJson.getString("msg"));
                    return EventResult.resultWith(EventResultEnum.ERROR, respJson.getString("msg"), null);
                }
            } else {
                log.info("SurSungOrderHandlerImpl-returnRefundUpload: 请求服务器失败" + httpResult.getHttpContent());
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }

        } catch (Exception e) {
            log.error("SurSungOrderHandlerImpl-returnRefundUpload: " + e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }

    }

    @Override
    public EventResult queryChannelOrder(SurSungOrderSearch surSungOrderSearch, SurSungSysData surSungSysData) {
        try {
            Date now = new Date();
            int time = (int) (now.getTime() / 1000);
            String requestData = JSON.toJSONString(surSungOrderSearch);
            String requestUrl = SurSungUtil.createRequestUrl(SurSungConstant.ORDERS_QUERY, time, surSungSysData);
            HttpResult httpResult = HttpClientUtil.getInstance().post(requestUrl, requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                SurSungOrderSearchResult surSungOrderSearchResult = JSON.parseObject(httpResult.getHttpContent(),
                        SurSungOrderSearchResult.class);
                if (0 == surSungOrderSearchResult.getCode()) {
                    // 处理成功
                    log.info("SurSungOrderHandlerImpl-queryChannelOrder: 查询订单成功");
//                    List<Order> orderList = convert2PlatformOrder(surSungSysData.getShopId(), surSungOrderSearchResult.getOrders());
//
                    return EventResult.resultWith(EventResultEnum.SUCCESS, surSungOrderSearchResult);
                } else {
                    // 处理失败s
                    log.info("SurSungOrderHandlerImpl-queryChannelOrder: " + surSungOrderSearchResult.getMsg());
                    return EventResult.resultWith(EventResultEnum.ERROR, surSungOrderSearchResult.getMsg(), null);
                }

            } else {
                log.info("SurSungOrderHandlerImpl-queryChannelOrder: " + httpResult.getHttpContent());
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }

        } catch (Exception e) {
            log.error("SurSungOrderHandlerImpl-queryChannelOrder: " + e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }


}
