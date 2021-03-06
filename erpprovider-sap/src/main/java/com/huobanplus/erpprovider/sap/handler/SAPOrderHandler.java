/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.handler;

import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * 订单相关
 * Created by liuzheng on 2015/7/24.
 */
public interface SAPOrderHandler {


    /**
     * 推送
     *
     * @param pushNewOrderEvent 订单信息实体
     * @return EventResult
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);


}
