/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.logs;

import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.searchbean.OrderDetailSyncSearch;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * 订单详细信息同步
 * <p>
 * Created by allan on 4/22/16.
 */
public interface OrderDetailSyncLogService {
    /**
     * 保存一条相信信息同步记录
     *
     * @param orderDetailSyncLog
     * @return
     */
    OrderDetailSyncLog save(OrderDetailSyncLog orderDetailSyncLog);

    /**
     * 根据订单号得到一个记录
     *
     * @param orderId
     * @return
     */
    OrderDetailSyncLog findByOrderId(String orderId);

    /**
     * 根据id得到一个记录
     *
     * @param id
     * @return
     */
    OrderDetailSyncLog findById(long id);

    /**
     * 按条件分页查询
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<OrderDetailSyncLog> findAll(int pageIndex, int pageSize, int customerId, OrderDetailSyncSearch orderDetailSyncSearch);

    /**
     * 根据订单状态及erp类型查询订单
     *
     * @param syncStatus
     * @return
     */
    List<OrderDetailSyncLog> findOrderBySyncStatusAndProviderType(int customerId,
                                                                  OrderSyncStatus.DetailSyncStatus syncStatus,
                                                                  ERPTypeEnum.ProviderType providerType,
                                                                  Date beginTime);
}
