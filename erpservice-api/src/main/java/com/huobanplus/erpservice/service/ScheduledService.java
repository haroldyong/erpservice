/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpservice.service;

import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.proxy.utils.OrderProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by allan on 07/03/2017.
 */
@Service
public class ScheduledService {
    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;
    @Autowired
    private OrderProxyService orderProxyService;

    @Scheduled(cron = "0 0 */2 * * ?")
    @Transactional
    public void rePushFailedOrder() {
        //得到所有失败的订单
        List<OrderDetailSyncLog> syncLogs = orderDetailSyncLogService.findBySyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
        //开始同步
        syncLogs.forEach(syncLog -> {
            ERPInfo erpInfo = new ERPInfo(syncLog.getProviderType(), syncLog.getErpSysData());
            ERPUserInfo erpUserInfo = new ERPUserInfo(syncLog.getUserType(), syncLog.getCustomerId());

            PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
            pushNewOrderEvent.setErpInfo(erpInfo);
            pushNewOrderEvent.setOrderInfoJson(syncLog.getOrderInfoJson());
            pushNewOrderEvent.setErpUserInfo(erpUserInfo);

            ApiResult apiResult = orderProxyService.handleEvent(pushNewOrderEvent);
            if (apiResult.getResultCode() == ResultCode.SUCCESS.getResultCode()) {
                syncLog.setSyncTime(new Date());
                syncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
                orderDetailSyncLogService.save(syncLog);
            }
        });
    }
}
