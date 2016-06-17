package com.huobanplus.erpprovider.gy.handler;

import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.formatgy.order.*;
import com.huobanplus.erpprovider.gy.search.GYDeliveryOrderSearch;
import com.huobanplus.erpprovider.gy.search.GYOrderSearch;
import com.huobanplus.erpprovider.gy.search.GYRefundOrderSearch;
import com.huobanplus.erpprovider.gy.search.GYReturnOrderSearch;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by admin on 2016/5/9.
 */
public interface GYOrderHandler {


    /**
     * 推送订单，包含正常订单和退单
     * @param pushNewOrderEvent
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);


    /**
     *  订单查询
     * @param orderSearch
     * @param gySysData
     * @return
     */
    EventResult orderQuery(GYOrderSearch orderSearch,GYSysData gySysData);

    /**
     *  订单备注修改
     * @param gyOrderMemo
     * @param gySysData
     * @return
     */
    EventResult orderMemoUpdate(GYOrderMemo gyOrderMemo, GYSysData gySysData);

    /**
     *  订单退款状态修改
     * @param gyRefundOrder
     * @param gySysData
     * @return
     */
    EventResult orderRefundStateUpdate(GYRefundOrder gyRefundOrder,GYSysData gySysData);

    /**
     *  发货单查询
     * @param gyDeliveryOrderSearch
     * @param gySysData
     * @return
     */
    EventResult deliveryOrderQuery(GYDeliveryOrderSearch gyDeliveryOrderSearch,GYSysData gySysData);

    /**
     * 历史发货单查询
     * @param gyDeliveryOrderSearch
     * @param gySysData
     * @return
     */
    EventResult historyDeliveryOrderQuery(GYDeliveryOrderSearch gyDeliveryOrderSearch,GYSysData gySysData);

    /**
     *  发货单信息修改
     * @param deliveryOrderUpdate
     * @param gySysData
     * @return
     */
    EventResult deliveryOrderUpdate(GYDeliveryOrderUpdate deliveryOrderUpdate,GYSysData gySysData);

    /**
     *  退换货单查询
     * @param gyReturnOrderSearch
     * @param gySysData
     * @return
     */
    EventResult returnOrderQuery(GYReturnOrderSearch gyReturnOrderSearch,GYSysData gySysData);

    /**
     *  退货单增加
     * @param gyReturnOrder
     * @param gySysData
     * @return
     */
    EventResult pushReturnOrder(GYReturnOrder gyReturnOrder,GYSysData gySysData);

    /**
     *  退货单入库
     * @param gyReturnOrderInStock
     * @return
     */
    EventResult returnOrderInStock(GYReturnOrderInStock gyReturnOrderInStock,GYSysData gySysData);

    /**
     *  历史订单查询
     * @param gyOrderSearch
     * @return
     */
    EventResult historyOrderQuery(GYOrderSearch gyOrderSearch,GYSysData gySysData);


    /**
     *  退款单新增
     * @param gyRefundOrder
     * @param gySysData
     * @return
     */
    EventResult refundOrderPush(GYRefundOrder gyRefundOrder,GYSysData gySysData);

    /**
     *  退款单查询
     * @param gyRefundOrderSearch
     * @return
     */
    EventResult refundOrderQuery(GYRefundOrderSearch gyRefundOrderSearch,GYSysData gySysData);



}
