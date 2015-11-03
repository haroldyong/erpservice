/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.controller;

import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 提供给erp使用者的api，erp通过此类接口推送数据到erp服务平台，并转交给相应的erp处理
 * Created by liual on 2015-10-19.
 */
@RequestMapping(value = "/hotProxy/order", method = RequestMethod.POST)
public interface OrderProxyController {
    /**
     * 创建订单
     *
     * @param orderInfoJson
     * @param erpInfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createOrder")
    ApiResult createOrder(
            String orderInfoJson,
            @RequestAttribute ERPInfo erpInfo
    ) throws Exception;

    @RequestMapping(value = "/updateOrder")
    ApiResult updateOrder(String orderInfoJson, @RequestAttribute ERPInfo erpInfo) throws Exception;

    @RequestMapping("/orderDeliver")
    ApiResult orderDeliver(
            String orderId,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date deliverTime,
            String logiName,
            String logiNo,
            String weight,
            @RequestAttribute ERPInfo erpInfo
    ) throws Exception;

    @RequestMapping("/cancelOrder")
    ApiResult cancelOrder(
            String orderId,
            @RequestAttribute ERPInfo erpInfo
    ) throws Exception;

    @RequestMapping("/getOrderDetail")
    ApiResult getOrderDetail(
            String orderId,
            @RequestAttribute ERPInfo erpInfo
    ) throws Exception;
}
