package com.huobanplus.erpprovider.gjbc.config;

import com.huobanplus.erpprovider.gjbc.handler.GJBCOrderHandler;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
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
 * Created by hxh on 2017-06-26.
 */
@Component
public class GJBCHandlerBuilder implements ERPHandlerBuilder {
    @Autowired
    private GJBCOrderHandler gjbcOrderHandler;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if (info.getErpType() == ERPTypeEnum.ProviderType.GJBC) {
            return new ERPHandler() {
                @Override
                public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                    if (erpBaseEvent instanceof PushNewOrderEvent) {
                        PushNewOrderEvent pushNewOrderEvent = (PushNewOrderEvent) erpBaseEvent;
                        return gjbcOrderHandler.pushOrder(pushNewOrderEvent);
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
