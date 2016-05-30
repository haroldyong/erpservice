/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.common.EDBEnum;
import com.huobanplus.erpprovider.edb.formatedb.EDBCreateOrderInfo;
import com.huobanplus.erpprovider.edb.formatedb.EDBOrderDeliver;
import com.huobanplus.erpprovider.edb.formatedb.EDBOrderDetail;
import com.huobanplus.erpprovider.edb.formatedb.EDBOrderItem;
import com.huobanplus.erpprovider.edb.handler.BaseHandler;
import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpprovider.edb.search.EDBOrderSearch;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.httputil.HttpUtil;
import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.CancelOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushDeliveryInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 订单处理事件实现类
 * Created by allan on 2015/7/24.
 */
@Component
public class EDBOrderHandlerImpl extends BaseHandler implements EDBOrderHandler {
    private static final Log log = LogFactory.getLog(EDBOrderHandlerImpl.class);
    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        Order orderInfo = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);

        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
        EDBSysData sysData = JSON.parseObject(erpInfo.getSysDataJson(), EDBSysData.class);
        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
        EDBCreateOrderInfo edbCreateOrderInfo = new EDBCreateOrderInfo();
        edbCreateOrderInfo.setOutTid(orderInfo.getOrderId());
        edbCreateOrderInfo.setShopId(sysData.getShopId());
        edbCreateOrderInfo.setStorageId(Integer.parseInt(sysData.getStorageId()));
        edbCreateOrderInfo.setBuyerId(orderInfo.getUserLoginName());
        edbCreateOrderInfo.setBuyerMsg(orderInfo.getMemo());
        edbCreateOrderInfo.setSellerRemark(orderInfo.getRemark());
        edbCreateOrderInfo.setConsignee(orderInfo.getShipName());
        edbCreateOrderInfo.setAddress(orderInfo.getShipAddr());
        edbCreateOrderInfo.setPostcode(orderInfo.getShipZip());
        edbCreateOrderInfo.setTelephone(orderInfo.getShipTel());
        edbCreateOrderInfo.setMobilPhone(orderInfo.getShipMobile());
        String shipArea = orderInfo.getShipArea();
        if (!StringUtils.isEmpty(shipArea)) {
            String[] shipAreaArray = shipArea.split("/");
            edbCreateOrderInfo.setProvince(shipAreaArray[0]);
            if (shipAreaArray.length > 1) {
                edbCreateOrderInfo.setCity(shipAreaArray[1]);
                if (shipAreaArray.length > 2) {
                    edbCreateOrderInfo.setArea(shipAreaArray[2]);
                }
            }
        }
        edbCreateOrderInfo.setActualFreightGet(orderInfo.getCostFreight());
        edbCreateOrderInfo.setActual_RP(orderInfo.getFinalAmount());
        edbCreateOrderInfo.setExpress(sysData.getExpress());
        edbCreateOrderInfo.setProcessStatus(EnumHelper.getEnumName(EDBEnum.OrderStatusEnum.class, orderInfo.getOrderStatus()));
        edbCreateOrderInfo.setPayStatus(EnumHelper.getEnumName(EDBEnum.PayStatusEnum.class, orderInfo.getPayStatus()));
        edbCreateOrderInfo.setDeliverStatus(EnumHelper.getEnumName(EDBEnum.ShipStatusEnum.class, orderInfo.getShipStatus()));
        edbCreateOrderInfo.setOrderTotalMoney(orderInfo.getFinalAmount());
        edbCreateOrderInfo.setProductTotalMoney(orderInfo.getCostItem());
        edbCreateOrderInfo.setPayMethod(orderInfo.getPaymentName());
        edbCreateOrderInfo.setFavorableMoney(orderInfo.getPmtAmount());
        edbCreateOrderInfo.setOutExpressMethod(orderInfo.getLogiName());
        edbCreateOrderInfo.setOrderDate(orderInfo.getCreateTime());
        edbCreateOrderInfo.setPayDate(orderInfo.getPayTime());
        edbCreateOrderInfo.setWuLiu(orderInfo.getLogiName());
        edbCreateOrderInfo.setWuLiuNo(orderInfo.getLogiNo());
        edbCreateOrderInfo.setActualFreightPay(orderInfo.getCostFreight());
        List<EDBOrderItem> edbOrderItemList = new ArrayList<>();
        for (OrderItem orderItem : orderInfo.getOrderItems()) {
            EDBOrderItem edbOrderItem = new EDBOrderItem();
            edbOrderItem.setBarCode(orderItem.getProductBn());
            edbOrderItem.setProductTitle(orderItem.getName());
            edbOrderItem.setStandard(orderItem.getStandard());
            edbOrderItem.setOutPrice(orderItem.getAmount());
            edbOrderItem.setOrderGoodsNum(orderItem.getNum());
            edbOrderItem.setCostPrice(orderItem.getAmount());
            edbOrderItem.setShopId(sysData.getShopId());
            edbOrderItem.setOutTid(orderInfo.getOrderId());
            edbOrderItem.setOutBarCode(orderItem.getProductBn());
            edbOrderItem.setProductIntro(orderItem.getBrief());
            edbOrderItemList.add(edbOrderItem);
        }
        edbCreateOrderInfo.setProductInfos(edbOrderItemList);

        Date now = new Date();
        EventResult eventResult = this.orderPush(edbCreateOrderInfo, sysData);

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

        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
        } else {
            orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
            orderDetailSyncLog.setErrorMsg(eventResult.getResultMsg());
        }

        orderDetailSyncLogService.save(orderDetailSyncLog);
        return eventResult;
    }

    public EventResult obtainOrderList(EDBSysData sysData, EDBOrderSearch edbOrderSearch) {
        try {
            Map<String, Object> requestData = getSysRequestData(EDBConstant.GET_ORDER_INFO, sysData);
            //系统参数
            requestData.put("storage_id", edbOrderSearch.getStorageId());
            requestData.put("shopid", edbOrderSearch.getShopId());
            requestData.put("begin_time", edbOrderSearch.getBeginTime());
            requestData.put("end_time", edbOrderSearch.getEndTime());

            if (StringUtils.isEmpty(edbOrderSearch.getOrderId())) {
                requestData.put("date_type", edbOrderSearch.getDateType());
                requestData.put("page_no", edbOrderSearch.getPageIndex());
                requestData.put("page_size", EDBConstant.PAGE_SIZE);
                requestData.put("order_status", edbOrderSearch.getShipStatus().getName());
//            requestData.put("platform_status", edbOrderSearch.getPlatformStatus().getName());
                requestData.put("payment_status", edbOrderSearch.getPayStatus().getName());
//            requestData.put("proce_Status", edbOrderSearch.getProceStatus().getName());
            } else {
                requestData.put("out_tid", edbOrderSearch.getOrderId());
            }

            Map<String, Object> signMap = new TreeMap<>(requestData);
            String sign = getSign(signMap, sysData);
            requestData.put("sign", sign);

            HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getRequestUrl(), requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject jsonObject = JSON.parseObject(httpResult.getHttpContent()).getJSONObject("Success");

                if (jsonObject == null) {
                    return EventResult.resultWith(EventResultEnum.ERROR, jsonObject.getString("error_msg"), null);
                }
                return EventResult.resultWith(EventResultEnum.SUCCESS, jsonObject);
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }
        } catch (IOException ex) {
            return EventResult.resultWith(EventResultEnum.ERROR, ex.getMessage(), null);
        }

    }


    @Override
    public EventResult orderDeliver(PushDeliveryInfoEvent pushDeliveryInfoEvent) {
        try {
            EDBSysData sysData = JSON.parseObject(pushDeliveryInfoEvent.getErpInfo().getSysDataJson(), EDBSysData.class);
            OrderDeliveryInfo deliveryInfo = pushDeliveryInfoEvent.getDeliveryInfo();
            EDBOrderDeliver orderDeliver = new EDBOrderDeliver();
            orderDeliver.setOrderId(deliveryInfo.getOrderId());
            orderDeliver.setExpress(deliveryInfo.getLogiName());
            orderDeliver.setExpressNo(deliveryInfo.getLogiNo());

            String xmlValues = "<order>" + new XmlMapper().writeValueAsString(orderDeliver) + "</order>";

            Map<String, Object> requestData = getSysRequestData(EDBConstant.ORDER_DELIVER, sysData);
            requestData.put("xmlValues", xmlValues);
            requestData.put("sign", getSign(requestData, sysData));

            HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getRequestUrl(), requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {

                return EventResult.resultWith(EventResultEnum.SUCCESS);
            }

            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR.getResultCode(), EventResultEnum.ERROR.getResultMsg() + "--" + e.getMessage(), null);
        }
    }

    @Override
    public EventResult getOrderDetail(String orderId, ERPInfo erpInfo) {
        try {
            EDBSysData sysData = new ObjectMapper().readValue(erpInfo.getSysDataJson(), EDBSysData.class);
            Map<String, Object> requestData = getSysRequestData(EDBConstant.GET_ORDER_INFO, sysData);
            requestData.put("out_tid", orderId);
            Map<String, Object> signMap = new TreeMap<>(requestData);
            String beginTime = StringUtil.DateFormat(new Date(0), StringUtil.DATE_PATTERN);
            String endTime = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            requestData.put("begin_time", URLEncoder.encode(beginTime, StringUtil.UTF8));
            requestData.put("end_time", URLEncoder.encode(endTime, StringUtil.UTF8));
            signMap.put("begin_time", beginTime);
            signMap.put("end_time", endTime);

            requestData.put("sign", getSign(signMap, sysData));
            String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);
            if (responseData == null) {
                return EventResult.resultWith(EventResultEnum.ERROR);
            }
            JSONObject jsonObject = JSON.parseObject(responseData);
            //如果成功
            //得到EDBOrderDetail
            if (jsonObject.getJSONObject("Success") != null) {
                JSONArray jsonArray = jsonObject.getJSONObject("items").getJSONArray("item");
                if (jsonArray.size() == 0) {
                    return EventResult.resultWith(EventResultEnum.NO_DATA, "未找到相关订单数据", null);
                }
                String orderInfoJson = jsonArray.getJSONObject(0).toJSONString();
                EDBOrderDetail orderDetail = JSON.parseObject(orderInfoJson, EDBOrderDetail.class);
                return EventResult.resultWith(EventResultEnum.SUCCESS, orderDetail);
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, jsonObject.getString("error_msg"), null);
            }


        } catch (IOException e) {
            return EventResult.resultWith(EventResultEnum.ERROR.getResultCode(), EventResultEnum.ERROR.getResultMsg() + "--" + e.getMessage(), null);
        }
    }

    @Override
    public EventResult cancelOrder(CancelOrderEvent cancelOrderEvent) {
        ERPInfo erpInfo = cancelOrderEvent.getErpInfo();
        try {
            EDBSysData sysData = JSON.parseObject(erpInfo.getSysDataJson(), EDBSysData.class);
            EventResult eventResult = this.getOrderDetail(cancelOrderEvent.getOrderId(), erpInfo);
            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return EventResult.resultWith(EventResultEnum.ERROR, "从edb获取数据失败--" + eventResult.getResultMsg(), null);
            }
            String tid = ((EDBOrderDetail) eventResult.getData()).getTid();
            Map<String, Object> requestData = getSysRequestData(EDBConstant.CANCEL_ORDER, sysData);
            requestData.put("tid", tid);
            Map<String, Object> signMap = new TreeMap<>(requestData);
            requestData.put("sign", getSign(signMap, sysData));
            String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);
            if (responseData == null) {
                return EventResult.resultWith(EventResultEnum.ERROR);
            }
            JSONObject jsonObject = JSON.parseObject(responseData);
            return EventResult.resultWith(EventResultEnum.SUCCESS);
        } catch (IOException e) {
            return EventResult.resultWith(EventResultEnum.ERROR.getResultCode(), EventResultEnum.ERROR.getResultMsg() + "--" + e.getMessage(), null);
        }
    }

    @Override
    public EventResult orderPush(EDBCreateOrderInfo edbCreateOrderInfo, EDBSysData sysData) {
        try {
            String xmlResult = new XmlMapper().writeValueAsString(edbCreateOrderInfo);
            int firstIndex = xmlResult.indexOf("<product_item>");
            int lastIndex = xmlResult.lastIndexOf("</product_item>");
            String firstPanel = xmlResult.substring(0, firstIndex);
            String productPanel = xmlResult.substring(firstIndex + 14, lastIndex);
            String xmlValues = ("<order>" + firstPanel + "<product_info>" + productPanel + "</product_info></orderInfo></order>").replaceAll(" xmlns=\"\"", "");


            Map<String, Object> requestData = getSysRequestData(EDBConstant.CREATE_ORDER, sysData);
            requestData.put("xmlvalues", xmlValues);
            Map<String, Object> signMap = new TreeMap<>(requestData);

            requestData.put("sign", getSign(signMap, sysData));

            HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getRequestUrl(), requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                System.out.println("订单:" + edbCreateOrderInfo.getOutTid() + "已推送edb");
                JSONObject jsonObject = JSON.parseObject(httpResult.getHttpContent());
                if (jsonObject.getJSONObject("Success") == null) {
                    return EventResult.resultWith(EventResultEnum.ERROR, jsonObject.getString("error_msg"), null);
                }
                return EventResult.resultWith(EventResultEnum.SUCCESS);
            }

            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }
}
