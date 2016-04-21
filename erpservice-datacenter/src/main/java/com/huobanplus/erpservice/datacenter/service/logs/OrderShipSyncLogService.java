/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.logs;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderShipSyncLog;
import org.springframework.data.domain.Page;

/**
 * 发货同步日志
 * Created by allan on 4/21/16.
 */
public interface OrderShipSyncLogService {
    /**
     * 保存一个发货同步日志
     *
     * @param orderShipSyncLog
     * @return
     */
    OrderShipSyncLog save(OrderShipSyncLog orderShipSyncLog);

    /**
     * 得到最后一次同步记录
     *
     * @param customerId
     * @param providerType
     * @return
     */
    OrderShipSyncLog findTop(int customerId, ERPTypeEnum.ProviderType providerType);

    /**
     * 按条件查询日志
     *
     * @return
     */
    Page<OrderShipSyncLog> findAll(int pageIndex, int pageSize);
}
