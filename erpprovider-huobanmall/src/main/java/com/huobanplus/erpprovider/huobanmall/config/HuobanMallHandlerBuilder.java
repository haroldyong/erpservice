package com.huobanplus.erpprovider.huobanmall.config;

import com.huobanplus.erpprovider.huobanmall.handler.MallInventoryHandler;
import com.huobanplus.erpprovider.huobanmall.handler.MallOrderHandler;
import com.huobanplus.erpservice.eventhandler.erpevent.*;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.FailedBean;
import com.huobanplus.erpservice.eventhandler.model.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 伙伴Mall功能具体操作类
 * 该处貌似和Contorller有冲突，该类待定
 */
@Component
public class HuobanMallHandlerBuilder implements ERPHandlerBuilder {

    @Autowired
    private MallOrderHandler mallOrderHandler;

    @Autowired
    private MallInventoryHandler mallInventoryHandler;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if (!"huobanMall".equals(info.getName())) {
            //不是伙伴商城
            return null;
        }
        return new ERPHandler() {


            @Override
            public boolean eventSupported(Class<? extends ERPBaseEvent> baseEventClass) {
                if (baseEventClass == CreateOrderEvent.class) {
                    return true;
                } else if (baseEventClass == DeliveryInfoEvent.class) {
                    return true;
                } else if (baseEventClass == InventoryEvent.class) {
                    return true;
                } else if (baseEventClass == OrderStatusInfoEvent.class) {
                    return true;
                } else if (baseEventClass == ProductInfoEvent.class) {
                    return true;
                } else if (baseEventClass == ObtainOrderListEvent.class) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public Monitor<EventResult> handleEvent(ERPBaseEvent erpBaseEvent, Object data) throws IOException, IllegalAccessException {
                HttpServletRequest request = (HttpServletRequest) data;
                if (erpBaseEvent instanceof CreateOrderEvent) {
                    mallOrderHandler.commitOrder(request);
                } else if (erpBaseEvent instanceof ObtainOrderListEvent) {
                    mallOrderHandler.obtainOrder(request);
                } else if (erpBaseEvent instanceof DeliveryInfoEvent) {
                } else if (erpBaseEvent instanceof InventoryEvent) {
                    mallInventoryHandler.commitInventoryInfo(request);
                } else if (erpBaseEvent instanceof OrderStatusInfoEvent) {
                    return mallOrderHandler.modifyOrder(request);
                } else if (erpBaseEvent instanceof ProductInfoEvent) {
                } else {
                    return null;
                }
                return null;
            }

            @Override
            public Monitor<EventResult> handleException(Class<? extends ERPBaseEvent> baseEventClass, FailedBean failedBean) {

                if (baseEventClass == CreateOrderEvent.class) {


                } else if (baseEventClass == ObtainOrderListEvent.class) {

                } else if (baseEventClass == DeliveryInfoEvent.class) {

                } else if (baseEventClass == InventoryEvent.class) {

                } else if (baseEventClass == OrderStatusInfoEvent.class) {

                } else if (baseEventClass == ProductInfoEvent.class) {

                } else {
                    return null;
                }
                return null;
            }
        };
    }
}
