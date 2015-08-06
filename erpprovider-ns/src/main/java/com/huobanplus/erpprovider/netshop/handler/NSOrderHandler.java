package com.huobanplus.erpprovider.netshop.handler;

import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 订单方法处理接口
 */
public interface NSOrderHandler {

    /**
     * 新增订单信息
     *
     * @param request 请求实体
     * @return 返回新增订单的结果
     * @throws IOException
     */
    Monitor<EventResult> commitOrderInfo(HttpServletRequest request) throws IOException;

    /**
     * 订单状态改变修改
     *
     * @param request 请求实体
     * @return 返回订单修改结果
     * @throws IOException
     */
    Monitor<EventResult> orderStatusInfo(HttpServletRequest request) throws IOException;

    /**
     * 获取订单信息
     *
     * @param request 请求实体
     * @return 返回订单详细信息结果
     * @throws IOException
     */
    Monitor<EventResult> obtainOrderInfo(HttpServletRequest request) throws IOException;
}
