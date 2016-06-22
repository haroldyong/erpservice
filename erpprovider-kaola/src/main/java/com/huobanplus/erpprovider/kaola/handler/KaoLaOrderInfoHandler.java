/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.kaola.handler;

import com.huobanplus.erpprovider.kaola.common.KaoLaSysData;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

import java.io.UnsupportedEncodingException;
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

    EventResult queryGoodsId(String skuId, KaoLaSysData kaoLaSysData) throws UnsupportedEncodingException;
}
