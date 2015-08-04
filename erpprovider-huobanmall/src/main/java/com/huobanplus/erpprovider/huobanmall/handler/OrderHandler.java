package com.huobanplus.erpprovider.huobanmall.handler;

import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>类描述：<p/>
 * 伙伴商城订单事件接口
 */
public interface OrderHandler {

    /**
     * 方法描述：提交（创建）单个订单信息
     * @param request 请求实体
     * @return 返回订单新增的结果
     * @throws IOException
     */
    Monitor<EventResult> commitOrder(HttpServletRequest request) throws IOException;

    /**
     * 根据订单编号获取单个订单信息
     * 该接口包含批量获取订单信息功能
     * {1、orderIds只包含一个orderId时，只获取一个订单信息 2、orderIds包含多个orderId时，批量获取订单信息}
     * orderIds包含多个orderId时 每个orderId以逗号隔开
     *
     * @param request 请求实体
     * @return 返回获取的订单信息
     * @throws IOException
     */
    Monitor<EventResult> obtainOrder(HttpServletRequest request) throws IOException;

    /**
     * <p>方法描述：<p/>
     * 更新单个订单信息
     * @param request 请求实体
     * @return 返回订单更新结果
     * @throws IOException
     */
    Monitor<EventResult> modifyOrder(HttpServletRequest request) throws IOException;

}
