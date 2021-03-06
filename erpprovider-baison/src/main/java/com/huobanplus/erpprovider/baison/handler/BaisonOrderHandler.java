/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpprovider.baison.handler;

import com.huobanplus.erpprovider.baison.common.BaisonSysData;
import com.huobanplus.erpprovider.baison.search.BaisonOrderSearch;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

public interface BaisonOrderHandler {

    /**
     * 订单推送
     *
     * @param pushNewOrderEvent
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);

    /**
     * 订单查询
     *
     * @param baisonOrderSearch
     * @param baisonSysData
     * @return
     */
    EventResult orderQuery(BaisonOrderSearch baisonOrderSearch, BaisonSysData baisonSysData);
}