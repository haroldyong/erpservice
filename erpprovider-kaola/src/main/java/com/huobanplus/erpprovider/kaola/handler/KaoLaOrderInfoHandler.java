package com.huobanplus.erpprovider.kaola.handler;

import com.huobanplus.erpservice.eventhandler.erpevent.OrderStatusInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by wuxiongliu on 2016/5/9.
 */

public interface KaoLaOrderInfoHandler {

    /**
     *  订单状态查询
     * @param orderStatusInfoEvent
     * @return
     */
    EventResult queryOrderStatusInfo(OrderStatusInfoEvent orderStatusInfoEvent);

    /**
     *  代下单代支付
     * @param pushNewOrderEvent
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);
}
