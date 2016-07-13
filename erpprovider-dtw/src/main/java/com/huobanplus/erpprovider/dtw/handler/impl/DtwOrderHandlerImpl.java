/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.dtw.common.DtwEnum;
import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpprovider.dtw.formatdtw.*;
import com.huobanplus.erpprovider.dtw.handler.DtwOrderHandler;
import com.huobanplus.erpprovider.dtw.search.DtwStockSearch;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.DeliveryInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.DeliveryInfo;
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
public class DtwOrderHandlerImpl implements DtwOrderHandler {

    private static final Log log = LogFactory.getLog(DtwOrderHandlerImpl.class);

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    @Autowired
    private ERPRegister erpRegister;

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
//            dtwOrder.setPreEntryNumber("");
            dtwOrder.setECommerceCode(dtwSysData.getECommerceCode());//(必填)
            dtwOrder.setECommerceName(dtwSysData.getECommerceName());//(必填)
            dtwOrder.setImportType(1);//进口类型（0一般进口，1保税进口）(必填)
            dtwOrder.setOrderType("");//订单类型（1：普通订单：与快递已经完成对接，2：综合订单：委托大田与快递公司对接）

            dtwOrder.setMsgid(order.getOrderId());
            dtwOrder.setPayType(3);// TODO: 2016/6/17 //(必填)
            dtwOrder.setPayCompanyCode("001");// 支付公司在海关的备案码 // FIXME: 2016-07-13
            dtwOrder.setPayNumber("100001");// 支付单号 // FIXME: 2016-07-13
            dtwOrder.setOrderTotalAmount(order.getFinalAmount());
            dtwOrder.setOrderGoodsAmount(order.getFinalAmount()-order.getCostFreight());
            dtwOrder.setOrderNo(order.getOrderId());//(必填)
            dtwOrder.setOrderTaxAmount(1.0);// FIXME: 2016/6/17//(必填)
            dtwOrder.setTotalCount(order.getItemNum());//(必填)
            dtwOrder.setTotalAmount(order.getFinalAmount());
            dtwOrder.setLogisCompanyName(order.getLogiName());//(必填)
            dtwOrder.setLogisCompanyCode(order.getLogiCode());// FIXME: 2016-07-13
            dtwOrder.setPurchaserId(String.valueOf(order.getMemberId()));//(必填)
            dtwOrder.setShipper("发货人姓名");// TODO: 2016/6/16//(必填)
            dtwOrder.setShipperPro("");
            dtwOrder.setShipperCity("");
            dtwOrder.setShipperDistrict("");
            dtwOrder.setShipperAddress("");//TODO 发货人地址（必填）
            dtwOrder.setShipperMobile("");
            dtwOrder.setShipperTel("");
            dtwOrder.setShipperCountry(DtwEnum.CountryEnum.CHINA.getCode());// FIXME: 2016-07-13 (必填)
            dtwOrder.setShipperZip("");
            dtwOrder.setConsignee(order.getShipName());//(必填)
            dtwOrder.setConsigneePro(order.getProvince());//(必填)
            dtwOrder.setConsigneeCity(order.getCity());//(必填)
            dtwOrder.setConsigneeDistrict(order.getDistrict());//(必填)
            dtwOrder.setConsigneeAdd(order.getShipAddr());//(必填)
            dtwOrder.setConsigneeMobile(order.getShipMobile());//收货人手机(手机与电话二选一)
            dtwOrder.setConsigneeTel(order.getShipTel());//收货人手机(手机与电话二选一)
            dtwOrder.setConsigneeCountry(DtwEnum.CountryEnum.CHINA.getCode());// FIXME: 2016-07-13 (必填)
            dtwOrder.setConsigneeZip(order.getShipZip());
            dtwOrder.setWeight(order.getWeight());// FIXME: 2016/6/16//(必填) 毛重
            dtwOrder.setLotNo("");
            dtwOrder.setNetWeight(order.getWeight());// FIXME: 2016/6/16//(必填) 净重
            dtwOrder.setIeFlag("I/O");//（I进口,O出口）// FIXME: 2016/6/16

            List<DtwOrderItem> dtwOrderItemList = new ArrayList<>();
            List<OrderItem> orderItemList = order.getOrderItems();
            for (int i = 0; i < orderItemList.size(); i++) {
                OrderItem orderItem = orderItemList.get(i);
                DtwOrderItem dtwOrderItem = new DtwOrderItem();
                dtwOrderItem.setMsgitem(i);
                dtwOrderItem.setPartno(orderItem.getProductBn());
                dtwOrderItem.setPartName(orderItem.getName());
                dtwOrderItem.setSpec(orderItem.getStandard());
                dtwOrderItem.setUnit("011");// FIXME: 2016-07-08 计量单位，海关三字代码(必填)  件
                dtwOrderItem.setCurrency(order.getCurrency());// FIXME: 2016/6/16
                dtwOrderItem.setAmount(orderItem.getAmount());
                dtwOrderItemList.add(dtwOrderItem);
            }


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
        List<OrderItem> orderItems = order.getOrderItems();

        DtwPersonalDelcareInfo dtwPersonalDelcareInfo = new DtwPersonalDelcareInfo();
        dtwPersonalDelcareInfo.setPassKey(dtwSysData.getPassKey());
        dtwPersonalDelcareInfo.setMsgid(order.getOrderId());
        dtwPersonalDelcareInfo.setCompanyName("");
        dtwPersonalDelcareInfo.setCompanyCode("");
//        dtwPersonalDelcareInfo.setPreEntryNumber("");
        dtwPersonalDelcareInfo.setImportType(0);// FIXME: 2016/7/4 进口类型（0一般进口，1保税进口）
        dtwPersonalDelcareInfo.setOrderNo(order.getOrderId());
        dtwPersonalDelcareInfo.setWayBill("");
        dtwPersonalDelcareInfo.setPackNo(order.getItemNum());// FIXME: 2016/7/4
        dtwPersonalDelcareInfo.setGrossWeight(order.getWeight());
        dtwPersonalDelcareInfo.setNetWeight(order.getWeight());
        dtwPersonalDelcareInfo.setRemark(order.getMemo());
        dtwPersonalDelcareInfo.setSenderName("");
        dtwPersonalDelcareInfo.setConsignee(order.getShipName());
        dtwPersonalDelcareInfo.setSenderCity("发件人城市");//非必填
        dtwPersonalDelcareInfo.setPaperType("");// 非必填
        dtwPersonalDelcareInfo.setPaperNumber("");// 非必填
        dtwPersonalDelcareInfo.setPurchaserTelNumber(order.getUserLoginName());
        dtwPersonalDelcareInfo.setBuyerIdType("1");
        dtwPersonalDelcareInfo.setBuyerIdNumber(order.getBuyerPid());
        dtwPersonalDelcareInfo.setBuyerName(order.getBuyerName());
        dtwPersonalDelcareInfo.setWorth(order.getFinalAmount());
        dtwPersonalDelcareInfo.setFeeAmount(order.getCostFreight());
        dtwPersonalDelcareInfo.setInsureAmount(0.0);
        dtwPersonalDelcareInfo.setCurrCode("142");
        if(orderItems.size()>0){
            dtwPersonalDelcareInfo.setMainGName(orderItems.get(0).getName());
        }
        dtwPersonalDelcareInfo.setTradeCountry("");
        dtwPersonalDelcareInfo.setSenderCountry("");
        dtwPersonalDelcareInfo.setECommerceCode(dtwSysData.getECommerceCode());
        dtwPersonalDelcareInfo.setECommerceName(dtwSysData.getECommerceName());

        List<DtwGoodsDelcareItem> dtwItems = new ArrayList<>();

        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem item = orderItems.get(i);

            DtwGoodsDelcareItem dtwGoodsDelcareItem = new DtwGoodsDelcareItem();
            dtwGoodsDelcareItem.setGoodsOrder(i);
            dtwGoodsDelcareItem.setCodeTs(item.getGoodBn());//商品HScode:填写商品海关编码
            dtwGoodsDelcareItem.setGoodsItemNo(item.getProductBn());
            dtwGoodsDelcareItem.setGoodsName(item.getName());
            dtwGoodsDelcareItem.setGoodsModel(item.getStandard());
            dtwGoodsDelcareItem.setOriginCountry("");
            dtwGoodsDelcareItem.setTradeCurr(DtwEnum.CurrencyEnum.RMB.getCode());// FIXME: 2016-07-13
            dtwGoodsDelcareItem.setTradeTotal(item.getAmount()*item.getNum());
            dtwGoodsDelcareItem.setDeclPrice(item.getAmount());
            dtwGoodsDelcareItem.setDeclTotalPrice(item.getAmount()*item.getNum());
//            dtwGoodsDelcareItem.setUseTo("");
            dtwGoodsDelcareItem.setDeclareCount(0);
            dtwGoodsDelcareItem.setGoodsUnit("011");
            dtwGoodsDelcareItem.setFirstUnit("011");
            dtwGoodsDelcareItem.setFirstCount(item.getNum());
//            dtwGoodsDelcareItem.setSecondUnit("");
//            dtwGoodsDelcareItem.setSecondCount(0.0);

            dtwItems.add(dtwGoodsDelcareItem);
        }

        
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
        } else{
            return EventResult.resultWith(EventResultEnum.ERROR,"服务器请求失败",null);
        }

    }

    @Override
    public EventResult deliverOrder(String Msgid,String wayBill, String weight,String state,ERPUserInfo erpUserInfo) {
        try {
            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
            if (erpUserHandler == null) {
                return EventResult.resultWith(EventResultEnum.NO_DATA,"未找到数据源信息",null);
            }
            DeliveryInfoEvent deliveryInfoEvent = new DeliveryInfoEvent();
            deliveryInfoEvent.setErpUserInfo(erpUserInfo);
            DeliveryInfo deliveryInfo = new DeliveryInfo();
            deliveryInfo.setOrderId(Msgid);
//            deliveryInfo.setLogiName(logiName);
            deliveryInfo.setLogiNo(wayBill);
            deliveryInfoEvent.setDeliveryInfo(deliveryInfo);
            EventResult eventResult = erpUserHandler.handleEvent(deliveryInfoEvent);
           return eventResult;
        } catch (Exception e) {
           return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @Override
    public EventResult stockQuery(DtwStockSearch dtwStockSearch, DtwSysData dtwSysData) {
        try{
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("data",JSON.toJSONString(dtwStockSearch));
            HttpResult httpResult = HttpClientUtil.getInstance().post(dtwSysData.getRequestUrl()+"/QBStockQey",requestMap);
            if(httpResult.getHttpStatus()==HttpStatus.SC_OK){
                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                if (result.getString("ErrCode").equals("000")) {
                    JSONArray jsonArray = result.getJSONArray("Items");
                    List<DtwStockItem> dtwStockItems = new ArrayList<>();
                    jsonArray.forEach(item->{
                        DtwStockItem dtwStockItem = JSON.parseObject(item.toString(),DtwStockItem.class);
                        dtwStockItems.add(dtwStockItem);
                    });
                    return EventResult.resultWith(EventResultEnum.SUCCESS,dtwStockItems);
                } else {
                    return EventResult.resultWith(EventResultEnum.ERROR, result.getString("ErrMsg"), null);
                }
            } else{
                return EventResult.resultWith(EventResultEnum.ERROR,"服务器请求失败",null);
            }

        } catch (Exception e){
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }
}
