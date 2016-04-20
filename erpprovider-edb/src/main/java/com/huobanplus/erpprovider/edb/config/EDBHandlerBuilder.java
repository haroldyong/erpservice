/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.config;


import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushDeliveryInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * com.huobanplus.erpprovider.edb 具体事件处理实现类
 * Created by allan on 2015/7/13.
 */
@Component
public class EDBHandlerBuilder implements ERPHandlerBuilder {
    @Autowired
    private EDBOrderHandler edbOrderHandler;

    /**
     * 根据erp信息判断是否由该erp-provider处理
     *
     * @param erpInfo
     * @return 无法处理返回空，可以处理返回该erp事件处理器
     */
    @SuppressWarnings("Duplcates")
    public ERPHandler buildHandler(ERPInfo erpInfo) {
        if (erpInfo.getErpType() == ERPTypeEnum.ProviderType.EDB) {
            return new ERPHandler() {
                @Override
                public boolean eventSupported(Class<? extends ERPBaseEvent> baseEventClass) {
                    if (baseEventClass == PushNewOrderEvent.class) {
                        return true;
                    }
                    if (baseEventClass == PushDeliveryInfoEvent.class) {
                        return true;
                    }
                    //todo 判断事件是否可以处理
                    return false;
                }

                @Override
                public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                    if (erpBaseEvent instanceof PushNewOrderEvent) {
                        PushNewOrderEvent pushNewOrderEvent = (PushNewOrderEvent) erpBaseEvent;

                        return edbOrderHandler.pushOrder(pushNewOrderEvent);
                    }
                    if (erpBaseEvent instanceof PushDeliveryInfoEvent) {
                        PushDeliveryInfoEvent pushDeliveryInfoEvent = (PushDeliveryInfoEvent) erpBaseEvent;
                        return edbOrderHandler.orderDeliver(pushDeliveryInfoEvent);
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
