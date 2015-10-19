package com.huobanplus.erpservice.eventhandler.erpevent;

import com.huobanplus.erpservice.eventhandler.model.OrderInfo;

/**
 * <p>获取订单信息事件<p/>
 */
public class ObtainOrderListEvent extends ERPBaseEvent {

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
