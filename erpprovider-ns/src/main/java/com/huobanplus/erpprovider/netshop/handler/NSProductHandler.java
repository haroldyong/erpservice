package com.huobanplus.erpprovider.netshop.handler;

import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 商品处理事件接口
 */
public interface NSProductHandler {

    /**
     * 获取商品信息
     *
     * @param request 请求实体
     * @return 返回返回结果值统一处理信息
     * @throws IOException IO 异常
     */
    Monitor<EventResult> obtainGoods(HttpServletRequest request) throws IOException;

    /**
     * 数据同步
     *
     * @param request
     * @return
     * @throws IOException
     */
    Monitor<EventResult> syncInventory(HttpServletRequest request) throws IOException;
}
