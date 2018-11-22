/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpprovider.sap.formatsap.LogiInfo;
import com.huobanplus.erpprovider.sap.handler.SAPOrderHandler;
import com.huobanplus.erpprovider.sap.util.ConnectHelper;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderShipSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.ShipSyncDeliverInfo;
import com.huobanplus.erpservice.datacenter.model.BatchDeliverResult;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.datacenter.service.logs.OrderShipSyncLogService;
import com.huobanplus.erpservice.datacenter.service.logs.ShipSyncDeliverInfoService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.BatchDeliverEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by allan on 12/24/15.
 */
@Service
public class SapScheduledService {
    private static final Log log = LogFactory.getLog(SapScheduledService.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ShipSyncDeliverInfoService shipSyncDeliverInfoService;
    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;
    @Autowired
    private SAPOrderHandler sapOrderHandler;

    @Autowired
    private ERPRegister erpRegister;

    /*
     * 同步订单发货状态轮训服务
     */
    @SuppressWarnings("Duplicates")
    @Scheduled(cron = "0 0 */1 * * ?")
    @Transactional
    public void syncOrderShip() {
        Date now = new Date();
        log.info("order ship sync for sap start:" + StringUtil.DateFormat(now, StringUtil.TIME_PATTERN));
        //得到所有配置过sap信息的商家,准备获取数据
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.SAP);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            if (detailConfig.getErpBaseConfig().getIsSyncDelivery() == 1) {
                log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "start to sync SAP syncOrderShip");
                try {
                    SAPSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), SAPSysData.class);
                    ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                    ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                   // log.info(JSON.toJSONString(sysData));
                    //log.info(JSON.toJSONString(erpUserInfo));

                    List<LogiInfo> results = new ArrayList<>();
                    //获取订单信息]
                    JCoDestination jCoDestination = ConnectHelper.connect(sysData, erpUserInfo);
                    JCoFunction jCoFunction = jCoDestination.getRepository().getFunction("ZWS_DATA_OUTPUT");
                    JCoFunction jCoFunctionIn = jCoDestination.getRepository().getFunction("ZWS_DATA_OUTPUT_IN");
                    if (jCoFunction == null) {
                        log.error("SAP中没有ZWS_DATA_IMPORT方法");
                        return;
                    }
                    log.info("start execute SAP call");
                    jCoFunction.execute(jCoDestination);
                    log.info("end execute SAP call");

                    JCoTable jCoTable = jCoFunction.getTableParameterList().getTable("ZTABLE");

                    for (int i = 0; i < jCoTable.getNumRows(); i++) {
                        LogiInfo logiInfo = new LogiInfo();

                        jCoTable.setRow(i);
                        String orderNo = jCoTable.getString("ZORDER");
                        if (checkOrderNo(orderNo)) {
                            logiInfo.setZVBELN(jCoTable.getString("ZVBELN"));
                            logiInfo.setYVBELN(jCoTable.getString("YVBELN"));
                            logiInfo.setZOrder(jCoTable.getString("ZORDER"));
                            logiInfo.setZType(jCoTable.getString("ZTYPE"));
                            logiInfo.setZWMOrder(jCoTable.getString("ZWMORDER"));
                            logiInfo.setZWMLogiName("圆通");
                            results.add(logiInfo);
                        }
                    }
                    //     String resultMsg = jCoFunction.getExportParameterList().getString("MESS");

                    log.info("SAP syncOrderShip  本次获取" + results.size() + "条订单数据");

                    //推送物流信息
                    if (results.size() > 0) {
                        List<OrderDeliveryInfo> failedOrders = new ArrayList<>(); //失败的订单
                        List<OrderDeliveryInfo> successOrders = new ArrayList<>(); //成功的订单

                        List<OrderDeliveryInfo> deliveryInfoList = new ArrayList<>(); //等待发货的订单物流信息列表
                        addDeliveryInfo(results, deliveryInfoList);

                        //推送给使用者,执行批量发货
                        BatchDeliverEvent batchDeliverEvent = new BatchDeliverEvent();
                        batchDeliverEvent.setErpUserInfo(erpUserInfo);
                        batchDeliverEvent.setErpInfo(erpInfo);
                        batchDeliverEvent.setOrderDeliveryInfoList(deliveryInfoList);

                        //处理事件,此处为推送订单列表信息到使用者
                        //推送给相应的erp使用商户
                        erpUserInfo.setErpUserType(detailConfig.getErpUserType());
                        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                        EventResult batchDeliverEventResult = erpUserHandler.handleEvent(batchDeliverEvent);
                        if (batchDeliverEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                            BatchDeliverResult batchDeliverResult = (BatchDeliverResult) batchDeliverEventResult.getData();
                            failedOrders = batchDeliverResult.getFailedOrders();
                            successOrders = batchDeliverResult.getSuccessOrders();

                        }
//
                        //回写已经取过并且物流信息同步成功的订单
                        JCoTable ztable = jCoFunctionIn.getTableParameterList().getTable("ZTABLE");
                        for (OrderDeliveryInfo successOrder : successOrders) {
                            LogiInfo info = null;
                            for (LogiInfo logiInfo : results) {
                                if (logiInfo.getZOrder().equals(successOrder.getOrderId())) {
                                    info = logiInfo;
                                    break;
                                }
                            }

                            if (info != null) {
                                ztable.appendRow();
                                ztable.setValue("ZVBELN", info.getZVBELN());
                                ztable.setValue("YVBELN", info.getYVBELN());
                                ztable.setValue("ZORDER", info.getZOrder());
                                ztable.setValue("ZTYPE", "X");
                                ztable.setValue("ZWMORDER", info.getZWMOrder());
                            }
                        }
                        jCoFunctionIn.execute(jCoDestination);
                        jCoFunctionIn.getExportParameterList().getString("MESS");

                        //记录同步日志
                        OrderShipSyncLog orderShipSyncLog = new OrderShipSyncLog();
                        orderShipSyncLog.setTotalCount(results.size());
                        orderShipSyncLog.setCustomerId(detailConfig.getCustomerId());
                        orderShipSyncLog.setSyncTime(now);
                        orderShipSyncLog.setProviderType(erpInfo.getErpType());
                        orderShipSyncLog.setUserType(erpUserInfo.getErpUserType());
                        orderShipSyncLog.setSuccessCount(successOrders.size());
                        orderShipSyncLog.setFailedCount(failedOrders.size());

                        int successCount = successOrders.size(), failedCount = failedOrders.size();

                        if (successCount == 0) {
                            orderShipSyncLog.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_FAILURE);
                        }
                        if (successCount > 0 && failedCount > 0) {
                            orderShipSyncLog.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_PARTY_SUCCESS);
                        }
                        if (successCount > 0 && failedCount == 0) {
                            orderShipSyncLog.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_SUCCESS);
                        }

                        orderShipSyncLog = orderShipSyncLogService.save(orderShipSyncLog);

                        //同步订单记录
                        List<ShipSyncDeliverInfo> shipSyncDeliverInfoList = new ArrayList<>();

                        shipSyncDeliverInfoService.shipSyncDeliverInfoList(shipSyncDeliverInfoList, failedOrders, orderShipSyncLog, OrderSyncStatus.ShipSyncStatus.SYNC_FAILURE);
                        shipSyncDeliverInfoService.shipSyncDeliverInfoList(shipSyncDeliverInfoList, successOrders, orderShipSyncLog, OrderSyncStatus.ShipSyncStatus.SYNC_SUCCESS);

                        shipSyncDeliverInfoService.batchSave(shipSyncDeliverInfoList);
                    }

                    //回写SAP标记已经获取过的订单--debug:应该是同步成功的订单需要回写
//                JCoTable ztable = jCoFunctionIn.getTableParameterList().getTable("ZTABLE");
//                for (LogiInfo info : results) {
//                    ztable.appendRow();
//                    ztable.setValue("ZVBELN", info.getZVBELN());
//                    ztable.setValue("YVBELN", info.getYVBELN());
//                    ztable.setValue("ZORDER", info.getZOrder());
//                    ztable.setValue("ZTYPE", "X");
//                    ztable.setValue("ZWMORDER", info.getZWMOrder());
//                }
//                jCoFunctionIn.execute(jCoDestination);
//                jCoFunctionIn.getExportParameterList().getString("MESS");
                } catch (Exception e) {
                    log.error(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "发生错误", e);
                }
            } else {
                log.info("sap customer " + detailConfig.getCustomerId() + " not open sync delivery");
            }

            log.info(detailConfig.getCustomerId() + " SAP syncOrderShip end");
        }
        log.info("sap ship sync end");
    }

    private void addDeliveryInfo(List<LogiInfo> orders, List<OrderDeliveryInfo> orderDeliveryInfoList) {
        for (LogiInfo o : orders) {
            OrderDeliveryInfo deliveryInfo = new OrderDeliveryInfo();
            deliveryInfo.setOrderId(o.getZOrder());
            deliveryInfo.setLogiNo(o.getZWMOrder());
            deliveryInfo.setLogiName(o.getZWMLogiName());
            deliveryInfo.setLogiCode("yuantong");
            orderDeliveryInfoList.add(deliveryInfo);
        }
    }

    /**
     * 3个月之内的有效
     *
     * @param orderNo
     * @return
     */
    private boolean checkOrderNo(String orderNo) {
        if (!StringUtils.isEmpty(orderNo) && orderNo.startsWith("20") && orderNo.length() > 8) {
            if (convert(orderNo.substring(0, 8)).isAfter(LocalDateTime.now().plusMonths(-2)) &&
                    convert(orderNo.substring(0, 8)).isBefore(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }

    public LocalDateTime convert(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
        return localDate.atStartOfDay();
    }

    /**
     * 定时重新推送失败的订单
     */
//    @Scheduled(cron = "0 0 */1 * * ?")
    @Transactional
    public void rePushFailedOrder() {

        String beginTime = "2016-12-01 00:00:00";
        Date begin = StringUtil.DateFormat(beginTime, StringUtil.TIME_PATTERN);

        int failedNum = 0;
        int totalNum = 0;
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.SAP);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            List<OrderDetailSyncLog> failedOrderLogs = orderDetailSyncLogService.findOrderBySyncStatusAndProviderType(detailConfig.getCustomerId(),
                    OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE,
                    ERPTypeEnum.ProviderType.SAP, begin);
            totalNum += failedOrderLogs.size();
            for (OrderDetailSyncLog failedLog : failedOrderLogs) {

                String orderJson = failedLog.getOrderInfoJson();
                PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
                ERPInfo erpInfo = new ERPInfo();
                erpInfo.setErpType(failedLog.getProviderType());
                erpInfo.setSysDataJson(failedLog.getErpSysData());

                ERPUserInfo erpUserInfo = new ERPUserInfo();
                erpUserInfo.setCustomerId(failedLog.getCustomerId());
                erpUserInfo.setErpUserType(failedLog.getUserType());

                pushNewOrderEvent.setOrderInfoJson(orderJson);
                pushNewOrderEvent.setErpUserInfo(erpUserInfo);
                pushNewOrderEvent.setErpInfo(erpInfo);

                EventResult eventResult = sapOrderHandler.pushOrder(pushNewOrderEvent);
                if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                    failedNum++;
                }
            }
        }

        log.info("本次订单重新推送共:" + totalNum);
        log.info("本次订单重新推送失败数:" + failedNum);
        log.info("本次订单重新推送成功数:" + (totalNum - failedNum));

    }
}
