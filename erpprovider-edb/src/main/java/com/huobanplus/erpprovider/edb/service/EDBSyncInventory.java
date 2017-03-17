/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.formatedb.EDBProStockInfo;
import com.huobanplus.erpprovider.edb.handler.EDBProductHandler;
import com.huobanplus.erpprovider.edb.search.EDBStockSearch;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.model.ProInventoryInfo;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.InventorySyncLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.sync.SyncInventoryEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by allan on 8/4/16.
 */
@Service
public class EDBSyncInventory {
    private static final Log log = LogFactory.getLog(EDBSyncInventory.class);
    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private InventorySyncLogService inventorySyncLogService;
    @Autowired
    private EDBProductHandler productHandler;
    @Autowired
    private ERPRegister erpRegister;

    @Scheduled(cron = "0 0 */1 * * ?")
    @Transactional
    public void syncInventoryForEDB() {
        Date now = new Date();
        log.info("order inventory sync for edb start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.EDB);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            doSync(detailConfig, now);
        }
        log.info("EDB inventory sync end");
    }

    private List<ProInventoryInfo> toProInventoryInfo(List<EDBProStockInfo> proStockInfoList) {
        List<ProInventoryInfo> proInventoryInfoList = new ArrayList<>();
        proStockInfoList.forEach(proStockInfo -> {
            ProInventoryInfo proInventoryInfo = new ProInventoryInfo();
            proInventoryInfo.setProductBn(proStockInfo.getProductBn());
            proInventoryInfo.setSalableInventory(proStockInfo.getSalableStock());
            proInventoryInfoList.add(proInventoryInfo);
        });
        return proInventoryInfoList;

    }

    public void doSync(ERPDetailConfigEntity detailConfig, Date now) {
        if (detailConfig.getErpBaseConfig().getIsSyncInventory() == 1) {
            try {
                ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                EDBSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), EDBSysData.class);
                List<ProInventoryInfo> failedList = new ArrayList<>(); //失败的列表
                int totalCount = 0; //总数量

//                    String[] storageIds = sysData.getStorageIds().split(",");
                int currentPageIndex = 1;

                EDBStockSearch stockSearch = new EDBStockSearch();
                stockSearch.setPageIndex(currentPageIndex);
                stockSearch.setPageSize(EDBConstant.PAGE_SIZE);
                stockSearch.setStoreId(sysData.getStorageIds());

                EventResult firstEventResult = productHandler.getProInventoryInfo(sysData, stockSearch);
                if (firstEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                    List<EDBProStockInfo> proStockInfoList = (List<EDBProStockInfo>) firstEventResult.getData();
                    if (proStockInfoList.size() > 0) {
                        totalCount = proStockInfoList.get(0).getTotalRecord();

                        List<ProInventoryInfo> proInventoryInfoList = toProInventoryInfo(proStockInfoList);
                        SyncInventoryEvent syncInventoryEvent = new SyncInventoryEvent();
                        syncInventoryEvent.setErpInfo(erpInfo);
                        syncInventoryEvent.setErpUserInfo(erpUserInfo);
                        syncInventoryEvent.setInventoryInfoList(proInventoryInfoList);

                        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                        EventResult firstSyncResult = erpUserHandler.handleEvent(syncInventoryEvent);

                        if (firstSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                            failedList.addAll((List<ProInventoryInfo>) firstSyncResult.getData());
                            int totalPage = totalCount / EDBConstant.PAGE_SIZE;

                            // 后续几页同步
                            if (totalCount % EDBConstant.PAGE_SIZE != 0) {
                                totalPage++;
                            }

                            if (totalPage > 1) {
                                currentPageIndex++;
                                for (int index = currentPageIndex; index <= totalPage; index++) {
                                    stockSearch.setPageIndex(index);
                                    EventResult nextEventResult = productHandler.getProInventoryInfo(sysData, stockSearch);
                                    if (nextEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                        List<ProInventoryInfo> nextResult = toProInventoryInfo(proStockInfoList);
                                        syncInventoryEvent.setInventoryInfoList(nextResult);

                                        EventResult nextSyncResult = erpUserHandler.handleEvent(syncInventoryEvent);
                                        if (nextSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                            failedList.addAll((List<ProInventoryInfo>) firstSyncResult.getData());
                                        } else {
                                            log.info("库存同步失败--" + nextEventResult.getResultMsg());
                                            return;
                                        }
                                    }
                                }
                            }
                        } else {
                            log.info("库存同步处理失败--" + firstEventResult.getResultMsg());
                            return;
                        }
                    }
                }
                log.info("inventory sync totalCount------->" + totalCount);
                if (totalCount > 0) {
                    inventorySyncLogService.saveLogAndDetail(
                            erpUserInfo.getErpUserType(),
                            erpInfo.getErpType(),
                            erpUserInfo.getCustomerId(),
                            totalCount,
                            failedList,
                            now
                    );
                }
            } catch (Exception e) {
                log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "库存同步发生错误--" + e.getMessage());
            }
        } else {
            log.info("edb customer " + detailConfig.getCustomerId() + " not open sync inventory");
        }
    }
}
