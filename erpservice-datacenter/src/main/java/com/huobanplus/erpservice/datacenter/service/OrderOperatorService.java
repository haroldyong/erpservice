/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service;

import com.huobanplus.erpservice.datacenter.entity.OrderOperatorLog;
import com.huobanplus.erpservice.datacenter.searchbean.OrderPushSearch;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by allan on 4/13/16.
 */
public interface OrderOperatorService {
    OrderOperatorLog save(OrderOperatorLog orderOperatorLog);

    Page<OrderOperatorLog> findAll(int pageIndex, int pageSize, OrderPushSearch orderPushSearch, int customerId);

    List<OrderOperatorLog> findByCustomerIdAndOrderId(int customerId, String orderId);

    OrderOperatorLog findByOrderId(String orderId);

    OrderOperatorLog findById(long id);
}
