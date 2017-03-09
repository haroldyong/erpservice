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

package com.huobanplus.erpprovider.wangdianv2.handler;

import com.huobanplus.erpprovider.wangdianv2.common.WangDianV2SysData;
import com.huobanplus.erpprovider.wangdianv2.search.WangDianV2OrderSearch;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by wuxiongliu on 2017-03-02.
 */
public interface WangDianV2OrderHandler {

    /**
     * 订单推送
     *
     * @param pushNewOrderEvent
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);

    EventResult queryOrder(WangDianV2OrderSearch wangDianV2OrderSearch, WangDianV2SysData wangDianV2SysData);
}
