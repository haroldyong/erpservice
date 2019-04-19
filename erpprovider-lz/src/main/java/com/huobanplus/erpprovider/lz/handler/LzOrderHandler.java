package com.huobanplus.erpprovider.lz.handler;

import com.huobanplus.erpprovider.lz.common.LzSysData;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.erpevent.push.CancelOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.GetTrackingInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.OrderRefundStatusUpdate;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpuser.huobanmall.common.ApiResult;

import javax.servlet.http.HttpServletRequest;

public interface LzOrderHandler {
    /**
     * 订单下单
     *
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);

    /**
     * 推送平台订单
     *
     * @return
     */
    EventResult pushPlatformOrder(Order order, LzSysData lzSysData);

    /**
     * 取消订单
     *
     * @param cancelOrderEvent
     * @return
     */
    EventResult cancelOrder(CancelOrderEvent cancelOrderEvent);

    /**
     * 获取物流轨迹查询
     * @param getTrackingInfoEvent
     * @return
     */
    EventResult tracking(GetTrackingInfoEvent getTrackingInfoEvent);
}
