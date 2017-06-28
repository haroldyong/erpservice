package com.huobanplus.erpprovider.gjbc.handler;

import com.huobanplus.erpprovider.gjbc.common.GJBCSysData;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by hxh on 2017-06-26.
 */
public interface GJBCOrderHandler {

    /**
     * 推送订单
     *
     * @param pushNewOrderEvent
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);

    /**
     * 推送支付宝支付单到海关
     *
     * @param order
     * @param gjbcSysData
     * @return
     */
    EventResult pushAliPayOrder(Order order, GJBCSysData gjbcSysData);
}
