package com.huobanplus.erpprovicer.huobanmall.service;

import com.huobanplus.erpprovicer.huobanmall.common.AuthBean;
import com.huobanplus.erpservice.event.model.BaseResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;

import java.util.List;

/**
 *
 * 类描述：订单事件接口定义
 * @author aaron
 * @since 2015年7月25日 下午4:30:43
 * @version V1.0
 */
public interface OrderEventService extends BaseEvent {

    /**
     * 方法描述：提交（创建）单个订单信息,
     * 失败处理： 提示错误信息，并开启重新提交机制
     * @param orderBean 需要提交订单的字段集合
     * @param  authBean 安全签名参数集合
     * @return 返回处理结果（成功、失败）
     * @since 2015年7月25日 下午4:30:43
     */
    public Monitor<BaseResult> commitOrder(AuthBean authBean, OrderInfo orderBean);

    /**
     * 方法描述：批量提交（创建）订单信息
     * 失败处理： 提示错误信息，并开启重新获取机制
     * @param authBean 安全签名参数集合
     * @param orderBeans 需要提交订单的字段集合列表
     * @return 返回处理结果（成功、失败）
     * @since 2015年7月25日 下午4:30:43
     */
    public Monitor<BaseResult> commitOrder(AuthBean authBean, List<OrderInfo> orderBeans);

    /**
     * 方法描述：根据订单编号获取订单信息
     * 失败处理： 提示错误信息，并开启重新获取机制
     * @param authBean 安全签名参数集合
     * @param orderIds 订单号集合{1、orderIds只包含一个orderId时，只获取一个订单信息 2、orderIds包含多个orderId时，批量获取订单信息}
     * @return 订单详细信息（列表）
     * @since 2015年7月25日 下午4:30:43
     */
    public Monitor<OrderInfo> obtainOrder(AuthBean authBean, String orderIds);

    /**
     * 方法描述：获取所有订单信息
     * 失败处理：提示错误信息，并开启重新获取机制
     * @param authBean 安全签名参数集合
     * @return 返回所有的订单信息
     * @since 2015年7月25日 下午4:30:43
     */
    public Monitor<OrderInfo> obtainOrders(AuthBean authBean);

    /**
     * 方法描述：更新指定订单信息（包含状态、业务状态、以及种种订单信息更新）
     * 失败处理： 提示错误信息，并开启提交更新信息获取机制
     * @param authBean 安全签名参数集合
     * @param orderBean 订单需要更新的信息
     * @return 返回处理结果（成功、失败）
     * @since 2015年7月25日 下午4:30:43
     */
    public Monitor<BaseResult> modifyOrderInfo(AuthBean authBean, OrderInfo orderBean);
}
