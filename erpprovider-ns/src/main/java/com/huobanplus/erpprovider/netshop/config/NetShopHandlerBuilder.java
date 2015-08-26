package com.huobanplus.erpprovider.netshop.config;

import com.huobanplus.erpprovider.netshop.handler.NSDeliverHandler;
import com.huobanplus.erpprovider.netshop.handler.NSInventoryHandler;
import com.huobanplus.erpprovider.netshop.handler.NSOrderHandler;
import com.huobanplus.erpprovider.netshop.service.NetShopService;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpservice.event.erpevent.*;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.event.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <b>类描述：具体处理事件<b/>
 */
@Component
public class NetShopHandlerBuilder implements ERPHandlerBuilder {
    /**
     * @param info 相关信息
     * @return 无法处理返回空，可以处理返回该erp事件处理器
     */
    @Resource
    private NetShopService netShopService;

    @Autowired
    private NSDeliverHandler nsDeliverHandler;

    @Resource
    private NSOrderHandler nsOrderHandler;

    @Resource
    private NSInventoryHandler nsInventoryHandler;

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
                } else {
                    return false;
                }
            }

            @Override
            public Monitor handleEvent(ERPBaseEvent erpBaseEvent, Object data) throws IOException, IllegalAccessException, IllegalArgumentException {
                HttpServletRequest request = (HttpServletRequest) data;

                if (erpBaseEvent instanceof DeliveryInfoEvent) {
                    return nsDeliverHandler.deliverInform(request);
                } else if (erpBaseEvent instanceof ObtainOrderDetailEvent) {
                    return nsOrderHandler.obtainOrderInfo(request);
                } else if (erpBaseEvent instanceof ObtainOrderListEvent) {
                    return nsOrderHandler.obtainOrderInfoList(request);
                } else {
                    return null;
                }
            }

            @Override
            public Monitor handleException(Class<? extends ERPBaseEvent> baseEventClass, FailedBean failedBean) {

                if (baseEventClass == DeliveryInfoEvent.class) {
                    return new BaseMonitor<>(new EventResult(0, "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>" + failedBean.getFailedMsg() + "</Cause></Order>"));
                } else if (baseEventClass == ObtainOrderListEvent.class) {
                    return new BaseMonitor<>(new EventResult(0, "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>" + failedBean.getFailedMsg() + "</Cause></Order>"));
                } else if (baseEventClass == ObtainOrderDetailEvent.class) {
                    return new BaseMonitor<>(new EventResult(0, "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>" + failedBean.getFailedMsg() + "</Cause></Order>"));
                }
                return null;
            }
        };
    }
}
