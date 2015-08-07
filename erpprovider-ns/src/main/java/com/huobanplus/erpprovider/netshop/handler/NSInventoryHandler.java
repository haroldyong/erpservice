package com.huobanplus.erpprovider.netshop.handler;

import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 库存相关处理
 * Created by allan on 2015/8/2.
 */
public interface NSInventoryHandler {
    /**
     * 库存同步
     * @param request 请求实体
     * @return 返回一个统一结果处理实体
     * @throws IOException IO异常
     */
    Monitor<EventResult> synsInventory(HttpServletRequest request) throws IOException;
}
