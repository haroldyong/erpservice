/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.common.EDBEnum;
import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpprovider.edb.search.EDBOrderSearch;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.common.ienum.EnumHelperV2;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderShipSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.ShipSyncDeliverInfo;
import com.huobanplus.erpservice.datacenter.model.BatchDeliverResult;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
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
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by allan on 12/24/15.
 */
@Service
public class EDBScheduledService {
    private static final Log log = LogFactory.getLog(EDBScheduledService.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ShipSyncDeliverInfoService shipSyncDeliverInfoService;
    @Autowired
    private ERPRegister erpRegister;
    @Autowired
    private EDBOrderHandler edbOrderHandler;

    /**
     * 同步订单发货状态轮训服务
     */
//    @Scheduled(cron = "0 0 */2 * * ?")
//    public void syncOrderShip() {
//
//        Date now = new Date();
//        log.info("E店宝获取订单开始:" + StringUtil.DateFormat(now, StringUtil.TIME_PATTERN));
//        //得到所有配置过edb信息的商家,准备获取数据
//        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.EDB);
//        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
//            log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "开始获取订单数据进行同步");
//            int currentPageIndex = 1;
//            EDBSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), EDBSysData.class);
//
//            EDBOrderSearch edbOrderSearch = new EDBOrderSearch();
//            edbOrderSearch.setBeginTime(sysData.getBeginTime());
//            edbOrderSearch.setEndTime(StringUtil.DateFormat(now, StringUtil.TIME_PATTERN));
//            edbOrderSearch.setShipStatus(EDBEnum.ShipStatusEnum.ALL_DELIVER);
//            edbOrderSearch.setPlatformStatus(EDBEnum.PlatformStatus.PAYED);
//            edbOrderSearch.setProceStatus(EDBEnum.OrderStatusEnum.ACTIVE);
//
//            //第一次获取订单
//            EventResult eventResult = edbOrderHandler.obtainOrderList(currentPageIndex, sysData, edbOrderSearch);
//
//            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
//                JSONObject jsonObject = (JSONObject) eventResult.getData();
//                JSONArray resultOrders = jsonObject.getJSONObject("items").getJSONArray("item");
//                log.info("本次获取" + resultOrders.size() + "条订单数据");
//                if (resultOrders.size() > 0) {
//                    int totalResult = resultOrders.getJSONObject(0).getIntValue("总数量");//本次获取的总数据量
//                    int successCount = 0;//成功走完流程的数量
//
//                    //推送给相应的erp使用商户
//                    List<Order> orders = getLogiInfo(resultOrders);
//                    ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
//                    //得到相应使用者处理器
//                    ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
//
//                    PushOrderListInfoEvent pushOrderListInfoEvent = new PushOrderListInfoEvent(JSON.toJSONString(orders));
//                    //处理事件,此处为推送订单列表信息到使用者
//                    EventResult firstPushResult = erpUserHandler.handleEvent(pushOrderListInfoEvent);
//
//                    if (firstPushResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
//                        List<Order> successList = JSON.parseArray(String.valueOf(firstPushResult.getData()), Order.class);
//
//                        int totalPage = totalResult / EDBConstant.PAGE_SIZE;
//                        if (totalResult % EDBConstant.PAGE_SIZE != 0) {
//                            totalPage++;
//                        }
//                        if (totalPage > 1) {
//                            currentPageIndex++;
//                            //取下一笔数据
//                            for (int i = currentPageIndex; i <= totalPage; i++) {
//                                EventResult nextResult = edbOrderHandler.obtainOrderList(currentPageIndex, sysData, edbOrderSearch);
//                                if (nextResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
//                                    JSONObject nextJsonObject = (JSONObject) nextResult.getData();
//                                    JSONArray nextJsonArray = nextJsonObject.getJSONObject("items").getJSONArray("item");
//                                    //推送给erp使用商户
//                                    List<Order> nextOrders = getLogiInfo(nextJsonArray);
//                                    pushOrderListInfoEvent = new PushOrderListInfoEvent(JSON.toJSONString(nextOrders));
//                                    EventResult nextPushResult = erpUserHandler.handleEvent(pushOrderListInfoEvent);
//                                    if (nextPushResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
//                                        successList.addAll(JSON.parseArray(String.valueOf(nextPushResult.getData()), Order.class));
//                                    }
//
//                                }
//                            }
//                        }
//                        //回写EDB,修改EDB的外部平台订单状态
//                        PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
//                        pushNewOrderEvent.setEventType(EventType.DELIVERY_SYNC);
//                        ERPInfo erpInfo = new ERPInfo(ERPTypeEnum.ProviderType.EDB, detailConfig.getErpSysData());
//                        pushNewOrderEvent.setErpInfo(erpInfo);
//                        pushNewOrderEvent.setErpUserInfo(erpUserInfo);
//                        for (Order successOrder : successList) {
//                            successOrder.setPayStatus(EDBEnum.PayStatusEnum.ALL_DELIVER.getCode());
//                            pushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(successOrder));
//                            EventResult rewriteResult = edbOrderHandler.pushOrder(pushNewOrderEvent);
//                            if (rewriteResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
//                                successCount++;
//                            }
//                        }
//                    }
//                    //存入轮训记录表
//                    OrderScheduledLog orderScheduledLog = new OrderScheduledLog();
//                    orderScheduledLog.setCustomerId(detailConfig.getCustomerId());
//                    orderScheduledLog.setNum(totalResult);
//                    orderScheduledLog.setSuccessNum(successCount);
//                    orderScheduledLog.setCreateTime(now);
//                    scheduledLogService.save(orderScheduledLog);
//                }
//            }
//        }
//
//    }

    /**
     * 同步发货状态,根据发货时间进行轮询同步
     * <p>
     * 1.如果第一次是第一次同步,以当前时间的前一天发货时间的开始时间
     * 2.如果同步过,则以上次同步记录的时间为开始时间
     * <p>
     * 结束时间均为同步开始时间
     * 每个一小时进行一次同步
     */
//    @Scheduled(cron = "0 0 */1 * * ?")
    @Scheduled(cron = "0 */20 * * * ?")
    @Transactional
    public void syncOrderShip() {
        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        log.info("order ship sync for edb start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.EDB);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {

            if (detailConfig.getErpBaseConfig().getIsSyncDelivery() == 1) {
                log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "start to sync order ship");
                try {
                    ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                    ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                    EDBSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), EDBSysData.class);
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

                    EDBOrderSearch edbOrderSearch = new EDBOrderSearch();
                    edbOrderSearch.setDateType(EDBEnum.DateType.DELIVER_TIME.getDateType());
                    edbOrderSearch.setBeginTime(StringUtil.DateFormat(beginTime, StringUtil.TIME_PATTERN));
                    edbOrderSearch.setEndTime(nowStr);
                    edbOrderSearch.setPageIndex(currentPageIndex);
                    edbOrderSearch.setPageSize(EDBConstant.PAGE_SIZE);
                    edbOrderSearch.setStorageId(sysData.getStorageId());
                    edbOrderSearch.setShopId(sysData.getShopId());
                    edbOrderSearch.setPayStatus(EDBEnum.PayStatusEnum.ALL_PAYED);
                    edbOrderSearch.setShipStatus(EDBEnum.ShipStatusEnum.ALL_DELIVER);
//            edbOrderSearch.setOrderId("2016050388984135");

                    //first pull
                    EventResult eventResult = edbOrderHandler.obtainOrderList(sysData, edbOrderSearch);
                    if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                        JSONObject result = (JSONObject) eventResult.getData();
                        JSONArray resultArray = result.getJSONObject("items").getJSONArray("item");
                        if (resultArray.size() > 0) {
                            int totalResult = resultArray.getJSONObject(0).getIntValue("总数量");//本次获取的总数据量
                            List<OrderDeliveryInfo> first = orderDeliveryInfoList(resultArray); //首次需要推送的物流信息
                            totalCount += first.size();

                            BatchDeliverEvent batchDeliverEvent = new BatchDeliverEvent();
                            batchDeliverEvent.setErpUserInfo(erpUserInfo);
                            batchDeliverEvent.setErpInfo(erpInfo);
                            batchDeliverEvent.setOrderDeliveryInfoList(first);
                            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                            EventResult firstSyncResult = erpUserHandler.handleEvent(batchDeliverEvent);
                            log.info("fistsyncresult-->" + firstSyncResult.getResultCode() + "--->" + firstSyncResult.getResultMsg());
                            if (firstSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                log.info("firstSyncResult success");
                                BatchDeliverResult firstBatchDeliverResult = (BatchDeliverResult) firstSyncResult.getData();
                                log.info("is this place error?");
                                failedOrders.addAll(firstBatchDeliverResult.getFailedOrders());
                                successOrders.addAll(firstBatchDeliverResult.getSuccessOrders());
                            }

                            //next pull
                            int totalPage = totalResult / EDBConstant.PAGE_SIZE;
                            if (totalResult % EDBConstant.PAGE_SIZE != 0) {
                                totalPage++;
                            }
                            if (totalPage > 1) {
                                currentPageIndex++;
                                //取下几笔数据
                                for (int i = currentPageIndex; i <= totalPage; i++) {
                                    edbOrderSearch.setPageIndex(i);
                                    EventResult nextEventResult = edbOrderHandler.obtainOrderList(sysData, edbOrderSearch);
                                    if (nextEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                        JSONObject nextResult = (JSONObject) nextEventResult.getData();
                                        JSONArray nextResultArray = nextResult.getJSONObject("items").getJSONArray("item");
                                        List<OrderDeliveryInfo> next = orderDeliveryInfoList(nextResultArray); //下几次需要同步的物流信息
                                        batchDeliverEvent.setOrderDeliveryInfoList(next);

                                        totalCount += next.size();

                                        EventResult nextSyncResult = erpUserHandler.handleEvent(batchDeliverEvent); //使用者同步

                                        if (nextSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                            BatchDeliverResult nextBatchDeliverResult = (BatchDeliverResult) nextSyncResult.getData();
                                            failedOrders.addAll(nextBatchDeliverResult.getFailedOrders());
                                            successOrders.addAll(nextBatchDeliverResult.getSuccessOrders());
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
                } catch (Exception e) {
                    log.error(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "发生错误", e);
                }
            }
        }
        log.info("edb ship sync end");
    }

    public List<OrderDeliveryInfo> orderDeliveryInfoList(JSONArray resultArray) {
        List<OrderDeliveryInfo> orderDeliveryInfoList = new ArrayList<>();
        for (Object o : resultArray) {
            JSONObject orderInfoJson = (JSONObject) o;
            JSONArray orderItemJsonArray = orderInfoJson.getJSONArray("tid_item");

            //E店宝会将两笔相同信息的订单合并成一笔订单,所以需要进行一次拆分
            List<String> splitOrderIdList = new ArrayList<>(); //已分配的订单号
            List<OrderDeliveryInfo> splitOrderDelivers = new ArrayList<>(); //已拆分的订单物流信息
            for (Object itemObj : orderItemJsonArray) {
                JSONObject orderItemJson = (JSONObject) itemObj;

                String originOrderId = orderItemJson.getString("out_tid"); //原始订单号
                String productBn = orderItemJson.getString("barcode"); //货号
                int proNum = orderItemJson.getInteger("pro_num"); //货品数量

                if (splitOrderIdList.indexOf(originOrderId) == -1) {
                    //未分配的订单
                    OrderDeliveryInfo orderDeliveryInfo = new OrderDeliveryInfo();
                    orderDeliveryInfo.setOrderId(originOrderId);
                    orderDeliveryInfo.setLogiName(orderInfoJson.getString("express"));
                    orderDeliveryInfo.setLogiNo(orderInfoJson.getString("express_no"));
                    String edbLogiCode = orderInfoJson.getString("express_coding");
                    String logiCode = (String) EnumHelperV2.getEnumName(EDBEnum.ExpressCompanyCode.class, edbLogiCode);
                    if (StringUtils.isEmpty(logiCode)) {
                        logiCode = edbLogiCode;
                    }
                    orderDeliveryInfo.setLogiCode(logiCode);
                    orderDeliveryInfo.setDeliverItemsStr(productBn + "," + proNum);
                    splitOrderDelivers.add(orderDeliveryInfo);
                    splitOrderIdList.add(originOrderId);//加入到已分配订单号列表中
                } else {
                    //已分配的订单
                    OrderDeliveryInfo orderDeliveryInfo = splitOrderDelivers.stream().filter(p -> p.getOrderId().equals(originOrderId)).findFirst().get();
                    orderDeliveryInfo.setDeliverItemsStr(orderDeliveryInfo.getDeliverItemsStr() + "|" + productBn + "," + proNum);
                }
            }
            orderDeliveryInfoList.addAll(splitOrderDelivers);
        }
        return orderDeliveryInfoList;
    }

    /**
     * 用于发货
     *
     * @param resultOrders
     * @return
     */
    private List<Order> getLogiInfo(JSONArray resultOrders) {
        //推送给erp使用商户
        List<Order> orders = new ArrayList<>();
        for (Object object : resultOrders) {
            JSONObject jo = (JSONObject) object;
            Order order = new Order();
            order.setOrderId(jo.getString("out_tid"));
            order.setLogiName(jo.getString("express"));
            order.setLogiNo(jo.getString("express_no"));
            order.setLogiCode(jo.getString("express_coding"));

            List<OrderItem> orderItems = new ArrayList<>();
            JSONArray jsonOrderItem = jo.getJSONArray("tid_item");
            for (Object ob : jsonOrderItem) {
                JSONObject joItem = (JSONObject) ob;
                OrderItem orderItem = new OrderItem();
                orderItem.setProductBn(joItem.getString("barcode"));
                String sendNum = joItem.getString("send_num");
                if (!StringUtils.isEmpty(sendNum)) {
                    orderItem.setSendNum(Integer.parseInt(sendNum));
                }
                orderItem.setSendNum(1);
                String refundNum = joItem.getString("refund_num");
                if (!StringUtils.isEmpty(refundNum)) {
                    orderItem.setRefundNum(Integer.parseInt(refundNum));
                }
                orderItems.add(orderItem);
            }
            order.setOrderItems(orderItems);
            orders.add(order);
        }
        return orders;
    }
}
