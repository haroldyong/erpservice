package com.huobanplus.erpprovider.dtw.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwGoodsDelcareItem;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwOrder;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwOrderItem;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwPersonalDelcareInfo;
import com.huobanplus.erpprovider.dtw.handler.DtwOrderHandler;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
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

import java.util.*;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Component
public class DtwOrderHandlerImpl implements DtwOrderHandler {

    private static final Log log = LogFactory.getLog(DtwOrderHandlerImpl.class);

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        try {
            Order order = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
            ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
            DtwSysData dtwSysData = JSON.parseObject(erpInfo.getSysDataJson(), DtwSysData.class);
            ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
            Map<String, Object> requestMap = new HashMap<>();
            DtwOrder dtwOrder = new DtwOrder();
            dtwOrder.setPassKey(dtwSysData.getPassKey());//(必填)
            dtwOrder.setPreEntryNumber("");
            dtwOrder.setECommerceCode(dtwSysData.getECommerceCode());//(必填)
            dtwOrder.setECommerceName(dtwSysData.getECommerceName());//(必填)
            dtwOrder.setImportType("");//进口类型（0一般进口，1保税进口）(必填)
            dtwOrder.setOrderType("");//订单类型（1：普通订单：与快递已经完成对接，2：综合订单：委托大田与快递公司对接）

            dtwOrder.setMsgid("1");// TODO: 2016/6/16 //(必填)
            dtwOrder.setPayType("");// TODO: 2016/6/17 //(必填)
            dtwOrder.setPayCompanyCode("001");// TODO: 2016/6/16//(必填)
            dtwOrder.setPayNumber("100001");// TODO: 2016/6/16//(必填)
            dtwOrder.setOrderTotalAmount(1.0);// FIXME: 2016/6/17//(必填)
            dtwOrder.setOrderGoodsAmount(1.0);// FIXME: 2016/6/17//(必填)
            dtwOrder.setOrderNo(order.getOrderId());//(必填)
            dtwOrder.setOrderTaxAmount(1.0);// FIXME: 2016/6/17//(必填)
            dtwOrder.setTotalCount(order.getItemNum());//(必填)
            dtwOrder.setTotalAmount(1.0);// FIXME: 2016/6/17//(必填)
            dtwOrder.setLogisCompanyName(order.getLogiName());//(必填)
            dtwOrder.setLogisCompanyCode("logisCompanyCode");// TODO: 2016/6/16//(必填)
            dtwOrder.setPurchaserId(String.valueOf(order.getMemberId()));//(必填)
            dtwOrder.setShipper("wuxiongliu");// TODO: 2016/6/16//(必填)
            dtwOrder.setShipperPro("");
            dtwOrder.setShipperCity("");
            dtwOrder.setShipperDistrict("");
            dtwOrder.setShipperAddress("");
            dtwOrder.setShipperMobile("");
            dtwOrder.setShipperTel("");
            dtwOrder.setShipperCountry("china");// TODO: 2016/6/16//(必填)
            dtwOrder.setShipperZip("");
            dtwOrder.setConsignee(order.getShipName());//(必填)
            dtwOrder.setConsigneePro(order.getProvince());//(必填)
            dtwOrder.setConsigneeCity(order.getCity());//(必填)
            dtwOrder.setConsigneeDistrict(order.getDistrict());//(必填)
            dtwOrder.setConsigneeAdd(order.getShipAddr());//(必填)
            dtwOrder.setConsigneeMobile(order.getShipMobile());//收货人手机(手机与电话二选一)
            dtwOrder.setConsigneeTel(order.getShipTel());//收货人手机(手机与电话二选一)
            dtwOrder.setConsigneeCountry("china");// TODO: 2016/6/16//(必填)
            dtwOrder.setConsigneeZip(order.getShipZip());
            dtwOrder.setWeight(order.getWeight());// FIXME: 2016/6/16//(必填)
            dtwOrder.setLotNo("");
            dtwOrder.setNetWeight(order.getWeight());// FIXME: 2016/6/16//(必填)
            dtwOrder.setIeFlag("I/O");//（I进口,O出口）// FIXME: 2016/6/16

            List<DtwOrderItem> dtwOrderItemList = new ArrayList<>();
            List<OrderItem> orderItemList = order.getOrderItems();
            orderItemList.forEach(orderItem -> {
                DtwOrderItem dtwOrderItem = new DtwOrderItem();
                dtwOrderItem.setPartno("test");orderItem.getProductBn();
                dtwOrderItem.setPartName("testtt");orderItem.getName();
                dtwOrderItem.setUnit("test");// TODO: 2016/6/16  
                dtwOrderItem.setCurrency(order.getCurrency());// FIXME: 2016/6/16 

                dtwOrderItemList.add(dtwOrderItem);
            });

            dtwOrder.setDtwOrderItems(dtwOrderItemList);//(必填)
            requestMap.put("data", JSON.toJSONString(dtwOrder));

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

            EventResult orderPushEventResult = orderPush(requestMap, dtwSysData);
            EventResult declarePushEvent = pushPersonalDeclareOrder(order,dtwSysData);
            boolean pushFlag = false;
            if(orderPushEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode() &&
                    declarePushEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()){
                pushFlag = true;
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
            } else if(orderPushEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode() &&
                    declarePushEvent.getResultCode() != EventResultEnum.SUCCESS.getResultCode()){// 订单推送成功，个人申报单推送失败

                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                orderDetailSyncLog.setErrorMsg(declarePushEvent.getResultMsg());

            } else if(orderPushEventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode() &&
                    declarePushEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()){

                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                orderDetailSyncLog.setErrorMsg(orderPushEventResult.getResultMsg());

            } else{
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                orderDetailSyncLog.setErrorMsg(orderPushEventResult.getResultMsg()+";"+declarePushEvent.getResultCode());
            }

            orderDetailSyncLogService.save(orderDetailSyncLog);
            return orderPushEventResult;

        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    private EventResult orderPush(Map<String, Object> requestMap, DtwSysData dtwSysData) {

        HttpResult httpResult = HttpClientUtil.getInstance().post(dtwSysData.getRequestUrl() + "/QBIntegratedOrder", requestMap);
        System.out.println("\n********************************");
        System.out.println(httpResult.getHttpContent());
        System.out.println("********************************");
        if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
            JSONObject result = JSON.parseObject(httpResult.getHttpContent());
            if (result.getString("ErrCode").equals("000")) {
                return EventResult.resultWith(EventResultEnum.SUCCESS);
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, result.getString("ErrMsg"), null);
            }
        } else {
            return EventResult.resultWith(EventResultEnum.ERROR, "服务器请求失败", null);
        }
    }

    private EventResult pushPersonalDeclareOrder(Order order, DtwSysData dtwSysData) {

        DtwPersonalDelcareInfo dtwPersonalDelcareInfo = new DtwPersonalDelcareInfo();
        dtwPersonalDelcareInfo.setMsgid("");
        dtwPersonalDelcareInfo.setPreEntryNumber("");
        dtwPersonalDelcareInfo.setImportType(0);// FIXME: 2016/7/4 进口类型（0一般进口，1保税进口）
        dtwPersonalDelcareInfo.setECommerceCode("");
        dtwPersonalDelcareInfo.setECommerceName("");
        dtwPersonalDelcareInfo.setOrderNo("");
        dtwPersonalDelcareInfo.setWayBill("");
        dtwPersonalDelcareInfo.setPackNo(0);// FIXME: 2016/7/4
        dtwPersonalDelcareInfo.setGrossWeight(0.0);
        dtwPersonalDelcareInfo.setNetWeight(0.0);
        dtwPersonalDelcareInfo.setRemark("");
        dtwPersonalDelcareInfo.setSenderName("");
        dtwPersonalDelcareInfo.setConsignee("");
        dtwPersonalDelcareInfo.setSenderCity("");
        dtwPersonalDelcareInfo.setPaperType("");
        dtwPersonalDelcareInfo.setPaperNumber("");
        dtwPersonalDelcareInfo.setWorth(0.0);
        dtwPersonalDelcareInfo.setCurrCode("");
        dtwPersonalDelcareInfo.setMainGName("");
        dtwPersonalDelcareInfo.setSenderCountry("");
        dtwPersonalDelcareInfo.setPurchaserTelNumber("");
        dtwPersonalDelcareInfo.setBuyerIdType("");
        dtwPersonalDelcareInfo.setBuyerName("");
        dtwPersonalDelcareInfo.setFeeAmount(0.0);
        dtwPersonalDelcareInfo.setInsureAmount(0.0);
        dtwPersonalDelcareInfo.setCompanyName("");
        dtwPersonalDelcareInfo.setCompanyCode("");
        dtwPersonalDelcareInfo.setTradeCountry("");

        List<DtwGoodsDelcareItem> dtwItems = new ArrayList<>();
        DtwGoodsDelcareItem dtwGoodsDelcareItem = new DtwGoodsDelcareItem();
        dtwGoodsDelcareItem.setGoodsOrder(1);
        dtwGoodsDelcareItem.setCodeTs("");
        dtwGoodsDelcareItem.setGoodsItemNo("");
        dtwGoodsDelcareItem.setGoodsName("");
        dtwGoodsDelcareItem.setGoodsModel("");
        dtwGoodsDelcareItem.setOriginCountry("");
        dtwGoodsDelcareItem.setTradeCurr("");
        dtwGoodsDelcareItem.setTradeTotal(0.0);
        dtwGoodsDelcareItem.setDeclPrice(0.0);
        dtwGoodsDelcareItem.setDeclTotalPrice(0.0);
        dtwGoodsDelcareItem.setUseTo("");
        dtwGoodsDelcareItem.setDeclareCount(0);
        dtwGoodsDelcareItem.setGoodsUnit("");
        dtwGoodsDelcareItem.setFirstUnit("");
        dtwGoodsDelcareItem.setFirstCount(0.0);
        dtwGoodsDelcareItem.setSecondUnit("");
        dtwGoodsDelcareItem.setSecondCount(0.0);

        dtwItems.add(dtwGoodsDelcareItem);

        dtwPersonalDelcareInfo.setItems(dtwItems);

        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("data",JSON.toJSONString(dtwPersonalDelcareInfo));
        HttpResult httpResult = HttpClientUtil.getInstance().post(dtwSysData.getRequestUrl()+"/QBPresonal",requestMap);
        System.out.println("\n********************************");
        System.out.println(httpResult.getHttpContent());
        System.out.println("********************************");
        if(httpResult.getHttpStatus()==HttpStatus.SC_OK){
            JSONObject result = JSON.parseObject(httpResult.getHttpContent());
            if (result.getString("ErrCode").equals("000")) {
                return EventResult.resultWith(EventResultEnum.SUCCESS);
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, result.getString("ErrMsg"), null);
            }
        }
        return null;
    }
}
