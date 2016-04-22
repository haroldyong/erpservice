/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpuser.hotsupplier.handler.impl;

import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.model.OrderSearchInfo;
import com.huobanplus.erpservice.datacenter.model.ReturnInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpuser.hotsupplier.handler.SupOrderHandler;
import com.huobanplus.erpuser.huobanmall.handler.HBOrderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liual on 2015-12-08.
 */
@Service
public class SupOrderHandlerImpl implements SupOrderHandler {
    @Autowired
    private HBOrderHandler hbOrderHandler;

    @Override
    public EventResult deliverInfo(OrderDeliveryInfo deliveryInfo, ERPUserInfo erpUserInfo) {
//        Map<String, Object> signMap = SupConstant.buildSignMap(deliveryInfo);
//        signMap.put("timestamp", String.valueOf(new Date().getTime()));
//        try {
//            String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, SupConstant.SECRET_KEY);
//            Map<String, Object> requestMap = new HashMap<>(signMap);
//
//            requestMap.put("sign", sign);
//            HttpResult httpResult = HttpClientUtil.getInstance().post(SupConstant.SUP_REQUEST_URL + "/order/deliveryInfo", requestMap);
//            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
//                SupApiResult apiResult = JSON.parseObject(httpResult.getHttpContent(), SupApiResult.class);
//                if (apiResult.getCode() == 200) {
//                    return EventResult.resultWith(EventResultEnum.SUCCESS);
//                }
//                return EventResult.resultWith(EventResultEnum.ERROR, apiResult.getMsg(), null);
//            }
//            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
//        } catch (IOException e) {
//            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
//        }
        return hbOrderHandler.deliverInfo(deliveryInfo, erpUserInfo);
    }

    @Override
    public EventResult returnInfo(ReturnInfo returnInfo, ERPUserInfo erpUserInfo) {
//        Map<String, Object> signMap = SupConstant.buildSignMap(returnInfo);
//        signMap.put("timestamp", String.valueOf(new Date().getTime()));
//        try {
//            String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, SupConstant.SECRET_KEY);
//            Map<String, Object> requestMap = new HashMap<>(signMap);
//            requestMap.put("sign", sign);
//            HttpResult httpResult = HttpClientUtil.getInstance().post(SupConstant.SUP_REQUEST_URL + "/order/returnInfo", requestMap);
//            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
//                SupApiResult apiResult = JSON.parseObject(httpResult.getHttpContent(), SupApiResult.class);
//                if (apiResult.getCode() == 200) {
//                    return EventResult.resultWith(EventResultEnum.SUCCESS);
//                }
//                return EventResult.resultWith(EventResultEnum.ERROR, apiResult.getMsg(), null);
//            }
//            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
//        } catch (IOException e) {
//            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
//        }
        return hbOrderHandler.returnInfo(returnInfo, erpUserInfo);
    }

    @Override
    public EventResult obtainOrderList(OrderSearchInfo orderSearchInfo, ERPUserInfo erpUserInfo) {
        //获取伙伴商城接口数据
        //签名
//        Map<String, Object> signMap = SupConstant.buildSignMap(orderSearchInfo);
//        signMap.put("supplierId", erpUserInfo.getCustomerId());
//        signMap.put("timestamp", new Date().getTime());
//        try {
//            String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, SupConstant.SECRET_KEY);
//            Map<String, Object> requestMap = new HashMap<>(signMap);
//            requestMap.put("sign", sign);
//            HttpResult httpResult = HttpClientUtil.getInstance().post(SupConstant.HB_REQUEST_URL + "/ErpOrderApi/SupplierOrderList", requestMap);
//            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
//                ApiResult<List<Order>> apiResult = JSON.parseObject(httpResult.getHttpContent(), new TypeReference<ApiResult<List<Order>>>() {
//                });
//                if (apiResult.getCode() == 200) {
//                    return EventResult.resultWith(EventResultEnum.SUCCESS, apiResult.getData());
//                }
//                return EventResult.resultWith(EventResultEnum.ERROR, apiResult.getMsg(), null);
//            }
//
//            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
//        } catch (IOException e) {
//            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
//        }
        return hbOrderHandler.obtainOrderList(orderSearchInfo, erpUserInfo);
    }

    @Override
    public EventResult obtainOrderDetail(String orderId, ERPUserInfo erpUserInfo) {
//        if (StringUtils.isEmpty(orderId)) {
//            return EventResult.resultWith(EventResultEnum.BAD_REQUEST_PARAM, "orderId未传", null);
//        }
//        Map<String, Object> signMap = new TreeMap<>();
//        signMap.put("orderId", orderId);
//        signMap.put("timestamp", new Date().getTime());
//        try {
//            String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, SupConstant.SECRET_KEY);
//            Map<String, Object> requestMap = new HashMap<>(signMap);
//            requestMap.put("sign", sign);
//            HttpResult httpResult = HttpClientUtil.getInstance().post(SupConstant.HB_REQUEST_URL + "/ErpOrderApi/OrderDetail", requestMap);
//            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
//                ApiResult<MallOrderBean> apiResult = JSON.parseObject(httpResult.getHttpContent(), new TypeReference<ApiResult<MallOrderBean>>() {
//                });
//                if (apiResult.getCode() == 200) {
//                    return EventResult.resultWith(EventResultEnum.SUCCESS, apiResult.getData());
//                }
//                return EventResult.resultWith(EventResultEnum.ERROR, apiResult.getMsg(), null);
//            }
//            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
//        } catch (IOException e) {
//            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
//        }
        return hbOrderHandler.obtainOrderDetail(orderId, erpUserInfo);
    }

    @Override
    public EventResult pushOrderDetailList(String orderListJson) {
//        if (StringUtils.isEmpty(orderListJson)) {
//            return EventResult.resultWith(EventResultEnum.BAD_REQUEST_PARAM, "没有可以推送的订单数据", null);
//        }
//        Map<String, Object> signMap = new TreeMap<>();
//        signMap.put("orderListJson", orderListJson);
//        signMap.put("timestamp", new Date().getTime());
//        try {
//            String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, SupConstant.SECRET_KEY);
//            Map<String, Object> requestMap = new HashMap<>(signMap);
//            requestMap.put("sign", sign);
//            HttpResult httpResult = HttpClientUtil.getInstance().post(SupConstant.SUP_REQUEST_URL + "/order/batchDeliver", requestMap);
//            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
//                ApiResult apiResult = JSON.parseObject(httpResult.getHttpContent(), ApiResult.class);
//                if (apiResult.getCode() == 200) {
//                    return EventResult.resultWith(EventResultEnum.SUCCESS, apiResult.getData());
//                }
//                return EventResult.resultWith(EventResultEnum.ERROR, apiResult.getMsg(), null);
//            }
//            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
//        } catch (IOException e) {
//            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
//        }
        return hbOrderHandler.pushOrderDetailList(orderListJson);
    }

    @Override
    public EventResult batchDeliver(List<OrderDeliveryInfo> orderDeliveryInfoList, ERPUserInfo erpUserInfo) {
        return hbOrderHandler.batchDeliver(orderDeliveryInfoList, erpUserInfo);
    }
}
