package com.huobanplus.erpprovider.gjbbc.handler;

import com.huobanplus.erpprovider.gjbbc.common.GjbbcSysData;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by hxh on 2017-08-15.
 */
public interface GjbbcOrderHandler {
    /**
     * 订单下单
     *
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);

    /**
     * 推送支付宝支付单到海关
     *
     * @param order        订单实体类
     * @param gjbbcSysData 系统参数
     * @return
     */
    EventResult PushOrderAliPay(Order order, GjbbcSysData gjbbcSysData);

    /**
     * 推送微信支付单到海关
     *
     * @param order
     * @param gjbbcSysData
     * @return
     */
    EventResult PushOrderWeiXin(Order order, GjbbcSysData gjbbcSysData);

    /**
     * 推送平台订单
     *
     * @return
     */
    EventResult pushPlatformOrder(Order order, GjbbcSysData gjbbcSysData);
}
