package com.huobanplus.erpservice.event.erpevent;

import com.huobanplus.erpservice.event.model.OrderInfo;

/**
 * 创建订单事件
 * Created by allan on 2015/7/21.
 */
public class CreateOrderEvent extends ERPBaseEvent {
    private OrderInfo orderInfo;

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
}
