package com.huobanplus.erpprovider.gy.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.gy.common.GYConstant;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.formatgy.order.*;
import com.huobanplus.erpprovider.gy.handler.GYBaseHandler;
import com.huobanplus.erpprovider.gy.handler.GYOrderHandler;
import com.huobanplus.erpprovider.gy.search.GYDeliveryOrderSearch;
import com.huobanplus.erpprovider.gy.search.GYOrderSearch;
import com.huobanplus.erpprovider.gy.search.GYRefundOrderSearch;
import com.huobanplus.erpprovider.gy.search.GYReturnOrderSearch;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by elvis on 2016/5/9.
 */
@Component
public class GYOrderHandlerImpl extends GYBaseHandler implements GYOrderHandler {

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    private static final Log log = LogFactory.getLog(GYOrderHandlerImpl.class);

    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {

        Order order = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
        GYSysData sysData = JSON.parseObject(erpInfo.getSysDataJson(), GYSysData.class);
        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();

        GYOrder newOrder = OrderChange(order,erpUserInfo);

        try {
            Map<String, Object> requestData = getRequestData(sysData, newOrder, GYConstant.ORDER_ADD);
            System.out.println("***********************");
            System.out.println(requestData);

            HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getURL(), requestData);

            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject resultJson = JSON.parseObject(httpResult.getHttpContent());
                if("true".equals(resultJson.getString("success").trim())){
                    //记录日志并返回
                    saveLog(order,erpUserInfo,erpInfo,pushNewOrderEvent,true);
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }
                saveLog(order,erpUserInfo,erpInfo,pushNewOrderEvent,false);
                log.error("推送订单失败,errorCode:"+resultJson.getString("errorCode").trim());
            }
        } catch (IOException e) {
            log.error("推送订单失败,得到请求参数"+e.toString());
            saveLog(order,erpUserInfo,erpInfo,pushNewOrderEvent,false);
        }


        return null;
    }

    private GYOrder OrderChange(Order order, ERPUserInfo erpUserInfo){

        GYOrder newOrder = new GYOrder();

        newOrder.setRefund(0);// FIXME: 2016/5/9 0-非退款 ，1--退款（退款中）；
        newOrder.setCod(false);// FIXME: 2016/5/9 非货到付款
        newOrder.setPlatformCode(order.getOrderId());
        newOrder.setShopCode(erpUserInfo.getCustomerId()+"");
        newOrder.setExpressCode("123");// FIXME: 2016/5/9 物流公司code 必填
        newOrder.setWarehouseCode("2123");// FIXME: 2016/5/9 仓库code 必填
        newOrder.setVipCode("123546");// FIXME: 2016/5/9 会员code 必填
        newOrder.setVipName("126584");

        newOrder.setReceiverAddress(order.getShipAddr());
        newOrder.setReceiverZip(order.getShipZip());
        newOrder.setReceiverMobile(order.getShipMobile());
        newOrder.setReceiverPhone(order.getShipTel());
        newOrder.setReceiverProvince(order.getProvince());
        newOrder.setReceiverCity(order.getCity());
        newOrder.setDiscountFee(order.getDistrict());
        newOrder.setTagCode(null);// FIXME: 2016/5/9
        newOrder.setDealDatetime(order.getCreateTime());
        newOrder.setPayDatetime(order.getPayTime());
        newOrder.setBusinessManCode(null);// FIXME: 2016/5/9
        newOrder.setPostFee("0.0");// FIXME: 2016/5/9
        newOrder.setCodFee(null);// FIXME: 2016/5/9
        newOrder.setDiscountFee(null);
        newOrder.setPlanDeliveryDate(null);//// FIXME: 2016/5/9 预计发货日期，可能有格式问题
        newOrder.setBuyerMemo(order.getMemo());
        newOrder.setSellerMemo(order.getRemark());
        newOrder.setSellerMemoLate(null);// FIXME: 2016/5/9 二次备注
        newOrder.setVipIdCard(null);// FIXME: 2016/5/9 身份证号
        newOrder.setVipRealName(null);// FIXME: 2016/5/9  	真实姓名
        newOrder.setVipEmail(order.getShipEmail());

        List<GYOrderItem> details = new ArrayList<>();

        order.getOrderItems().forEach(item ->{
            GYOrderItem detail = new GYOrderItem();
            detail.setPrice(item.getPrice()+"");
            detail.setQty(item.getAmount()+"");
            detail.setSkuCode(item.getOrderId());
            details.add(detail);
        });

        //发票信息
        // TODO: 2016/5/9

        List<Invoice> invoices = new ArrayList<>();
        for(int i=0;i<5;i++){
            Invoice invoice = new Invoice();
            invoice.setBillAmount("test");
            invoice.setInvoiceAmount("test");
            invoice.setInvoiceContent("test");
            invoice.setInvoiceTitle("test");
            invoice.setInvoiceType("test");
            invoices.add(invoice);
        }

        newOrder.setInvoices(invoices);


        //支付信息
        List<Payment> payments = new ArrayList<>();
        for(int i=0;i<5;i++){
            Payment payment = new Payment();
            payment.setAccount("test");
            payment.setPayCode("test");
            payment.setPayment("test");
            payment.setPaytime("test");
            payment.setPayTypeCode("test");
            payments.add(payment);
        }

        newOrder.setPayments(payments);



        return newOrder;
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

    @Override
    public EventResult orderQuery(GYOrderSearch orderSearch,GYSysData gySysData) {
        try {

            Map<String, Object> requestData = getRequestData(gySysData, orderSearch,GYConstant.ORDER_QUERY);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getURL(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    JSONArray jsonArray = result.getJSONArray("orders");
                    return EventResult.resultWith(EventResultEnum.SUCCESS,jsonArray);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @Override
    public EventResult orderMemoUpdate(GYOrderMemo gyOrderMemo, GYSysData gySysData) {
        try {

            //fill entity // TODO: 2016/6/17

            Map<String, Object> requestData = getRequestData(gySysData, gyOrderMemo,GYConstant.ORDER_MEMO_UPDATE);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getURL(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @Override
    public EventResult orderRefundStateUpdate(GYRefundOrder gyRefundOrder, GYSysData gySysData) {
        try {

            //fill entity // TODO: 2016/6/17

            Map<String, Object> requestData = getRequestData(gySysData, gyRefundOrder,GYConstant.ORDER_REFUND_STATE_UPDATE);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getURL(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @Override
    public EventResult deliveryOrderQuery(GYDeliveryOrderSearch gyDeliveryOrderSearch, GYSysData gySysData) {
        try {
            Map<String, Object> requestData = getRequestData(gySysData, gyDeliveryOrderSearch,GYConstant.DELIVERY_QUERY);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getURL(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @Override
    public EventResult historyDeliveryOrderQuery(GYDeliveryOrderSearch gyDeliveryOrderSearch, GYSysData gySysData) {
        try {
            Map<String, Object> requestData = getRequestData(gySysData, gyDeliveryOrderSearch,GYConstant.HISTORY_DELIVERY_QUERY);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getURL(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @Override
    public EventResult deliveryOrderUpdate(GYDeliveryOrderUpdate deliveryOrderUpdate, GYSysData gySysData) {
        try {

            //fill entity // TODO: 2016/6/17

            Map<String, Object> requestData = getRequestData(gySysData, deliveryOrderUpdate,GYConstant.DELIVERY_INFO_UPDATE);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getURL(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @Override
    public EventResult returnOrderQuery(GYReturnOrderSearch gyReturnOrderSearch, GYSysData gySysData) {
        try {
            Map<String, Object> requestData = getRequestData(gySysData, gyReturnOrderSearch,GYConstant.RETURN_ORDER_QUERY);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getURL(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @Override
    public EventResult pushReturnOrder(GYReturnOrder gyReturnOrder, GYSysData gySysData) {
        try {
            Map<String, Object> requestData = getRequestData(gySysData, gyReturnOrder,GYConstant.RETUR_ORDER_ADD);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getURL(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @Override
    public EventResult returnOrderInStock(GYReturnOrderInStock gyReturnOrderInStock, GYSysData gySysData) {
        try {
            Map<String, Object> requestData = getRequestData(gySysData, gyReturnOrderInStock,GYConstant.RETURN_ORDER_IN_STOCK);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getURL(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @Override
    public EventResult historyOrderQuery(GYOrderSearch gyOrderSearch,GYSysData gySysData) {
        try {
            Map<String, Object> requestData = getRequestData(gySysData, gyOrderSearch,GYConstant.HISTORY_ORDER_QUERY);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getURL(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @Override
    public EventResult refundOrderPush(GYRefundOrder gyRefundOrder, GYSysData gySysData) {
        try {
            Map<String, Object> requestData = getRequestData(gySysData, gyRefundOrder,GYConstant.REFUND_ORDER_ADD);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getURL(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @Override
    public EventResult refundOrderQuery(GYRefundOrderSearch gyRefundOrderSearch,GYSysData gySysData) {
        try {
            Map<String, Object> requestData = getRequestData(gySysData, gyRefundOrderSearch,GYConstant.REFUND_ORDER_QUERY);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getURL(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }
}
