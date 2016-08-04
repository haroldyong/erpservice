/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.logs.impl;

import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.entity.logs.InventorySyncDetail;
import com.huobanplus.erpservice.datacenter.entity.logs.InventorySyncLog;
import com.huobanplus.erpservice.datacenter.model.ProInventoryInfo;
import com.huobanplus.erpservice.datacenter.repository.logs.InventorySyncDetailRepository;
import com.huobanplus.erpservice.datacenter.service.logs.InventorySyncDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allan on 8/3/16.
 */
@Service
public class InventorySyncDetailServiceImpl implements InventorySyncDetailService {
    @Autowired
    private InventorySyncDetailRepository inventorySyncDetailRepository;

    @Override
    @Transactional
    public InventorySyncDetail save(InventorySyncDetail inventorySyncDetail) {
        return inventorySyncDetailRepository.save(inventorySyncDetail);
    }

    @Override
    @Transactional
    public List<InventorySyncDetail> batchSave(List<InventorySyncDetail> inventorySyncDetails) {
        return inventorySyncDetailRepository.save(inventorySyncDetails);
    }

    @Override
    @Transactional
    public List<InventorySyncDetail> batchSaveInventoryInfo(List<ProInventoryInfo> inventoryInfoList, InventorySyncLog inventorySyncLog) {
        List<InventorySyncDetail> inventorySyncDetails = new ArrayList<>();
        inventoryInfoList.forEach(inventoryInfo -> {
            InventorySyncDetail inventorySyncDetail = new InventorySyncDetail();
            inventorySyncDetail.setInventorySyncLog(inventorySyncLog);
            inventorySyncDetail.setInventorySyncStatus(OrderSyncStatus.InventorySyncStatus.SYNC_FAILURE);
            inventorySyncDetail.setProductBn(inventoryInfo.getProductBn());
            inventorySyncDetail.setProductName(inventoryInfo.getProductName());
            inventorySyncDetail.setMarketable(inventoryInfo.getMarketable());
            inventorySyncDetail.setInventory(inventoryInfo.getInventory());
            inventorySyncDetail.setSalableInventory(inventoryInfo.getSalableInventory());
            inventorySyncDetail.setPickInventory(inventoryInfo.getPickInventory());
            inventorySyncDetail.setOnRoadInventory(inventoryInfo.getOnRoadInventory());
            inventorySyncDetail.setErrorMsg(inventoryInfo.getRemark());
            inventorySyncDetails.add(inventorySyncDetail);
        });

        return this.batchSave(inventorySyncDetails);
    }
}
