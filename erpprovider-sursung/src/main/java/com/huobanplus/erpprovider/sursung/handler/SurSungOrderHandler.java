/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.handler;

import com.huobanplus.erpprovider.sursung.common.SurSungSysData;
import com.huobanplus.erpprovider.sursung.formatdata.SurSungInventory;
import com.huobanplus.erpprovider.sursung.formatdata.SurSungLogistic;
import com.huobanplus.erpprovider.sursung.search.SurSungLogisticSearch;
import com.huobanplus.erpprovider.sursung.search.SurSungOrderSearch;
import com.huobanplus.erpservice.eventhandler.erpevent.push.CancelOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushReturnInfoEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

import java.util.List;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
public interface SurSungOrderHandler {

    /**
     * 推送订单
     *
     * @param pushNewOrderEvent 推送订单事件
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);

    /**
     * 发货查询
     *
     * @param surSungDeliverySearch 发货查询model
     * @return
     */
    EventResult logisticSearch(SurSungLogisticSearch surSungDeliverySearch, SurSungSysData surSungSysData);

    /**
     * 发货单由erp推送至平台
     *
     * @param surSungLogistic 物流信息bean
     * @param erpUserInfo erp使用者信息
     * @param erpInfo erp提供者信息
     * @return
     */
    EventResult logisticUpload(SurSungLogistic surSungLogistic, ERPUserInfo erpUserInfo, ERPInfo erpInfo);


    /**
     * 库存由erp推送至平台
     *
     * @param surSungInventoryList 库存list
     * @param erpUserInfo erp使用者信息
     * @param erpInfo erp提供者信息
     * @return
     */
    EventResult inventoryUpload(List<SurSungInventory> surSungInventoryList, ERPUserInfo erpUserInfo, ERPInfo erpInfo);

    /**
     * 退货退款接口
     *
     * @param pushReturnInfoEvent
     * @return
     */
    EventResult returnRefundUpload(PushReturnInfoEvent pushReturnInfoEvent);

    /**
     * 渠道订单查询
     *
     * @param surSungOrderSearch 渠道订单查询bean
     * @param surSungSysData 系统参数
     * @return
     */
    EventResult queryChannelOrder(SurSungOrderSearch surSungOrderSearch, SurSungSysData surSungSysData);

    /**
     * @param cancelOrderEvent
     * @return
     */
    EventResult cancelOrder(CancelOrderEvent cancelOrderEvent);

}
