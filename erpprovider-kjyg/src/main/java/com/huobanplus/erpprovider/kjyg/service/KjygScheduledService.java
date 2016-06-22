/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.kjyg.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.kjyg.common.KjygSysData;
import com.huobanplus.erpprovider.kjyg.handler.KjygOrderHandler;
import com.huobanplus.erpprovider.kjyg.util.KjygConstant;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderShipSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.ShipSyncDeliverInfo;
import com.huobanplus.erpservice.datacenter.model.*;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.OrderShipSyncLogService;
import com.huobanplus.erpservice.datacenter.service.logs.ShipSyncDeliverInfoService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetOrderDetailListEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.BatchDeliverEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import com.huobanplus.erpuser.huobanmall.handler.HBOrderHandler;
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
 * Created by allan on 12/24/15.
 */
@Service
public class KjygScheduledService {
    private static final Log log = LogFactory.getLog(KjygScheduledService.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ShipSyncDeliverInfoService shipSyncDeliverInfoService;
    @Autowired
    private ERPRegister erpRegister;
    @Autowired
    private KjygOrderHandler kjygOrderHandler;
    @Autowired
    private HBOrderHandler hbOrderHandler;


    /**
     * 同步发货状态,根据发货时间进行轮询同步
     * <p>
     * 1.如果第一次是第一次同步,以当前时间的前一天发货时间的开始时间
     * 2.如果同步过,则以上次同步记录的时间为开始时间
     * <p>
     * 结束时间均为同步开始时间
     * 每个一小时进行一次同步
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    @Transactional
    public void syncOrderShip() {
        log.info("order ship sync for kjyg start!");
        Date now = new Date();
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.KJYG);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            try {

                ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                KjygSysData kjygSysData = JSON.parseObject(detailConfig.getErpSysData(), KjygSysData.class);
                int currentPageIndex = 1;
                OrderShipSyncLog lastSyncLog = orderShipSyncLogService.findTop(erpUserInfo.getCustomerId(), ERPTypeEnum.ProviderType.KJYG);
                List<OrderDeliveryInfo> failedOrders = new ArrayList<>(); //失败的订单列表
                List<OrderDeliveryInfo> successOrders = new ArrayList<>(); //成功的订单列表
                int totalCount = 0; //总数量
                OrderSearchInfo orderSearchInfo = new OrderSearchInfo();
                orderSearchInfo.setPageIndex(currentPageIndex);
                orderSearchInfo.setPageSize(KjygConstant.PAGE_SIZE);
                orderSearchInfo.setShipStatus(OrderEnum.ShipStatus.NOT_DELIVER.getCode());
                orderSearchInfo.setPayStatus(OrderEnum.PayStatus.PAYED.getCode());

                GetOrderDetailListEvent getOrderDetailListEvent = new GetOrderDetailListEvent();
                getOrderDetailListEvent.setErpUserInfo(erpUserInfo);
                getOrderDetailListEvent.setErpInfo(erpInfo);
                getOrderDetailListEvent.setOrderSearchInfo(orderSearchInfo);

                ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                EventResult firstEventResult = erpUserHandler.handleEvent(getOrderDetailListEvent);
                if (firstEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                    OrderListInfo orderListInfo = (OrderListInfo) firstEventResult.getData();
                    int totalResult = orderListInfo.getRecordCount();
//                    totalCount = orderListInfo.getRecordCount();
                    List<Order> orderList = orderListInfo.getOrders();

                    // first pull
                    BatchDeliverEvent batchDeliverEvent = new BatchDeliverEvent();
                    batchDeliverEvent.setErpUserInfo(erpUserInfo);
                    batchDeliverEvent.setErpInfo(erpInfo);
                    EventResult firstSyncResult = null;

                    if (orderList != null && orderList.size() > 0) {

                        EventResult deliveryResult = kjygOrderHandler.queryOrderTradNo(orderList, kjygSysData);
                        if (deliveryResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                            List<OrderDeliveryInfo> orderDeliveryInfoList = (List<OrderDeliveryInfo>) deliveryResult.getData();
                            totalCount += orderDeliveryInfoList.size();
                            batchDeliverEvent.setOrderDeliveryInfoList(orderDeliveryInfoList);
                            firstSyncResult = erpUserHandler.handleEvent(batchDeliverEvent);
                            if (firstSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                BatchDeliverResult firstBatchDeliverResult = (BatchDeliverResult) firstSyncResult.getData();
                                failedOrders.addAll(firstBatchDeliverResult.getFailedOrders());
                                successOrders.addAll(firstBatchDeliverResult.getSuccessOrders());
                            }
                        }
                    }

                    // next pull
                    int totalPage = totalResult / KjygConstant.PAGE_SIZE;
                    if (totalResult % KjygConstant.PAGE_SIZE != 0) {
                        totalPage++;
                    }
                    if (totalPage > 1) {
                        currentPageIndex++;
                        for (int i = currentPageIndex; i <= totalPage; i++) {
                            orderSearchInfo.setPageIndex(currentPageIndex);
                            EventResult nextEventResult = erpUserHandler.handleEvent(getOrderDetailListEvent);
                            if (nextEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                OrderListInfo nextOrderListInfo = (OrderListInfo) nextEventResult.getData();
                                if (nextOrderListInfo != null) {
                                    List<Order> nextOrderList = orderListInfo.getOrders();
                                    if (nextOrderList != null && nextOrderList.size() > 0) {
                                        EventResult nextDeliveryResult = kjygOrderHandler.queryOrderTradNo(nextOrderList, kjygSysData);
                                        if (nextDeliveryResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                            List<OrderDeliveryInfo> next = (List<OrderDeliveryInfo>) nextDeliveryResult.getData();
                                            batchDeliverEvent.setOrderDeliveryInfoList(next);
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

                    List<ShipSyncDeliverInfo> shipSyncDeliverInfoList = new ArrayList<>();

                    shipSyncDeliverInfoService.shipSyncDeliverInfoList(shipSyncDeliverInfoList, failedOrders, orderShipSyncLog, OrderSyncStatus.ShipSyncStatus.SYNC_FAILURE);
                    shipSyncDeliverInfoService.shipSyncDeliverInfoList(shipSyncDeliverInfoList, successOrders, orderShipSyncLog, OrderSyncStatus.ShipSyncStatus.SYNC_SUCCESS);

                    shipSyncDeliverInfoService.batchSave(shipSyncDeliverInfoList);
                }

            } catch (Exception e) {
                log.error(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "发生错误", e);
            }
        }
        log.info("kjyg ship sync end");
    }

}
