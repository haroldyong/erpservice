package com.huobanplus.erpprovider.gy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.gy.common.GYConstant;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.handler.GYBaseHandler;
import com.huobanplus.erpprovider.gy.handler.GYOrderHandler;
import com.huobanplus.erpprovider.gy.search.GYDeliveryOrderSearch;
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
 * Created by elvis on 2016/5/31.
 */
@Service
public class GYSyncDelivery extends GYBaseHandler {

    private static final Log log = LogFactory.getLog(GYSyncDelivery.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ShipSyncDeliverInfoService shipSyncDeliverInfoService;

    @Autowired
    private ERPRegister erpRegister;

    @Autowired
    private GYOrderHandler gyOrderHandler;


    @Scheduled(cron = "0 0/1 * * * ? ")
    @Transactional
    public void syncOrderShip() {
        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        log.info("order ship sync for GY start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.GY);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "start to sync order ship");
            try {
                ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                GYSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), GYSysData.class);

                //是否是第一次同步,第一次同步beginTime则为当前时间的前一天
                OrderShipSyncLog lastSyncLog = orderShipSyncLogService.findTop(erpUserInfo.getCustomerId(), ERPTypeEnum.ProviderType.EDB);
                Date beginTime = lastSyncLog == null
                        ? Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusDays(1))
                        : lastSyncLog.getSyncTime();

                List<OrderDeliveryInfo> failedOrders = new ArrayList<>(); //失败的订单列表
                List<OrderDeliveryInfo> successOrders = new ArrayList<>(); //成功的订单列表
                int totalCount = 0; //总数量
                boolean syncFlag = true;

                GYDeliveryOrderSearch orderSearch = new GYDeliveryOrderSearch();
                orderSearch.setPageSize(GYConstant.PAGE_SIZE);
                orderSearch.setStartDeliveryDate(StringUtil.DateFormat(beginTime,StringUtil.TIME_PATTERN));
                orderSearch.setEndDeliveryDate(nowStr);
                orderSearch.setShopCode(sysData.getShopCode());// FIXME: 2016/6/22 eg:ruyi
                orderSearch.setDelivery(1);// 发货完成

                // 第一次同步
                EventResult eventResult = gyOrderHandler.deliveryOrderQuery(orderSearch, sysData);
                List<OrderDeliveryInfo> first = new ArrayList<>();
                if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                    JSONObject result = (JSONObject) eventResult.getData();
                    JSONArray deliveryArray = result.getJSONArray("deliverys");
                    totalCount = result.getInteger("total");

                    first = changeToSyncOrder(deliveryArray);
                }
                if(totalCount == 0){
                    syncFlag = false;
                }

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
                } else{
                    failedOrders.addAll(first);
                }

                int totalPage = 0;
                // 后续几页同步
                totalPage = totalCount / GYConstant.PAGE_SIZE;
                if(totalCount%GYConstant.PAGE_SIZE != 0){
                    totalPage++;
                }
                if (totalPage > 1) {
                    for (int index = 2; index <= totalPage; index++) {
                        orderSearch.setPageNo(index);
                        EventResult nextEventResult = gyOrderHandler.deliveryOrderQuery(orderSearch, sysData);
                        List<OrderDeliveryInfo> next = new ArrayList<>();
                        if (nextEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                            JSONObject result = (JSONObject) nextEventResult.getData();
                            JSONArray deliveryArray = result.getJSONArray("deliverys");
                            next = changeToSyncOrder(deliveryArray);
                        }
                        batchDeliverEvent.setOrderDeliveryInfoList(next);
                        EventResult nextSyncResult = erpUserHandler.handleEvent(batchDeliverEvent);
                        if (nextSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                            BatchDeliverResult nextBatchDeliverResult = (BatchDeliverResult) nextSyncResult.getData();
                            failedOrders.addAll(nextBatchDeliverResult.getFailedOrders());
                            successOrders.addAll(nextBatchDeliverResult.getSuccessOrders());
                        } else{
                            failedOrders.addAll(next);
                        }
                    }
                }
                if(syncFlag){// 轮询若无数据，则不记录日志
                    syncLog(failedOrders,successOrders,totalCount,erpUserInfo,erpInfo);
                }

            } catch (Exception e) {
                log.error(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "发生错误", e);
            }
            log.info("GY ship sync end");
        }
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

    /**
     * 将从管易拿到的 发货单信息转换成erpUser需要的订单推送信息
     *
     * @param deliverys
     * @return
     */
    public List<OrderDeliveryInfo> changeToSyncOrder(JSONArray deliverys) {

        List<OrderDeliveryInfo> orderDeliveryInfoList = new ArrayList<>();

        deliverys.forEach(delivery -> {
            JSONObject jsonObject = (JSONObject) delivery;
            JSONObject deliveryStatusInfo = jsonObject.getJSONObject("delivery_statusInfo");
            int deliveryStatus = deliveryStatusInfo.getInteger("delivery");
            if (deliveryStatus == 1 || deliveryStatus == 2) {// 发货中 发货成功
                OrderDeliveryInfo orderDeliveryInfo = new OrderDeliveryInfo();
                orderDeliveryInfo.setLogiName(jsonObject.getString("express_name"));
                orderDeliveryInfo.setOrderId(jsonObject.getString("platform_code"));
                orderDeliveryInfo.setLogiNo(jsonObject.getString("express_no"));
                // 序列化商品明细
                JSONArray detailsArray = jsonObject.getJSONArray("details");
                String itemStr = "";
                for (Object item : detailsArray) {
                    JSONObject obj = (JSONObject) item;
                    String productBn = obj.getString("sku_code");
                    int qty = obj.getInteger("qty");
                    itemStr += productBn + "," + qty + "|";
                }
                orderDeliveryInfo.setDeliverItemsStr(itemStr);
                orderDeliveryInfo.setFreight(jsonObject.getDouble("post_fee"));
                orderDeliveryInfo.setRemark(jsonObject.getString("seller_memo"));
                orderDeliveryInfo.setLogiCode(jsonObject.getString("express_code"));
                orderDeliveryInfoList.add(orderDeliveryInfo);
            }
        });

        return orderDeliveryInfoList;
    }

}
