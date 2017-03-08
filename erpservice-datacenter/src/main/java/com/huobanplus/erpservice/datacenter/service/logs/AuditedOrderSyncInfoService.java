/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpservice.datacenter.service.logs;

import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.entity.logs.AuditedOrderSyncInfo;
import com.huobanplus.erpservice.datacenter.entity.logs.AuditedOrderSyncLog;
import com.huobanplus.erpservice.datacenter.model.AuditedOrder;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by wuxiongliu on 2017-03-08.
 */
public interface AuditedOrderSyncInfoService {

    /**
     * 保存一个发货同步失败的订单
     *
     * @param auditedOrderSyncInfo
     * @return
     */
    AuditedOrderSyncInfo save(AuditedOrderSyncInfo auditedOrderSyncInfo);

    /**
     * 批量保存
     *
     * @param auditedOrderSyncInfos
     */
    void batchSave(List<AuditedOrderSyncInfo> auditedOrderSyncInfos);

    /**
     * 按条件搜索
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<AuditedOrderSyncInfo> findAll(int pageIndex, int pageSize, long syncId, String orderId);

    AuditedOrderSyncInfo findById(long id);

    /**
     * 将发货信息组装成同步日志
     *
     * @param auditedOrderSyncInfoList
     * @param auditedOrderList
     * @param auditedOrderSyncLog
     * @param auditedSyncStatus
     */
    void auditedOrderInfoList(List<AuditedOrderSyncInfo> auditedOrderSyncInfoList, List<AuditedOrder> auditedOrderList, AuditedOrderSyncLog auditedOrderSyncLog, OrderSyncStatus.AuditedSyncStatus auditedSyncStatus);
}
