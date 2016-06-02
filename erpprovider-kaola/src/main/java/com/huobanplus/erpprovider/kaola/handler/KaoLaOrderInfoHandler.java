package com.huobanplus.erpprovider.kaola.handler;

import com.huobanplus.erpprovider.kaola.common.KaoLaSysData;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

import java.util.List;

/**
 * Created by wuxiongliu on 2016/5/9.
 */

public interface KaoLaOrderInfoHandler {

    /**
     *  订单状态查询
     * @param orderList
     * @return
     */
    EventResult queryOrderStatusInfo(List<Order> orderList, KaoLaSysData kaoLaSysData);

    /**
     *  代下单代支付
     * @param pushNewOrderEvent
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);
}
