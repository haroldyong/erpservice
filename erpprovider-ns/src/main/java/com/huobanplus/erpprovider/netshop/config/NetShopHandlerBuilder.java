package com.huobanplus.erpprovider.netshop.config;

import com.huobanplus.erpprovider.netshop.bean.*;
import com.huobanplus.erpprovider.netshop.service.NetShopService;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpservice.event.erpevent.*;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.event.model.*;
import com.huobanplus.erpservice.event.model.AuthBean;
import org.springframework.dao.DataAccessException;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <b>类描述：<b/>具体处理事件
 */
public class NetShopHandlerBuilder implements ERPHandlerBuilder {
    /**
     *
     * @param info 相关信息
     * @return 无法处理返回空，可以处理返回该erp事件处理器
     */
    @Resource
    private NetShopService netShopService;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if(!"netShop".equals(info.getName()))
        {
            //不是网店管家
            return null;
        }
        return new ERPHandler() {
            @Override
            public boolean eventSupported(ERPBaseEvent erpEvent) {
                if (erpEvent instanceof CreateOrderEvent) {
                    return true;
                } else if (erpEvent instanceof InventoryEvent) {
                    return true;
                } else if (erpEvent instanceof DeliveryInfoEvent) {
                    return true;
                } else if (erpEvent instanceof OrderStatusInfoEvent) {
                    return true;
                } else if(erpEvent instanceof  ProductInfoEvent)
                {
                    return true;
                }
                else {
                    return false;
                }
            }

            @Override
            public Monitor<EventResult> handleEvent(ERPBaseEvent erpEvent) throws IOException, IllegalAccessException, DataAccessException {
                if (erpEvent instanceof CreateOrderEvent) {
                    OrderInfo orderInfo = ((CreateOrderEvent) erpEvent).getOrderInfo();
                    AuthBean authBean = ((CreateOrderEvent) erpEvent).getAuthBean();
                    netShopService.obtainOrderDetail(authBean, orderInfo.getOrderCode());
                }
                else if (erpEvent instanceof InventoryEvent) {
                    InventoryInfo inventoryInfo = ((InventoryEvent) erpEvent).getInventoryInfo();
                    AuthBean authBean = ((InventoryEvent) erpEvent).getAuthBean();
                    netShopService.syncInventory(authBean, inventoryInfo);

                }
                else if (erpEvent instanceof DeliveryInfoEvent) {

                }
                else if (erpEvent instanceof OrderStatusInfoEvent) {

                }
                else if(erpEvent instanceof  ProductInfoEvent)
                {

                }
                else
                {

                }
                return null;
            }
        };
    }
}
