/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpprovider.sap.formatsap.LogiInfo;
import com.huobanplus.erpprovider.sap.util.ConnectHelper;
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
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by allan on 12/24/15.
 */
@Service
public class ScheduledService {
    private static final Log log = LogFactory.getLog(ScheduledService.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ShipSyncDeliverInfoService shipSyncDeliverInfoService;

    @Autowired
    private ERPRegister erpRegister;

    /*
     * 同步订单发货状态轮训服务
     */
//    @Scheduled(cron = "0 0 */1 * * ?")
    @Transactional
    public void syncOrderShip() {
        Date now = new Date();
        log.info("order ship sync for sap start:" + StringUtil.DateFormat(now, StringUtil.TIME_PATTERN));
        //得到所有配置过edb信息的商家,准备获取数据
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.SAP);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "start to sync order ship");
            try {
                SAPSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), SAPSysData.class);
                ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                ERPUserInfo erpUserInfo = new ERPUserInfo();
                erpUserInfo.setCustomerId(detailConfig.getCustomerId());

                List<LogiInfo> results = new ArrayList<>();
                //获取订单信息]
                JCoDestination jCoDestination = ConnectHelper.connect(sysData, erpUserInfo);
                JCoFunction jCoFunction = jCoDestination.getRepository().getFunction("ZWS_DATA_OUTPUT");
                JCoFunction jCoFunctionIn = jCoDestination.getRepository().getFunction("ZWS_DATA_OUTPUT_IN");
                if (jCoFunction == null) {
                    log.error("SAP中没有ZWS_DATA_IMPORT方法");
                    return;
                }
                jCoFunction.execute(jCoDestination);

                JCoTable jCoTable = jCoFunction.getTableParameterList().getTable("ZTABLE");

                for (int i = 0; i < jCoTable.getNumRows(); i++) {
                    LogiInfo logiInfo = new LogiInfo();
                    jCoTable.setRow(i);
                    logiInfo.setZVBELN(jCoTable.getString("ZVBELN"));
                    logiInfo.setYVBELN(jCoTable.getString("YVBELN"));
                    logiInfo.setZOrder(jCoTable.getString("ZORDER"));
                    logiInfo.setZType(jCoTable.getString("ZTYPE"));
                    logiInfo.setZWMOrder(jCoTable.getString("ZWMORDER"));
                    results.add(logiInfo);
                }
                //     String resultMsg = jCoFunction.getExportParameterList().getString("MESS");

                log.info("本次获取" + results.size() + "条订单数据");
                //发货同步记录
                OrderShipSyncLog orderShipSyncLog = new OrderShipSyncLog();
                List<OrderDeliveryInfo> failedOrders = new ArrayList<>();
                List<OrderDeliveryInfo> successOrders = new ArrayList<>();
                //推送物流信息
                if (results.size() > 0) {
                    List<OrderDeliveryInfo> deliveryInfoList = new ArrayList<>(); //等待发货的订单物流信息列表
                    addDeliveryInfo(results, deliveryInfoList);
                    orderShipSyncLog.setTotalCount(results.size());

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
                        orderShipSyncLog.setSuccessCount(successOrders.size());
                        orderShipSyncLog.setFailedCount(failedOrders.size());

                        int successCount = successOrders.size(), failedCount = failedOrders.size();

                        if (successCount > 0 && failedCount > 0) {
                            orderShipSyncLog.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_PARTY_SUCCESS);
                        }
                        if (successCount > 0 && failedCount == 0) {
                            orderShipSyncLog.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_SUCCESS);
                        }
                        if (successCount == 0) {
                            orderShipSyncLog.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.SYNC_FAILURE);
                        }
                    }
                } else {
                    orderShipSyncLog.setShipSyncStatus(OrderSyncStatus.ShipSyncStatus.NO_DATA);
                }

                orderShipSyncLog = orderShipSyncLogService.save(orderShipSyncLog);

                //同步失败的订单记录
                List<ShipSyncDeliverInfo> shipSyncDeliverInfoList = new ArrayList<>();

                shipSyncDeliverInfoService.shipSyncDeliverInfoList(shipSyncDeliverInfoList, failedOrders, orderShipSyncLog);
                shipSyncDeliverInfoService.shipSyncDeliverInfoList(shipSyncDeliverInfoList, successOrders, orderShipSyncLog);

                shipSyncDeliverInfoService.batchSave(shipSyncDeliverInfoList);

                log.info("修改物流状态");
                JCoTable ztable = jCoFunctionIn.getTableParameterList().getTable("ZTABLE");
                for (LogiInfo info : results) {
                    ztable.appendRow();
                    ztable.setValue("ZVBELN", info.getZVBELN());
                    ztable.setValue("YVBELN", info.getYVBELN());
                    ztable.setValue("ZORDER", info.getZOrder());
                    ztable.setValue("ZTYPE", "X");
                    ztable.setValue("ZWMORDER", info.getZWMOrder());
                }
                jCoFunctionIn.execute(jCoDestination);
                jCoFunctionIn.getExportParameterList().getString("MESS");
            } catch (Exception e) {
                log.error(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "发生错误", e);
            }
            log.info("sap ship sync end");
        }
    }

    private void addDeliveryInfo(List<LogiInfo> orders, List<OrderDeliveryInfo> orderDeliveryInfoList) {
        for (LogiInfo o : orders) {
            OrderDeliveryInfo deliveryInfo = new OrderDeliveryInfo();
            deliveryInfo.setOrderId(o.getZVBELN());
            deliveryInfo.setLogiNo(o.getZOrder());
            orderDeliveryInfoList.add(deliveryInfo);
        }
    }
}
