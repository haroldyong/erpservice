package com.huobanplus.erpprovider.netshop.handler;

import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by allan on 2015/7/31.
 */
public interface DeliverHandler {
    Monitor<EventResult> deliverInform(HttpServletRequest request) throws IllegalArgumentException;
}
