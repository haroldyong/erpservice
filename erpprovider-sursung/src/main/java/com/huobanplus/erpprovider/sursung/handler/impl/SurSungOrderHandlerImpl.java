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
import com.huobanplus.erpservice.common.httputil.HttpClientUtil2;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderShipSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.ReturnRefundSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.ShipSyncDeliverInfo;
import com.huobanplus.erpservice.datacenter.model.*;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.datacenter.service.logs.OrderShipSyncLogService;
import com.huobanplus.erpservice.datacenter.service.logs.ReturnRefundSyncLogService;
import com.huobanplus.erpservice.datacenter.service.logs.ShipSyncDeliverInfoService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.*;
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
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ShipSyncDeliverInfoService shipSyncDeliverInfoService;
    @Autowired
    private ReturnRefundSyncLogService returnRefundSyncLogService;

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

    private SurSungOrder convert2ErpOrder(Order order, int shopId) {
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
            surSungOrderItem.setShopSkuId(item.getProductBn());
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

            PushDeliveryInfoEvent pushDeliveryInfoEvent = new PushDeliveryInfoEvent();
            pushDeliveryInfoEvent.setErpInfo(erpInfo);
            pushDeliveryInfoEvent.setErpUserInfo(erpUserInfo);
            pushDeliveryInfoEvent.setDeliveryInfo(orderDeliveryInfo);

            BatchDeliverEvent batchDeliverEvent = new BatchDeliverEvent();
            batchDeliverEvent.setErpInfo(erpInfo);
            batchDeliverEvent.setErpUserInfo(erpUserInfo);
            batchDeliverEvent.setOrderDeliveryInfoList(orderDeliveryInfoList);
            EventResult eventResult = erpUserHandler.handleEvent(batchDeliverEvent);

            OrderShipSyncLog orderShipSyncLog = new OrderShipSyncLog();
            orderShipSyncLog.setProviderType(erpInfo.getErpType());
            orderShipSyncLog.setUserType(erpUserInfo.getErpUserType());
            orderShipSyncLog.setCustomerId(erpUserInfo.getCustomerId());
            orderShipSyncLog.setTotalCount(1);

            orderShipSyncLog.setSyncTime(new Date());

            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {


                log.info("SurSungOrderHandlerImpl-logisticUpload: 发货同步失败 " + eventResult.getResultMsg());
                orderShipSyncLog.setSuccessCount(0);
                orderShipSyncLog.setFailedCount(1);
                orderShipSyncLog.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_FAILURE);
                orderShipSyncLog = orderShipSyncLogService.save(orderShipSyncLog);

                ShipSyncDeliverInfo shipSyncDeliverInfo = new ShipSyncDeliverInfo();
                shipSyncDeliverInfo.setOrderDeliveryInfo(orderDeliveryInfo);
                shipSyncDeliverInfo.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_FAILURE);
                shipSyncDeliverInfo.setOrderShipSyncLog(orderShipSyncLog);
                shipSyncDeliverInfoService.save(shipSyncDeliverInfo);

                return SurSungExceptionHandler.handleException(false, eventResult.getResultMsg());
            } else {

                BatchDeliverResult batchDeliverResult = (BatchDeliverResult) eventResult.getData();
                List<OrderDeliveryInfo> successOrder = batchDeliverResult.getSuccessOrders();
                List<OrderDeliveryInfo> failedOrder = batchDeliverResult.getFailedOrders();

                ShipSyncDeliverInfo shipSyncDeliverInfo = new ShipSyncDeliverInfo();

                if (successOrder.size() == 0) {
                    orderShipSyncLog.setSuccessCount(0);
                    orderShipSyncLog.setFailedCount(1);
                    orderShipSyncLog.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_FAILURE);
                    orderShipSyncLog = orderShipSyncLogService.save(orderShipSyncLog);

                    shipSyncDeliverInfo.setOrderDeliveryInfo(orderDeliveryInfo);
                    shipSyncDeliverInfo.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_FAILURE);
                    shipSyncDeliverInfo.setOrderShipSyncLog(orderShipSyncLog);
                    shipSyncDeliverInfoService.save(shipSyncDeliverInfo);

                    log.info("SurSungOrderHandlerImpl-logisticUpload: 发货同步失败 |errorMsg:" + failedOrder.get(0).getRemark());
                    return SurSungExceptionHandler.handleException(false, failedOrder.get(0).getRemark());
                } else {
                    orderShipSyncLog.setSuccessCount(1);
                    orderShipSyncLog.setFailedCount(0);
                    orderShipSyncLog.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_SUCCESS);
                    orderShipSyncLog = orderShipSyncLogService.save(orderShipSyncLog);

                    shipSyncDeliverInfo.setOrderDeliveryInfo(orderDeliveryInfo);
                    shipSyncDeliverInfo.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_SUCCESS);
                    shipSyncDeliverInfo.setOrderShipSyncLog(orderShipSyncLog);
                    shipSyncDeliverInfoService.save(shipSyncDeliverInfo);

                    log.info("SurSungOrderHandlerImpl-logisticUpload: 发货同步成功");
                    return SurSungExceptionHandler.handleException(true, null);
                }
            }

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
    public EventResult returnRefundUpload(PushAfterSaleEvent pushAfterSaleEvent) {
        try {
            Date now = new Date();
            int time = (int) (now.getTime() / 1000);
            EventResult eventResult = null;

            ERPInfo erpInfo = pushAfterSaleEvent.getErpInfo();
            SurSungSysData surSungSysData = JSON.parseObject(erpInfo.getSysDataJson(), SurSungSysData.class);
            ERPUserInfo erpUserInfo = pushAfterSaleEvent.getErpUserInfo();

            String afterSaleJson = pushAfterSaleEvent.getAfterSaleInfo();
            AfterSaleInfo afterSaleInfo = JSON.parseObject(afterSaleJson, AfterSaleInfo.class);
            if (afterSaleInfo.getShipStatus() == 0) {// 未发货 // TODO: 2016-12-29
                OrderDetailSyncLog orderDetailSyncLog = orderDetailSyncLogService.findByOrderId(afterSaleInfo.getOrderId());
                String orderJson = orderDetailSyncLog.getOrderInfoJson();
                Order order = JSON.parseObject(orderJson, Order.class);


                SurSungOrder surSungOrder = convert2ErpOrder(order, surSungSysData.getShopId());

                List<SurSungOrderItem> surSungOrderItems = new ArrayList<>();
                List<AfterSaleItem> afterSaleItems = afterSaleInfo.getItems();
                double refundAllAmount = 0.0;
                for (AfterSaleItem item : afterSaleItems) {

                    SurSungOrderItem surSungOrderItem = new SurSungOrderItem();
                    surSungOrderItem.setSkuId(item.getSkuId());
                    surSungOrderItem.setShopSkuId(item.getSkuId());
                    surSungOrderItem.setPropertiesValue(item.getProperties());
                    surSungOrderItem.setAmount(item.getAmount());
                    surSungOrderItem.setBasePrice(item.getAmount());
                    surSungOrderItem.setQty(item.getReturnNum());
                    surSungOrderItem.setName(item.getName());
                    surSungOrderItem.setOuterOiId(item.getOrderId());
                    surSungOrderItem.setRefundStatus("success");
                    surSungOrderItem.setRefundQty(item.getReturnNum());
                    refundAllAmount += item.getAmount();
                    surSungOrderItems.add(surSungOrderItem);
                }
                ;

                surSungOrder.setSurSungOrderItems(surSungOrderItems);
                surSungOrder.setPayAmount(surSungOrder.getPayAmount() - refundAllAmount);

                JSONArray jsonArray = new JSONArray();
                jsonArray.add(surSungOrder);
                String requestData = JSON.toJSONString(jsonArray);
                String requestUrl = SurSungUtil.createRequestUrl(SurSungConstant.ORDER_PUSH, time, surSungSysData);

                eventResult = orderPush(requestUrl, requestData);
            } else {
                List<AfterSaleItem> afterSaleItems = afterSaleInfo.getItems();
//
                List<SurSungReturnRefundItem> surSungReturnRefundItems = new ArrayList<>();
                if (afterSaleItems != null) {
                    for (AfterSaleItem item : afterSaleItems) {

                        SurSungReturnRefundItem surSungReturnRefundItem = new SurSungReturnRefundItem();
                        surSungReturnRefundItem.setOuterOiId(item.getOrderId());
                        surSungReturnRefundItem.setSkuId(item.getSkuId());
                        surSungReturnRefundItem.setQty(item.getReturnNum());
                        surSungReturnRefundItem.setAmount(item.getAmount());
                        surSungReturnRefundItem.setType("其他");
                        surSungReturnRefundItems.add(surSungReturnRefundItem);
                    }
                }

                SurSungReturnRefund surSungReturnRefund = new SurSungReturnRefund();
                surSungReturnRefund.setShopId(surSungSysData.getShopId());
                surSungReturnRefund.setOuterAsId(afterSaleInfo.getOrderId());
                surSungReturnRefund.setSoId(afterSaleInfo.getOrderId());
                surSungReturnRefund.setType("其他");
                surSungReturnRefund.setLogiCompany(afterSaleInfo.getLogiCompany());
                surSungReturnRefund.setLogiNo(afterSaleInfo.getLogiNo());
                surSungReturnRefund.setShopStatus(SurSungConstant.AFTER_STATUS[afterSaleInfo.getAfterStatus()]);
                surSungReturnRefund.setRemark(afterSaleInfo.getRemark());
                surSungReturnRefund.setTotalAmount(afterSaleInfo.getTotalAmount());
                surSungReturnRefund.setRefund(afterSaleInfo.getRefund());
                surSungReturnRefund.setPayment(afterSaleInfo.getPayment());
                surSungReturnRefund.setItems(surSungReturnRefundItems);

                JSONArray jsonArray = new JSONArray();
                jsonArray.add(surSungReturnRefund);
                String requestData = JSON.toJSONString(jsonArray);
                String requestUrl = SurSungUtil.createRequestUrl(SurSungConstant.AFTERSALE_UPLOAD, time, surSungSysData);

                eventResult = returnRefundOrderPush(requestUrl, requestData);

            }


            ReturnRefundSyncLog returnRefundSyncLog = returnRefundSyncLogService.findByOrderId(afterSaleInfo.getOrderId());
            if (returnRefundSyncLog == null) {
                returnRefundSyncLog = new ReturnRefundSyncLog();
                returnRefundSyncLog.setCreateTime(now);
            }
            returnRefundSyncLog.setCustomerId(erpUserInfo.getCustomerId());
            returnRefundSyncLog.setProviderType(erpInfo.getErpType());
            returnRefundSyncLog.setUserType(erpUserInfo.getErpUserType());
            returnRefundSyncLog.setOrderId(afterSaleInfo.getOrderId());
            returnRefundSyncLog.setReturnInfoJson(afterSaleJson);
            returnRefundSyncLog.setErpSysData(erpInfo.getSysDataJson());
            returnRefundSyncLog.setSyncTime(now);

            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                returnRefundSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
            } else {
                returnRefundSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                returnRefundSyncLog.setErrorMsg(eventResult.getResultMsg());
            }
            returnRefundSyncLogService.save(returnRefundSyncLog);
            return eventResult;

        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR);
        }
    }

    private EventResult returnRefundOrderPush(String requestUrl, String requestData) {
        try {
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
            HttpResult httpResult = HttpClientUtil2.getInstance().post(requestUrl, requestData);
            System.out.println("content:" + httpResult.getHttpContent());
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                SurSungOrderSearchResult surSungOrderSearchResult = JSON.parseObject(httpResult.getHttpContent(),
                        SurSungOrderSearchResult.class);
                if (0 == surSungOrderSearchResult.getCode()) {
                    // 处理成功
                    log.info("SurSungOrderHandlerImpl-queryChannelOrder: 查询订单成功");
                    return EventResult.resultWith(EventResultEnum.SUCCESS, surSungOrderSearchResult);
                } else {
                    // 处理失败
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

    @Override
    public EventResult cancelOrder(CancelOrderEvent cancelOrderEvent) {
        Date now = new Date();
        int time = (int) (now.getTime() / 1000);

        String orderId = cancelOrderEvent.getOrderId();
        ERPInfo erpInfo = cancelOrderEvent.getErpInfo();
        SurSungSysData surSungSysData = JSON.parseObject(erpInfo.getSysDataJson(), SurSungSysData.class);

        JSONObject cancelOrderObj = new JSONObject();
        cancelOrderObj.put("shop_id", surSungSysData.getShopId());
        cancelOrderObj.put("so_id", orderId);
        cancelOrderObj.put("remark", "");

        JSONArray cancelOrderArray = new JSONArray();
        cancelOrderArray.add(cancelOrderObj);

        try {
            String requestData = JSON.toJSONString(cancelOrderArray);
            String requestUrl = SurSungUtil.createRequestUrl(SurSungConstant.ORDER_CANCEL_TO_ERP, time, surSungSysData);
            HttpResult httpResult = HttpClientUtil.getInstance().post(requestUrl, requestData);

            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject respJson = JSONObject.parseObject(httpResult.getHttpContent());
                if (respJson.getBoolean("issuccess")) {
                    log.info("SurSungOrderHandlerImpl-cancelOrder:取消订单成功");
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                } else {
                    log.info("SurSungOrderHandlerImpl-cancelOrder:取消订单失败 " + respJson.getString("msg"));
                    return EventResult.resultWith(EventResultEnum.ERROR, respJson.getString("msg"), null);
                }

            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }

        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }
}
