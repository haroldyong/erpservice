/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.iscs.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.iscs.common.ISCSConstant;
import com.huobanplus.erpprovider.iscs.common.ISCSSysData;
import com.huobanplus.erpprovider.iscs.handler.ISCSOrderHandler;
import com.huobanplus.erpprovider.iscs.search.ISCSOrderSearch;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderShipSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.ShipSyncDeliverInfo;
import com.huobanplus.erpservice.datacenter.model.BatchDeliverResult;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.OrderShipSyncLogService;
import com.huobanplus.erpservice.datacenter.service.logs.ShipSyncDeliverInfoService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.BatchDeliverEvent;
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
 * 网仓的轮训服务
 * <p>
 * Created by allan on 4/21/16.
 */
@Service
public class ISCSScheduledService {
    private static final Log log = LogFactory.getLog(ISCSScheduledService.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ShipSyncDeliverInfoService shipSyncDeliverInfoService;
    @Autowired
    private ISCSOrderHandler orderHandler;
    @Autowired
    private ERPRegister erpRegister;

    /**
     * 同步发货状态
     * <p>
     * 1.如果第一次是第一次同步,以当前时间的前一天发货时间的开始时间
     * 2.如果同步过,则以上次同步记录的时间为开始时间
     * <p>
     * 结束时间均为同步开始时间
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    @SuppressWarnings("Duplcates")
    @Transactional
    public void syncOrderShip() {
        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        log.info("order ship for iscs start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.ISCS);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "start to sync order ship");
            try {
                ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                ISCSSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), ISCSSysData.class);
                //当前页索引
                int currentPageIndex = 1;
                //是否是第一次同步,第一次同步beginTime则为当前时间的前一天
                OrderShipSyncLog lastSyncLog = orderShipSyncLogService.findTop(detailConfig.getCustomerId(), ERPTypeEnum.ProviderType.ISCS);
                Date beginTime = lastSyncLog == null
                        ? Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusDays(1))
                        : lastSyncLog.getSyncTime();

                List<OrderDeliveryInfo> failedOrders = new ArrayList<>(); //失败的订单列表
                List<OrderDeliveryInfo> successOrders = new ArrayList<>(); //成功的订单列表
                int totalCount = 0; //总的同步数量

                ISCSOrderSearch orderSearch = new ISCSOrderSearch();
                orderSearch.setStockId(sysData.getStockId());
                orderSearch.setShopId(sysData.getShopId());
                orderSearch.setBeginTime(StringUtil.DateFormat(beginTime, StringUtil.TIME_PATTERN));
                orderSearch.setEndTime(nowStr);
                orderSearch.setPageIndex(currentPageIndex);
                orderSearch.setPageSize(ISCSConstant.PAGE_SIZE);

                //第一次获取
                EventResult eventResult = orderHandler.getOrderDeliveryInfo(sysData, orderSearch);
                if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                    JSONObject result = (JSONObject) eventResult.getData();
                    int totalResult = result.getInteger("total_count");
                    JSONArray resultArray = result.getJSONArray("trades");

                    List<OrderDeliveryInfo> first = orderDeliveryInfoList(resultArray);
                    totalCount += first.size();

                    BatchDeliverEvent batchDeliverEvent = new BatchDeliverEvent();
                    batchDeliverEvent.setErpUserInfo(erpUserInfo);
                    batchDeliverEvent.setErpInfo(erpInfo);
                    batchDeliverEvent.setOrderDeliveryInfoList(first);
                    ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                    EventResult firstSyncResult = erpUserHandler.handleEvent(batchDeliverEvent);
                    if (firstSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                        BatchDeliverResult firstBatchDeliverResult = (BatchDeliverResult) firstSyncResult.getData();
                        failedOrders.addAll(firstBatchDeliverResult.getFailedOrders());
                        successOrders.addAll(firstBatchDeliverResult.getSuccessOrders());
                    }

                    //进行后面几次的获取
                    int totalPage = totalResult / ISCSConstant.PAGE_SIZE;
                    if (totalResult % ISCSConstant.PAGE_SIZE != 0) {
                        totalPage++;
                    }
                    if (totalPage > 1) {
                        currentPageIndex++;
                        //取下几笔数据
                        for (int i = currentPageIndex; i <= totalPage; i++) {
                            orderSearch.setPageIndex(currentPageIndex);
                            EventResult nextEventResult = orderHandler.getOrderDeliveryInfo(sysData, orderSearch);
                            if (nextEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                JSONObject nextResult = (JSONObject) nextEventResult.getData();
                                JSONArray nextResultArray = nextResult.getJSONArray("trades");
                                List<OrderDeliveryInfo> next = orderDeliveryInfoList(nextResultArray);
                                totalCount += next.size();

                                EventResult nextSyncResult = erpUserHandler.handleEvent(batchDeliverEvent); //使用者同步

                                if (nextSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                    BatchDeliverResult firstBatchDeliverResult = (BatchDeliverResult) firstSyncResult.getData();
                                    failedOrders.addAll(firstBatchDeliverResult.getFailedOrders());
                                    successOrders.addAll(firstBatchDeliverResult.getSuccessOrders());
                                }
                            }
                        }
                    }
                }
                if (totalCount > 0) {
                    int successCount = successOrders.size(), failedCount = failedOrders.size();
                    //发货同步记录
                    OrderShipSyncLog orderShipSyncLog = new OrderShipSyncLog();
                    orderShipSyncLog.setProviderType(erpInfo.getErpType());
                    orderShipSyncLog.setUserType(erpUserInfo.getErpUserType());
                    orderShipSyncLog.setCustomerId(erpUserInfo.getCustomerId());
                    orderShipSyncLog.setTotalCount(totalCount);
                    orderShipSyncLog.setSuccessCount(successCount);
                    orderShipSyncLog.setFailedCount(failedCount);
                    orderShipSyncLog.setSyncTime(now);
                    if (totalCount > 0) {
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

                    //记录推送失败的订单
                    List<ShipSyncDeliverInfo> shipSyncDeliverInfoList = new ArrayList<>();

                    shipSyncDeliverInfoService.shipSyncDeliverInfoList(shipSyncDeliverInfoList, failedOrders, orderShipSyncLog, OrderSyncStatus.ShipSyncStatus.SYNC_FAILURE);
                    shipSyncDeliverInfoService.shipSyncDeliverInfoList(shipSyncDeliverInfoList, successOrders, orderShipSyncLog, OrderSyncStatus.ShipSyncStatus.SYNC_SUCCESS);

                    shipSyncDeliverInfoService.batchSave(shipSyncDeliverInfoList);
                }

            } catch (Exception e) {
                log.error(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "发生错误", e);
            }
        }
        log.info("iscs ship sync end");
    }

    private List<OrderDeliveryInfo> orderDeliveryInfoList(JSONArray resultArray) {
        List<OrderDeliveryInfo> orderDeliveryInfoList = new ArrayList<>();
        for (Object o : resultArray) {
            JSONObject deliverJson = (JSONObject) o;
            int flag = deliverJson.getInteger("flag");//是否全部发完,全部发完才需要推送到伙伴商城执行发货
            if (flag == 1) {
                OrderDeliveryInfo deliveryInfo = new OrderDeliveryInfo();
                deliveryInfo.setOrderId(deliverJson.getString("order_no"));
                deliveryInfo.setLogiName(deliverJson.getString("transporter_id"));
                deliveryInfo.setLogiNo(deliverJson.getString("out_ids"));
                orderDeliveryInfoList.add(deliveryInfo);
            }

        }
        return orderDeliveryInfoList;
    }
}
