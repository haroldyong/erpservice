/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.lgj.config;

import com.huobanplus.erpprovider.lgj.handler.LGJOrderHandler;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
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
 * Created by elvsi on 4/28/16.
 */
@Component
public class LGJHandlerBuilder implements ERPHandlerBuilder {


    @Autowired
    private LGJOrderHandler LGJOrderHandler;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        return new ERPHandler() {
            @Override
            public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                if (erpBaseEvent instanceof PushNewOrderEvent) {
                    PushNewOrderEvent pushNewOrderEvent = (PushNewOrderEvent) erpBaseEvent;
                    LGJOrderHandler.pushOrder(pushNewOrderEvent);
                }
                return null;
            }

            @Override
            public EventResult handleRequest(HttpServletRequest request, ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType) {
//                String method = request.getParameter("mType");
//                switch (method){
//                    case LGJConstant.ORDER_MAKESURE_METHOD:
                return EventResult.resultWith(EventResultEnum.SUCCESS, "success");
//                    default:break;
//                }
//                return null;
//            }
            }
        };
    }

}
