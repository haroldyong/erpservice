/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.lgj.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.lgj.common.LGJConstant;
import com.huobanplus.erpprovider.lgj.common.LGJSysData;
import com.huobanplus.erpprovider.lgj.formatlgj.LGJCreateOrderInfo;
import com.huobanplus.erpprovider.lgj.formatlgj.LGJCreateOrderItem;
import com.huobanplus.erpprovider.lgj.handler.LGJBaseHandler;
import com.huobanplus.erpprovider.lgj.handler.LGJOrderHandler;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
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
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by elvis on 4/28/28.
 */
@Component
public class LGJOrderHandlerImpl extends LGJBaseHandler implements LGJOrderHandler {

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    private static final Log log = LogFactory.getLog(LGJOrderHandlerImpl.class);

    private String getOrdersn(LGJSysData sysData, String orderId,String token) throws UnsupportedEncodingException {

        if(token==null){
            return null;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("func",LGJConstant.GET_ORDERSN_FUN);
        data.put("token",token);
        data.put("thirdsn",orderId);
        //转换成需要的形式
        Map<String,Object> requestData = getParamMap(data);

        HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getHost(), requestData);
        if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
            JSONObject resultJson = JSON.parseObject(httpResult.getHttpContent());
            if("0".equals(resultJson.getString("result").trim())){
                return resultJson.getString("ordersn");
            }
            log.error("获得礼管家订单号："+resultJson.getString("msg")+":错误代码是"+resultJson.getString("result"));
        }
        return null;
    }


    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        Order order = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
        LGJSysData sysData = JSON.parseObject(erpInfo.getSysDataJson(), LGJSysData.class);
        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
        try {
            //获得Token
            String token = getToken(sysData);
            //获得礼管家单号
            String orderSn = getOrdersn(sysData, order.getOrderId(),token);
            if (orderSn == null && order.getOrderItems() == null) {
                saveLog(order,erpUserInfo,erpInfo,pushNewOrderEvent,false);
                return EventResult.resultWith(EventResultEnum.ERROR);
            }

            //发送订单
            LGJCreateOrderInfo createOrder = new LGJCreateOrderInfo();

            createOrder.setThirdsn(order.getOrderId());
            createOrder.setOrdersn(orderSn);
            createOrder.setName(order.getShipName());
            createOrder.setMobile(order.getShipMobile());

            String shipArea = order.getShipArea();
            createOrder.setAddress(shipArea);
            createOrder.setCountry(LGJConstant.COUNTY);//FIXME: 2016/4/29
            if (!StringUtils.isEmpty(shipArea)) {
                String[] shipAreaArray = shipArea.split("/");
                createOrder.setProvince(shipAreaArray[0]);
                if (shipAreaArray.length > 1) {
                    createOrder.setCity(shipAreaArray[1]);
                    if (shipAreaArray.length > 2) {
                        createOrder.setTown(shipAreaArray[2]);
                    }
                }
            }
            List<LGJCreateOrderItem> items = new ArrayList<>();
            for (OrderItem sourceItem : order.getOrderItems()) {
                LGJCreateOrderItem item = new LGJCreateOrderItem();
                item.setSkuId(sourceItem.getItemId() + "");
                item.setNum(sourceItem.getNum() + "");
                items.add(item);
            }
            createOrder.setCreateOrderItems(items);
            //设定请求方法名
            createOrder.setFunc(LGJConstant.ORDERSUBMIT_FUN);
            //设定Token
            createOrder.setToken(token);

            String JsonString = JSON.toJSONString(createOrder);
            Map<String, Object> requestData = getParamMap(JsonString);

            HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getHost(), requestData);

            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject resultJson = JSON.parseObject(httpResult.getHttpContent());
                if("0".equals(resultJson.getString("result").trim())){
                    //记录日志并返回
                    saveLog(order,erpUserInfo,erpInfo,pushNewOrderEvent,true);
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }
                saveLog(order,erpUserInfo,erpInfo,pushNewOrderEvent,false);
                log.error("推送订单失败,错误代买是："+resultJson.getString("result").trim());
            }

        } catch (UnsupportedEncodingException e) {
            saveLog(order,erpUserInfo,erpInfo,pushNewOrderEvent,false);
            log.error("推送失败"+e.toString());
            return EventResult.resultWith(EventResultEnum.SUCCESS);
        }
        return null;
    }

    /**
     * 记录日志
     * @param orderInfo
     * @param erpUserInfo
     * @param erpInfo
     * @param pushNewOrderEvent
     * @param isSuccess
     */
    private void saveLog(Order orderInfo,ERPUserInfo erpUserInfo,ERPInfo erpInfo,PushNewOrderEvent pushNewOrderEvent,boolean isSuccess ){
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

   /* @Override
    public EventResult getOrderDeliveryInfo(LGJSysData sysData, ISCSOrderSearch orderSearch) {
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

        *//*ISCSSysData iscsSysData = JSON.parseObject(erpInfo.getSysDataJson(), ISCSSysData.class);
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
        System.out.println("***********Result****************");*//*

        return null;
    }*/
}
