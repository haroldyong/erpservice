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

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.logs.AuditedOrderSyncLog;
import org.springframework.data.domain.Page;

/**
 * Created by wuxiongliu on 2017-03-07.
 */
public interface AuditedOrderSyncLogService {

    AuditedOrderSyncLog save(AuditedOrderSyncLog auditedOrderSyncLog);

    /**
     * 得到最后一次同步记录
     *
     * @param customerId
     * @param providerType
     * @return
     */
    AuditedOrderSyncLog findTop(int customerId, ERPTypeEnum.ProviderType providerType);

    Page<AuditedOrderSyncLog> findAll(int pageIndex, int pageSize, String beginTime, String endTime, int customerId);

    AuditedOrderSyncLog findOne(long syncId);
}
