package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;

import java.io.IOException;

/**
 * Created by allan on 2015/7/28.
 */
public interface ProductHandler {
    Monitor<EventResult> getProductInfo() throws IOException;
}
