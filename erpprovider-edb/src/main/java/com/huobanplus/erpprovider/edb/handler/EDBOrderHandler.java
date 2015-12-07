/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpservice.datacenter.entity.MallOrderBean;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

import java.io.IOException;
import java.util.Date;

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
    EventResult createOrder(Order orderInfo, ERPInfo info);

    /**
     * 获取订单列表
     * <p>返回json</p>
     *
     * @return 请求结果
     * @throws IOException IO异常
     */
//    @Scheduled(fixedRate = 60000, initialDelay = 60000)
//    void obtainOrderList() throws IOException;

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
    EventResult orderStatusUpdate(MallOrderBean orderInfo, ERPInfo info) throws IOException;

    /**
     * 订单业务状态更新
     *
     * @param orderInfo 订单信息
     * @param info      ERP信息
     * @return 请求结果
     * @throws IOException IO异常
     */
    EventResult orderUpdate(MallOrderBean orderInfo, ERPInfo info);

    /**
     * 订单发货
     *
     * @param orderId
     * @param deliverTime
     * @param expressNo
     * @param express
     * @param weight
     * @param info
     * @return 请求结果
     * @throws IOException IO异常
     */
    EventResult orderDeliver(String orderId, Date deliverTime, String expressNo, String express, String weight, ERPInfo info);

    /**
     * 根据外部订单编号获取订单详情
     *
     * @param orderId
     * @return
     */
    EventResult getOrderDetail(String orderId, ERPInfo erpInfo);

    /**
     * 订单作废
     *
     * @param orderId
     * @param erpInfo
     * @return
     */
    EventResult cancelOrder(String orderId, ERPInfo erpInfo);
}
