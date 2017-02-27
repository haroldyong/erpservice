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

package com.huobanplus.erpprovider.wangdianv2.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.wangdianv2.common.WangDianV2Constant;
import com.huobanplus.erpprovider.wangdianv2.common.WangDianV2SysData;
import com.huobanplus.erpprovider.wangdianv2.handler.WangDianV2ProductHandler;
import com.huobanplus.erpprovider.wangdianv2.search.WangDianV2StockSearch;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2017-02-24.
 */
@Service
public class WangDianV2ProductScheduleService {

    private static final Log log = LogFactory.getLog(WangDianV2ProductScheduleService.class);
    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private InventorySyncLogService inventorySyncLogService;
    @Autowired
    private WangDianV2ProductHandler wangDianV2ProductHandler;
    @Autowired
    private ERPRegister erpRegister;

    @Scheduled(cron = "0 0 0/3 * * ? *")// 每隔三小时执行一次，因为旺店通的查询时间最大间隔为三小时
    @Transactional
    public void syncInventory() {
        Date now = Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusMinutes(2));
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        log.info("order inventory sync for wangdianv2 start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.WANGDIANV2);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            if (detailConfig.getErpBaseConfig().getIsSyncInventory() == 1) {

                try {
                    ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                    ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                    WangDianV2SysData sysData = JSON.parseObject(detailConfig.getErpSysData(), WangDianV2SysData.class);
                    List<ProInventoryInfo> failedList = new ArrayList<>(); //失败的列表
                    int totalCount = 0; //总数量
                    int currentPageIndex = 0;

                    //是否是第一次同步,第一次同步beginTime则为当前时间的前一天
                    InventorySyncLog lastSyncLog = inventorySyncLogService.findLast(erpUserInfo.getCustomerId(), ERPTypeEnum.ProviderType.WANGDIANV2);
                    Date beginTime = lastSyncLog == null
                            ? Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusHours(3))
                            : lastSyncLog.getSyncTime();

                    WangDianV2StockSearch wangDianV2StockSearch = new WangDianV2StockSearch();
                    wangDianV2StockSearch.setPageNo(currentPageIndex);
                    wangDianV2StockSearch.setPageSize(WangDianV2Constant.PAGE_SIZE);
                    wangDianV2StockSearch.setStartTime(StringUtil.DateFormat(beginTime, StringUtil.TIME_PATTERN));
                    wangDianV2StockSearch.setEndTime(nowStr);
                    wangDianV2StockSearch.setWarehouseNo(sysData.getWarehouseNo());

                    // first pull
                    EventResult firstQueryStockEvent = wangDianV2ProductHandler.queryStock(wangDianV2StockSearch, sysData);
                    if (firstQueryStockEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                        JSONObject firstQueryResp = (JSONObject) firstQueryStockEvent.getData();
                        totalCount = firstQueryResp.getInteger("total_count");
                        JSONArray orderArray = firstQueryResp.getJSONArray("stocks");


                        if (totalCount > 0) {
                            // convert to platform stock
                            List<ProInventoryInfo> first = toProInventoryInfo(orderArray);
                            // first push
                            SyncInventoryEvent syncInventoryEvent = new SyncInventoryEvent();
                            syncInventoryEvent.setErpUserInfo(erpUserInfo);
                            syncInventoryEvent.setErpInfo(erpInfo);
                            syncInventoryEvent.setInventoryInfoList(first);
                            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                            EventResult firstSyncResult = erpUserHandler.handleEvent(syncInventoryEvent);
                            if (firstSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                failedList.addAll((List<ProInventoryInfo>) firstSyncResult.getData());

                                int totalPage = totalCount / WangDianV2Constant.PAGE_SIZE;
                                // 后续几页同步
                                if (totalCount % WangDianV2Constant.PAGE_SIZE != 0) {
                                    totalPage++;
                                }
                                if (totalPage > 1) {
                                    currentPageIndex++;
                                    for (int index = currentPageIndex; index <= totalPage; index++) {
                                        wangDianV2StockSearch.setPageNo(index);
                                        EventResult nextQueryStockEvent = wangDianV2ProductHandler.queryStock(wangDianV2StockSearch, sysData);
                                        if (nextQueryStockEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                            JSONObject nextQueryResp = (JSONObject) nextQueryStockEvent.getData();
                                            JSONArray nextOrderArray = nextQueryResp.getJSONArray("stocks");

                                            List<ProInventoryInfo> next = toProInventoryInfo(nextOrderArray);
                                            syncInventoryEvent.setInventoryInfoList(next);

                                            EventResult nextSyncResult = erpUserHandler.handleEvent(syncInventoryEvent);
                                            if (nextSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                                failedList.addAll((List<ProInventoryInfo>) firstSyncResult.getData());
                                            } else {
                                                log.info("库存同步失败--" + nextSyncResult.getResultMsg());
                                                return;
                                            }
                                        }
                                    }
                                }
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
                    e.printStackTrace();
                }
            }

        }
    }

    private List<ProInventoryInfo> toProInventoryInfo(JSONArray jsonDataArray) {
        List<ProInventoryInfo> proInventoryInfoList = new ArrayList<>();
        for (Object o : jsonDataArray) {
            JSONObject itemJsonObj = JSON.parseObject(o.toString());
            ProInventoryInfo proInventoryInfo = new ProInventoryInfo();
            proInventoryInfo.setGoodBn(itemJsonObj.getString("goods_no"));
            proInventoryInfo.setProductBn(itemJsonObj.getString("spec_code"));// TODO: 2017-02-27
            proInventoryInfo.setMarketable(0);
            proInventoryInfo.setInventory((int) itemJsonObj.getDoubleValue("stock_num"));
            proInventoryInfo.setPickInventory((int) itemJsonObj.getDoubleValue("stock_num"));
            proInventoryInfo.setOnRoadInventory((int) itemJsonObj.getDoubleValue("purchase_num"));
            proInventoryInfo.setSalableInventory((int) itemJsonObj.getDoubleValue("stock_num"));
            proInventoryInfoList.add(proInventoryInfo);
        }
        return proInventoryInfoList;
    }

}
