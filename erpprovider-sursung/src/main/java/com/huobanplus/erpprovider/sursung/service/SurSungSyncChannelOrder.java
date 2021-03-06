/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.sursung.common.SurSungConstant;
import com.huobanplus.erpprovider.sursung.common.SurSungEnum;
import com.huobanplus.erpprovider.sursung.common.SurSungSysData;
import com.huobanplus.erpprovider.sursung.formatdata.SurSungOrder;
import com.huobanplus.erpprovider.sursung.formatdata.SurSungOrderItem;
import com.huobanplus.erpprovider.sursung.handler.SurSungOrderHandler;
import com.huobanplus.erpprovider.sursung.search.SurSungOrderSearch;
import com.huobanplus.erpprovider.sursung.search.SurSungOrderSearchResult;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil2;
import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.logs.ChannelOrderSyncInfo;
import com.huobanplus.erpservice.datacenter.entity.logs.ChannelOrderSyncLog;
import com.huobanplus.erpservice.datacenter.model.BatchPushOrderResult;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.repository.logs.ChannelOrderSyncLogRepository;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.ChannelOrderSyncInfoService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.sync.SyncChannelOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2016-09-16.
 * 同步不同渠道的订单到平台
 */
@Component
public class SurSungSyncChannelOrder {

    private static final Log log = LogFactory.getLog(SurSungSyncChannelOrder.class);

    // for test use
    private static final String[] productBns = {
            "BN-0",
            "BN-1",
            "BN-2",
            "BN-3",
            "BN-4",
            "BN-5",
            "BN-6",
            "BN-7",
            "BN-8",
            "BN-9",
            "BN-10",
            "BN-11",
            "BN-12",
            "BN-13",
            "BN-14",
            "BN-15",
            "BN-16",
            "BN-17",
            "BN-18",
            "BN-19",
    };

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private ChannelOrderSyncLogRepository channelOrderSyncLogRepository;
    @Autowired
    private ERPRegister erpRegister;

    @Autowired
    private SurSungOrderHandler surSungOrderHandler;
    @Autowired
    private ChannelOrderSyncInfoService channelOrderSyncInfoService;

    @Scheduled(cron = "0 0 */3 * * ?")
    @Transactional
    public void syncChannelOrder() {
        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        log.info("channel Order sync for SurSung start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.SURSUNG);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            if (detailConfig.getErpBaseConfig().getIsSyncChannelOrder() == 1) {

                try {
                    ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                    ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                    SurSungSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), SurSungSysData.class);

//                    String[] syncShopIds = sysData.getSyncShopId().split(",");
//                    List<String>


                    //是否是第一次同步,第一次同步beginTime则为当前时间的前一天
                    ChannelOrderSyncLog lastSyncLog = channelOrderSyncLogRepository.findTopByCustomerIdAndProviderTypeOrderByIdDesc(erpUserInfo.getCustomerId(), ERPTypeEnum.ProviderType.SURSUNG);
                    Date beginTime = lastSyncLog == null
                            ? Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusDays(1))
                            : lastSyncLog.getSyncTime();

                    List<Order> failedOrders = new ArrayList<>(); //失败的订单列表
                    int totalCount = 0; //总数量
                    int pageIndex = 1;
                    int lastPageIndex = 1;
                    int totalSyncNum = 0;

                    HttpClientUtil2.getInstance().initHttpClient();


                    SurSungOrderSearch orderSearch = new SurSungOrderSearch();
                    orderSearch.setPageIndex(1);
                    orderSearch.setPageSize(SurSungConstant.PAGE_SIZE);
                    orderSearch.setModifiedBegin(StringUtil.DateFormat(beginTime, StringUtil.TIME_PATTERN));
                    orderSearch.setModifiedEnd(nowStr);

                    // 第一次同步
                    EventResult eventResult = surSungOrderHandler.queryChannelOrder(orderSearch, sysData);
                    ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                    if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                        SurSungOrderSearchResult surSungOrderSearchResult = (SurSungOrderSearchResult) eventResult.getData();
                        totalCount = surSungOrderSearchResult.getDataCount();

                        // 第一次推送
                        SyncChannelOrderEvent syncChannelOrderEvent = new SyncChannelOrderEvent();
                        syncChannelOrderEvent.setErpInfo(erpInfo);
                        syncChannelOrderEvent.setErpUserInfo(erpUserInfo);
                        syncChannelOrderEvent.setOrderList(convert2PlatformOrder(sysData.getShopId(),
                                surSungOrderSearchResult.getOrders()));

                        totalSyncNum += syncChannelOrderEvent.getOrderList().size();

                        // 推送至平台
                        if (syncChannelOrderEvent.getOrderList().size() >= 0) {
                            EventResult firstSyncEvent = erpUserHandler.handleEvent(syncChannelOrderEvent);
                            if (firstSyncEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                BatchPushOrderResult firstBatchPushOrderResult = (BatchPushOrderResult) firstSyncEvent.getData();
                                failedOrders.addAll(firstBatchPushOrderResult.getFailedOrders());
                            } else {
                                failedOrders.addAll(syncChannelOrderEvent.getOrderList());
                                log.info("code:" + firstSyncEvent.getResultCode() + " msg:" + firstSyncEvent.getResultMsg());
                            }
                        }


                        while (surSungOrderSearchResult.isHasNext()) {
                            pageIndex++;
                            lastPageIndex = pageIndex;
                            orderSearch.setPageIndex(pageIndex);
                            EventResult nextEventResult = surSungOrderHandler.queryChannelOrder(orderSearch, sysData);
                            if (nextEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                surSungOrderSearchResult = (SurSungOrderSearchResult) nextEventResult.getData();
                                //后续几次推送
                                syncChannelOrderEvent.setOrderList(convert2PlatformOrder(sysData.getShopId(),
                                        surSungOrderSearchResult.getOrders()));
                                totalSyncNum += syncChannelOrderEvent.getOrderList().size();

                                //推送至平台
                                if (syncChannelOrderEvent.getOrderList().size() > 0) {
                                    EventResult nextSyncEvent = erpUserHandler.handleEvent(syncChannelOrderEvent);
                                    if (nextSyncEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                        BatchPushOrderResult nextBatchPushOrderResult = (BatchPushOrderResult) nextSyncEvent.getData();
                                        failedOrders.addAll(nextBatchPushOrderResult.getFailedOrders());
                                    } else {
                                        failedOrders.addAll(syncChannelOrderEvent.getOrderList());
                                        log.info("code:" + nextEventResult.getResultCode() + " msg:" + nextSyncEvent.getResultMsg());
                                    }
                                }
                            } else {
                                pageIndex = lastPageIndex - 1;
                            }
                        }
                    } else {
                        log.info("code:" + eventResult.getResultCode() + " msg:" + eventResult.getResultMsg());
                    }


                    // 记录日志
                    syncLog(failedOrders, totalSyncNum - failedOrders.size(), totalSyncNum, erpUserInfo, erpInfo);


                } catch (Exception e) {
                    e.printStackTrace();
                    log.info(e.getMessage());
                } finally {
                    try {
                        HttpClientUtil2.getInstance().close();
                    } catch (Exception e) {

                    }
                }
            }
        }
        log.info("channel Order sync for SurSung end!");

    }

    public void syncLog(List<Order> failedOrders,
                        int successCount, int totalCount,
                        ERPUserInfo erpUserInfo, ERPInfo erpInfo) throws UnsupportedEncodingException {

        ChannelOrderSyncLog channelOrderSyncLog = new ChannelOrderSyncLog();
        channelOrderSyncLog.setUserType(erpUserInfo.getErpUserType());
        channelOrderSyncLog.setProviderType(erpInfo.getErpType());
        channelOrderSyncLog.setCustomerId(erpUserInfo.getCustomerId());
        channelOrderSyncLog.setSyncTime(new Date());
        channelOrderSyncLog.setTotalCount(totalCount);
        channelOrderSyncLog.setSuccessCount(successCount);
        channelOrderSyncLog.setFailedCount(failedOrders.size());
        if (successCount > 0 && failedOrders.size() > 0) {
            channelOrderSyncLog.setOrderSyncStatus(OrderSyncStatus.ChannelOrderSyncStatus.SYNC_PARTY_SUCCESS);
        }
        if (successCount > 0 && failedOrders.size() == 0) {
            channelOrderSyncLog.setOrderSyncStatus(OrderSyncStatus.ChannelOrderSyncStatus.SYNC_SUCCESS);
        }
        if (successCount == 0) {
            channelOrderSyncLog.setOrderSyncStatus(OrderSyncStatus.ChannelOrderSyncStatus.SYNC_FAILURE);
        }

        channelOrderSyncLog = channelOrderSyncLogRepository.save(channelOrderSyncLog);

        List<ChannelOrderSyncInfo> syncFailedChannelOrders = new ArrayList<>();
        for (Order failedOrder : failedOrders) {
            ChannelOrderSyncInfo channelOrderSyncInfo = new ChannelOrderSyncInfo();
            channelOrderSyncInfo.setOrderId(failedOrder.getOrderId());
            channelOrderSyncInfo.setChannelOrderSyncStatus(OrderSyncStatus.ChannelOrderSyncStatus.SYNC_FAILURE);
            channelOrderSyncInfo.setRemark(failedOrder.getErrorMessage());
            channelOrderSyncInfo.setChannelOrderSyncLog(channelOrderSyncLog);
            channelOrderSyncInfo.setOrderJson(URLEncoder.encode(JSON.toJSONString(failedOrder), "utf-8"));
            syncFailedChannelOrders.add(channelOrderSyncInfo);
        }
        channelOrderSyncInfoService.batchSave(syncFailedChannelOrders);
        log.info("save channelorder sync log");

    }

    /**
     * 将聚水潭的渠道订单转换为平台的订单
     *
     * @param shopId
     * @param surSungOrders
     * @return
     */
    public List<Order> convert2PlatformOrder(int shopId, List<SurSungOrder> surSungOrders) {
        // 订单过滤，过滤掉erp中的平台订单；
        // 过滤方式1：获取订单号，根据此订单号从erp推送日志中查询，如果存在，则过滤掉；
        // 过滤方式2：获取订单中的店铺id，查询此店铺id，如果和系统参数的店铺id一致，则表示是平台订单，过滤掉；

        List<Order> orderList = new ArrayList<>();
        if (surSungOrders != null) {
            surSungOrders.forEach(surSungOrder -> {

                if ((surSungOrder.getShopId() != shopId) && (!surSungOrder.getStatus().equals("Merged"))
                        && !surSungOrder.getType().equals("补发订单")) {// 过滤方式2

                    String shopStatus = StringUtil.getWithDefault(surSungOrder.getShopStatus(), "");

                    Order order = new Order();

                    order.setOrderId(surSungOrder.getSoId());
                    String shopSite = surSungOrder.getShopSite();
                    SurSungEnum.SourceShop sourceShopEnum = EnumHelper.getEnumType(SurSungEnum.SourceShop.class, shopSite);
                    if (sourceShopEnum == null) {
                        order.setSourceShop(SurSungEnum.SourceShop.OTHER.getCode());
                    } else {
                        order.setSourceShop(sourceShopEnum.getCode());
                    }
                    order.setUserLoginName(surSungOrder.getShopBuyerId());
                    order.setConfirm(1);

                    if (shopStatus.equals(SurSungEnum.OrderStatus.TRADE_FINISHED.toString())) {
                        order.setOrderStatus(1);//0：活动；1：完成；-1：作废，关闭；
                    } else {
                        order.setOrderStatus(0);
                    }

//                0：未支付；
//                1：已支付；
//                2：部分付款；
//                3：部分退款；
//                4：全额退款；
//                5：售后退款中
                    double paidAmount = surSungOrder.getPaidAmount();
                    double payAmount = surSungOrder.getPayAmount();
                    if (paidAmount == 0) {// 未支付
                        order.setPayStatus(0);
                        order.setOnlinePayAmount(0);

                    } else {
                        order.setPayTime(surSungOrder.getPayDate());
                        order.setPayStatus(1);
                        order.setOnlinePayAmount(surSungOrder.getPayAmount());
                    }


//                0：未发货；
//                1：已发货；
//                2：部分发货；
//                3：部分退货；
//                4：已退货；
                    if (surSungOrder.getStatus().equals("Sent")) {
                        order.setShipStatus(1);
                    } else {
                        order.setShipStatus(0);
                    }
                    order.setItemNum(surSungOrder.getSurSungOrderItems().size());
                    order.setCreateTime(surSungOrder.getOrderDate());
                    order.setPayNumber(surSungOrder.getOuterPayId());


                    order.setShipName(surSungOrder.getReceiverName());
                    order.setShipArea(surSungOrder.getReceiverState() + "/" + surSungOrder.getReceiverCity() + "/"
                            + surSungOrder.getReceiverDistrict());// /省/市/区
                    order.setProvince(surSungOrder.getReceiverState());
                    order.setCity(surSungOrder.getReceiverCity());
                    order.setDistrict(surSungOrder.getReceiverDistrict());
                    order.setShipAddr(surSungOrder.getReceiverAddress());
                    order.setShipTel(surSungOrder.getReceiverPhone());
                    order.setShipMobile(surSungOrder.getReceiverMobile());
                    order.setCostFreight(surSungOrder.getFreight());
                    order.setFinalAmount(surSungOrder.getPayAmount());
                    order.setMemo(surSungOrder.getBuyerMessage());
                    order.setRemark(surSungOrder.getRemark());
                    order.setPayType(OrderEnum.PaymentOptions.WEIXINPAY_V3.getCode());
                    order.setLastUpdateTime(surSungOrder.getModified());
//            order.setWeight(0);
//            order.setOrderName("");
//            order.setBuyerName("");
//            order.setCurrency("");
//            order.setShipZip("");
//            order.setShipEmail();
//            order.setPrintStatus(0);
//            order.setPaymentName("");
//            order.setCustomerId(1);
//            order.setSupplierId(1);
//            order.setLogiName("");
//            order.setLogiNo("");
//            order.setLogiCode("");
//            order.setMemberId();
//            order.setUserNickname("");
//            order.setUnionOrderId("");
//            order.setReceiveStatus(0);
//            order.setIsTax(0);
//            order.setTaxCompany("");
//            order.setBuyerPid("");


                    order.setPmtAmount(surSungOrder.getFreeAmount());
                    double costItem = 0.0;

                    List<OrderItem> orderItems = new ArrayList<>();
                    for (SurSungOrderItem surSungOrderItem : surSungOrder.getSurSungOrderItems()) {

                        costItem += surSungOrderItem.getAmount();
                        OrderItem orderItem = new OrderItem();

                        orderItem.setName(surSungOrderItem.getName());
                        orderItem.setOrderId(surSungOrder.getSoId());
                        orderItem.setProductBn(surSungOrderItem.getSkuId());
                        orderItem.setPrice(surSungOrderItem.getPrice());
                        orderItem.setAmount(surSungOrderItem.getAmount());
                        orderItem.setNum(surSungOrderItem.getQty());
                        orderItem.setGoodBn(surSungOrderItem.getSkuId());
                        orderItem.setStandard(surSungOrderItem.getPropertiesValue());
                        orderItems.add(orderItem);
//                orderItem.setItemId(0);
//                orderItem.setUnionOrderId("");
//                orderItem.setCost(0);
//                orderItem.setSendNum(0);
//                orderItem.setRefundNum(0);
//                orderItem.setSupplierId(0);
//                orderItem.setCustomerId(0);
//                orderItem.setBrief("");
//                orderItem.setShipStatus(0);
                    }

                    order.setCostItem(costItem);
                    order.setOrderItems(orderItems);
                    orderList.add(order);
                }
            });
        }
        return orderList;
    }

}
