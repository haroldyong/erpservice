/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.repository.logs;

import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by allan on 4/22/16.
 */
public interface OrderDetailSyncLogRepository extends JpaRepository<OrderDetailSyncLog, Long>, JpaSpecificationExecutor<OrderDetailSyncLog> {
    OrderDetailSyncLog findByOrderId(String orderId);

    @Query("select l from OrderDetailSyncLog l where l.customerId=?1 and l.detailSyncStatus=?2 and l.providerType=?3 and l.syncTime > ?4")
    List<OrderDetailSyncLog> findByCustomerIdAndDetailSyncStatusAndProviderType(int customerId, OrderSyncStatus.DetailSyncStatus syncStatus,
                                                                                ERPTypeEnum.ProviderType providerType, Date begin);

    List<OrderDetailSyncLog> findByDetailSyncStatus(OrderSyncStatus.DetailSyncStatus syncStatus);
}
