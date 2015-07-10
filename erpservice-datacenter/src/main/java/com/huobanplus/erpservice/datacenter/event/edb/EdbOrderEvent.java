package com.huobanplus.erpservice.datacenter.event.edb;

import com.huobanplus.erpservice.datacenter.event.OrderEvent;
import com.huobanplus.erpservice.datacenter.event.edb.bean.OrderApiResult;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2015/7/10.
 */
@Component("edbOrderEnvent")
public class EdbOrderEvent implements OrderEvent {
    public OrderApiResult getOrderDetail(String orderId) {
        return null;
    }
}
