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

package com.huobanplus.erpservice.datacenter.service.logs.impl;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.logs.AuditedOrderSyncLog;
import com.huobanplus.erpservice.datacenter.repository.logs.AuditedOrderSyncLogREpository;
import com.huobanplus.erpservice.datacenter.service.logs.AuditedOrderSyncLogService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wuxiongliu on 2017-03-07.
 */
public class AuditedOrderSyncLogServiceImpl implements AuditedOrderSyncLogService {

    @Autowired
    private AuditedOrderSyncLogREpository auditedOrderSyncLogREpository;

    @Override
    public AuditedOrderSyncLog save(AuditedOrderSyncLog auditedOrderSyncLog) {
        return auditedOrderSyncLogREpository.save(auditedOrderSyncLog);
    }

    @Override
    public AuditedOrderSyncLog findTop(int customerId, ERPTypeEnum.ProviderType providerType) {
        return auditedOrderSyncLogREpository.findTopByCustomerIdAndProviderTypeOrderByIdDesc(customerId, providerType);
    }
}
