/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edi.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.edi.common.EDIConstant;
import com.huobanplus.erpprovider.edi.common.EDISysData;
import com.huobanplus.erpprovider.edi.handler.EDIOrderHandler;
import com.huobanplus.erpprovider.edi.search.EDILogiSearch;
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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2016-09-21.
 */
@Service
public class EDIScheduleService {

    private static final Log log = LogFactory.getLog(EDIScheduleService.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ShipSyncDeliverInfoService shipSyncDeliverInfoService;
    @Autowired
    private ERPRegister erpRegister;
    @Autowired
    private EDIOrderHandler ediOrderHandler;

    /**
     * 物流状态同步
     */
    public void syncLogistic() {

        // 获取基本配置信息
        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        log.info("order ship sync for edi start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.EDI);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            try {
                ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                EDISysData ediSysData = JSON.parseObject(detailConfig.getErpSysData(), EDISysData.class);
                int currentPageIndex = 1;
                OrderShipSyncLog lastSyncLog = orderShipSyncLogService.findTop(erpUserInfo.getCustomerId(), ERPTypeEnum.ProviderType.EDI);
                Date beginTime = lastSyncLog == null
                        ? Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusDays(1))
                        : lastSyncLog.getSyncTime();

                List<OrderDeliveryInfo> failedOrders = new ArrayList<>();
                List<OrderDeliveryInfo> successOrders = new ArrayList<>();
                int totalCount = 0;

                EDILogiSearch ediLogiSearch = new EDILogiSearch();
                ediLogiSearch.setStartTime(StringUtil.DateFormat(beginTime, StringUtil.TIME_PATTERN));
                ediLogiSearch.setEndTime(nowStr);
                ediLogiSearch.setPage(currentPageIndex);
                ediLogiSearch.setPageSize(EDIConstant.PAGE_SIZE);

                EventResult eventResult = ediOrderHandler.logisticSearch(ediLogiSearch, ediSysData);
                if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                    String xmlResp = (String) eventResult.getData();
                    Document document = DocumentHelper.parseText(xmlResp);
                    Element root = document.getRootElement();
                    Element totalCountElem = root.element("totalCount");
                    int totalResult = Integer.parseInt(totalCountElem.getText());

                    List<OrderDeliveryInfo> first = orderDeliveryInfoList(xmlResp);
                    totalCount += first.size();

                    BatchDeliverEvent batchDeliverEvent = new BatchDeliverEvent();
                    batchDeliverEvent.setErpUserInfo(erpUserInfo);
                    batchDeliverEvent.setErpInfo(erpInfo);
                    batchDeliverEvent.setOrderDeliveryInfoList(first);

                    // 推送至erpuser
                    ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                    EventResult firstSyncResult = erpUserHandler.handleEvent(batchDeliverEvent);
                    if (firstSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                        BatchDeliverResult firstBatchDeliverResult = (BatchDeliverResult) firstSyncResult.getData();
                        failedOrders.addAll(firstBatchDeliverResult.getFailedOrders());
                        successOrders.addAll(firstBatchDeliverResult.getSuccessOrders());
                    }


                    int totalPage = totalResult / EDIConstant.PAGE_SIZE;
                    if (totalResult % EDIConstant.PAGE_SIZE != 0) {
                        totalPage++;
                    }
                    if (totalPage > 1) {
                        currentPageIndex++;
                        for (int i = currentPageIndex; i <= totalPage; i++) {
                            ediLogiSearch.setPage(currentPageIndex);
                            EventResult nextEventResult = ediOrderHandler.logisticSearch(ediLogiSearch, ediSysData);
                            if (nextEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                // TODO: 2016-09-21
                                String nextXmlResp = (String) eventResult.getData();
                                List<OrderDeliveryInfo> next = orderDeliveryInfoList(nextXmlResp); //下几次需要同步的物流信息
                                totalCount += next.size();

                                batchDeliverEvent.setOrderDeliveryInfoList(next);

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

                    List<ShipSyncDeliverInfo> shipSyncDeliverInfoList = new ArrayList<>();

                    shipSyncDeliverInfoService.shipSyncDeliverInfoList(shipSyncDeliverInfoList, failedOrders, orderShipSyncLog, OrderSyncStatus.ShipSyncStatus.SYNC_FAILURE);
                    shipSyncDeliverInfoService.shipSyncDeliverInfoList(shipSyncDeliverInfoList, successOrders, orderShipSyncLog, OrderSyncStatus.ShipSyncStatus.SYNC_SUCCESS);

                    shipSyncDeliverInfoService.batchSave(shipSyncDeliverInfoList);
                }

            } catch (Exception e) {
                log.error(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "发生错误", e);
            }
        }

        // 抓取第一批数据并推送
        // 根据数据总量执行后续几次抓取并推送
        // 记录日志
    }

    private List<OrderDeliveryInfo> orderDeliveryInfoList(String responseXml) {

        return null;
    }
}
