/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.handler;

import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.formatgy.order.GYDeliveryOrderUpdate;
import com.huobanplus.erpprovider.gy.formatgy.order.GYRefundOrder;
import com.huobanplus.erpprovider.gy.formatgy.order.GYReturnOrderInStock;
import com.huobanplus.erpprovider.gy.search.GYDeliveryOrderSearch;
import com.huobanplus.erpprovider.gy.search.GYOrderSearch;
import com.huobanplus.erpprovider.gy.search.GYRefundOrderSearch;
import com.huobanplus.erpprovider.gy.search.GYReturnOrderSearch;
import com.huobanplus.erpservice.datacenter.model.OrderRefundStatusInfo;
import com.huobanplus.erpservice.datacenter.model.OrderRemarkUpdateInfo;
import com.huobanplus.erpservice.datacenter.model.ReturnInfo;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by admin on 2016/5/9.
 */
public interface GYOrderHandler {


    /**
     * 推送订单，包含正常订单和退单
     *
     * @param pushNewOrderEvent
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent, GYSysData sysData);


    /**
     * 订单查询
     *
     * @param orderSearch
     * @param gySysData
     * @return
     */
    EventResult orderQuery(GYOrderSearch orderSearch, GYSysData gySysData);

    /**
     * 订单备注修改
     *
     * @return
     */
    EventResult orderMemoUpdate(OrderRemarkUpdateInfo orderRemarkUpdateInfo, GYSysData gySysData);

    /**
     * 订单退款状态修改
     *
     * @return
     */
    EventResult orderRefundStateUpdate(OrderRefundStatusInfo orderRefundStatusInfo, GYSysData gySysData);

    /**
     * 发货单查询
     *
     * @param gyDeliveryOrderSearch
     * @param gySysData
     * @return
     */
    EventResult deliveryOrderQuery(GYDeliveryOrderSearch gyDeliveryOrderSearch, GYSysData gySysData);

    /**
     * 历史发货单查询
     *
     * @param gyDeliveryOrderSearch
     * @param gySysData
     * @return
     */
    EventResult historyDeliveryOrderQuery(GYDeliveryOrderSearch gyDeliveryOrderSearch, GYSysData gySysData);

    /**
     * 发货单信息修改
     *
     * @return
     */
    EventResult deliveryOrderUpdate(GYDeliveryOrderUpdate deliveryOrderUpdate, GYSysData gySysData);

    /**
     * 退换货单查询
     *
     * @param gyReturnOrderSearch
     * @param gySysData
     * @return
     */
    EventResult returnOrderQuery(GYReturnOrderSearch gyReturnOrderSearch, GYSysData gySysData);

    /**
     * 退货单增加
     *
     * @return
     */
    EventResult pushReturnOrder(ReturnInfo returnInfo, GYSysData gySysData);

    /**
     * 退货单入库
     *
     * @return
     */
    EventResult returnOrderInStock(GYReturnOrderInStock gyReturnOrderInStock, GYSysData gySysData);

    /**
     * 历史订单查询
     *
     * @param gyOrderSearch
     * @return
     */
    EventResult historyOrderQuery(GYOrderSearch gyOrderSearch, GYSysData gySysData);


    /**
     * 退款单新增
     *
     * @return
     */
    EventResult pushRefundOrder(GYRefundOrder gyRefundOrder, GYSysData gySysData);

    /**
     * 退款单查询
     *
     * @param gyRefundOrderSearch
     * @param gySysData
     * @return
     */
    EventResult refundOrderQuery(GYRefundOrderSearch gyRefundOrderSearch, GYSysData gySysData);


}
