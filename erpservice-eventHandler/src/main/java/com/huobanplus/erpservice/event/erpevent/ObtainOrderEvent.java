package com.huobanplus.erpservice.event.erpevent;

import com.huobanplus.erpservice.event.model.OrderInfo;

/**
 * <p>获取订单信息事件<p/>
 */
public class ObtainOrderEvent extends ERPBaseEvent {

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
