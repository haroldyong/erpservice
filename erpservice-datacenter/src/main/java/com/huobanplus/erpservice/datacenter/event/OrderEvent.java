package com.huobanplus.erpservice.datacenter.event;

/**
 * Created by Administrator on 2015/7/10.
 */
public interface OrderEvent<T> extends HOTEvent {
    T getOrderDetail(String orderId);
}
