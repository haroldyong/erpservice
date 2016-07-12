package com.huobanplus.erpprovider.dtw.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpprovider.dtw.handler.DtwOrderHandler;
import com.huobanplus.erpprovider.dtw.search.DtwStockSearch;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderShipSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.ShipSyncDeliverInfo;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.OrderShipSyncLogService;
import com.huobanplus.erpservice.datacenter.service.logs.ShipSyncDeliverInfoService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2016-07-12.
 */
public class DtwScheduledService {

    private static final Log log = LogFactory.getLog(DtwScheduledService.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ShipSyncDeliverInfoService shipSyncDeliverInfoService;

    @Autowired
    private ERPRegister erpRegister;

    private DtwOrderHandler dtwOrderHandler;

    @Scheduled(cron = "0 */1 * * * ? ")
    @Transactional
    public synchronized void syncOrderShip() {
        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        log.info("order ship sync for GY start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.GY);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "start to sync order ship");
            try {
                ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                DtwSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), DtwSysData.class);

                //是否是第一次同步,第一次同步beginTime则为当前时间的前一天
                OrderShipSyncLog lastSyncLog = orderShipSyncLogService.findTop(erpUserInfo.getCustomerId(), ERPTypeEnum.ProviderType.EDB);
                Date beginTime = lastSyncLog == null
                        ? Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusDays(1))
                        : lastSyncLog.getSyncTime();

                List<OrderDeliveryInfo> failedOrders = new ArrayList<>(); //失败的订单列表
                List<OrderDeliveryInfo> successOrders = new ArrayList<>(); //成功的订单列表
                int totalCount = 0; //总数量

                DtwStockSearch stockSearch = new DtwStockSearch();
                stockSearch.setECommerceCode(sysData.getECommerceCode());
                stockSearch.setECommerceName(sysData.getECommerceName());
                stockSearch.setPartNo("");
                stockSearch.setPassKey(sysData.getPassKey());

                // 第一次同步
//                EventResult eventResult = dtwOrderHandler.stockQuery(stockSearch, sysData);
//                List<OrderDeliveryInfo> first = new ArrayList<>();
//                if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
//                    JSONObject result = (JSONObject) eventResult.getData();
//                    JSONArray deliveryArray = result.getJSONArray("deliverys");
//                    totalCount = result.getInteger("total");
//
//                    first = changeToSyncOrder(deliveryArray);
//                }





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
