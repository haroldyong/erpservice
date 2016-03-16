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
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetOrderDetailEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetOrderDetailListEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushDeliveryInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushOrderListInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushReturnInfoEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandlerBuilder;
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

    @Override
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
                    return orderHandler.pushOrderDetailList(pushOrderListInfoEvent.getOrderListJson(), info);
                }
                return null;
            };
        }
        return null;
    }
}
