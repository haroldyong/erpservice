/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.logs;

import com.huobanplus.erpservice.datacenter.entity.logs.InventorySyncDetail;
import com.huobanplus.erpservice.datacenter.entity.logs.InventorySyncLog;
import com.huobanplus.erpservice.datacenter.model.ProInventoryInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by allan on 8/3/16.
 */
public interface InventorySyncDetailService {

    /**
     * 保存库存同步详细信息（失败的才记录）
     *
     * @param inventorySyncDetail
     * @return
     */
    @Transactional
    InventorySyncDetail save(InventorySyncDetail inventorySyncDetail);

    /**
     * 批量保存
     *
     * @param inventorySyncDetails
     * @return
     */
    @Transactional
    List<InventorySyncDetail> batchSave(List<InventorySyncDetail> inventorySyncDetails);

    @Transactional
    List<InventorySyncDetail> batchSaveInventoryInfo(List<ProInventoryInfo> inventoryInfoList, InventorySyncLog inventorySyncLog);
}
