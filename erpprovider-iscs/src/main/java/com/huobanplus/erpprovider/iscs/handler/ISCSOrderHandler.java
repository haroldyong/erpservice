/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.iscs.handler;

import com.huobanplus.erpprovider.iscs.common.ISCSSysData;
import com.huobanplus.erpprovider.iscs.search.ISCSOrderSearch;
import com.huobanplus.erpservice.eventhandler.erpevent.push.CancelReturnOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushReturnInfoEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * 网仓订单相关处理
 * <p>
 * Created by allan on 4/19/16.
 */
public interface ISCSOrderHandler {
    /**
     * 推送订单（新增,修改）
     *
     * @param pushNewOrderEvent 推送新订单事件,里面包含了订单信息,相关提供者和使用者信息,事件信息
     * @return 处理结果
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);

    EventResult getOrderDeliveryInfo(ISCSSysData sysData, ISCSOrderSearch orderSearch);

    /**
     *  取消订单
     * @param cancelOrderEvent 取消订单事件，包含订单id，相关提供者和使用者信息，事件信息
     * @return
     */
    //EventResult cancelOrder(CancelOrderEvent cancelOrderEvent);

    /**
     *  订单状态查询
     * @param orderStatusInfoEvent 订单状态信息事件，包含订单信息实体，相关提供者和使用者信息，事件信息
     * @return
     */
    //EventResult orderStatusQuery(OrderStatusInfoEvent orderStatusInfoEvent);

    /**
     * 销售退货单导入
     * @param pushReturnInfoEvent 推送退货单事件，包含退货单信息实体，相关提供者和使用者信息，事件信息
     * @return
     */
    EventResult pushReturnOrder(PushReturnInfoEvent pushReturnInfoEvent);

    //EventResult orderQuery(OrderSearchInfo orderSearchInfo, ERPUserInfo erpUserInfo, ERPInfo erpInfo);

    /**
     * 销售退货单导入取消
     * @param cancelReturnOrderEvent 取消退货单事件，包含退货单号，相关提供者和使用者信息，事件信息
     * @return
     */
    EventResult cancelReturnOrder(CancelReturnOrderEvent cancelReturnOrderEvent);

}
