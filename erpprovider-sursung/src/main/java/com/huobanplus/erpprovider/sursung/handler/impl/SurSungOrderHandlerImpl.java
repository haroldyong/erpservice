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
import com.huobanplus.erpprovider.sursung.common.SurSungConstant;
import com.huobanplus.erpprovider.sursung.common.SurSungEnum;
import com.huobanplus.erpprovider.sursung.common.SurSungSysData;
import com.huobanplus.erpprovider.sursung.formatdata.*;
import com.huobanplus.erpprovider.sursung.handler.SurSungOrderHandler;
import com.huobanplus.erpprovider.sursung.search.SurSungLogisticSearch;
import com.huobanplus.erpprovider.sursung.util.SurSungUtil;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
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
        orderInfo.setLogiCode("201111111111");
        orderInfo.setLogiName("圆通速递");
        Date now = new Date();
        int time = (int) (now.getTime() / 1000);

        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
        SurSungSysData surSungSysData = JSON.parseObject(erpInfo.getSysDataJson(), SurSungSysData.class);
        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();

        SurSungOrder surSungOrder = convertOrder(orderInfo, surSungSysData.getShopId());
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(surSungOrder);

        try {

            String requestData = JSON.toJSONString(jsonArray);
            String requestUrl = SurSungUtil.createRequestUrl(SurSungConstant.ORDER_PUSH, time, surSungSysData);

            HttpResult httpResult = HttpClientUtil.getInstance().post(requestUrl, requestData);

            System.out.println("\n********************************");
            System.out.println("请求地址:" + requestUrl);
            System.out.println("请求数据：" + requestData);
            System.out.println("\n********************************");


            return EventResult.resultWith(EventResultEnum.SUCCESS, httpResult.getHttpContent());
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    private SurSungOrder convertOrder(Order order, int shopId) {
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
        sursungPay.setOuterPayId("1111111");// FIXME: 2016-08-30
        sursungPay.setPayDate(order.getPayTime());
        sursungPay.setPayment(order.getPaymentName());
        sursungPay.setSellerAccount("wuxiongliu");
        sursungPay.setBuyerAccount("wuxiongliu2");

        surSungOrder.setPay(sursungPay);
        surSungOrder.setSurSungOrderItems(surSungOrderItems);
        return surSungOrder;
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
            log.error(e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    @Override
    public EventResult logisticUpload(SurSungLogistic surSungLogistic, ERPUserInfo erpUserInfo, ERPInfo erpInfo) {
        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (erpUserHandler == null) {
            return EventResult.resultWith(EventResultEnum.ERROR);
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


        return erpUserHandler.handleEvent(batchDeliverEvent);

    }
}
