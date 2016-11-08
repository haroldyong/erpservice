/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.wangdian.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.wangdian.common.WangDianSysData;
import com.huobanplus.erpprovider.wangdian.formatdata.*;
import com.huobanplus.erpprovider.wangdian.handler.WangDianOrderHandler;
import com.huobanplus.erpprovider.wangdian.util.WangDianSignUtil;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.model.BatchDeliverResult;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.BatchDeliverEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
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
public class WangDianOrderHandlerImpl implements WangDianOrderHandler {

    private static final Log log = LogFactory.getLog(WangDianOrderHandlerImpl.class);

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
        WangDianSysData wangDianSysData = JSON.parseObject(erpInfo.getSysDataJson(), WangDianSysData.class);
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

            WangDianOrder wangDianOrder = convertOrder2ErpOrder(orderInfo, wangDianSysData.getWarehouseNo(), wangDianSysData.getShopName());// TODO: 2016-11-07
            EventResult eventResult = orderPush(wangDianOrder, wangDianSysData);
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
            } else {
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                orderDetailSyncLog.setErrorMsg(eventResult.getResultMsg());
            }

            orderDetailSyncLogService.save(orderDetailSyncLog);
            log.info("WangDianOrderHandlerImpl-pushOrder: 推送订单完成");
            return eventResult;

        } catch (Exception e) {
            log.error("WangDianOrderHandlerImpl-pushOrder:" + e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    private WangDianOrder convertOrder2ErpOrder(Order order, String warehouseNo, String shopName) {
        WangDianOrder wangDianOrder = new WangDianOrder();

        wangDianOrder.setOutInFlag(3);
        wangDianOrder.setIFOrderCode(order.getOrderId());
        wangDianOrder.setWarehouseNO(warehouseNo);
        wangDianOrder.setRemark(order.getRemark());// 商家留言?
        wangDianOrder.setTheCause("");
        wangDianOrder.setProviderNO("");
        wangDianOrder.setProviderName("");
        wangDianOrder.setLinkMan("");
        wangDianOrder.setLinkManTel("");
        wangDianOrder.setLinkManAdr("");
        wangDianOrder.setRegOperatorNO("");
        wangDianOrder.setGoodsTotal(order.getFinalAmount() - order.getCostFreight());
        wangDianOrder.setFavourableTotal(order.getPmtAmount());
//        wangDianOrder.setOtherFee(0);
        wangDianOrder.setCodFlag(0);// 0 不需要货到付款
        wangDianOrder.setOrderPay(order.getFinalAmount());
        wangDianOrder.setLogisticsPay(order.getCostFreight());
        wangDianOrder.setLogisticsCode(order.getLogiCode());
        wangDianOrder.setShopName(shopName);
        wangDianOrder.setNickName(order.getUserLoginName());
        wangDianOrder.setBuyerName(order.getShipName());
        wangDianOrder.setBuyerPostCode(order.getShipZip());
        wangDianOrder.setBuyerTel(order.getShipMobile());
        wangDianOrder.setBuyerProvince(order.getProvince());
        wangDianOrder.setBuyerCity(order.getCity());
        wangDianOrder.setBuyerDistrict(order.getDistrict());
        wangDianOrder.setBuyerAdr(order.getShipAddr());
        wangDianOrder.setBuyerEmail(order.getShipEmail());
        wangDianOrder.setNeedInvoice(0);// 0 不需要
        wangDianOrder.setInvoiceTitle("");
        wangDianOrder.setInvoiceContent("");
        wangDianOrder.setItemCount(order.getOrderItems().size());
        wangDianOrder.setPayTime(order.getPayTime());
        wangDianOrder.setTradeTime(order.getCreateTime());
        wangDianOrder.setChargeID(order.getPayNumber());


        List<OrderItem> orderItemList = order.getOrderItems();

        List<WangDianOrderItem> wangDianOrderItems = new ArrayList<>();

        orderItemList.forEach(item -> {
            WangDianOrderItem wangDianOrderItem = new WangDianOrderItem();

            wangDianOrderItem.setSkuCode(item.getProductBn());
            wangDianOrderItem.setSkuName(item.getName());
            wangDianOrderItem.setSkuPrice(item.getPrice());
//            wangDianOrderItem.setDiscount(1);
            wangDianOrderItem.setTotal(item.getAmount());
            wangDianOrderItem.setQty(item.getNum());
            wangDianOrderItem.setItemRemark("");

            wangDianOrderItems.add(wangDianOrderItem);

        });
        WangDianItemList wangDianItemList = new WangDianItemList();
        wangDianItemList.setItemList(wangDianOrderItems);
        wangDianOrder.setOrderItem(wangDianItemList);

        return wangDianOrder;
    }

    private EventResult orderPush(WangDianOrder wangDianOrder, WangDianSysData wangDianSysData) {
        try {
            String sign = WangDianSignUtil.buildSign(JSON.toJSONString(wangDianOrder), wangDianSysData.getAppKey());
//            String requestData = createRequestData("NewOrder",wangDianSysData,sign,JSON.toJSONString(wangDianOrder));
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("Method", "NewOrder");
            requestMap.put("SellerID", "dev5");
            requestMap.put("InterfaceID", "youzuntest");
            requestMap.put("Sign", sign);
            requestMap.put("Content", JSON.toJSONString(wangDianOrder));


//            System.out.println("\nrequestData:"+requestData);
            System.out.println("\nrequestMap:" + requestMap);
            HttpResult httpResult = HttpClientUtil.getInstance().post(wangDianSysData.getRequestUrl(), requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                String content = httpResult.getHttpContent();

                JSONObject obj = JSON.parseObject(content);
                if (obj.getString("ResultCode").equals("0")) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                } else {
                    return EventResult.resultWith(EventResultEnum.ERROR, obj.getString("ResultMsg"), null);
                }
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return EventResult.resultWith(EventResultEnum.ERROR);
        }
    }

    private String createRequestData(String method, WangDianSysData wangDianSysData, String sign, String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("Method=").append(method)
                .append("&").append("SellerID=").append(wangDianSysData.getSellerId())
                .append("&").append("InterfaceID=").append(wangDianSysData.getInterfaceId())
                .append("&").append("Sign=").append(sign)
                .append("&").append("Content=").append(content);
        return sb.toString();
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
    private void saveLog(Order orderInfo, ERPUserInfo erpUserInfo, ERPInfo erpInfo, PushNewOrderEvent pushNewOrderEvent, boolean isSuccess, String errorMsg) {
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
        if (errorMsg != null) {
            orderDetailSyncLog.setErrorMsg(errorMsg);
        }

        if (isSuccess) {
            orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
        } else {
            orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
        }

        orderDetailSyncLogService.save(orderDetailSyncLog);
    }

    @Override
    public EventResult deliverOrder(List<WangDianLogistic> wangDianLogistics, ERPUserInfo erpUserInfo, ERPInfo erpInfo) {

        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (erpUserHandler == null) {
            return null;
        }

        List<OrderDeliveryInfo> orderDeliveryInfoList = new ArrayList<>();

        wangDianLogistics.forEach(wangDianLogistic -> {
            OrderDeliveryInfo orderDeliveryInfo = new OrderDeliveryInfo();
            orderDeliveryInfo.setLogiNo(wangDianLogistic.getPostId());
            orderDeliveryInfo.setOrderId(wangDianLogistic.getOrderCode());
            orderDeliveryInfo.setLogiCode(wangDianLogistic.getLogisticCode());
            orderDeliveryInfo.setLogiName(wangDianLogistic.getLogisticName());
            orderDeliveryInfoList.add(orderDeliveryInfo);
        });


        BatchDeliverEvent batchDeliverEvent = new BatchDeliverEvent();
        batchDeliverEvent.setErpInfo(erpInfo);
        batchDeliverEvent.setErpUserInfo(erpUserInfo);
        batchDeliverEvent.setOrderDeliveryInfoList(orderDeliveryInfoList);
        EventResult eventResult = erpUserHandler.handleEvent(batchDeliverEvent);

        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            BatchDeliverResult batchDeliverResult = (BatchDeliverResult) eventResult.getData();
            List<OrderDeliveryInfo> successOrders = batchDeliverResult.getSuccessOrders();
            List<OrderDeliveryInfo> failedOrders = batchDeliverResult.getFailedOrders();

            List<LogisticResponse> logisticResponses = new ArrayList<>();
            successOrders.forEach(successOrder -> {
                LogisticResponse logisticResponse = new LogisticResponse();
                logisticResponse.setOrderCode(successOrder.getOrderId());
                logisticResponse.setResultCode(0);
                logisticResponse.setResultMsg("");
                logisticResponses.add(logisticResponse);
            });

            failedOrders.forEach(failedOrder -> {
                LogisticResponse logisticResponse = new LogisticResponse();
                logisticResponse.setOrderCode(failedOrder.getOrderId());
                logisticResponse.setResultCode(1);
                logisticResponse.setResultMsg("");
                logisticResponses.add(logisticResponse);
            });

            JSONObject respJson = new JSONObject();
            respJson.put("ResultCode", 0);
            respJson.put("ResultMsg", "");
            respJson.put("Result", JSON.parseArray(JSON.toJSONString(logisticResponses)));

            JSONObject response = new JSONObject();
            response.put("ResultList", respJson);
            return EventResult.resultWith(EventResultEnum.SUCCESS, response);

        } else {
            JSONObject respJson = new JSONObject();
            respJson.put("ResultCode", 1);
            respJson.put("ResultMsg", eventResult.getResultMsg());

            JSONObject response = new JSONObject();
            response.put("ResultList", respJson);

            return EventResult.resultWith(EventResultEnum.ERROR, response);
        }

    }
}
