package com.huobanplus.erpprovider.huobanmall.config;

import com.huobanplus.erpprovider.huobanmall.handler.InventoryHandler;
import com.huobanplus.erpprovider.huobanmall.handler.OrderHandler;
import com.huobanplus.erpservice.event.erpevent.*;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.FailedBean;
import com.huobanplus.erpservice.event.model.Monitor;
import org.springframework.dao.DataAccessException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 伙伴Mall功能具体操作类
 * 该处貌似和Contorller有冲突，该类待定
 */

public class HuobanMallHandlerBuilder implements ERPHandlerBuilder {

    @Resource
    private OrderHandler orderHandler;

    @Resource
    private InventoryHandler inventoryHandler;

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
                else if(baseEventClass == ObtainOrderEvent.class)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }

            @Override
            public Monitor<EventResult> handleEvent(Class<? extends ERPBaseEvent> baseEventClass, Object data) throws IOException, IllegalAccessException, DataAccessException {
                HttpServletRequest request = (HttpServletRequest) data;
                if (baseEventClass == CreateOrderEvent.class)
                {
                    orderHandler.commitOrder(request);
                }
                else if(baseEventClass == ObtainOrderEvent.class)
                {
                    orderHandler.obtainOrder(request);
                }
                else if(baseEventClass == DeliveryInfoEvent.class)
                {
                }
                else if(baseEventClass == InventoryEvent.class)
                {
                    inventoryHandler.commitInventoryInfo(request);
                }
                else if(baseEventClass == OrderStatusInfoEvent.class)
                {
                    return orderHandler.modifyOrder(request);
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

            @Override
            public Monitor<EventResult> handleException(Class<? extends ERPBaseEvent> baseEventClass, FailedBean failedBean) {

                if (baseEventClass == CreateOrderEvent.class) {


                }
                else if(baseEventClass == ObtainOrderEvent.class)
                {

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
