/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.gy.common.GYConstant;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.formatgy.stock.GYStockInfo;
import com.huobanplus.erpprovider.gy.formatgy.stock.GYStockResponse;
import com.huobanplus.erpprovider.gy.handler.GYStockHandler;
import com.huobanplus.erpprovider.gy.search.GYStockSearchNew;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.logs.InventorySyncLog;
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
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by allan on 8/2/16.
 */
@Service
public class GYSyncInventory {
    private static final Log log = LogFactory.getLog(GYSyncInventory.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;

    @Autowired
    private InventorySyncLogService inventorySyncLogService;
    @Autowired
    private ERPRegister erpRegister;

    @Autowired
    private GYStockHandler gyStockHandler;

    //    @Scheduled(cron = "0 0 */1 * * ?")
    @Transactional
    public void syncInventoryForGy() {
        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        log.info("inventory sync for gy start");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.GY);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            if (detailConfig.getErpBaseConfig().getIsSyncInventory() == 1) {

                try {
                    ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                    ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                    GYSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), GYSysData.class);

                    //是否是第一次同步,第一次同步beginTime则为当前时间的前一天
                    InventorySyncLog lastSyncLog = inventorySyncLogService.findLast(erpUserInfo.getCustomerId(), ERPTypeEnum.ProviderType.GY);
                    Date beginTime = lastSyncLog == null
                            ? Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusDays(7))
                            : lastSyncLog.getSyncTime();

                    int currentPageIndex = 1;

                    List<ProInventoryInfo> failedList = new ArrayList<>(); //失败的列表

                    int totalCount = 0; //总数量

                    GYStockSearchNew stockSearchNew = new GYStockSearchNew();
                    stockSearchNew.setPageNo(currentPageIndex);
                    stockSearchNew.setPageSize(GYConstant.PAGE_SIZE);
                    stockSearchNew.setStartDate(StringUtil.DateFormat(beginTime, StringUtil.TIME_PATTERN));
                    stockSearchNew.setEndDate(nowStr);
                    stockSearchNew.setWarehouseCode(sysData.getWarehouseCode());

                    // 第一次同步
                    EventResult eventResult = gyStockHandler.stockQueryNew(stockSearchNew, sysData);
                    if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                        GYStockResponse stockResponse = (GYStockResponse) eventResult.getData();

                        totalCount = stockResponse.getTotalCount();
                        if (totalCount > 0) {
                            List<ProInventoryInfo> first = toProInventoryInfo(stockResponse.getStockInfoList());
                            SyncInventoryEvent syncInventoryEvent = new SyncInventoryEvent();
                            syncInventoryEvent.setErpUserInfo(erpUserInfo);
                            syncInventoryEvent.setErpInfo(erpInfo);
                            syncInventoryEvent.setInventoryInfoList(first);
                            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);

                            EventResult firstSyncResult = erpUserHandler.handleEvent(syncInventoryEvent);

                            if (firstSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                failedList.addAll((List<ProInventoryInfo>) firstSyncResult.getData());

                                int totalPage = totalCount / GYConstant.PAGE_SIZE;

                                // 后续几页同步
                                if (totalCount % GYConstant.PAGE_SIZE != 0) {
                                    totalPage++;
                                }
                                if (totalPage > 1) {
                                    currentPageIndex++;
                                    for (int index = currentPageIndex; index <= totalPage; index++) {
                                        stockSearchNew.setPageNo(index);
                                        EventResult nextEventResult = gyStockHandler.stockQueryNew(stockSearchNew, sysData);
                                        if (nextEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                            GYStockResponse nextStockResponse = (GYStockResponse) eventResult.getData();
                                            List<ProInventoryInfo> next = toProInventoryInfo(nextStockResponse.getStockInfoList());
                                            syncInventoryEvent.setInventoryInfoList(next);

                                            EventResult nextSyncResult = erpUserHandler.handleEvent(syncInventoryEvent);
                                            if (nextSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                                failedList.addAll((List<ProInventoryInfo>) firstSyncResult.getData());
                                            } else {
                                                log.info("库存同步失败--" + eventResult.getResultMsg());
                                                return;
                                            }
                                        }
                                    }
                                }
                            } else {
                                log.info("库存同步处理失败--" + eventResult.getResultMsg());
                                return;
                            }
                        }
                    }


                    if (totalCount > 0) {// 轮询若无数据，则不记录日志
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
                    log.error(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "发生错误", e);
                }
            } else {
                log.info("gy customer " + detailConfig.getCustomerId() + " not open sync inventory");
            }
        }
        log.info("GY inventory sync end");
    }

    private List<ProInventoryInfo> toProInventoryInfo(List<GYStockInfo> stockInfoList) {
        List<ProInventoryInfo> proInventoryInfoList = new ArrayList<>();
        stockInfoList.forEach(stockInfo -> {
            ProInventoryInfo proInventoryInfo = new ProInventoryInfo();
            proInventoryInfo.setGoodBn(stockInfo.getItemCode());
            proInventoryInfo.setProductBn(stockInfo.getSkuCode());
            proInventoryInfo.setMarketable(stockInfo.isDel() ? 1 : 0);
            proInventoryInfo.setInventory(stockInfo.getQty());
            proInventoryInfo.setPickInventory(stockInfo.getPickQty());
            proInventoryInfo.setOnRoadInventory(stockInfo.getRoadQty());
            proInventoryInfo.setSalableInventory(stockInfo.getSalableQty());
            proInventoryInfoList.add(proInventoryInfo);
        });

        return proInventoryInfoList;
    }
}
