package com.huobanplus.erpprovider.lz.config;

import com.huobanplus.erpprovider.lz.handler.LzOrderHandler;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.*;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpuser.huobanmall.handler.HBOrderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LzHandlerBuilder implements ERPHandlerBuilder {
    @Autowired
    private LzOrderHandler lzOrderHandler;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if (info.getErpType() == ERPTypeEnum.ProviderType.LZ) {
            return new ERPHandler() {
                @Override
                public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                    if (erpBaseEvent instanceof PushNewOrderEvent) {
                        PushNewOrderEvent pushNewOrderEvent = (PushNewOrderEvent) erpBaseEvent;
                        return lzOrderHandler.pushOrder(pushNewOrderEvent);
                    }

                    if (erpBaseEvent instanceof CancelOrderEvent) {
                        CancelOrderEvent cancelOrderEvent = (CancelOrderEvent) erpBaseEvent;
                        return lzOrderHandler.cancelOrder(cancelOrderEvent);
                    }

                    if (erpBaseEvent instanceof GetTrackingInfoEvent) {
                        GetTrackingInfoEvent getTrackingInfoEvent = (GetTrackingInfoEvent) erpBaseEvent;
                        return lzOrderHandler.tracking(getTrackingInfoEvent);
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
