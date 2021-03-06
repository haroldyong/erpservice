/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.iscs.config;

import com.huobanplus.erpprovider.iscs.handler.ISCSOrderHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by allan on 4/19/16.
 */
@Component
public class ISCSHandlerBuilder implements ERPHandlerBuilder {

    @Autowired
    private ISCSOrderHandler iscsOrderHandler;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
//        if(info.getErpType() == ERPTypeEnum.ProviderType.ISCS){
//            return new ERPHandler() {
//                @Override
//                public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
//                    if (erpBaseEvent instanceof PushNewOrderEvent) {
//                        PushNewOrderEvent pushNewOrderEvent = (PushNewOrderEvent) erpBaseEvent;
//                        return iscsOrderHandler.pushOrder(pushNewOrderEvent);
//                    }
//                    if (erpBaseEvent instanceof PushReturnInfoEvent){
//                        PushReturnInfoEvent pushReturnInfoEvent = (PushReturnInfoEvent) erpBaseEvent;
//                        return iscsOrderHandler.pushReturnOrder(pushReturnInfoEvent);
//                    }
//                    return EventResult.resultWith(EventResultEnum.UNSUPPORT_EVENT);
//                }
//
//                @Override
//                public EventResult handleRequest(HttpServletRequest request, ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType) {
//                    return null;
//                }
//            };
//        }
        return null;
    }
}
