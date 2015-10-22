/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb;


import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpprovider.edb.handler.EDBProductHandler;
import com.huobanplus.erpprovider.edb.handler.EDBStorageHandler;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.bean.MallOutStoreBean;
import com.huobanplus.erpservice.datacenter.bean.MallProductOutBean;
import com.huobanplus.erpservice.eventhandler.erpevent.*;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.eventhandler.model.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EDBOrderHandler EDBOrderHandler;

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
                } else if (baseEventClass == ObtainOrderListEvent.class) {
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

            public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                if (erpBaseEvent instanceof CreateOrderEvent) {
                    CreateOrderEvent createOrderEvent = (CreateOrderEvent) erpBaseEvent;
                    return EDBOrderHandler.createOrder(createOrderEvent.getOrderInfo(), erpBaseEvent.getErpInfo());
                }
//                else if (erpBaseEvent instanceof InventoryEvent) {
//                    return EDBProductHandler.getProInventoryInfo(erpBaseEvent.getErpInfo());
//                } else if (erpBaseEvent instanceof ObtainOrderListEvent) {
//                    //EDBOrderHandler.obtainOrderList((MallOrderSearchBean) data, erpBaseEvent.getErpInfo());
//                } else if (erpBaseEvent instanceof OrderDeliverEvent) {
//                    return EDBOrderHandler.orderDeliver((MallOrderBean) data, erpBaseEvent.getErpInfo());
//                } else if (erpBaseEvent instanceof OrderStatusUpdateEvent) {
//                    return EDBOrderHandler.orderStatusUpdate((MallOrderBean) data, erpBaseEvent.getErpInfo());
//                } else if (erpBaseEvent instanceof OrderUpdateEvent) {
//                    return EDBOrderHandler.orderUpdate((MallOrderBean) data, erpBaseEvent.getErpInfo());
//                } else if (erpBaseEvent instanceof AddOutStoreEvent) {
//                    return edbStorageHandler.outStorageAdd((MallOutStoreBean) data, erpBaseEvent.getErpInfo());
//                } else if (erpBaseEvent instanceof ConfirmOutStoreEvent) {
//                    return edbStorageHandler.outStoreConfirm((MallOutStoreBean) data, erpBaseEvent.getErpInfo());
//                } else if (erpBaseEvent instanceof OutStoreWriteBackEvent) {
//                    return edbStorageHandler.outStoreWriteback((MallProductOutBean) data, erpBaseEvent.getErpInfo());
//                }
                return null;
            }

            @Override
            public EventResult handleRequest(HttpServletRequest request) {
                return null;
            }
        };
    }
}
