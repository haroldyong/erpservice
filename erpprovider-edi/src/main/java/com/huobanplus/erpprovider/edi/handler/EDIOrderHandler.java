/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edi.handler;

import com.huobanplus.erpprovider.edi.common.EDISysData;
import com.huobanplus.erpprovider.edi.search.EDILogiSearch;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

public interface EDIOrderHandler {

    /**
     * 订单推送
     *
     * @param pushNewOrderEvent
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);

    /**
     * 订单物流查询
     *
     * @param ediLogiSearch
     * @param ediSysData
     * @return
     */
    EventResult logisticSearch(EDILogiSearch ediLogiSearch, EDISysData ediSysData);
}