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

package com.huobanplus.erpprovider.baison.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.baison.common.BaisonConstant;
import com.huobanplus.erpprovider.baison.common.BaisonSysData;
import com.huobanplus.erpprovider.baison.formatdata.BaisonPage;
import com.huobanplus.erpprovider.baison.formatdata.BaisonQueryOrder;
import com.huobanplus.erpprovider.baison.formatdata.BaisonQueryOrderItem;
import com.huobanplus.erpprovider.baison.handler.BaisonOrderHandler;
import com.huobanplus.erpprovider.baison.search.BaisonOrderSearch;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2016-12-26.
 */
@Service
public class BaisonDeliveryScheduleService {

    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ShipSyncDeliverInfoService shipSyncDeliverInfoService;
    @Autowired
    private ERPDetailConfigService erpDetailConfigService;
    @Autowired
    private BaisonOrderHandler baisonOrderHandler;
    @Autowired
    private ERPRegister erpRegister;

    public void syncOrderShip() {

        Date now = new Date();
        List<ERPDetailConfigEntity> detailConfigEntityList = erpDetailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.BAISONE3);
        for (ERPDetailConfigEntity erpDetailConfigEntity : detailConfigEntityList) {
            BaisonSysData sysData = JSON.parseObject(erpDetailConfigEntity.getErpSysData(), BaisonSysData.class);
            int customerId = erpDetailConfigEntity.getCustomerId();
            OrderShipSyncLog lastSyncLog = orderShipSyncLogService.findTop(customerId, ERPTypeEnum.ProviderType.BAISONE3);
            Date beginTime = lastSyncLog == null
                    ? Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusDays(2))
                    : lastSyncLog.getSyncTime();

            List<OrderDeliveryInfo> successOrders = new ArrayList<>();
            List<OrderDeliveryInfo> failedOrders = new ArrayList<>();
            int totalCount = 0;
            int totalPage = 0;

            BaisonOrderSearch baisonOrderSearch = new BaisonOrderSearch();
            baisonOrderSearch.setSdCode(sysData.getBaisonShopCode());
            baisonOrderSearch.setStartModified("2015-12-30 08:00:00");
            baisonOrderSearch.setEndModified(StringUtil.DateFormat(now, StringUtil.TIME_PATTERN));
            baisonOrderSearch.setPageNo(1);
            baisonOrderSearch.setPageSize(BaisonConstant.PAGE_SIZE);

            ERPInfo erpInfo = new ERPInfo(erpDetailConfigEntity.getErpType(), erpDetailConfigEntity.getErpSysData());
            ERPUserInfo erpUserInfo = new ERPUserInfo(erpDetailConfigEntity.getErpUserType(), customerId);

            BatchDeliverEvent batchDeliverEvent = new BatchDeliverEvent();
            batchDeliverEvent.setErpUserInfo(erpUserInfo);
            batchDeliverEvent.setErpInfo(erpInfo);

            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);

            // 第一次查询& 推送
            EventResult firstQueryEvent = baisonOrderHandler.orderQuery(baisonOrderSearch, sysData);
            if (firstQueryEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                // 获取查询结果data
                JSONObject jsonData = (JSONObject) firstQueryEvent.getData();// 约定接口返回的数据是json对象的数据

                BaisonPage baisonPage = jsonData.getObject("page", BaisonPage.class);
                totalCount = baisonPage.getTotalResult();
                totalPage = baisonPage.getPageTotal();

                JSONArray orderJsonArray = jsonData.getJSONArray("orderListGets");
                List<OrderDeliveryInfo> tempToPush = convert2OrderDeliveryInfo(orderJsonArray);
                if (tempToPush.size() > 0) {
                    // 第一次推送
                    batchDeliverEvent.setOrderDeliveryInfoList(tempToPush);// TODO: 2016-12-26
                    EventResult firstSyncEvent = erpUserHandler.handleEvent(batchDeliverEvent);
                    if (firstSyncEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                        BatchDeliverResult batchDeliverResult = (BatchDeliverResult) firstSyncEvent.getData();
                        successOrders.addAll(batchDeliverResult.getSuccessOrders());
                        failedOrders.addAll(batchDeliverResult.getFailedOrders());
                    } else {
                        failedOrders.addAll(tempToPush);
                    }
                }


            }

            // 后几次查询& 推送
            for (int index = 2; index <= totalPage; index++) {
                baisonOrderSearch.setPageNo(index);
                EventResult nextQueryEvent = baisonOrderHandler.orderQuery(baisonOrderSearch, sysData);
                if (nextQueryEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {

                    JSONObject jsonData = (JSONObject) nextQueryEvent.getData();// 约定接口返回的数据是json对象的数据
                    JSONArray orderJsonArray = jsonData.getJSONArray("orderListGets");
                    List<OrderDeliveryInfo> tempToPush = convert2OrderDeliveryInfo(orderJsonArray);

                    if (tempToPush.size() > 0) {
                        // 后续几次推送
                        batchDeliverEvent.setOrderDeliveryInfoList(tempToPush);
                        EventResult nextSyncEvent = erpUserHandler.handleEvent(batchDeliverEvent);
                        if (nextSyncEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                            BatchDeliverResult batchDeliverResult = (BatchDeliverResult) nextSyncEvent.getData();
                            successOrders.addAll(batchDeliverResult.getSuccessOrders());
                            failedOrders.addAll(batchDeliverResult.getFailedOrders());
                        } else {
                            failedOrders.addAll(tempToPush);
                        }
                    }
                }
            }

        }
    }

    private List<OrderDeliveryInfo> convert2OrderDeliveryInfo(JSONArray baisonOrderArray) {

        List<OrderDeliveryInfo> orderDeliveryInfoList = new ArrayList<>();

        for (Object o : baisonOrderArray) {
            JSONObject obj = JSON.parseObject(o.toString());
            BaisonQueryOrder baisonQueryOrder = obj.getObject("orderListGet", BaisonQueryOrder.class);
//            System.out.println("\n-----------------Data----------------");
//            System.out.println(baisonQueryOrder);
//            System.out.println("\n-----------------Data----------------");

            if (baisonQueryOrder.getShippingStatus().equals("7")) {// 已发货

                OrderDeliveryInfo orderDeliveryInfo = new OrderDeliveryInfo();
                orderDeliveryInfo.setOrderId(baisonQueryOrder.getOrderSn());
                orderDeliveryInfo.setLogiName(baisonQueryOrder.getShippingName());
                orderDeliveryInfo.setLogiNo(baisonQueryOrder.getShippingSn());
                orderDeliveryInfo.setLogiCode(baisonQueryOrder.getShippingCode());
                orderDeliveryInfo.setFreight(baisonQueryOrder.getShippingFee());

                StringBuilder deliverItemStr = new StringBuilder();

                for (JSONObject jsonObject : baisonQueryOrder.getOrderDetails()) {
                    BaisonQueryOrderItem baisonQueryOrderItem = jsonObject.getObject("orderDetailGet", BaisonQueryOrderItem.class);
                    deliverItemStr.append(baisonQueryOrderItem.getGoods_sn())
                            .append(",")
                            .append(baisonQueryOrderItem.getGoods_number())
                            .append("|");
//                    System.out.println("\n************Detail**************");
//                    System.out.println(baisonQueryOrderItem);
//                    System.out.println("\n************Detail**************");
                }
                orderDeliveryInfo.setDeliverItemsStr(deliverItemStr.toString());
                orderDeliveryInfoList.add(orderDeliveryInfo);

            }
        }
        return orderDeliveryInfoList;
    }

    /**
     * 记录日志
     *
     * @param totalCount
     * @param erpUserInfo
     * @param erpInfo
     */
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

        List<ShipSyncDeliverInfo> shipSyncDeliverInfoList = new ArrayList<>();

        shipSyncDeliverInfoService.shipSyncDeliverInfoList(shipSyncDeliverInfoList, failedOrders, orderShipSyncLog, OrderSyncStatus.ShipSyncStatus.SYNC_FAILURE);
        shipSyncDeliverInfoService.shipSyncDeliverInfoList(shipSyncDeliverInfoList, successOrders, orderShipSyncLog, OrderSyncStatus.ShipSyncStatus.SYNC_SUCCESS);
        shipSyncDeliverInfoService.batchSave(shipSyncDeliverInfoList);
    }

}
