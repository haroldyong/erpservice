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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        try {
            Order order = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
            ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
            GYSysData sysData = JSON.parseObject(erpInfo.getSysDataJson(), GYSysData.class);
            ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();

            GYOrder newOrder = OrderChange(order,erpUserInfo);

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


            String requestData = getRequestData2(sysData, newOrder, GYConstant.ORDER_ADD);
            EventResult eventResult = orderPush(requestData,sysData);
            if(eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()){
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
            } else{
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                orderDetailSyncLog.setErrorMsg(eventResult.getResultMsg());
            }
            orderDetailSyncLogService.save(orderDetailSyncLog);
            return eventResult;

        } catch (Exception e) {
            log.error(e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    private GYOrder OrderChange(Order order, ERPUserInfo erpUserInfo) throws ParseException {

        GYOrder newOrder = new GYOrder();

        newOrder.setRefund(0);// FIXME: 2016/5/9 0-非退款 ，1--退款（退款中）；
        newOrder.setCod(false);// FIXME: 2016/5/9 非货到付款
//        newOrder.setOrderSettlementCode("nouse");// 没有用的字段
        newOrder.setPlatformCode(order.getOrderId());
        newOrder.setShopCode("9999");// FIXME: 2016/6/21   店铺code
        newOrder.setExpressCode("QFKD");// FIXME: 2016/5/9 物流公司code 必填
        newOrder.setWarehouseCode("tk01");// FIXME: 2016/5/9 仓库code 必填  指定一个默认的
        newOrder.setVipCode(order.getUserLoginName());// FIXME: 2016/5/9 会员code 必填 
        newOrder.setVipName(order.getBuyerName());// FIXME: 2016/6/21

        newOrder.setReceiverAddress(order.getShipAddr());
        newOrder.setReceiverZip(order.getShipZip());
        newOrder.setReceiverMobile(order.getShipMobile());
        newOrder.setReceiverPhone(order.getShipTel());
        newOrder.setReceiverProvince(order.getProvince());
        newOrder.setReceiverCity(order.getCity());
        newOrder.setReceiverDistrict(order.getDistrict());
        newOrder.setTagCode(null);// FIXME: 2016/5/9
        newOrder.setDealDatetime(order.getCreateTime());
        newOrder.setPayDatetime(order.getPayTime());
        newOrder.setBusinessManCode(null);// FIXME: 2016/5/9
        newOrder.setPostFee(order.getCostFreight());// FIXME: 2016/5/9
        newOrder.setCodFee(0.0);// FIXME: 2016/5/9  到付服务费
        newOrder.setDiscountFee(order.getPmtAmount());// 让利金额
        newOrder.setPlanDeliveryDate(null);//// FIXME: 2016/5/9 预计发货日期，可能有格式问题
        newOrder.setBuyerMemo(order.getMemo());
        newOrder.setSellerMemo(order.getRemark());
        newOrder.setSellerMemoLate(null);// FIXME: 2016/5/9 二次备注
        newOrder.setVipIdCard(order.getBuyerPid());
        newOrder.setVipRealName(order.getBuyerName());
        newOrder.setVipEmail(order.getShipEmail());

        List<GYOrderItem> details = new ArrayList<>();

        order.getOrderItems().forEach(item ->{
            GYOrderItem detail = new GYOrderItem();
            detail.setItemCode(item.getGoodBn());
            detail.setSkuCode(item.getProductBn());
            detail.setPrice(item.getPrice());
            detail.setRefund(0);//0非退款 ,1退款(退款中);
            detail.setNote("");//备注
            detail.setQty(item.getNum());
            detail.setOid(item.getStandard());// FIXME: 2016/6/21  子订单ID 用于后续订单状态修改的查询
            details.add(detail);
        });
        newOrder.setDetails(details);

        // 一笔订单对应一条发票信息
        List<GYInvoice> invoices = new ArrayList<>();
        GYInvoice gyInvoice = new GYInvoice();
        gyInvoice.setInvoiceAmount(100.0);// FIXME: 2016/6/21
        gyInvoice.setInvoiceContent("test");// FIXME: 2016/6/21
        gyInvoice.setInvoiceTitle(order.getTaxCompany());//发票抬头
        gyInvoice.setInvoiceType(1);// FIXME: 2016/6/21 1-普通发票；2-增值发票
        invoices.add(gyInvoice);
        newOrder.setInvoices(invoices);


        //一笔订单支付信息
        List<GYPayment> payments = new ArrayList<>();
        GYPayment payment = new GYPayment();
        payment.setPayTypeCode("test");//支付类型code
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        payment.setPaytime(dateFormat.parse(order.getPayTime()));// 支付时间 时间戳类型
        payment.setPayment(order.getOnlinePayAmount()); // 支付金额
        payment.setPayCode("test");// FIXME: 2016/6/21 支付交易号
        payment.setAccount("test");// FIXME: 2016/6/21
        payments.add(payment);
        newOrder.setPayments(payments);

        return newOrder;
    }

    @SuppressWarnings("Duplicates")
    private EventResult orderPush(String requestData,GYSysData gySysData){
        try{
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getURL(), requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject resultJson = JSON.parseObject(httpResult.getHttpContent());
                if(resultJson.getBoolean("success")){
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                } else{
                    return  EventResult.resultWith(EventResultEnum.ERROR,resultJson.getString("errorCode"),null);
                }
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        }catch (Exception e){
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }


    @SuppressWarnings("Duplicates")
    @Override
    public EventResult orderQuery(GYOrderSearch orderSearch,GYSysData gySysData) {
        try {

            String requestData = getRequestData2(gySysData,orderSearch,GYConstant.ORDER_QUERY);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getURL(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // 封装实体类
                    JSONArray jsonArray = result.getJSONArray("orders");
                    List<GYResponseOrder> responseOrders = new ArrayList<>();
                    jsonArray.forEach(order->{
                        GYResponseOrder responseOrder = JSON.parseObject(order.toString(),GYResponseOrder.class);
                        responseOrders.add(responseOrder);
                    });

                    return EventResult.resultWith(EventResultEnum.SUCCESS,responseOrders);
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

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult orderMemoUpdate(GYOrderMemo gyOrderMemo,GYSysData gySysData) {
        try {

            //fill entity // TODO: 2016/6/17


            String requestData = getRequestData2(gySysData, gyOrderMemo,GYConstant.ORDER_MEMO_UPDATE);
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

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult orderRefundStateUpdate(GYOrderRefundUpdate gyOrderRefundUpdate,GYSysData gySysData) {
        try {

            //fill entity // TODO: 2016/6/17

            String requestData = getRequestData2(gySysData, gyOrderRefundUpdate,GYConstant.ORDER_REFUND_STATE_UPDATE);
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

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult deliveryOrderQuery(GYDeliveryOrderSearch gyDeliveryOrderSearch, GYSysData gySysData) {
        try {
            String requestData = getRequestData2(gySysData, gyDeliveryOrderSearch,GYConstant.DELIVERY_QUERY);
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

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult historyDeliveryOrderQuery(GYDeliveryOrderSearch gyDeliveryOrderSearch, GYSysData gySysData) {
        try {
            String requestData = getRequestData2(gySysData, gyDeliveryOrderSearch,GYConstant.HISTORY_DELIVERY_QUERY);
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

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult deliveryOrderUpdate(GYDeliveryOrderUpdate deliveryOrderUpdate,GYSysData gySysData) {
        try {

            //fill entity // TODO: 2016/6/17

            String requestData = getRequestData2(gySysData, deliveryOrderUpdate,GYConstant.DELIVERY_INFO_UPDATE);
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

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult returnOrderQuery(GYReturnOrderSearch gyReturnOrderSearch, GYSysData gySysData) {
        try {
            String requestData = getRequestData2(gySysData, gyReturnOrderSearch,GYConstant.RETURN_ORDER_QUERY);
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

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult pushReturnOrder(GYReturnOrder gyReturnOrder,GYSysData gySysData) {
        try {

            String requestData = getRequestData2(gySysData, gyReturnOrder,GYConstant.RETUR_ORDER_ADD);
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

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult returnOrderInStock() {
        try {
            //fill entity // TODO: 2016/6/17
            GYReturnOrderInStock gyReturnOrderInStock = new GYReturnOrderInStock();

            GYSysData gySysData =  new GYSysData();

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

    @SuppressWarnings("Duplicates")
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

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult pushRefundOrder(GYRefundOrder gyRefundOrder,GYSysData gySysData) {
        try {
            //fill entity // TODO: 2016/6/17

            String requestData = getRequestData2(gySysData, gyRefundOrder,GYConstant.REFUND_ORDER_ADD);
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

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult refundOrderQuery(GYRefundOrderSearch gyRefundOrderSearch,GYSysData gySysData) {
        try {
            String requestData = getRequestData2(gySysData, gyRefundOrderSearch,GYConstant.REFUND_ORDER_QUERY);
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
