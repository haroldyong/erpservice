package com.huobanplus.erpservice.event.erpevent;

import com.huobanplus.erpservice.event.model.AuthBean;
import com.huobanplus.erpservice.event.model.OrderInfo;

/**
 * <b>类描述：<b/>创建订单事件
 * Created by allan on 2015/7/21.
 */
public class CreateOrderEvent extends ERPBaseEvent {

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
