package com.huobanplus.erpservice.datacenter.erp.hotapi.edb;

import com.huobanplus.erpservice.datacenter.erp.hotapi.ERPApi;
import com.huobanplus.erpservice.datacenter.event.GoodEvent;
import com.huobanplus.erpservice.datacenter.event.OrderEvent;
import com.huobanplus.erpservice.datacenter.event.ShipEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2015/7/10.
 */
@Component("edbErpApi")
public class EdbErpApi implements ERPApi {
    @Resource(name = "edbOrderEnvent")
    OrderEvent orderEnvent;
    @Resource(name = "edbGoodEnvent")
    GoodEvent goodEnvent;
    @Resource(name = "shipEnvent")
    ShipEvent shipEnvent;

    public OrderEvent buildOrderEvent() {
        return orderEnvent;
    }

    public GoodEvent buildGoodEvent() {
        return goodEnvent;
    }

    public ShipEvent buildShipEvent() {
        return shipEnvent;
    }
}
