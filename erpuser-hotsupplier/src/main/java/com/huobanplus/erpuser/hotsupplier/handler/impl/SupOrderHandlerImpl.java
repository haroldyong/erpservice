/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpuser.hotsupplier.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.datacenter.entity.MallOrderBean;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.*;
import com.huobanplus.erpuser.hotsupplier.common.ApiResult;
import com.huobanplus.erpuser.hotsupplier.common.SupConstant;
import com.huobanplus.erpuser.hotsupplier.handler.SupOrderHandler;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by liual on 2015-12-08.
 */
@Service
public class SupOrderHandlerImpl implements SupOrderHandler {
    @Override
    public EventResult deliverInfo(DeliveryInfo deliveryInfo, ERPUserInfo erpUserInfo) {
        return null;
    }

    @Override
    public EventResult returnInfo(ReturnInfo returnInfo, ERPUserInfo erpUserInfo) {
        return null;
    }

    @Override
    public EventResult obtainOrderList(OrderSearchInfo orderSearchInfo, ERPUserInfo erpUserInfo) {
        //获取伙伴商城接口数据
        //签名
        Map<String, Object> signMap = SupConstant.buildSignMap(orderSearchInfo);
        signMap.put("supplierId", erpUserInfo.getCustomerId());
        signMap.put("timestamp", new Date().getTime());
        try {
            String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, SupConstant.SECRET_KEY);
            Map<String, Object> requestMap = new HashMap<>(signMap);
            requestMap.put("sign", sign);
            HttpResult httpResult = HttpClientUtil.getInstance().post(SupConstant.HB_REQUEST_URL + "/ErpOrderApi/SupplierOrderList", requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                ApiResult<List<Order>> apiResult = JSON.parseObject(httpResult.getHttpContent(), new TypeReference<ApiResult<List<Order>>>() {
                });
                if (apiResult.getCode() == 200) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS, apiResult.getData());
                }
                return EventResult.resultWith(EventResultEnum.ERROR, apiResult.getMsg(), null);
            }

            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
        } catch (IOException e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    @Override
    public EventResult obtainOrderDetail(String orderId, ERPUserInfo erpUserInfo) {
        if (StringUtils.isEmpty(orderId)) {
            return EventResult.resultWith(EventResultEnum.BAD_REQUEST_PARAM, "orderId未传", null);
        }
        Map<String, Object> signMap = new TreeMap<>();
        signMap.put("orderId", orderId);
        signMap.put("timestamp", new Date().getTime());
        try {
            String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, SupConstant.SECRET_KEY);
            Map<String, Object> requestMap = new HashMap<>(signMap);
            requestMap.put("sign", sign);
            HttpResult httpResult = HttpClientUtil.getInstance().post(SupConstant.HB_REQUEST_URL + "/ErpOrderApi/OrderDetail", requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                ApiResult<MallOrderBean> apiResult = JSON.parseObject(httpResult.getHttpContent(), new TypeReference<ApiResult<MallOrderBean>>() {
                });
                if (apiResult.getCode() == 200) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS, apiResult.getData());
                }
                return EventResult.resultWith(EventResultEnum.ERROR, apiResult.getMsg(), null);
            }
            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
        } catch (IOException e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }
}
