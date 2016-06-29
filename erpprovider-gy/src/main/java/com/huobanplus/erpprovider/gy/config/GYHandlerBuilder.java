/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.config;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.handler.GYOrderHandler;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.OrderRefundStatusUpdate;
import com.huobanplus.erpservice.eventhandler.erpevent.push.OrderRemarkUpdate;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by elvsi on 5/6/16.
 */
@Component
public class GYHandlerBuilder implements ERPHandlerBuilder {


    @Autowired
    private GYOrderHandler gyOrderHandler;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if (info.getErpType() == ERPTypeEnum.ProviderType.GY) {
            return new ERPHandler() {
                @Override
                public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                    GYSysData gySysData = JSON.parseObject(erpBaseEvent.getErpInfo().getSysDataJson(), GYSysData.class);

                    if (erpBaseEvent instanceof PushNewOrderEvent) {
                        PushNewOrderEvent pushNewOrderEvent = (PushNewOrderEvent) erpBaseEvent;
                        return gyOrderHandler.pushOrder(pushNewOrderEvent, gySysData);
                    }
                    if (erpBaseEvent instanceof OrderRemarkUpdate) {
                        OrderRemarkUpdate orderRemarkUpdate = (OrderRemarkUpdate) erpBaseEvent;
                        return gyOrderHandler.orderMemoUpdate(orderRemarkUpdate.getOrderRemarkUpdateInfo(), gySysData);
                    }
                    if (erpBaseEvent instanceof OrderRefundStatusUpdate) {
                        OrderRefundStatusUpdate orderRefundStatusUpdate = (OrderRefundStatusUpdate) erpBaseEvent;
                        return gyOrderHandler.orderRefundStateUpdate(orderRefundStatusUpdate.getOrderRefundStatusInfo(), gySysData);
                    }
                    return EventResult.resultWith(EventResultEnum.UNSUPPORT_EVENT);
                }

                @Override
                public EventResult handleRequest(HttpServletRequest request, ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType) {
                    return null;
                }
            };
        }
        return null;
    }
}
