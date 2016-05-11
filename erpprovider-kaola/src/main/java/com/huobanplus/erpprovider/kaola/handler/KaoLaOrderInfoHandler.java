package com.huobanplus.erpprovider.kaola.handler;

import com.huobanplus.erpservice.eventhandler.erpevent.OrderStatusInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by wuxiongliu on 2016/5/9.
 */

public interface KaoLaOrderInfoHandler {

    EventResult queryOrderStatusInfo(OrderStatusInfoEvent orderStatusInfoEvent);

    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);
}
