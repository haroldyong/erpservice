/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service;

import com.huobanplus.erpservice.datacenter.entity.MallOrderBean;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 订单业务
 * Created by allan on 2015/7/10.
 */
public interface MallOrderService {
    /**
     * 保存订单
     *
     * @param orderBean
     * @return
     */
    MallOrderBean save(MallOrderBean orderBean);

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    MallOrderBean findByOrderId(String orderId);

    /**
     * 条件分组查询订单
     *
     * @param orderStatus 订单状态
     * @param payStatus   支付状态
     * @param orderId     订单号
     * @param pageIndex   页码
     * @param pageSize    每页数量
     * @return
     */
    Page<MallOrderBean> findAll(Integer orderStatus, Integer payStatus, String orderId, String sysData, int pageIndex, int pageSize);

    /**
     * 得到所有订单
     *
     * @return
     */
    List<MallOrderBean> findAll();

    /**
     * 得到需要轮询的数据
     *
     * @param rotaryStatus
     * @return
     */
    List<MallOrderBean> findByRotaryStatus(int rotaryStatus);
}
