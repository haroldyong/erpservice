package com.huobanplus.erpprovider.kaola.config;

import com.huobanplus.erpprovider.kaola.handler.KaoLaOrderInfoHandler;
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
 * Created by wuxiongliu on 2016/5/9.
 */
@Component
public class KaoLaHandlerBuilder implements ERPHandlerBuilder {

    @Autowired
    private KaoLaOrderInfoHandler kaoLaOrderInfoHandler;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
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
                    return kaoLaOrderInfoHandler.pushOrder(pushNewOrderEvent);
                }

                return null;
            }

            @Override
            public EventResult handleRequest(HttpServletRequest request, ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType) {
                return null;
            }
        };
    }
}
