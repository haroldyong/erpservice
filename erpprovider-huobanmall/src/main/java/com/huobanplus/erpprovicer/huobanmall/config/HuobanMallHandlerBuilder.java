package com.huobanplus.erpprovicer.huobanmall.config;

import com.huobanplus.erpservice.event.erpevent.*;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.dom4j.DocumentException;
import org.springframework.dao.DataAccessException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 伙伴Mall功能具体操作类
 * 该处貌似和Contorller有冲突，该类待定
 */

public class HuobanMallHandlerBuilder implements ERPHandlerBuilder {

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if(!"huobanMall".equals(info.getName()))
        {
            //不是伙伴商城
            return null;
        }
        return new ERPHandler() {


            @Override
            public boolean eventSupported(Class<? extends ERPBaseEvent> baseEventClass) {
                if (baseEventClass == CreateOrderEvent.class) {
                    return true;
                }
                else if(baseEventClass == DeliveryInfoEvent.class)
                {
                    return true;
                }
                else if(baseEventClass == InventoryEvent.class)
                {
                    return true;
                }
                else if(baseEventClass == OrderStatusInfoEvent.class)
                {
                    return true;
                }
                else if(baseEventClass == ProductInfoEvent.class)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }

            @Override
            public Monitor<EventResult> handleEvent(Class<? extends ERPBaseEvent> baseEventClass, Object data) throws IOException, IllegalAccessException, DataAccessException, DocumentException {
                HttpServletRequest request = (HttpServletRequest) data;
                if (baseEventClass == CreateOrderEvent.class) {
                    request.getAttribute("uCode");
                    request.getAttribute("sign");

                }
                else if(baseEventClass == DeliveryInfoEvent.class)
                {

                }
                else if(baseEventClass == InventoryEvent.class)
                {

                }
                else if(baseEventClass == OrderStatusInfoEvent.class)
                {

                }
                else if(baseEventClass == ProductInfoEvent.class)
                {

                }
                else
                {
                    return null;
                }
                return null;
            }
        };
    }
}
