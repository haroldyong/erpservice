/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.sursung.common.SurSungConstant;
import com.huobanplus.erpprovider.sursung.common.SurSungSysData;
import com.huobanplus.erpprovider.sursung.handler.SurSungOrderHandler;
import com.huobanplus.erpprovider.sursung.search.SurSungOrderSearch;
import com.huobanplus.erpprovider.sursung.search.SurSungOrderSearchResult;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderShipSyncLog;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.OrderShipSyncLogService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2016-09-16.
 * 同步不同渠道的订单到平台
 */
@Component
public class SurSungSyncChannelOrder {

    private static final Log log = LogFactory.getLog(SurSungSyncChannelOrder.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;

    @Autowired
    private SurSungOrderHandler surSungOrderHandler;

    //    @Scheduled(cron = "")
    @Transactional
    public void syncChannelOrder() {
        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        log.info("channel Order sync for SurSung start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.SURSUNG);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            try {
                ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                SurSungSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), SurSungSysData.class);

                //是否是第一次同步,第一次同步beginTime则为当前时间的前一天
                OrderShipSyncLog lastSyncLog = orderShipSyncLogService.findTop(erpUserInfo.getCustomerId(), ERPTypeEnum.ProviderType.SURSUNG);
                Date beginTime = lastSyncLog == null
                        ? Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusDays(2))
                        : lastSyncLog.getSyncTime();

                List<OrderDeliveryInfo> failedOrders = new ArrayList<>(); //失败的订单列表
                List<OrderDeliveryInfo> successOrders = new ArrayList<>(); //成功的订单列表
                int totalCount = 0; //总数量
                int pageIndex = 1;

                SurSungOrderSearch orderSearch = new SurSungOrderSearch();
                orderSearch.setPageIndex(1);
                orderSearch.setPageSize(SurSungConstant.PAGE_SIZE);
                orderSearch.setModifiedBegin(StringUtil.DateFormat(beginTime, StringUtil.TIME_PATTERN));
                orderSearch.setModifiedEnd(nowStr);

                // 第一次同步
                EventResult eventResult = surSungOrderHandler.queryChannelOrder(orderSearch, sysData);
                if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                    SurSungOrderSearchResult surSungOrderSearchResult = (SurSungOrderSearchResult) eventResult.getData();
                    totalCount = surSungOrderSearchResult.getDataCount();

                    while (surSungOrderSearchResult.isHasNext()) {
                        pageIndex++;
                        orderSearch.setPageIndex(pageIndex);
                        EventResult nextEventResult = surSungOrderHandler.queryChannelOrder(orderSearch, sysData);
                        if (nextEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                            surSungOrderSearchResult = (SurSungOrderSearchResult) nextEventResult.getData();
                        }
                    }
                }

                if (totalCount > 0) {// 轮询若无数据，则不记录日志
                    syncLog(failedOrders, successOrders, totalCount, erpUserInfo, erpInfo);
                }
            } catch (Exception e) {

            }
        }
        log.info("channel Order sync for SurSung end!");

    }

    public void syncLog(List<OrderDeliveryInfo> failedOrders,
                        List<OrderDeliveryInfo> successOrders, int totalCount,
                        ERPUserInfo erpUserInfo, ERPInfo erpInfo) {
        //发货同步记录
        OrderShipSyncLog orderShipSyncLog = new OrderShipSyncLog();
        if (totalCount > 0) {
            int successCount = successOrders.size(), failedCount = failedOrders.size();
            orderShipSyncLog.setProviderType(erpInfo.getErpType());
            orderShipSyncLog.setUserType(erpUserInfo.getErpUserType());
            orderShipSyncLog.setCustomerId(erpUserInfo.getCustomerId());
            orderShipSyncLog.setTotalCount(totalCount);
            orderShipSyncLog.setSuccessCount(successCount);
            orderShipSyncLog.setFailedCount(failedCount);
            orderShipSyncLog.setSyncTime(new Date());
            if (successCount > 0 && failedCount > 0) {
                orderShipSyncLog.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_PARTY_SUCCESS);
            }
            if (successCount > 0 && failedCount == 0) {
                orderShipSyncLog.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_SUCCESS);
            }
            if (successCount == 0) {
                orderShipSyncLog.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_FAILURE);
            }
        } else {
            orderShipSyncLog.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.NO_DATA);
        }
        orderShipSyncLog = orderShipSyncLogService.save(orderShipSyncLog);

    }
}
