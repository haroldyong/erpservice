/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.controller;

import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.eventhandler.model.DeliveryInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <b>提供给erp使用者的api，erp通过此类接口推送数据到erp服务平台，并转交给相应的erp处理</b>
 * <p>
 * <p>
 * <p>可能未在接口参数中体现,但是必传的字段</p>
 * <ul>
 * <li>userType: 使用者类型 {@link com.huobanplus.erpservice.datacenter.common.ERPTypeEnum.UserType}</li>
 * <li>customerId: 商户id,根据userTye区分是哪种类型的商户</li>
 * </ul>
 * <p>
 * Created by liual on 2015-10-19.
 */
@RequestMapping(value = "/hotProxy/order", method = RequestMethod.POST)
public interface OrderProxyController {
    /**
     * 创建订单
     *
     * @param orderInfoJson 订单json序列化
     * @param erpInfo       erp提供者信息 {@link ERPInfo}
     * @param erpUserInfo   erp使用者信息 {@link ERPUserInfo}
     * @param eventType     事件类型 {@link com.huobanplus.erpservice.eventhandler.common.EventType}
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createOrder")
    ApiResult createOrder(
            String orderInfoJson,
            @RequestAttribute ERPInfo erpInfo,
            @RequestAttribute ERPUserInfo erpUserInfo,
            int eventType
    ) throws Exception;

    @RequestMapping(value = "/updateOrder")
    ApiResult updateOrder(String orderInfoJson, @RequestAttribute ERPInfo erpInfo) throws Exception;

    /**
     * 将发货信息推送给ERP（发货）
     *
     * @param erpInfo
     * @param erpUserInfo
     * @param deliveryInfo 发货信息 {@link DeliveryInfo}
     * @return
     * @throws Exception
     */
    @RequestMapping("/orderDeliver")
    ApiResult orderDeliver(
            @RequestAttribute ERPInfo erpInfo,
            @RequestAttribute ERPUserInfo erpUserInfo,
            DeliveryInfo deliveryInfo
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
