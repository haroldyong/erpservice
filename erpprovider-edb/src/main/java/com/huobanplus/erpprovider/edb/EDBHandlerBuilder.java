package com.huobanplus.erpprovider.edb;


import com.huobanplus.erpprovider.edb.handler.OrderHandler;
import com.huobanplus.erpprovider.edb.handler.ProductHandler;
import com.huobanplus.erpprovider.edb.net.HttpUtil;
import com.huobanplus.erpprovider.edb.support.SimpleMonitor;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpservice.event.erpevent.*;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.event.model.*;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * com.huobanplus.erpprovider.edb 具体事件处理实现类
 * Created by allan on 2015/7/13.
 */
@Component
public class EDBHandlerBuilder implements ERPHandlerBuilder {
    @Autowired
    private OrderHandler orderHandler;
    @Autowired
    private ProductHandler productHandler;

    /**
     * 根据erp信息判断是否由该erp-provider处理
     *
     * @param erpInfo
     * @return 无法处理返回空，可以处理返回该erp事件处理器
     */
    public ERPHandler buildHandler(ERPInfo erpInfo) {
        if (!erpInfo.getName().equals("edb")) {
            return null;
        }
        return new ERPHandler() {
            public boolean eventSupported(Class<? extends ERPBaseEvent> baseEventClass) {
                //todo 判断事件是否可以处理
                if (baseEventClass == CreateOrderEvent.class) {
                    return true;
                } else if (baseEventClass == InventoryEvent.class) {
                    return true;
                } else if (baseEventClass == DeliveryInfoEvent.class) {
                    return true;
                } else if (baseEventClass == OrderStatusInfoEvent.class) {
                    return true;
                } else if (baseEventClass == ObtainOrderEvent.class) {

                    return true;
                } else {
                    return false;
                }
            }

            public Monitor<EventResult> handleEvent(Class<? extends ERPBaseEvent> baseEventClass, Object data) throws IOException, IllegalAccessException {
                if (baseEventClass == CreateOrderEvent.class) {
                    OrderInfo orderInfo = (OrderInfo) data;
                    return orderHandler.createOrder(orderInfo);
                } else if (baseEventClass == InventoryEvent.class) {
                    return productHandler.getProInventoryInfo();
                } else if (baseEventClass == DeliveryInfoEvent.class) {
                    return orderHandler.getOrderInfo();
                } else if (baseEventClass == OrderStatusInfoEvent.class) {
                    return orderHandler.orderStatusUpdate((OrderInfo) data);
                } else if (baseEventClass == ObtainOrderEvent.class) {
                    return orderHandler.obtainOrderList();
                } else {

                }
                return null;
            }

            @Override
            public Monitor<EventResult> handleException(Class<? extends ERPBaseEvent> baseEventClass, FailedBean failedBean) {
                if (baseEventClass == CreateOrderEvent.class) {

                } else if (baseEventClass == InventoryEvent.class) {

                } else if (baseEventClass == DeliveryInfoEvent.class) {

                } else if (baseEventClass == OrderStatusInfoEvent.class) {

                } else if (baseEventClass == ObtainOrderEvent.class) {

                } else {

                }
                return null;
            }
        };
    }
}
