package com.huobanplus.erpprovider.gy.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.formatgy.CreateNewOrder;
import com.huobanplus.erpprovider.gy.formatgy.Invoice;
import com.huobanplus.erpprovider.gy.formatgy.OrderItem;
import com.huobanplus.erpprovider.gy.formatgy.Payment;
import com.huobanplus.erpprovider.gy.handler.GYBaseHandler;
import com.huobanplus.erpprovider.gy.handler.GYOrderHandler;
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

        CreateNewOrder newOrder = OrderChange(order,erpUserInfo);

        try {
            Map<String, Object> requestData = getRequestData(sysData, newOrder,"gy.erp.trade.add");
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

    private CreateNewOrder OrderChange(Order order,ERPUserInfo erpUserInfo){

        CreateNewOrder newOrder = new CreateNewOrder();

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

        List<OrderItem> details = new ArrayList<>();

        order.getOrderItems().forEach(item ->{
            OrderItem detail = new OrderItem();
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

}
