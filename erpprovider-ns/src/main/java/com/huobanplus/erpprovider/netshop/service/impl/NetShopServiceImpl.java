package com.huobanplus.erpprovider.netshop.service.impl;

import com.huobanplus.erpprovider.netshop.dao.NetShopDao;
import com.huobanplus.erpprovider.netshop.service.NetShopService;
import com.huobanplus.erpservice.event.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 网店管家操作实现类
 */
@Service
public class NetShopServiceImpl implements NetShopService {

    @Override
    public Monitor<OrderInfo> commitOrderInfo(AuthBean authBean, OrderInfo orderInfo) {

        return null;
    }

    @Override
    public Monitor<OrderInfo> obtainOrderList(com.huobanplus.erpservice.event.model.AuthBean authBean, OrderInfo orderInfo) {
        return null;
    }

    @Override
    public Monitor<OrderInfo> obtainOrderDetail(com.huobanplus.erpservice.event.model.AuthBean authBean, String orderId) {
        return null;
    }

    @Override
    public Monitor<EventResult> deliveryNotice(AuthBean authBean, DeliveryInfo deliveryInfo) {
        return null;
    }

    @Override
    public Monitor<ProductInfo> obtainGoods(com.huobanplus.erpservice.event.model.AuthBean authBean, ProductInfo productInfo) {
        return null;
    }

    @Override
    public Monitor<EventResult> syncInventory(AuthBean authBean, InventoryInfo inventoryInfo) {
        return null;
    }

    @Override
    public Monitor<EventResult> modifyOrderInfo(AuthBean authBean, OrderInfo orderInfo) {
        return null;
    }

    @Override
    public Monitor<EventResult> notifyFailedEvent(FailedBean failedBean) {
        return null;
    }
}
