package com.huobanplus.erpprovider.gy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.gy.common.GYConstant;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.formatgy.GetDeliveryBean;
import com.huobanplus.erpprovider.gy.handler.GYBaseHandler;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
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
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by elvis on 2016/5/31.
 */
public class GYSyncDelivery extends GYBaseHandler{

    private static final Log log = LogFactory.getLog(GYSyncDelivery.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ShipSyncDeliverInfoService shipSyncDeliverInfoService;

    @Autowired
    private ERPRegister erpRegister;


//    @Scheduled(cron = "0 0 */1 * * ?")
    @Transactional
    public void syncOrderShip() {
        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        log.info("order ship sync for edb start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.GY);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "start to sync order ship");
            try {
                ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                GYSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), GYSysData.class);

                //当前索引
                int currentPageIndex = 1;
                //是否是第一次同步,第一次同步beginTime则为当前时间的前一天
                OrderShipSyncLog lastSyncLog = orderShipSyncLogService.findTop(erpUserInfo.getCustomerId(), ERPTypeEnum.ProviderType.EDB);
                Date beginTime = lastSyncLog == null
                        ? Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusDays(1))
                        : lastSyncLog.getSyncTime();

                List<OrderDeliveryInfo> failedOrders = new ArrayList<>(); //失败的订单列表
                List<OrderDeliveryInfo> successOrders = new ArrayList<>(); //成功的订单列表
                int totalCount = 0; //总数量

                GetDeliveryBean orderSearch = new GetDeliveryBean();

                boolean flag = true;//用于控制循环
                int numbers = 0;
                //循环查询
                while (flag) {

                    numbers++;//查询次数自增

                    orderSearch.setPageNo(numbers+"");
                    orderSearch.setPageSize(GYConstant.PAGE_SIZE + "");
                    Map<String, Object> requestData = GYBaseHandler.getRequestData(sysData, orderSearch,"gy.erp.trade.deliverys.get");//// FIXME: 2016/5/31 方法名gy.erp.trade.deliverys.get
                    HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getURL(), requestData);
                    if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                        JSONObject resultJson = JSON.parseObject(httpResult.getHttpContent());
                        if (Integer.parseInt(resultJson.get("total").toString()) <= numbers * GYConstant.PAGE_SIZE) {
                            flag = false;
                        }
                        totalCount = Integer.parseInt(resultJson.get("total").toString());

                        //将查出的一页的信息，同步到系统
                        JSONArray deliverys = resultJson.getJSONArray("deliverys");

                        List<OrderDeliveryInfo> orderDeliveryList = changeToSyncOrder(deliverys); //需要推送的物流信息

                        BatchDeliverEvent batchDeliverEvent = new BatchDeliverEvent();
                        batchDeliverEvent.setErpUserInfo(erpUserInfo);
                        batchDeliverEvent.setErpInfo(erpInfo);
                        batchDeliverEvent.setOrderDeliveryInfoList(orderDeliveryList);
                        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                        EventResult result = erpUserHandler.handleEvent(batchDeliverEvent);
                        if (result.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                            //保存成功和失败
                            BatchDeliverResult batchDeliverResult = (BatchDeliverResult) result.getData();
                            failedOrders.addAll(batchDeliverResult.getFailedOrders());
                            successOrders.addAll(batchDeliverResult.getSuccessOrders());
                        }else{
                            log.info("sync GY orderdelivery but erpUser have a failed!");
                        }
                    } else {
                        flag = false;
                        log.info("sync GY orderdelivery but request erpUser failed!");
                    }
                }
                //记录日志
                this.syncLog(failedOrders,successOrders,totalCount,erpUserInfo,erpInfo);

            } catch (Exception e) {
                log.error(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "发生错误", e);
            }
            log.info("edb ship sync end");
        }
    }

    /**
     * 记录日志
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
     * @param deliverys
     * @return
     */
    public List<OrderDeliveryInfo> changeToSyncOrder(JSONArray deliverys){

        List<OrderDeliveryInfo> orderDeliveryInfoList = new ArrayList<>();
        for (Object o : deliverys) {
            JSONObject orderInfoJson = (JSONObject) o;


        }

        return orderDeliveryInfoList;
    }

}
