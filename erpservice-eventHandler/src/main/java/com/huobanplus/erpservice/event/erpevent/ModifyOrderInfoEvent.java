package com.huobanplus.erpservice.event.erpevent;

import com.huobanplus.erpservice.event.model.OrderInfo;

/**
 * 修改订单信息事件
 */
public class ModifyOrderInfoEvent extends ERPBaseEvent {

    /**
     * 订单信息实体
     */
    private OrderInfo orderInfo;

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

}

