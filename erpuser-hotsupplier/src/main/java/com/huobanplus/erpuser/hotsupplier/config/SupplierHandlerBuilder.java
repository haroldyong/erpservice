/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpuser.hotsupplier.config;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetOrderDetailEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetOrderDetailListEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetProductInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.*;
import com.huobanplus.erpservice.eventhandler.erpevent.sync.SyncChannelOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.sync.SyncInventoryEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandlerBuilder;
import com.huobanplus.erpuser.hotsupplier.handler.SupGoodHandler;
import com.huobanplus.erpuser.hotsupplier.handler.SupOrderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by liual on 2015-11-05.
 */
@Component
public class SupplierHandlerBuilder implements ERPUserHandlerBuilder {
    @Autowired
    private SupOrderHandler orderHandler;
    @Autowired
    private SupGoodHandler goodHandler;

    @Override
    @SuppressWarnings("Duplicates")
    public ERPUserHandler buildHandler(ERPUserInfo info) {
        if (info.getErpUserType() == ERPTypeEnum.UserType.HUOBAN_SUPPLIER) {
            return erpBaseEvent -> {
                if (erpBaseEvent instanceof GetOrderDetailListEvent) {
                    return orderHandler.obtainOrderList(((GetOrderDetailListEvent) erpBaseEvent).getOrderSearchInfo(), info);
                } else if (erpBaseEvent instanceof PushDeliveryInfoEvent) {
                    return orderHandler.deliverInfo(((PushDeliveryInfoEvent) erpBaseEvent).getDeliveryInfo(), info);
                } else if (erpBaseEvent instanceof GetOrderDetailEvent) {
                    return orderHandler.obtainOrderDetail(((GetOrderDetailEvent) erpBaseEvent).getOrderId(), info);
                } else if (erpBaseEvent instanceof PushReturnInfoEvent) {
                    return orderHandler.returnInfo(((PushReturnInfoEvent) erpBaseEvent).getReturnInfo(), info);
                } else if (erpBaseEvent instanceof PushOrderListInfoEvent) {
                    PushOrderListInfoEvent pushOrderListInfoEvent = (PushOrderListInfoEvent) erpBaseEvent;
                    return orderHandler.pushOrderDetailList(pushOrderListInfoEvent.getOrderListJson());
                } else if (erpBaseEvent instanceof BatchDeliverEvent) {
                    BatchDeliverEvent batchDeliverEvent = (BatchDeliverEvent) erpBaseEvent;
                    return orderHandler.batchDeliver(batchDeliverEvent.getOrderDeliveryInfoList(), batchDeliverEvent.getErpUserInfo());
                } else if (erpBaseEvent instanceof SyncInventoryEvent) {
                    SyncInventoryEvent syncInventoryEvent = (SyncInventoryEvent) erpBaseEvent;

                    return goodHandler.syncProInventory(syncInventoryEvent.getInventoryInfoList(), syncInventoryEvent.getErpUserInfo());
                } else if (erpBaseEvent instanceof SyncChannelOrderEvent) {
                    SyncChannelOrderEvent syncChannelOrderEvent = (SyncChannelOrderEvent) erpBaseEvent;
                    return orderHandler.syncChannelOrderList(syncChannelOrderEvent.getOrderList(), syncChannelOrderEvent.getErpUserInfo());

                } else if (erpBaseEvent instanceof GetProductInfoEvent) {
                    GetProductInfoEvent getProductInfoEvent = (GetProductInfoEvent) erpBaseEvent;
                    return goodHandler.obtainProductListInfo(getProductInfoEvent.getProductSearchInfo(), getProductInfoEvent.getErpUserInfo());

                } else if (erpBaseEvent instanceof PushAuditedOrderEvent) {
                    PushAuditedOrderEvent pushAuditedOrderEvent = (PushAuditedOrderEvent) erpBaseEvent;
                    return EventResult.resultWith(EventResultEnum.SUCCESS); // TODO: 2017-03-08
                }
                return null;
            };
        }
        return null;
    }
}
