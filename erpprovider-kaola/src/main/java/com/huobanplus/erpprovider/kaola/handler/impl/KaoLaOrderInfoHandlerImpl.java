package com.huobanplus.erpprovider.kaola.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.kaola.common.KaoLaSysData;
import com.huobanplus.erpprovider.kaola.formatkaola.KaoLaOrderItem;
import com.huobanplus.erpprovider.kaola.formatkaola.KaoLaUserInfo;
import com.huobanplus.erpprovider.kaola.handler.KaoLaBaseHandler;
import com.huobanplus.erpprovider.kaola.handler.KaoLaOrderInfoHandler;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.OrderStatusInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.OrderInfo;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuxiongliu on 2016/5/10.
 */
@Component
public class KaoLaOrderInfoHandlerImpl extends KaoLaBaseHandler implements KaoLaOrderInfoHandler {
    @Override
    public EventResult queryOrderStatusInfo(OrderStatusInfoEvent orderStatusInfoEvent) {

        OrderInfo orderInfo = orderStatusInfoEvent.getOrderInfo();
        ERPInfo erpInfo = orderStatusInfoEvent.getErpInfo();
        KaoLaSysData kaoLaSysData = JSON.parseObject(erpInfo.getSysDataJson(), KaoLaSysData.class);


        Map<String,Object> parameterMap = new TreeMap<String,Object>();
        parameterMap.put("channelId", orderInfo.getOrderChannel());
        parameterMap.put("thirdPartOrderId",orderInfo.getOrderCode());
        parameterMap.put("timestamp", StringUtil.DateFormat(orderInfo.getPayTime(),StringUtil.TIME_PATTERN));
        parameterMap.put("v",kaoLaSysData.getV());
        parameterMap.put("sign_method","md5");
        parameterMap.put("app_key",kaoLaSysData.getAppKey());


        try {
            Map<String,Object> requestData = getRequestData(kaoLaSysData,parameterMap);
            HttpResult httpResult = HttpClientUtil.getInstance().post(kaoLaSysData.getHost()+"/queryOrderStatus",requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                if(result.getString("recCode").equals("200")){
                    // TODO: 2016/5/9
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.get("recMeg").toString(),null);
                }

            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return EventResult.resultWith(EventResultEnum.ERROR);
        }
    }

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {

        Order orderInfo = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);

        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
        KaoLaSysData kaoLaSysData = JSON.parseObject(erpInfo.getSysDataJson(), KaoLaSysData.class);
        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();

        List<OrderItem> orderItems = orderInfo.getOrderItems();
        List<KaoLaOrderItem> kaoLaOrderItems = new ArrayList<KaoLaOrderItem>();

        orderItems.forEach(item->{
            KaoLaOrderItem kaoLaOrderItem = new KaoLaOrderItem();
            kaoLaOrderItem.setGoodsId("123");
            kaoLaOrderItem.setSkuId("123");
            kaoLaOrderItem.setBuyAmount(item.getNum());
            kaoLaOrderItems.add(kaoLaOrderItem);
        });

        KaoLaUserInfo userInfo = new KaoLaUserInfo();
        userInfo.setAccountId(String.valueOf(orderInfo.getMemberId()));
        userInfo.setName(orderInfo.getShipName());
        userInfo.setMobile(orderInfo.getShipMobile());
        userInfo.setEmail(orderInfo.getShipEmail());
        userInfo.setProvinceName(orderInfo.getProvince());
        //userInfo.setProvinceCode("");
        userInfo.setCityName(orderInfo.getCity());
//        userInfo.setCityCode("");
        userInfo.setDistrictName(orderInfo.getDistrict());
//        userInfo.setDistrictCode("");
        userInfo.setAddress(orderInfo.getShipAddr());
//        userInfo.setPostCode(orderInfo.getShipZip());
//        userInfo.setPhoneNum("");
//        userInfo.setPhoneAreaNum("");
//        userInfo.setPhoneExtNum("");
        userInfo.setIdentityId("362322199411050053");// FIXME: 2016/5/11



        Map<String,Object> parameterMap = new TreeMap<String,Object>();

        parameterMap.put("source", 1200L);
        parameterMap.put("thirdPartOrderId",orderInfo.getOrderId());
        parameterMap.put("timestamp", orderInfo.getPayTime());
        parameterMap.put("v",kaoLaSysData.getV());
        parameterMap.put("sign_method","md5");
        parameterMap.put("app_key",kaoLaSysData.getAppKey());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderItemList",kaoLaOrderItems);
        parameterMap.put("orderItemList",jsonObject.toJSONString());


        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("userInfo",userInfo);
        parameterMap.put("userInfo",jsonObject1.toJSONString());

        System.out.println("\n***********************");
        System.out.println(jsonObject.toJSONString());
        System.out.println(jsonObject1.toJSONString());
        System.out.println("***********************");

        try{
            Map<String,Object> requestData = getRequestData(kaoLaSysData,parameterMap);
            System.out.println(requestData.get("timestamp").toString());
            System.out.println(requestData.get("sign").toString());
            HttpResult httpResult = HttpClientUtil.getInstance().post(kaoLaSysData.getHost()+"/bookpayorder",requestData);
            System.out.println("status:"+httpResult.getHttpStatus());
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){


                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                System.out.println(result.toString());
                System.out.println("****************************");
                if(result.getString("recCode").equals("200")){
                    // TODO: 2016/5/9
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.get("recMeg").toString(),null);
                }

            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return EventResult.resultWith(EventResultEnum.ERROR);
        }

    }
}
