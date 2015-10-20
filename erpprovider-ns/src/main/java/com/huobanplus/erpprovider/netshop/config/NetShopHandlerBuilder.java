/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.config;

import com.huobanplus.erpprovider.netshop.handler.NSOrderHandler;
import com.huobanplus.erpprovider.netshop.handler.NSProductHandler;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.*;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.eventhandler.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <b>类描述：具体处理事件<b/>
 */
@Component
public class NetShopHandlerBuilder implements ERPHandlerBuilder {
    @Autowired
    private NSOrderHandler nsOrderHandler;
    @Autowired
    private NSProductHandler productHandler;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if (!"netShop".equals(info.getName())) {
            //不是网店管家
            return null;
        }
        return new ERPHandler() {

            @Override
            public boolean eventSupported(Class<? extends ERPBaseEvent> baseEventClass) {

                if (baseEventClass == DeliveryInfoEvent.class) {
                    return true;
                } else if (baseEventClass == ObtainOrderListEvent.class) {
                    return true;
                } else if (baseEventClass == ObtainOrderDetailEvent.class) {
                    return true;
                } else if (baseEventClass == ObtainGoodListEvent.class) {
                    return true;
                } else if (baseEventClass == InventoryEvent.class) {
                    return true;
                }
                return false;
            }

            @Override
            public EventResult handleEvent(ERPBaseEvent erpBaseEvent, Object data) throws IOException, IllegalAccessException, IllegalArgumentException {
                HttpServletRequest request = (HttpServletRequest) data;

                if (erpBaseEvent instanceof DeliveryInfoEvent) {
                    return nsOrderHandler.deliverOrder(request);
                } else if (erpBaseEvent instanceof ObtainOrderDetailEvent) {
                    return nsOrderHandler.obtainOrderInfo(request);
                } else if (erpBaseEvent instanceof ObtainOrderListEvent) {
                    return nsOrderHandler.obtainOrderInfoList(request);
                } else if (erpBaseEvent instanceof ObtainGoodListEvent) {
                    return productHandler.obtainGoods(request);
                } else if (erpBaseEvent instanceof InventoryEvent) {
                    return productHandler.syncInventory(request);
                }
                return null;
            }

            @Override
            public EventResult handleException(Class<? extends ERPBaseEvent> baseEventClass, FailedBean failedBean) {
//
                if (baseEventClass == DeliveryInfoEvent.class) {
                    return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>" + failedBean.getFailedMsg() + "</Cause></Rsp>");
                } else if (baseEventClass == ObtainOrderListEvent.class) {
                    return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>" + failedBean.getFailedMsg() + "</Cause></Order>");
                } else if (baseEventClass == ObtainOrderDetailEvent.class) {
                    return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>" + failedBean.getFailedMsg() + "</Cause></Order>");
                } else if (baseEventClass == ObtainGoodListEvent.class) {
                    return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>" + failedBean.getFailedMsg() + "</Cause></Rsp>");
                } else if (baseEventClass == InventoryEvent.class) {
                    return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</result><GoodsType></GoodsType><Cause>" + failedBean.getFailedMsg() + "</Cause></Rsp>");
                }
                return null;
            }
        };
    }
}
