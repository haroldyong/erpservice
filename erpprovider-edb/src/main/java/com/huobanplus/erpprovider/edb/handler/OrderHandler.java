package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by allan on 2015/7/24.
 */
public interface OrderHandler {
    Monitor<EventResult> createOrder(OrderInfo orderInfo) throws IOException;
}
