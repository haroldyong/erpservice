package com.huobanplus.erpservice.eventhandler.erpevent;

import com.huobanplus.erpservice.eventhandler.model.OrderInfo;

/**
 * <b>类描述：<b/>订单(发货)状态信息事件
 * Created by allan on 2015/7/21.
 */
public class OrderStatusInfoEvent extends ERPBaseEvent {

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
