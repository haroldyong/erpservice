/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpuser.huobanmall.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.huobanplus.erpservice.common.util.HttpUtil;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.datacenter.entity.MallOrderBean;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.*;
import com.huobanplus.erpuser.huobanmall.common.ApiResult;
import com.huobanplus.erpuser.huobanmall.common.HBConstant;
import com.huobanplus.erpuser.huobanmall.handler.HBOrderHandler;
import com.huobanplus.erpuser.huobanmall.model.OrderListParse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liual on 2015-10-19.
 */
@Service
public class HBOrderHandlerImpl implements HBOrderHandler {

    @Override
    public EventResult deliverInfo(DeliveryInfo deliveryInfo, ERPUserInfo erpUserInfo) {
        if (deliveryInfo == null)
            return EventResult.resultWith(EventResultEnum.NO_DATA);

        return null;
    }

    @Override
    public EventResult returnInfo(ReturnInfo returnInfo, ERPUserInfo erpUserInfo) {
        return null;
    }

    @Override
    public EventResult syncInventory(InventoryInfo inventoryInfo, ERPUserInfo erpUserInfo) {
        return null;
    }

    @Override
    public EventResult obtainOrderList(OrderSearchInfo orderSearchInfo, ERPUserInfo erpUserInfo) {
        HttpUtil httpUtil = HttpUtil.getInstance();
        //获取伙伴商城接口数据
        //签名
        Map<String, String> signMap = new TreeMap<>();
        if (orderSearchInfo.getOrderStatus() != null) {
            signMap.put("orderStatus", String.valueOf(orderSearchInfo.getOrderStatus()));
        }
        if (orderSearchInfo.getPageIndex() != null) {
            signMap.put("pageIndex", String.valueOf(orderSearchInfo.getPageIndex()));
        }
        if (orderSearchInfo.getPageSize() != null) {
            signMap.put("pageSize", String.valueOf(orderSearchInfo.getPageSize()));
        }
        if (orderSearchInfo.getShipStatus() != null) {
            signMap.put("shipStatus", String.valueOf(orderSearchInfo.getShipStatus()));
        }
        if (orderSearchInfo.getPayStatus() != null) {
            signMap.put("payStatus", String.valueOf(orderSearchInfo.getPayStatus()));
        }
        signMap.put("timestamp", String.valueOf(new Date().getTime()));
        signMap.put("customerId", String.valueOf(erpUserInfo.getCustomerId()));
        signMap.put("beginTime", orderSearchInfo.getBeginTime());
        signMap.put("endTime", orderSearchInfo.getEndTime());
        try {
            String sign = SignBuilder.buildSign(signMap, null, HBConstant.SECRET_KEY);
            Map<String, String> requestMap = new HashMap<>(signMap);
            requestMap.put("sign", sign);
            String responseData = httpUtil.doPost(HBConstant.REQUEST_URL + "/ErpOrderApi/OrderList", requestMap);
            ApiResult<OrderListParse> apiResult = JSON.parseObject(responseData, new TypeReference<ApiResult<OrderListParse>>() {
            }.getType());
            if (apiResult.getCode() == 200) {
                return EventResult.resultWith(EventResultEnum.SUCCESS, apiResult.getData());
            }
            return EventResult.resultWith(EventResultEnum.ERROR, apiResult.getMsg(), null);
        } catch (IOException e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    @Override
    public EventResult obtainOrderDetail(String orderId, ERPUserInfo erpUserInfo) {
        if (StringUtils.isEmpty(orderId)) {
            return EventResult.resultWith(EventResultEnum.BAD_REQUEST_PARAM, "orderId未传", null);
        }
        HttpUtil httpUtil = HttpUtil.getInstance();
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderId", orderId);
        signMap.put("timestamp", String.valueOf(new Date().getTime()));
        try {
            String sign = SignBuilder.buildSign(signMap, null, HBConstant.SECRET_KEY);
            Map<String, String> requestMap = new HashMap<>(signMap);
            requestMap.put("sign", sign);
            String responseData = httpUtil.doPost(HBConstant.REQUEST_URL + "/ErpOrderApi/OrderDetail", requestMap);
            ApiResult<MallOrderBean> apiResult = JSON.parseObject(responseData, new TypeReference<ApiResult<MallOrderBean>>() {
            });
            if (apiResult.getCode() == 200) {
                return EventResult.resultWith(EventResultEnum.SUCCESS, apiResult.getData());
            }
            return EventResult.resultWith(EventResultEnum.ERROR, apiResult.getMsg(), null);
        } catch (IOException e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }
}
