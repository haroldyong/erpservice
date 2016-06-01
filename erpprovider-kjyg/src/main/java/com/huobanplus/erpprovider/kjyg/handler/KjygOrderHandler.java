package com.huobanplus.erpprovider.kjyg.handler;

import com.huobanplus.erpprovider.kjyg.common.KjygSysData;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
public interface KjygOrderHandler {

    /**
     *  推送订单
     * @param pushNewOrderEvent
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);

    /**
     * 销售商获取运单号及航班
     * @param orderNo
     * @return
     */
    EventResult queryOrderTradNo(String orderNo,KjygSysData kjygSysData);

    /**
     * 销售商订单跟踪
     * @param orderNo
     * @return
     */
    EventResult queryOrderStat(String orderNo, KjygSysData kjygSysData);
}
