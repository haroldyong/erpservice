package com.huobanplus.erpprovider.edb;


import com.huobanplus.erpprovider.edb.handler.OrderHandler;
import com.huobanplus.erpprovider.edb.handler.ProductHandler;
import com.huobanplus.erpprovider.edb.net.HttpUtil;
import com.huobanplus.erpprovider.edb.support.SimpleMonitor;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpservice.event.erpevent.*;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
            public boolean eventSupported(ERPBaseEvent erpEvent) {
                //todo 判断事件是否可以处理
                if (erpEvent instanceof CreateOrderEvent) {
                    return true;
                } else if (erpEvent instanceof InventoryEvent) {
                    return true;
                } else if (erpEvent instanceof DeliveryInfoEvent) {
                    return true;
                } else if (erpEvent instanceof OrderStatusInfoEvent) {
                    return true;
                } else {
                    return false;
                }
            }

            public Monitor<EventResult> handleEvent(ERPBaseEvent erpEvent) throws IOException, IllegalAccessException, DocumentException {
                if (erpEvent instanceof CreateOrderEvent) {
                    OrderInfo orderInfo = ((CreateOrderEvent) erpEvent).getOrderInfo();
                    return orderHandler.createOrder(orderInfo);
                } else if (erpEvent instanceof InventoryEvent) {
                    return productHandler.getProInventoryInfo();
                } else if (erpEvent instanceof DeliveryInfoEvent) {
                    return orderHandler.getOrderInfo();
                } else if (erpEvent instanceof OrderStatusInfoEvent) {

                } else {

                }
                return null;
            }
        };
    }
}
