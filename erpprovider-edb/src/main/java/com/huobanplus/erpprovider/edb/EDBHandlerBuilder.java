package com.huobanplus.erpprovider.edb;


import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpprovider.edb.handler.EDBProductHandler;
import com.huobanplus.erpprovider.edb.handler.EDBStorageHandler;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.bean.MallOutStoreBean;
import com.huobanplus.erpservice.datacenter.bean.MallProductOutBean;
import com.huobanplus.erpservice.datacenter.searchbean.MallOrderSearchBean;
import com.huobanplus.erpservice.event.erpevent.*;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.event.model.*;
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
    private EDBOrderHandler EDBOrderHandler;
    @Autowired
    private EDBProductHandler EDBProductHandler;
    @Autowired
    private EDBStorageHandler edbStorageHandler;

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
                } else if (baseEventClass == OrderDeliverEvent.class) {
                    return true;
                } else if (baseEventClass == OrderStatusUpdateEvent.class) {
                    return true;
                } else if (baseEventClass == OrderUpdateEvent.class) {
                    return true;
                } else if (baseEventClass == AddOutStoreEvent.class) {
                    return true;
                } else if (baseEventClass == ConfirmOutStoreEvent.class) {
                    return true;
                } else if (baseEventClass == OutStoreWriteBackEvent.class) {
                    return true;
                }
                return false;
            }

            public Monitor<EventResult> handleEvent(ERPBaseEvent erpBaseEvent, Object data) throws IOException, IllegalAccessException {
                if (erpBaseEvent instanceof CreateOrderEvent) {
                    MallOrderBean orderInfo = (MallOrderBean) data;
                    return EDBOrderHandler.createOrder(orderInfo, erpBaseEvent.getErpInfo());
                } else if (erpBaseEvent instanceof InventoryEvent) {
                    return EDBProductHandler.getProInventoryInfo(erpBaseEvent.getErpInfo());
                } else if (erpBaseEvent instanceof ObtainOrderEvent) {
                    return EDBOrderHandler.obtainOrderList((MallOrderSearchBean) data, erpBaseEvent.getErpInfo());
                } else if (erpBaseEvent instanceof OrderDeliverEvent) {
                    return EDBOrderHandler.orderDeliver((MallOrderBean) data, erpBaseEvent.getErpInfo());
                } else if (erpBaseEvent instanceof OrderStatusUpdateEvent) {
                    return EDBOrderHandler.orderStatusUpdate((MallOrderBean) data, erpBaseEvent.getErpInfo());
                } else if (erpBaseEvent instanceof OrderUpdateEvent) {
                    return EDBOrderHandler.orderUpdate((MallOrderBean) data, erpBaseEvent.getErpInfo());
                } else if (erpBaseEvent instanceof AddOutStoreEvent) {
                    return edbStorageHandler.outStorageAdd((MallOutStoreBean) data, erpBaseEvent.getErpInfo());
                } else if (erpBaseEvent instanceof ConfirmOutStoreEvent) {
                    return edbStorageHandler.outStoreConfirm((MallOutStoreBean) data, erpBaseEvent.getErpInfo());
                } else if (erpBaseEvent instanceof OutStoreWriteBackEvent) {
                    return edbStorageHandler.outStoreWriteback((MallProductOutBean) data, erpBaseEvent.getErpInfo());
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
