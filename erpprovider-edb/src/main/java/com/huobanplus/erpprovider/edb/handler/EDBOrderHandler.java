package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.searchbean.MallOrderSearchBean;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;
import org.dom4j.DocumentException;
import org.springframework.scheduling.annotation.Scheduled;
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
     * @param orderInfo 订单信息实体
     * @param info      ERP信息
     * @return 请求结果
     * @throws IOException IO异常
     */
    Monitor<EventResult> createOrder(MallOrderBean orderInfo, ERPInfo info) throws IOException;

    /**
     * 获取订单列表
     * <p>返回json</p>
     *
     * @return 请求结果
     * @throws IOException IO异常
     */
    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    void obtainOrderList() throws IOException;

    /**
     * 更新订单导入标记为 已导入，可更新条件为
     * <p>1、	订单导入标记为未导入</p>
     * <p>2、	订单非货到付款，其状态为 已确认   已付款（订单）</p>
     * <p>3、	订单为货到付款，其确认状态为  已确认（订单）</p>
     * <p>
     * <p> numId      单据号,可以输入一个,可以输入多个,多个单据号以逗号(,)隔开.如果某个单据号不导入,但需要批注,则在该单据号后加冒号(:),加上批注内容</p>
     * <p>tidType    单据类型,例如:Order订单/salesReturn退货单/stock_in_detail入库单/stock_out_detail出库单(请填入英文)</p>
     * <p>importMark 导入标记</p>
     *
     * @param orderInfo 订单信息
     * @param info      ERP信息
     * @return 请求结果
     * @throws IOException IO异常
     */
    Monitor<EventResult> orderStatusUpdate(MallOrderBean orderInfo, ERPInfo info) throws IOException;

    /**
     * 订单业务状态更新
     *
     * @param orderInfo 订单信息
     * @param info      ERP信息
     * @return 请求结果
     * @throws IOException IO异常
     */
    Monitor<EventResult> orderUpdate(MallOrderBean orderInfo, ERPInfo info) throws IOException;

    /**
     * 订单发货
     *
     * @param orderInfo 订单信息
     * @param info      ERP信息
     * @return 请求结果
     * @throws IOException IO异常
     */
    Monitor<EventResult> orderDeliver(MallOrderBean orderInfo, ERPInfo info) throws IOException;
}
