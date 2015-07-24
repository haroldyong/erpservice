package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpservice.event.model.OrderInfo;
import org.springframework.stereotype.Component;

/**
 * Created by allan on 2015/7/24.
 */
public interface OrderHandler {
    void createOrder(OrderInfo orderInfo);
}
