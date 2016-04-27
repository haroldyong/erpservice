/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.iscs.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.iscs.common.ISCSSysData;
import com.huobanplus.erpprovider.iscs.formatiscs.*;
import com.huobanplus.erpprovider.iscs.handler.ISCSBaseHandler;
import com.huobanplus.erpprovider.iscs.handler.ISCSOrderHandler;
import com.huobanplus.erpprovider.iscs.search.ISCSOrderSearch;
import com.huobanplus.erpprovider.iscs.search.ISCSOrderSearchCp;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.ReturnInfo;
import com.huobanplus.erpservice.datacenter.searchbean.OrderSearchInfo;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushReturnInfoEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by allan on 4/19/16.
 */
@Component
public class ISCSOrderHandlerImpl extends ISCSBaseHandler implements ISCSOrderHandler {

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        Order order = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
        ISCSSysData sysData = JSON.parseObject(erpInfo.getSysDataJson(), ISCSSysData.class);
        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
        try {
            ISCSCreateOrderInfo createOrderInfo = new ISCSCreateOrderInfo();
            createOrderInfo.setOrderNo(order.getOrderId());
            createOrderInfo.setStockId(sysData.getStockId());
            createOrderInfo.setTransporterFlag(0);
            createOrderInfo.setShopId(sysData.getShopId());
            createOrderInfo.setCreateTime(order.getCreateTime());
            createOrderInfo.setPayTime(order.getPayTime());
            createOrderInfo.setBuyNick(order.getShipName());// FIXME: 2016/4/26
            createOrderInfo.setCountry("中国");// FIXME: 2016/4/26
            String shipArea = order.getShipArea();
            if (!StringUtils.isEmpty(shipArea)) {
                String[] shipAreaArray = shipArea.split("/");
                createOrderInfo.setProvince(shipAreaArray[0]);
                if (shipAreaArray.length > 1) {
                    createOrderInfo.setCity(shipAreaArray[1]);
                    if (shipAreaArray.length > 2) {
                        createOrderInfo.setCounty(shipAreaArray[2]);
                    }
                }
            }
            createOrderInfo.setAddress(order.getShipAddr());
            createOrderInfo.setZip(order.getShipZip());
            createOrderInfo.setName(order.getShipName());
            createOrderInfo.setMobile(order.getShipMobile());
            createOrderInfo.setTel(order.getShipTel());
//            createOrderInfo.setRequestShipDate();
            createOrderInfo.setNeedInvoice(1);
//            createOrderInfo.setInvoiceName();
//            createOrderInfo.setInvoiceContent();
            createOrderInfo.setPayment(order.getFinalAmount());
            createOrderInfo.setTotalFee(order.getFinalAmount());
            createOrderInfo.setDiscountFee(order.getPmtAmount());
            createOrderInfo.setPostFee(order.getCostFreight());
            createOrderInfo.setNeedCard(0);
            List<ISCSCreateOrderItem> createOrderItems = new ArrayList<>();
            order.getOrderItems().forEach(item -> {
                ISCSCreateOrderItem createOrderItem = new ISCSCreateOrderItem();
                createOrderItem.setProductCode(item.getProductBn());
                createOrderItem.setNum(item.getNum());
                createOrderItem.setPrice(item.getPrice());
//                createOrderItem.setDiscountFee();
                createOrderItem.setShopId(sysData.getShopId());
                createOrderItem.setOwnerId(sysData.getOwnerId());
                createOrderItem.setSellPrice(item.getCost());
                createOrderItems.add(createOrderItem);
            });
            createOrderInfo.setCreateOrderItems(createOrderItems);

            ISCSCreateOrder createOrder = new ISCSCreateOrder(1, Arrays.asList(createOrderInfo));

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

            String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
            Map<String, Object> requestData = getRequestData(sysData, nowStr, "pushTrades", JSON.toJSONString(createOrder));

            HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getHost(), requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                String errorCode = result.getString("errorCode");
                if (errorCode.equals("100")) {
                    String data = result.getString("data");
                    JSONObject dataJson = JSON.parseObject(data);
                    int successCount = dataJson.getInteger("success_count");
                    if (successCount == 1) {
                        orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
                        orderDetailSyncLogService.save(orderDetailSyncLog);
                        return EventResult.resultWith(EventResultEnum.SUCCESS);
                    } else {
                        orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                        orderDetailSyncLogService.save(orderDetailSyncLog);
                        String reason = JSON.parseObject(JSON.parseArray(dataJson.getString("failed_list")).get(0).toString()).getString("reason");
                        return EventResult.resultWith(EventResultEnum.ERROR, reason, null);
                    }

                } else {
                    orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                    orderDetailSyncLogService.save(orderDetailSyncLog);
                    return EventResult.resultWith(EventResultEnum.ERROR, result.getString("subMessage"), null);
                }
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR);
            }

        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR);
        }
    }

    @Override
    public EventResult getOrderDeliveryInfo(ISCSSysData sysData, ISCSOrderSearch orderSearch) {
        try {
            Date now = new Date();
            String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
            Map<String, Object> requestData = getRequestData(sysData, nowStr, "tradeDeliverQuery", JSON.toJSONString(orderSearch));

            HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getHost(), requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                //如果有errorCode,说明失败了,返回失败信息
                if (result.getJSONObject("errorCode") != null) {
                    return EventResult.resultWith(EventResultEnum.ERROR, result.getString("subMessage"), null);
                }
                return EventResult.resultWith(EventResultEnum.SUCCESS, result);
            }
            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR);
        }
    }

//    @Override
//    public EventResult cancelOrder(CancelOrderEvent cancelOrderEvent) {
//        try{
//            ERPInfo erpInfo = cancelOrderEvent.getErpInfo();
//            ERPUserInfo erpUserInfo = cancelOrderEvent.getErpUserInfo();
//            ISCSSysData iscsSysData = JSON.parseObject(erpInfo.getSysDataJson(), ISCSSysData.class);
//            Date now = new Date();
//            String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
//
//            ISCSCancelOrder iscsCancelOrder = new ISCSCancelOrder();// TODO: 2016/4/25
//            iscsCancelOrder.setOrderNum(cancelOrderEvent.getOrderId());
//            iscsCancelOrder.setStockId(iscsSysData.getStockId());
//            iscsCancelOrder.setShopId(iscsSysData.getShopId());
//            iscsCancelOrder.setRemark("remark");
//
//            Map<String,Object> requestData = getRequestData(iscsSysData,nowStr,"cancelTrade",JSON.toJSONString(iscsCancelOrder));
//            HttpResult httpResult = HttpClientUtil.getInstance().post(iscsSysData.getHost(),requestData);
//            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
//                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
//
//                String errorCode = result.getString("errorCode");
//                if(errorCode.equals("100")){
//                    return EventResult.resultWith(EventResultEnum.SUCCESS, result);
//                }else{
//                    String message = result.getJSONObject("errorText").getString("message");
//                    return EventResult.resultWith(EventResultEnum.ERROR,result.getJSONObject("errorText").getString("message"),null);
//                }
//            }
//            return EventResult.resultWith(EventResultEnum.ERROR);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return EventResult.resultWith(EventResultEnum.ERROR);
//        }
//    }

//    @Override
//    public EventResult orderStatusQuery(OrderStatusInfoEvent orderStatusInfoEvent) {
//        try{
//            ERPInfo erpInfo = orderStatusInfoEvent.getErpInfo();
//            //ERPUserInfo erpUserInfo = cancelOrderEvent.getErpUserInfo();
//            ISCSSysData iscsSysData = JSON.parseObject(erpInfo.getSysDataJson(), ISCSSysData.class);
//            Date now = new Date();
//            String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
//
//            ISCSOrderStatusSearch iscsOrderStatusSearch = new ISCSOrderStatusSearch();
//            OrderInfo orderInfo = orderStatusInfoEvent.getOrderInfo();
//            iscsOrderStatusSearch.setStockId(iscsSysData.getStockId());
//            iscsOrderStatusSearch.setShopId(iscsSysData.getShopId());
//            iscsOrderStatusSearch.setOrderNo(orderInfo.getOrderCode());// FIXME: 2016/4/25
//            iscsOrderStatusSearch.setBeginTime(StringUtil.DateFormat(orderInfo.getBeginTime(),StringUtil.TIME_PATTERN));// FIXME: 2016/4/25
//            iscsOrderStatusSearch.setEndTime(StringUtil.DateFormat(orderInfo.getEndTime(),StringUtil.TIME_PATTERN));// FIXME: 2016/4/25
//            if(StringUtil.isNotEmpty(orderInfo.getPageNo())){
//                iscsOrderStatusSearch.setPageIndex(Integer.parseInt(orderInfo.getPageNo()));
//            }
//            if(StringUtil.isNotEmpty(orderInfo.getPageSize())){
//                iscsOrderStatusSearch.setPageSize(Integer.parseInt(orderInfo.getPageSize()));
//            }
//            if(StringUtil.isNotEmpty(orderInfo.getStatus())){
//                iscsOrderStatusSearch.setStatus(Integer.parseInt(orderInfo.getStatus()));// FIXME: 2016/4/25
//            }
//
//            iscsOrderStatusSearch.setTimeType(orderInfo.getDateType());// FIXME: 2016/4/25
//
//            Map<String,Object> requestData = getRequestData(iscsSysData,nowStr,"tradeStatusQuery",JSON.toJSONString(orderStatusInfoEvent));
//            HttpResult httpResult = HttpClientUtil.getInstance().post(iscsSysData.getHost(),requestData);
//            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
//                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
//                String errorCode = result.getString("errorCode");
//                if(errorCode.equals("100")){
//                    return EventResult.resultWith(EventResultEnum.SUCCESS,result);
//                }else{
//                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorText"),null);
//                }
//            }
//            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
//        }catch (Exception e){
//            e.printStackTrace();
//            return EventResult.resultWith(EventResultEnum.ERROR);
//        }
//    }

    @Override
    public EventResult pushReturnOrder(PushReturnInfoEvent pushReturnInfoEvent) {
        // 根据微商城的退单信息封装iscs的退单实体
        ERPInfo erpInfo = pushReturnInfoEvent.getErpInfo();
        ERPUserInfo erpUserInfo = pushReturnInfoEvent.getErpUserInfo();
        ISCSSysData iscsSysData = JSON.parseObject(erpInfo.getSysDataJson(), ISCSSysData.class);
        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);

        ReturnInfo returnInfo = pushReturnInfoEvent.getReturnInfo();
        ISCSReturnOrder iscsReturnOrder = new ISCSReturnOrder();
        iscsReturnOrder.setOrderReturnNo("");// TODO: 2016/4/26  
        iscsReturnOrder.setOrderNo(returnInfo.getOrderId());
        iscsReturnOrder.setStockId(iscsSysData.getStockId());
        iscsReturnOrder.setTransporterId(1);// TODO: 2016/4/26
        iscsReturnOrder.setOutSid(returnInfo.getLogiNo());// TODO: 2016/4/26  
        //iscsReturnOrder.setBuyerNick("nickName");
        iscsReturnOrder.setReceiverName(returnInfo.getReturnName());
        iscsReturnOrder.setReceiverMobile(returnInfo.getReturnMobile());
        //iscsReturnOrder.setReceiverPhone("");
        iscsReturnOrder.setReturnNote(returnInfo.getRemark());
        iscsReturnOrder.setShopId(iscsSysData.getShopId());

        List<ISCSReturnOrderItem> iscsReturnOrderItems = new ArrayList<>();
        String returnItemStr = returnInfo.getReturnItemStr();
        String[] returnItems = returnItemStr.split("\\|");
        for (int i = 0; i < returnItems.length; i++) {
            ISCSReturnOrderItem iscsReturnOrderItem = new ISCSReturnOrderItem();
            String[] orderItem = returnItems[i].split(",");
            iscsReturnOrderItem.setShopId(iscsSysData.getShopId());
            iscsReturnOrderItem.setOwnerId(iscsSysData.getOwnerId());
            iscsReturnOrderItem.setProductCode(orderItem[0]);
            iscsReturnOrderItem.setNum(Integer.parseInt(orderItem[1]));
            iscsReturnOrderItems.add(iscsReturnOrderItem);
        }
        iscsReturnOrder.setIscsReturnOrderItems(iscsReturnOrderItems);

        // 请求网仓pushBackTrades接口
        HttpResult httpResult = null;
        try {
            Map<String, Object> requestData = getRequestData(iscsSysData, nowStr, "cancelTrade", JSON.toJSONString(iscsReturnOrder));
            httpResult = HttpClientUtil.getInstance().post(iscsSysData.getHost(), requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                String errorCode = result.getString("errorCode");
                if (errorCode.equals("100")) {
                    String data = result.getString("data");
                    JSONObject dataJson = JSON.parseObject(data);
                    int successCount = dataJson.getInteger("success_count");
                    if (successCount == 1) {
                        return EventResult.resultWith(EventResultEnum.SUCCESS, result);
                    } else {
                        return EventResult.resultWith(EventResultEnum.ERROR);
                    }

                } else {
                    return EventResult.resultWith(EventResultEnum.ERROR);
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return EventResult.resultWith(EventResultEnum.ERROR);
        }

        return EventResult.resultWith(EventResultEnum.ERROR);
    }

    @Override
    public EventResult orderQuery(OrderSearchInfo orderSearchInfo, ERPUserInfo erpUserInfo, ERPInfo erpInfo) {

        ISCSSysData iscsSysData = JSON.parseObject(erpInfo.getSysDataJson(), ISCSSysData.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = null;
        Date end = null;
        try {
            begin = sdf.parse("2016-01-10");
            end = sdf.parse("2016-04-20");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {

        }
        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);


        String beginStr = StringUtil.DateFormat(begin, StringUtil.TIME_PATTERN);
        String endStr = StringUtil.DateFormat(end, StringUtil.TIME_PATTERN);

        ISCSOrderSearchCp iscsOrderSearchCp = new ISCSOrderSearchCp();
        iscsOrderSearchCp.setShopId(iscsSysData.getShopId());
        iscsOrderSearchCp.setStockId(iscsSysData.getStockId());
        iscsOrderSearchCp.setBeginTime(beginStr);
        iscsOrderSearchCp.setEndTime(endStr);
        //iscsOrderSearchCp.setOrderNo("234rd");

        Map<String, Object> requestData = null;

        try {
            requestData = getRequestData(iscsSysData, nowStr, "tradeQuery", JSON.toJSONString(iscsOrderSearchCp));
            String str = JSON.toJSONString(iscsOrderSearchCp);
            System.out.println("*********************");
            System.out.println(str);
            System.out.println("*********************");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResult httpResult = HttpClientUtil.getInstance().post(iscsSysData.getHost(),requestData);
        JSONObject result = JSON.parseObject(httpResult.getHttpContent());
        String data = result.getString("data");
        System.out.println("***********Result****************");
        System.out.println(result.toJSONString());
        System.out.println("***********Result****************");

        return null;
    }

}
