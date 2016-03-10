/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb;


import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
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
 * com.huobanplus.erpprovider.edb 具体事件处理实现类
 * Created by allan on 2015/7/13.
 */
@Component
public class EDBHandlerBuilder implements ERPHandlerBuilder {
    @Autowired
    private EDBOrderHandler edbOrderHandler;

    /**
     * 根据erp信息判断是否由该erp-provider处理
     *
     * @param erpInfo
     * @return 无法处理返回空，可以处理返回该erp事件处理器
     */
    public ERPHandler buildHandler(ERPInfo erpInfo) {
        if (erpInfo.getErpType() == ERPTypeEnum.ProviderType.EDB) {
            return new ERPHandler() {
                @Override
                public boolean eventSupported(Class<? extends ERPBaseEvent> baseEventClass) {
                    if (baseEventClass == PushNewOrderEvent.class) {
                        return true;
                    }
                    //todo 判断事件是否可以处理
//                    if (baseEventClass == CreateOrderEvent.class) {
//                        return true;
//                    } else if (baseEventClass == InventoryEvent.class) {
//                        return true;
//                    } else if (baseEventClass == DeliveryInfoEvent.class) {
//                        return true;
//                    } else if (baseEventClass == OrderStatusInfoEvent.class) {
//                        return true;
//                    } else if (baseEventClass == ObtainOrderListEvent.class) {
//                        return true;
//                    } else if (baseEventClass == OrderDeliverEvent.class) {
//                        return true;
//                    } else if (baseEventClass == OrderStatusUpdateEvent.class) {
//                        return true;
//                    } else if (baseEventClass == OrderUpdateEvent.class) {
//                        return true;
//                    } else if (baseEventClass == AddOutStoreEvent.class) {
//                        return true;
//                    } else if (baseEventClass == ConfirmOutStoreEvent.class) {
//                        return true;
//                    } else if (baseEventClass == OutStoreWriteBackEvent.class) {
//                        return true;
//                    }
                    return false;
                }

                @Override
                public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                    if (erpBaseEvent instanceof PushNewOrderEvent) {
                        PushNewOrderEvent pushNewOrderEvent = (PushNewOrderEvent) erpBaseEvent;
                        EDBSysData sysData = JSON.parseObject(erpBaseEvent.getErpInfo().getSysDataJson(), EDBSysData.class);
                        return edbOrderHandler.pushOrder(pushNewOrderEvent.getOrderInfo(), sysData);
                    }
//                    if (erpBaseEvent instanceof CreateOrderEvent) {
//                        CreateOrderEvent createOrderEvent = (CreateOrderEvent) erpBaseEvent;
//                        return edbOrderHandler.pushOrder(createOrderEvent.getOrderInfo(), erpBaseEvent.getErpInfo());
//                    }
//                    if (erpBaseEvent instanceof OrderUpdateEvent) {
//                        OrderUpdateEvent orderUpdateEvent = (OrderUpdateEvent) erpBaseEvent;
//                        return edbOrderHandler.orderUpdate(orderUpdateEvent.getOrderInfo(), erpBaseEvent.getErpInfo());
//                    }
//                    if (erpBaseEvent instanceof OrderDeliverEvent) {
//                        OrderDeliverEvent orderDeliverEvent = (OrderDeliverEvent) erpBaseEvent;
//                        OrderDeliverInfo orderDeliverInfo = orderDeliverEvent.getOrderDeliverInfo();
//                        return edbOrderHandler.orderDeliver(orderDeliverInfo.getOrderId(), orderDeliverInfo.getDeliverTime(), orderDeliverInfo.getLogiNo(), orderDeliverInfo.getLogiName(), orderDeliverInfo.getWeight(), erpBaseEvent.getErpInfo());
//                    }
//                    if (erpBaseEvent instanceof ObtainOrderDetailEvent) {
//                        ObtainOrderDetailEvent orderDetailEvent = (ObtainOrderDetailEvent) erpBaseEvent;
//                        return edbOrderHandler.getOrderDetail(orderDetailEvent.getOrderId(), erpBaseEvent.getErpInfo());
//                    }
//                    if (erpBaseEvent instanceof CancelOrderEvent) {
//                        CancelOrderEvent cancelOrderEvent = (CancelOrderEvent) erpBaseEvent;
//                        return edbOrderHandler.cancelOrder(cancelOrderEvent.getOrderId(), erpBaseEvent.getErpInfo());
//                    }
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
