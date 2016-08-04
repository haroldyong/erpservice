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
import com.huobanplus.erpservice.datacenter.entity.logs.InventorySyncLog;
import com.huobanplus.erpservice.datacenter.model.ProInventoryInfo;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by allan on 8/3/16.
 */
public interface InventorySyncLogService {
    /**
     * 保存一个库存同步记录
     *
     * @param inventorySyncLog
     * @return
     */
    @Transactional
    InventorySyncLog save(InventorySyncLog inventorySyncLog);

    /**
     * 找到最后一次同步记录
     *
     * @param customerId
     * @param providerType
     * @return
     */
    InventorySyncLog findLast(int customerId, ERPTypeEnum.ProviderType providerType);

    /**
     * 分组查找同步记录
     *
     * @param pageIndex
     * @param pageSize
     * @param beginTime
     * @param endTime
     * @param customerId
     * @return
     */
    Page<InventorySyncLog> findAll(int pageIndex, int pageSize, String beginTime, String endTime, int customerId);

    /**
     * 保存日志及详情
     */
    @Transactional
    void saveLogAndDetail(
            ERPTypeEnum.UserType userType,
            ERPTypeEnum.ProviderType providerType,
            int customerId,
            int totalCount,
            List<ProInventoryInfo> proInventoryInfoList,
            Date syncTime
    );
}
