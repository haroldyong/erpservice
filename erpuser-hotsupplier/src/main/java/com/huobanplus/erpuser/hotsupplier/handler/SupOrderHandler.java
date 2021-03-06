/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpuser.hotsupplier.handler;

import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.model.OrderSearchInfo;
import com.huobanplus.erpservice.datacenter.model.ReturnInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

import java.util.List;

/**
 * Created by liual on 2015-12-08.
 */
public interface SupOrderHandler {
    /**
     * 发货通知处理
     *
     * @param deliveryInfo
     * @return
     */
    EventResult deliverInfo(OrderDeliveryInfo deliveryInfo, ERPUserInfo erpUserInfo);

    /**
     * 退货通知
     *
     * @param returnInfo
     * @return
     */
    EventResult returnInfo(ReturnInfo returnInfo, ERPUserInfo erpUserInfo);

    /**
     * 获取订单列表
     *
     * @param orderSearchInfo
     * @return
     */
    EventResult obtainOrderList(OrderSearchInfo orderSearchInfo, ERPUserInfo erpUserInfo);

    /**
     * 获得订单详情
     *
     * @param orderId
     * @return
     */
    EventResult obtainOrderDetail(String orderId, ERPUserInfo erpUserInfo);

    /**
     * 推送订单列表信息,到角色出保存
     *
     * @param orderListJson
     * @return
     */
    EventResult pushOrderDetailList(String orderListJson);

    /**
     * 批量发货同步
     *
     * @param orderDeliveryInfoList 批量发货物流信息
     * @param erpUserInfo           erp使用者信息
     * @return
     */
    EventResult batchDeliver(List<OrderDeliveryInfo> orderDeliveryInfoList, ERPUserInfo erpUserInfo);

    /**
     * 同步渠道订单到
     *
     * @param orderList
     * @param erpUserInfo
     * @return
     */
    EventResult syncChannelOrderList(List<Order> orderList, ERPUserInfo erpUserInfo);

    /**
     * 推送已审核单到平台
     *
     * @param orderIds
     * @return
     */
    EventResult pushAuditedOrderList(List<String> orderIds, ERPUserInfo erpUserInfo);
}
