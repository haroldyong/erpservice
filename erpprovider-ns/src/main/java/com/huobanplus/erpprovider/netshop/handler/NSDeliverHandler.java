package com.huobanplus.erpprovider.netshop.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by allan on 2015/7/31.
 */
public interface NSDeliverHandler {
    Monitor<EventResult> deliverInform(HttpServletRequest request) throws IOException;
}
