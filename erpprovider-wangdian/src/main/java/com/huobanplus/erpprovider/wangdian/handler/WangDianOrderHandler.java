package com.huobanplus.erpprovider.wangdian.handler;

import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
public interface WangDianOrderHandler {

    /**
     *  推送订单
     * @param pushNewOrderEvent
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);

}
