package com.huobanplus.erpprovider.lz.handler.impl;

import com.huobanplus.erpprovider.lz.common.LzSysData;
import com.huobanplus.erpprovider.lz.handler.LzOrderHandler;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.springframework.stereotype.Component;

@Component
public class LzOrderHandlerImpl implements LzOrderHandler {
    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        return null;
    }

    @Override
    public EventResult pushPlatformOrder(Order order, LzSysData lzSysData) {
        return null;
    }
}
