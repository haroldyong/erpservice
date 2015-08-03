package com.huobanplus.erpprovider.netshop.handler;

import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 商品处理事件接口
 */
public interface ProductHandler {

    /**
     * 获取商品信息
     * @param request
     * @return
     * @throws IOException
     */
    Monitor<EventResult> obtainGood(HttpServletRequest request) throws IOException;
}
