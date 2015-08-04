package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 订单相关
 * Created by allan on 2015/7/24.
 */
public interface EDBOrderHandler {
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
    Monitor<EventResult> createOrder(OrderInfo orderInfo) throws IOException;

    /**
     * 获取订单信息
     * <p>返回json</p>
     *
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    Monitor<EventResult> getOrderInfo() throws IOException;

    /**
     * 获取订单列表
     * <p>返回json</p>
     *
     * @return
     * @throws IOException
     */
    Monitor<EventResult> obtainOrderList() throws IOException;

    /**
     * 更新订单状态
     * <p> numId      单据号,可以输入一个,可以输入多个,多个单据号以逗号(,)隔开.如果某个单据号不导入,但需要批注,则在该单据号后加冒号(:),加上批注内容</p>
     * <p>tidType    单据类型,例如:Order订单/salesReturn退货单/stock_in_detail入库单/stock_out_detail出库单(请填入英文)</p>
     * <p>importMark 导入标记</p>
     *
     * @return
     * @throws IOException
     */
    Monitor<EventResult> orderStatusUpdate(OrderInfo orderInfo) throws IOException;

    /**
     * 订单业务状态更新
     *
     * @param orderInfo
     * @return
     * @throws IOException
     */
    Monitor<EventResult> orderUpdate(OrderInfo orderInfo) throws IOException;

    /**
     * 订单发货
     *
     * @param orderInfo
     * @return
     * @throws IOException
     */
    Monitor<EventResult> orderDeliver(OrderInfo orderInfo) throws IOException;
}
