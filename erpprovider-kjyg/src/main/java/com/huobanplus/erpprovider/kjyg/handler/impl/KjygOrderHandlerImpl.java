package com.huobanplus.erpprovider.kjyg.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.kjyg.common.KjygSysData;
import com.huobanplus.erpprovider.kjyg.formatkjyg.KjygCreateOrderInfo;
import com.huobanplus.erpprovider.kjyg.formatkjyg.KjygOrderItem;
import com.huobanplus.erpprovider.kjyg.handler.KjygOrderHandler;
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
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Component
public class KjygOrderHandlerImpl implements KjygOrderHandler {

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        Order orderInfo = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);

        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
        KjygSysData kjygSysData = JSON.parseObject(erpInfo.getSysDataJson(), KjygSysData.class);
        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();

        List<OrderItem> orderItems = orderInfo.getOrderItems();
        List<KjygOrderItem> kjygOrderItems = new ArrayList<KjygOrderItem>();
        orderItems.forEach(orderItem -> {
            KjygOrderItem kjygOrderItem = new KjygOrderItem();
            kjygOrderItem.setSpe(String.valueOf(orderItem.getItemId()));
            kjygOrderItem.setAmount(String.valueOf(orderItem.getNum()));
            kjygOrderItem.setPrice(String.valueOf(orderItem.getPrice()));
            kjygOrderItems.add(kjygOrderItem);
        });

        KjygCreateOrderInfo createOrderInfo = new KjygCreateOrderInfo();

        createOrderInfo.setClientcode(kjygSysData.getClientCode());
        createOrderInfo.setShipName(orderInfo.getShipName());
        createOrderInfo.setShipMobile(orderInfo.getShipMobile());
        createOrderInfo.setWebAccountNo("5555@qq.com");// TODO: 2016/5/24  
        createOrderInfo.setWebTradeNo("JD0000002");// TODO: 2016/5/24  
        createOrderInfo.setWebPayNo("P0000001");// TODO: 2016/5/24  
        createOrderInfo.setPayWay("01");// TODO: 2016/5/24  
        createOrderInfo.setBuyerPid("360782199009090099");// TODO: 2016/5/24
        createOrderInfo.setBuyerName(orderInfo.getShipName());// FIXME: 2016/5/24
        createOrderInfo.setBuyerTel("13211112222");// TODO: 2016/5/24  
        createOrderInfo.setPayment("100.00");// TODO: 2016/5/24  
        createOrderInfo.setWebsite("01");// TODO: 2016/5/24  
        createOrderInfo.setProvince("01");orderInfo.getProvince();
        createOrderInfo.setCity("0101");orderInfo.getCity();
        createOrderInfo.setArea("010105");orderInfo.getShipArea();
        createOrderInfo.setPostCode("341401");orderInfo.getShipZip();
        createOrderInfo.setShipAddr("浙江省杭州市西湖区XXX栋2303室");orderInfo.getShipAddr();
        createOrderInfo.setRemark("订单备注内容");orderInfo.getRemark();
        createOrderInfo.setFharea("日本");// TODO: 2016/5/24
        createOrderInfo.setOrderNo("XE0000003");orderInfo.getOrderId();
        createOrderInfo.setOrderItems(kjygOrderItems);

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(createOrderInfo);

        Map<String,Object> requestData = new HashMap<>();
        requestData.put("clientkey",kjygSysData.getClientKey());
        requestData.put("mtype","trade");
        requestData.put("tradelist",jsonArray);

        System.out.println(JSON.toJSONString(jsonArray).toString());
        HttpResult httpResult = HttpClientUtil.getInstance().post(kjygSysData.getRequestUrl(),requestData);
        if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
            JSONObject result = JSON.parseObject(httpResult.getHttpContent());
            if(result.getString("sts").equals("Y")){
                saveLog(orderInfo,erpUserInfo,erpInfo,pushNewOrderEvent,true,null);
                System.out.println(result.getString("res"));
                return EventResult.resultWith(EventResultEnum.SUCCESS);
            }else{
                System.out.println(result.getString("res"));
                saveLog(orderInfo,erpUserInfo,erpInfo,pushNewOrderEvent,false,result.getString("res"));
                return EventResult.resultWith(EventResultEnum.ERROR);
            }
        }else{
            saveLog(orderInfo,erpUserInfo,erpInfo,pushNewOrderEvent,false,httpResult.getHttpContent());
            return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
        }
    }

    /**
     * 记录日志
     * @param orderInfo
     * @param erpUserInfo
     * @param erpInfo
     * @param pushNewOrderEvent
     * @param isSuccess
     */
    private void saveLog(Order orderInfo,ERPUserInfo erpUserInfo,ERPInfo erpInfo,PushNewOrderEvent pushNewOrderEvent,boolean isSuccess,String errorMsg ){
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
        if(errorMsg != null){
            orderDetailSyncLog.setErrorMsg(errorMsg);
        }

        if (isSuccess) {
            orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
        } else {
            orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
        }

        orderDetailSyncLogService.save(orderDetailSyncLog);
    }
}
