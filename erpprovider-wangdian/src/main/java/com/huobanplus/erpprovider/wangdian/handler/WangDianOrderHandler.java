/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.wangdian.handler;

import com.huobanplus.erpprovider.wangdian.formatdata.WangDianLogistic;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

import java.util.List;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
public interface WangDianOrderHandler {

    /**
     * 推送订单
     *
     * @param pushNewOrderEvent
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);


    /**
     * 推送发货单至平台
     *
     * @param wangDianLogistics
     * @param erpUserInfo
     * @param erpInfo
     * @return
     */
    EventResult deliverOrder(List<WangDianLogistic> wangDianLogistics, ERPUserInfo erpUserInfo, ERPInfo erpInfo);

}
