package com.huobanplus.erpprovider.kjyg.config;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.kjyg.common.KjygSysData;
import com.huobanplus.erpprovider.kjyg.handler.KjygOrderHandler;
import com.huobanplus.erpprovider.kjyg.util.KjygConstant;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPSysDataInfo;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.ERPSysDataInfoService;
import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Component
public class KjygHandlerBuilder implements ERPHandlerBuilder {

    @Autowired
    private KjygOrderHandler kjygOrderHandler;

    @Autowired
    private ERPSysDataInfoService sysDataInfoService;

    @Autowired
    private ERPDetailConfigService detailConfigService;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        return new ERPHandler() {
            @Override
            public boolean eventSupported(Class<? extends ERPBaseEvent> baseEventClass) {
                if (baseEventClass == PushNewOrderEvent.class) {
                    return true;
                }
                return false;
            }

            @Override
            public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                if (erpBaseEvent instanceof PushNewOrderEvent) {
                    PushNewOrderEvent pushNewOrderEvent = (PushNewOrderEvent) erpBaseEvent;
                    return kjygOrderHandler.pushOrder(pushNewOrderEvent);
                }
                return null;
            }

            @Override
            public EventResult handleRequest(HttpServletRequest request, ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType) {
                String method = request.getParameter("mType");
                String orderNo = request.getParameter("OrderNO");

                //通过uCode得到指定erp配置信息
                List<ERPSysDataInfo> sysDataInfos = sysDataInfoService.findByErpTypeAndErpUserType(providerType, erpUserType);
                ERPDetailConfigEntity erpDetailConfig = detailConfigService.findBySysData(sysDataInfos, providerType, erpUserType);
                KjygSysData kjygSysData = JSON.parseObject(erpDetailConfig.getErpSysData(), KjygSysData.class);
                switch (method){
                    case KjygConstant.QUERY_ORDER_TRADNO:
                        return kjygOrderHandler.queryOrderTradNo(orderNo,kjygSysData);
                    case KjygConstant.QUERY_ORDER_STAT:
                        return kjygOrderHandler.queryOrderStat(orderNo,kjygSysData);
                }
                return null;
            }
        };
    }
}
