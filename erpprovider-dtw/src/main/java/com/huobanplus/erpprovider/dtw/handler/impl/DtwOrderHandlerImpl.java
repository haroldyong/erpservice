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
            dtwOrder.setOrderType(2);//订单类型（1：普通订单：与快递已经完成对接，2：综合订单：委托大田与快递公司对接）

            dtwOrder.setMsgid(order.getOrderId());//(必填)
            dtwOrder.setPayType(3);// TODO: 2016/6/17 //(必填)
            dtwOrder.setPayCompanyCode("001");//(必填) 支付公司在海关的备案码 // FIXME: 2016-07-13
            dtwOrder.setPayNumber("100001");//(必填) 支付单号 // FIXME: 2016-07-13
            dtwOrder.setOrderTotalAmount(order.getFinalAmount());//(必填)
            dtwOrder.setOrderGoodsAmount(order.getFinalAmount()-order.getCostFreight());//(必填)
            dtwOrder.setOrderNo(order.getOrderId());//(必填)
            dtwOrder.setOrderTaxAmount(1.0);// FIXME: 2016/6/17//(必填)
            dtwOrder.setTotalCount(order.getItemNum());//(必填)
            dtwOrder.setTotalAmount(order.getFinalAmount());//(必填)
            dtwOrder.setLogisCompanyName(order.getLogiName());//(必填)
            dtwOrder.setLogisCompanyCode(order.getLogiCode());//(必填) FIXME: 2016-07-13
            dtwOrder.setPurchaserId(String.valueOf(order.getMemberId()));//(必填)
            dtwOrder.setShipper("发货人姓名");// TODO: 2016/6/16//(必填)
            dtwOrder.setShipperPro("");
            dtwOrder.setShipperCity("");
            dtwOrder.setShipperDistrict("");
            dtwOrder.setShipperAddress("浙江杭州滨江区");//TODO 发货人地址（必填）
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
            dtwOrder.setConsigneeZip(order.getShipZip());//(必填)
            dtwOrder.setWeight(order.getWeight());// FIXME: 2016/6/16//(必填) 毛重
            dtwOrder.setLotNo("");
            dtwOrder.setNetWeight(order.getWeight());// FIXME: 2016/6/16//(必填) 净重
            dtwOrder.setIeFlag(73);//（(必填)// FIXME: 2016/6/16 73:进口 79:出口

            List<DtwOrderItem> dtwOrderItemList = new ArrayList<>();
            List<OrderItem> orderItemList = order.getOrderItems();
            for (int i = 0; i < orderItemList.size(); i++) {
                OrderItem orderItem = orderItemList.get(i);
                DtwOrderItem dtwOrderItem = new DtwOrderItem();
                dtwOrderItem.setMsgitem(i);//(必填)
                dtwOrderItem.setPartno(orderItem.getProductBn());//(必填)
                dtwOrderItem.setPartName(orderItem.getName());//(必填)
                dtwOrderItem.setSpec(orderItem.getStandard());//(必填)
                dtwOrderItem.setUnit("011");// FIXME: 2016-07-08 计量单位，海关三字代码(必填)  件
                dtwOrderItem.setCurrency(order.getCurrency());// FIXME: 2016/6/16 (必填)
                dtwOrderItem.setAmount(orderItem.getAmount());//(必填)
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
            if(orderPushEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()){
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
            } else{
                orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                orderDetailSyncLog.setErrorMsg(orderPushEventResult.getResultMsg()+";"+orderPushEventResult.getResultCode());
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
        dtwPersonalDelcareInfo.setPassKey(dtwSysData.getPassKey());// 必填
        dtwPersonalDelcareInfo.setMsgid(order.getOrderId());// 必填
        dtwPersonalDelcareInfo.setCompanyName("");// 必填
        dtwPersonalDelcareInfo.setCompanyCode("");// 必填
//        dtwPersonalDelcareInfo.setPreEntryNumber("");
        dtwPersonalDelcareInfo.setImportType(0);// FIXME: 2016/7/4 进口类型（0一般进口，1保税进口）
        dtwPersonalDelcareInfo.setOrderNo(order.getOrderId());// 必填
        dtwPersonalDelcareInfo.setWayBill("");
        dtwPersonalDelcareInfo.setPackNo(order.getItemNum());// 必填// FIXME: 2016/7/4
        dtwPersonalDelcareInfo.setGrossWeight(order.getWeight());// 必填
        dtwPersonalDelcareInfo.setNetWeight(order.getWeight());// 必填
        dtwPersonalDelcareInfo.setRemark(order.getMemo());
        dtwPersonalDelcareInfo.setSenderName("");// 必填
        dtwPersonalDelcareInfo.setConsignee(order.getShipName());// 必填
        dtwPersonalDelcareInfo.setSenderCity("发件人城市");//非必填
        dtwPersonalDelcareInfo.setPaperType("");// 非必填
        dtwPersonalDelcareInfo.setPaperNumber("");// 非必填
        dtwPersonalDelcareInfo.setPurchaserTelNumber(order.getUserLoginName());// 必填
        dtwPersonalDelcareInfo.setBuyerIdType("1");// 必填
        dtwPersonalDelcareInfo.setBuyerIdNumber(order.getBuyerPid());// 必填
        dtwPersonalDelcareInfo.setBuyerName(order.getBuyerName());// 必填
        dtwPersonalDelcareInfo.setWorth(order.getFinalAmount());// 必填
        dtwPersonalDelcareInfo.setFeeAmount(order.getCostFreight());// 必填
        dtwPersonalDelcareInfo.setInsureAmount(0.0);// 必填
        dtwPersonalDelcareInfo.setCurrCode("142");
        if(orderItems.size()>0){
            dtwPersonalDelcareInfo.setMainGName(orderItems.get(0).getName());// 必填
        }
        dtwPersonalDelcareInfo.setTradeCountry("");// 必填
        dtwPersonalDelcareInfo.setSenderCountry("");// 必填
        dtwPersonalDelcareInfo.setECommerceCode(dtwSysData.getECommerceCode());// 必填
        dtwPersonalDelcareInfo.setECommerceName(dtwSysData.getECommerceName());// 必填

        List<DtwGoodsDelcareItem> dtwItems = new ArrayList<>();

        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem item = orderItems.get(i);

            DtwGoodsDelcareItem dtwGoodsDelcareItem = new DtwGoodsDelcareItem();
            dtwGoodsDelcareItem.setGoodsOrder(i);// 必填
            dtwGoodsDelcareItem.setCodeTs(item.getGoodBn());// 必填//商品HScode:填写商品海关编码
            dtwGoodsDelcareItem.setGoodsItemNo(item.getProductBn());// 必填
            dtwGoodsDelcareItem.setGoodsName(item.getName());// 必填
            dtwGoodsDelcareItem.setGoodsModel(item.getStandard());// 必填
            dtwGoodsDelcareItem.setOriginCountry("");// 必填
            dtwGoodsDelcareItem.setTradeCurr(DtwEnum.CurrencyEnum.RMB.getCode());// FIXME: 2016-07-13
            dtwGoodsDelcareItem.setTradeTotal(item.getAmount()*item.getNum());// 必填
            dtwGoodsDelcareItem.setDeclPrice(item.getAmount());// 必填
            dtwGoodsDelcareItem.setDeclTotalPrice(item.getAmount()*item.getNum());// 必填
//            dtwGoodsDelcareItem.setUseTo("");
            dtwGoodsDelcareItem.setDeclareCount(item.getNum());// 必填
            dtwGoodsDelcareItem.setGoodsUnit("011");// 必填
//            dtwGoodsDelcareItem.setGoodsGrossWeight("");
            dtwGoodsDelcareItem.setFirstUnit("011");// 必填
            dtwGoodsDelcareItem.setFirstCount(item.getNum());// 必填
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
