/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpprovider.baison.config;

import com.huobanplus.erpprovider.baison.handler.BaisonOrderHandler;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.ERPSysDataInfoService;
import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class BaisonHandlerBuilder implements ERPHandlerBuilder {

    private static final Log log = LogFactory.getLog(BaisonHandlerBuilder.class);

    @Autowired
    private BaisonOrderHandler baisonOrderHandler;
    @Autowired
    private ERPSysDataInfoService sysDataInfoService;
    @Autowired
    private ERPDetailConfigService detailConfigService;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if (info.getErpType() == ERPTypeEnum.ProviderType.DTW) {
            return new ERPHandler() {
                @Override
                public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                    if (erpBaseEvent instanceof PushNewOrderEvent) {
                        PushNewOrderEvent pushNewOrderEvent = (PushNewOrderEvent) erpBaseEvent;
                        return baisonOrderHandler.pushOrder(pushNewOrderEvent);
                    }
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