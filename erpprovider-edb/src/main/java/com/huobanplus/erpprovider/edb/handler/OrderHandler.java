package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 订单相关
 * Created by allan on 2015/7/24.
 */
public interface OrderHandler {
    /**
     * 创建订单
     * <p>返回json</p>
     * <p>返回字段：result(结果);status(状态)</p>
     *
     * @param orderInfo
     * @return 请求结果
     * @throws IOException
     * @throws DocumentException
     */
    Monitor<EventResult> createOrder(OrderInfo orderInfo) throws IOException, DocumentException;

    /**
     * 获取订单信息
     * <p>返回json</p>
     *
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    Monitor<EventResult> getOrderInfo() throws IOException, DocumentException;
}
