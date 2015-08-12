package com.huobanplus.erpprovider.netshop.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 发货通知信息
 */
public interface NSDeliverHandler {
    Monitor<EventResult> deliverInform(HttpServletRequest request) throws IOException;
}
