package com.huobanplus.erpservice.datacenter.erp;

import com.huobanplus.erpservice.datacenter.event.GoodEvent;
import com.huobanplus.erpservice.datacenter.event.OrderEvent;
import com.huobanplus.erpservice.datacenter.event.DeliverEvent;

/**
 * Created by Administrator on 2015/7/10.
 */
public interface HOTErp {
    OrderEvent buildOrderEvent();

    GoodEvent buildGoodEvent();

    DeliverEvent buildShipEvent();
}
