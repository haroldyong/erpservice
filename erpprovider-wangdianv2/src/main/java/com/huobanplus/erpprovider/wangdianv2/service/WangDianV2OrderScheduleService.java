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
import com.huobanplus.erpprovider.wangdianv2.handler.WangDianV2OrderHandler;
import com.huobanplus.erpprovider.wangdianv2.search.WangDianV2OrderSearch;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.logs.AuditedOrderSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderShipSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.ShipSyncDeliverInfo;
import com.huobanplus.erpservice.datacenter.model.BatchDeliverResult;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.AuditedOrderSyncLogService;
import com.huobanplus.erpservice.datacenter.service.logs.OrderShipSyncLogService;
import com.huobanplus.erpservice.datacenter.service.logs.ShipSyncDeliverInfoService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.BatchDeliverEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushAuditedOrderEvent;
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
public class WangDianV2OrderScheduleService {

    private static final Log log = LogFactory.getLog(WangDianV2OrderScheduleService.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ShipSyncDeliverInfoService shipSyncDeliverInfoService;
    @Autowired
    private AuditedOrderSyncLogService auditedOrderSyncLogService;
    @Autowired
    private ERPRegister erpRegister;
    @Autowired
    private WangDianV2OrderHandler wangDianV2OrderHandler;


    @Scheduled(cron = "0 0 0/3 * * ? ")// 每隔三小时执行一次，因为旺店通的查询时间最大间隔为三小时
    @Transactional
    public void syncShip() {
        Date now = Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusMinutes(2));// 与服务器的时间有点误差啊
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        log.info("order ship sync for wangdianv2 start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.WANGDIANV2);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {

            if (detailConfig.getErpBaseConfig().getIsSyncDelivery() == 1) {
                log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "start to sync order ship");
                try {
                    ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                    ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                    WangDianV2SysData sysData = JSON.parseObject(detailConfig.getErpSysData(), WangDianV2SysData.class);

                    //当前索引
                    int currentPageIndex = 0;
                    //是否是第一次同步,第一次同步beginTime则为当前时间的前三小时
                    OrderShipSyncLog lastSyncLog = orderShipSyncLogService.findTop(erpUserInfo.getCustomerId(), ERPTypeEnum.ProviderType.WANGDIANV2);
                    Date beginTime = lastSyncLog == null
                            ? Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusHours(3))
                            : lastSyncLog.getSyncTime();

                    List<OrderDeliveryInfo> failedOrders = new ArrayList<>(); //失败的订单列表
                    List<OrderDeliveryInfo> successOrders = new ArrayList<>(); //成功的订单列表
                    int totalCount = 0; //总数量

                    WangDianV2OrderSearch wangDianV2OrderSearch = new WangDianV2OrderSearch();
                    wangDianV2OrderSearch.setStartTime(StringUtil.DateFormat(beginTime, StringUtil.TIME_PATTERN));
                    wangDianV2OrderSearch.setEndTime(nowStr);
                    wangDianV2OrderSearch.setPageNo(currentPageIndex);
                    wangDianV2OrderSearch.setPageSize(WangDianV2Constant.PAGE_SIZE);
                    wangDianV2OrderSearch.setStatus(95);// 已发货的订单

                    EventResult firstQueryEvent = wangDianV2OrderHandler.queryOrder(wangDianV2OrderSearch, sysData);
                    if (firstQueryEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                        JSONObject firstQueryResp = (JSONObject) firstQueryEvent.getData();
                        totalCount = firstQueryResp.getInteger("total_count");
                        JSONArray orderArray = firstQueryResp.getJSONArray("trades");

                        // convert to delivery order
                        List<OrderDeliveryInfo> orderDeliveryInfoList = convert2OrderDeliveryInfoList(orderArray, sysData);
                        BatchDeliverEvent batchDeliverEvent = new BatchDeliverEvent();
                        batchDeliverEvent.setErpUserInfo(erpUserInfo);
                        batchDeliverEvent.setErpUserInfo(erpUserInfo);

                        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);

                        if (orderDeliveryInfoList.size() > 0) {

                            batchDeliverEvent.setOrderDeliveryInfoList(orderDeliveryInfoList);
                            // push to platform

                            EventResult firstSyncEvent = erpUserHandler.handleEvent(batchDeliverEvent);
                            if (firstSyncEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                BatchDeliverResult firstBatchDeliverResult = (BatchDeliverResult) firstSyncEvent.getData();
                                failedOrders.addAll(firstBatchDeliverResult.getFailedOrders());
                                successOrders.addAll(firstBatchDeliverResult.getSuccessOrders());
                            } else {
                                failedOrders.addAll(orderDeliveryInfoList);
                            }
                        }


                        // next pull
                        int totalPage = totalCount / WangDianV2Constant.PAGE_SIZE;
                        if (totalCount % WangDianV2Constant.PAGE_SIZE != 0) {
                            totalPage++;
                        }

                        if (totalPage > 1) {
                            currentPageIndex++;
                            //取下几笔数据
                            for (int i = currentPageIndex; i <= totalPage; i++) {
                                wangDianV2OrderSearch.setPageNo(i);
                                EventResult nextQueryEvent = wangDianV2OrderHandler.queryOrder(wangDianV2OrderSearch, sysData);
                                if (nextQueryEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                    JSONObject nextQueryResp = (JSONObject) firstQueryEvent.getData();
                                    JSONArray nextOrderArray = nextQueryResp.getJSONArray("trades");

                                    // convert to delivery order
                                    orderDeliveryInfoList = convert2OrderDeliveryInfoList(nextOrderArray, sysData);
                                    if (orderDeliveryInfoList.size() > 0) {
                                        batchDeliverEvent.setOrderDeliveryInfoList(orderDeliveryInfoList);
                                        // push to platform
                                        EventResult nextSyncEvent = erpUserHandler.handleEvent(batchDeliverEvent);
                                        if (nextSyncEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                            BatchDeliverResult nextBatchDeliverResult = (BatchDeliverResult) nextSyncEvent.getData();
                                            failedOrders.addAll(nextBatchDeliverResult.getFailedOrders());
                                            successOrders.addAll(nextBatchDeliverResult.getSuccessOrders());
                                        } else {
                                            failedOrders.addAll(orderDeliveryInfoList);
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

                    log.info("wangdianv2 ship sync end");
                } catch (Exception e) {
                    log.info("ship sync exception caused by " + e.getMessage());
                }
            }
        }
    }

    /**
     * 转换为发货单
     *
     * @param jsonArray
     * @return
     */
    public List<OrderDeliveryInfo> convert2OrderDeliveryInfoList(JSONArray jsonArray, WangDianV2SysData wangDianV2SysData) {
        List<OrderDeliveryInfo> orderDeliveryInfoList = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject itemJsonObj = JSON.parseObject(o.toString());
            if (itemJsonObj.getString("shop_no").equals(wangDianV2SysData.getShopNo())) {// 筛选出本店铺的订单
                OrderDeliveryInfo orderDeliveryInfo = new OrderDeliveryInfo();
                orderDeliveryInfo.setOrderId(itemJsonObj.getString("src_tids").split(",")[0]);
                orderDeliveryInfo.setFreight(itemJsonObj.getDouble("post_amount"));
                orderDeliveryInfo.setLogiCode(itemJsonObj.getString("logistics_code"));
                orderDeliveryInfo.setLogiName("");// TODO: 2017-02-24
                orderDeliveryInfo.setLogiNo(itemJsonObj.getString("logistics_no"));
                orderDeliveryInfo.setRemark(itemJsonObj.getString("cs_remark"));

                JSONArray goodsItem = itemJsonObj.getJSONArray("goods_list");
                StringBuffer sb = new StringBuffer();
                for (Object goodsItemObj : goodsItem) {
                    JSONObject goodsItemJsonObj = JSON.parseObject(goodsItemObj.toString());
                    sb.append(goodsItemJsonObj.getString("spec_no"))
                            .append(",")
                            .append(goodsItemJsonObj.getDouble("num"))
                            .append("|");
                }
                orderDeliveryInfo.setDeliverItemsStr(sb.toString());
                orderDeliveryInfoList.add(orderDeliveryInfo);
            }
        }
        return orderDeliveryInfoList;
    }

    private List<String> convert2AuditedOrderList(JSONArray jsonArray, WangDianV2SysData wangDianV2SysData) {
        List<String> orderIds = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject itemJsonObj = JSON.parseObject(o.toString());
            if (itemJsonObj.getString("shop_no").equals(wangDianV2SysData.getShopNo())) {// 筛选出本店铺的订单
                orderIds.add(itemJsonObj.getString("src_tids").split(",")[0]);
            }
        }
        return orderIds;
    }

    /**
     * 同步已审核订单
     */
    @Scheduled(cron = "0 0 0/3 * * ? ")// 每隔三小时执行一次，因为旺店通的查询时间最大间隔为三小时
    public void syncAuditedOrder() {
        Date now = Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusMinutes(2));// 与服务器的时间有点误差啊
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        log.info("audited order sync for wangdianv2 start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.WANGDIANV2);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {

            if (detailConfig.getErpBaseConfig().getIsSyncDelivery() == 1) { // TODO: 2017-03-08
                log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "start to sync audited order ");
                try {
                    ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                    ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                    WangDianV2SysData sysData = JSON.parseObject(detailConfig.getErpSysData(), WangDianV2SysData.class);

                    //当前索引
                    int currentPageIndex = 0;
                    //是否是第一次同步,第一次同步beginTime则为当前时间的前三小时
                    AuditedOrderSyncLog lastSyncLog = auditedOrderSyncLogService.findTop(erpUserInfo.getCustomerId(), ERPTypeEnum.ProviderType.WANGDIANV2);
                    Date beginTime = lastSyncLog == null
                            ? Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusHours(3))
                            : lastSyncLog.getSyncTime();

                    int totalCount = 0; //总数量
                    int successCount = 0;
                    int failedCount = 0;
                    List<String> auditedOrders = new ArrayList<>();

                    WangDianV2OrderSearch wangDianV2OrderSearch = new WangDianV2OrderSearch();
                    wangDianV2OrderSearch.setStartTime(StringUtil.DateFormat(beginTime, StringUtil.TIME_PATTERN));
                    wangDianV2OrderSearch.setEndTime(nowStr);
                    wangDianV2OrderSearch.setPageNo(currentPageIndex);
                    wangDianV2OrderSearch.setPageSize(WangDianV2Constant.PAGE_SIZE);
                    wangDianV2OrderSearch.setStatus(55);// 已审核订单

                    EventResult firstQueryEvent = wangDianV2OrderHandler.queryOrder(wangDianV2OrderSearch, sysData);
                    if (firstQueryEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                        JSONObject firstQueryResp = (JSONObject) firstQueryEvent.getData();
                        totalCount = firstQueryResp.getInteger("total_count");
                        JSONArray orderArray = firstQueryResp.getJSONArray("trades");

                        List<String> orderIds = convert2AuditedOrderList(orderArray, sysData);
                        PushAuditedOrderEvent pushAuditedOrderEvent = new PushAuditedOrderEvent();
                        pushAuditedOrderEvent.setErpUserInfo(erpUserInfo);
                        pushAuditedOrderEvent.setErpUserInfo(erpUserInfo);

                        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);

                        if (orderIds.size() > 0) {

                            auditedOrders.addAll(orderIds);
                            pushAuditedOrderEvent.setOrderIds(orderIds);
                            // push to platform
                            EventResult firstSyncEvent = erpUserHandler.handleEvent(pushAuditedOrderEvent);
                            if (firstSyncEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                successCount++;
                            } else {
                                failedCount++;
                            }
                        }

                        // next pull
                        int totalPage = totalCount / WangDianV2Constant.PAGE_SIZE;
                        if (totalCount % WangDianV2Constant.PAGE_SIZE != 0) {
                            totalPage++;
                        }

                        if (totalPage > 1) {
                            currentPageIndex++;
                            //取下几笔数据
                            for (int i = currentPageIndex; i <= totalPage; i++) {
                                wangDianV2OrderSearch.setPageNo(i);
                                EventResult nextQueryEvent = wangDianV2OrderHandler.queryOrder(wangDianV2OrderSearch, sysData);
                                if (nextQueryEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                    JSONObject nextQueryResp = (JSONObject) firstQueryEvent.getData();
                                    JSONArray nextOrderArray = nextQueryResp.getJSONArray("trades");

                                    orderIds = convert2AuditedOrderList(nextOrderArray, sysData);
                                    if (orderIds.size() > 0) {
                                        auditedOrders.addAll(orderIds);
                                        pushAuditedOrderEvent.setOrderIds(orderIds);
                                        // push to platform
                                        EventResult nextSyncEvent = erpUserHandler.handleEvent(pushAuditedOrderEvent);
                                        if (nextSyncEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                            successCount++;
                                        } else {
                                            failedCount++;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (totalCount > 0) {
                        //发货同步记录
                        AuditedOrderSyncLog auditedOrderSyncLog = new AuditedOrderSyncLog();

                        auditedOrderSyncLog.setProviderType(erpInfo.getErpType());
                        auditedOrderSyncLog.setUserType(erpUserInfo.getErpUserType());
                        auditedOrderSyncLog.setCustomerId(erpUserInfo.getCustomerId());
                        auditedOrderSyncLog.setTotalCount(totalCount);
                        auditedOrderSyncLog.setSyncTime(now);
                        auditedOrderSyncLog.setOrderJson(JSON.toJSONString(auditedOrders));
                        auditedOrderSyncLog.setErpSysData(JSON.toJSONString(sysData));
                        if (successCount == 0) {
                            auditedOrderSyncLog.setAuditedSyncStatus(OrderSyncStatus.AuditedSyncStatus.SYNC_FAILURE);
                        }
                        if (successCount > 0 && failedCount > 0) {
                            auditedOrderSyncLog.setAuditedSyncStatus(OrderSyncStatus.AuditedSyncStatus.SYNC_PARTY_SUCCESS);
                        }
                        if (successCount > 0 && failedCount == 0) {
                            auditedOrderSyncLog.setAuditedSyncStatus(OrderSyncStatus.AuditedSyncStatus.SYNC_SUCCESS);
                        }
                        auditedOrderSyncLogService.save(auditedOrderSyncLog);
                    }

                    log.info("wangdianv2 audited order sync end");
                } catch (Exception e) {
                    log.info("audited order sync exception caused by " + e.getMessage());
                }
            }
        }
    }
}
