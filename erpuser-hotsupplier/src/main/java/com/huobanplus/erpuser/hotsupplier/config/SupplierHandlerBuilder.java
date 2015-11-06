/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpuser.hotsupplier.config;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandlerBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by liual on 2015-11-05.
 */
@Component
public class SupplierHandlerBuilder implements ERPUserHandlerBuilder {

    @Override
    public ERPUserHandler buildHandler(ERPUserInfo info) {
        if (info.getErpUserType() == ERPTypeEnum.UserType.HUOBAN_SUPPLIER) {
            return new ERPUserHandler() {
                @Override
                public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                    return null;
                }
            };
        }
        return null;
    }
}
