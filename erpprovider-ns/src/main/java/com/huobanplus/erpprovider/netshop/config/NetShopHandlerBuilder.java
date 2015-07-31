package com.huobanplus.erpprovider.netshop.config;

import com.huobanplus.erpprovider.netshop.bean.*;
import com.huobanplus.erpprovider.netshop.handler.DeliverHandler;
import com.huobanplus.erpprovider.netshop.service.NetShopService;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpservice.event.erpevent.*;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.event.model.*;
import com.huobanplus.erpservice.event.model.AuthBean;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <b>类描述：<b/>具体处理事件
 */
public class NetShopHandlerBuilder implements ERPHandlerBuilder {
    /**
     * @param info 相关信息
     * @return 无法处理返回空，可以处理返回该erp事件处理器
     */
    @Resource
    private NetShopService netShopService;
    @Autowired
    private DeliverHandler deliverHandler;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if (!"netShop".equals(info.getName())) {
            //不是网店管家
            return null;
        }
        return new ERPHandler() {

            @Override
            public boolean eventSupported(Class<? extends ERPBaseEvent> baseEventClass) {
                return false;
            }

            @Override
            public Monitor<EventResult> handleEvent(Class<? extends ERPBaseEvent> baseEventClass, Object data) throws IOException, IllegalAccessException, DataAccessException, IllegalArgumentException {
                HttpServletRequest request = (HttpServletRequest) data;
                if (baseEventClass == CreateOrderEvent.class) {
                    request.getAttribute("uCode");
                    request.getAttribute("sign");

                } else if (baseEventClass == InventoryEvent.class) {

                } else if (baseEventClass == DeliveryInfoEvent.class) {
                    deliverHandler.deliverInform(request);
                }
                return null;
            }
        };
    }
}
