/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpuser.huobanmall.common;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.*;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.FailedBean;
import com.huobanplus.erpservice.eventhandler.model.Monitor;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandlerBuilder;
import com.huobanplus.erpuser.huobanmall.handler.HBOrderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by liual on 2015-10-15.
 */
@Component
public class HuobanmallHandlerBuilder implements ERPUserHandlerBuilder {
    @Autowired
    private HBOrderHandler orderHandler;


    @Override
    public ERPUserHandler buildHandler(ERPUserInfo info) {
        if (info.getErpUserType() == ERPTypeEnum.UserType.HUOBAN_MALL) {
            return erpBaseEvent -> {
                if (erpBaseEvent instanceof ObtainOrderListEvent) {
                    return orderHandler.obtainOrderList(((ObtainOrderListEvent) erpBaseEvent).getOrderSearchInfo(), info);
                } else if (erpBaseEvent instanceof DeliveryInfoEvent) {
                    return orderHandler.deliverInfo(((DeliveryInfoEvent) erpBaseEvent).getDeliveryInfo(), info);
                } else if (erpBaseEvent instanceof ObtainOrderDetailEvent) {
                    return orderHandler.obtainOrderDetail(((ObtainOrderDetailEvent) erpBaseEvent).getOrderId(), info);
                } else if (erpBaseEvent instanceof ReturnInfoEvent) {
                    return orderHandler.returnInfo(((ReturnInfoEvent) erpBaseEvent).getReturnInfo(), info);
                }
                return null;
            };
        }
        return null;
    }
}
