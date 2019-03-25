package com.huobanplus.erpprovider.lz.handler;

import com.huobanplus.erpprovider.lz.common.LzSysData;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

public interface LzOrderHandler {
    /**
     * 订单下单
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);
    /**
     * 推送平台订单
     * @return
     */
    EventResult pushPlatformOrder(Order order, LzSysData lzSysData);
}
