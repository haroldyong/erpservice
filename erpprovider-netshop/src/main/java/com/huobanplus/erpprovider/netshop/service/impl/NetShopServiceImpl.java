package com.huobanplus.erpprovider.netshop.service.impl;

import com.huobanplus.erpprovider.netshop.bean.AuthBean;
import com.huobanplus.erpprovider.netshop.dao.NetShopDao;
import com.huobanplus.erpprovider.netshop.service.NetShopService;
import com.huobanplus.erpservice.event.model.BaseResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;
import com.huobanplus.erpservice.event.model.ProductInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 网店管家操作实现类
 */
@Service
public class NetShopServiceImpl implements NetShopService {

    @Resource
    private NetShopDao netShopDao;

    @Override
    public Monitor<OrderInfo> obtainOrderList(AuthBean authBean, OrderInfo orderInfo) {
        return null;
    }

    @Override
    public Monitor<OrderInfo> obtainOrderDetail(AuthBean authBean, String orderId) {
        return null;
    }

    @Override
    public Monitor<BaseResult> deliveryNotice(AuthBean authBean, OrderInfo orderInfo) {
        return null;
    }

    @Override
    public Monitor<ProductInfo> obtainGoods(AuthBean authBean, ProductInfo productInfo) {
        return null;
    }

    @Override
    public Monitor<BaseResult> syncInventory(AuthBean authBean, ProductInfo productInfo) {
        return null;
    }
}
