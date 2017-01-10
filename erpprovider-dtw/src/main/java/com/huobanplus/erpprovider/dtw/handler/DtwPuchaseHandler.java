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

package com.huobanplus.erpprovider.dtw.handler;

import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwPurchaseOrder;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushPurchaseOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by wuxiongliu on 2016-12-12.
 */
public interface DtwPuchaseHandler {

    /**
     * 采购单导入
     *
     * @param dtwPurchaseOrder
     * @param dtwSysData
     * @return
     */
    EventResult purchaseOrderPush(DtwPurchaseOrder dtwPurchaseOrder, DtwSysData dtwSysData);


    /**
     * 采购单推送至大田物流
     *
     * @param pushPurchaseOrderEvent
     * @return
     */
    EventResult pushPurchaseOrder(PushPurchaseOrderEvent pushPurchaseOrderEvent);
}
