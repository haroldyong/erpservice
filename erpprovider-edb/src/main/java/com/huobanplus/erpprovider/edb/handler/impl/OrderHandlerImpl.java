package com.huobanplus.erpprovider.edb.handler.impl;

import com.huobanplus.erpprovider.edb.handler.OrderHandler;
import com.huobanplus.erpprovider.edb.utils.HTNetService;
import com.huobanplus.erpservice.event.model.OrderInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by allan on 2015/7/24.
 */
@Component
public class OrderHandlerImpl implements OrderHandler {

    @Override
    public void createOrder(OrderInfo orderInfo) {
        HTNetService htNetService = HTNetService.getInstance();
        Map<Object, Object> map = new HashMap<>();
        map.put("tid", orderInfo.getTid());
        map.put("out_tid", orderInfo.getOutTid());
        map.put("shop_id", orderInfo.getShopId());
    }
}
