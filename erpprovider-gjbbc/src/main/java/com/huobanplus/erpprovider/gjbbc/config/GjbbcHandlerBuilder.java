package com.huobanplus.erpprovider.gjbbc.config;

import com.huobanplus.erpprovider.gjbbc.handler.GjbbcOrderHandler;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
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
 * Created by hxh on 2017-08-15.
 */
@Component
public class GjbbcHandlerBuilder implements ERPHandlerBuilder {

    @Autowired
    private GjbbcOrderHandler gjbbcOrderHandler;

    @Override
    public ERPHandler buildHandler(ERPInfo erpInfo) {
        if (erpInfo.getErpType() == ERPTypeEnum.ProviderType.GJBBC) {
            return new ERPHandler() {
                @Override
                public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                    if (erpBaseEvent instanceof PushNewOrderEvent) {
                        PushNewOrderEvent pushNewOrderEvent = (PushNewOrderEvent) erpBaseEvent;
                        return gjbbcOrderHandler.pushOrder(pushNewOrderEvent);
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
