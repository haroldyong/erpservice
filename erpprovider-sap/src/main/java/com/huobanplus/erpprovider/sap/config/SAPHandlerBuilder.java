/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.config;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpprovider.sap.handler.SAPOrderHandler;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by allan on 4/11/16.
 */
@Component
public class SAPHandlerBuilder implements ERPHandlerBuilder {


    @Autowired
    private SAPOrderHandler sapOrderHandler;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if (info.getErpType() == ERPTypeEnum.ProviderType.SAP) {
            return new ERPHandler() {
                @Override
                public boolean eventSupported(Class<? extends ERPBaseEvent> baseEventClass) {
                    if (baseEventClass == PushNewOrderEvent.class) {
                        return true;
                    }
                    return false;
                }

                @Override
                public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {

                    if (erpBaseEvent instanceof PushNewOrderEvent) {
                        PushNewOrderEvent pushNewOrderEvent = (PushNewOrderEvent) erpBaseEvent;
                        return sapOrderHandler.pushOrder(pushNewOrderEvent);
                    }
                    return null;
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
