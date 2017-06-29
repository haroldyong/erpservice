package com.huobanplus.erpprovider.gjbc.handler;

import com.huobanplus.erpprovider.gjbc.common.GjbcData;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by montage on 2017/6/26.
 */
public interface GjbcOrderHandler {


    /**
     * 订单推送
     *
     * @param pushNewOrderEvent 订单推送事件
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);

    /**
     * 推送平台订单
     * @return
     */
    EventResult pushOrderPlatform(PushNewOrderEvent pushNewOrderEvent);

    /**
     * 推送支付宝支付单到海关
     *
     * @param order 订单实体类
     * @param gjbcData 系统参数
     * @return
     */
    EventResult PushOrderAliPay(Order order, GjbcData gjbcData);

    /**
     * 推送微信支付单到海关
     *
     * @param order
     * @param gjbcData
     * @return
     */
    EventResult PushOrderWeiXin(Order order, GjbcData gjbcData);

    /**
     * 推送订单到海关
     *
     * @param order    订单实体类
     * @param gjbcData 系统参数
     * @return
     */
    EventResult PushOrderCustom(Order order, GjbcData gjbcData);


}
