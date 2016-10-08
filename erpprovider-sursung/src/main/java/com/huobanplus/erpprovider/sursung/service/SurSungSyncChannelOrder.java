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
import com.huobanplus.erpprovider.sursung.common.SurSungSysData;
import com.huobanplus.erpprovider.sursung.formatdata.SurSungOrder;
import com.huobanplus.erpprovider.sursung.handler.SurSungOrderHandler;
import com.huobanplus.erpprovider.sursung.search.SurSungOrderSearch;
import com.huobanplus.erpprovider.sursung.search.SurSungOrderSearchResult;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.logs.ChannelOrderSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderShipSyncLog;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.repository.logs.ChannelOrderSyncLogRepository;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.OrderShipSyncLogService;
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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ChannelOrderSyncLogRepository channelOrderSyncLogRepository;
    @Autowired
    private ERPRegister erpRegister;

    @Autowired
    private SurSungOrderHandler surSungOrderHandler;

    //    @Scheduled(cron = "")
    @Transactional
    public void syncChannelOrder() {
        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        log.info("channel Order sync for SurSung start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.SURSUNG);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            try {
                ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                SurSungSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), SurSungSysData.class);

                //是否是第一次同步,第一次同步beginTime则为当前时间的前一天
                OrderShipSyncLog lastSyncLog = orderShipSyncLogService.findTop(erpUserInfo.getCustomerId(), ERPTypeEnum.ProviderType.SURSUNG);
                Date beginTime = lastSyncLog == null
                        ? Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusDays(2))
                        : lastSyncLog.getSyncTime();

                List failedOrders = new ArrayList<>(); //失败的订单列表
                List successOrders = new ArrayList<>(); //成功的订单列表
                int totalCount = 0; //总数量
                int pageIndex = 1;

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
                    // 推送至平台
                    EventResult firstSyncEvent = erpUserHandler.handleEvent(syncChannelOrderEvent);
                    if (firstSyncEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                        // TODO: 2016-09-27  
                    }


                    while (surSungOrderSearchResult.isHasNext()) {
                        pageIndex++;
                        orderSearch.setPageIndex(pageIndex);
                        EventResult nextEventResult = surSungOrderHandler.queryChannelOrder(orderSearch, sysData);
                        if (nextEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                            surSungOrderSearchResult = (SurSungOrderSearchResult) nextEventResult.getData();
                            //后续几次推送
                            syncChannelOrderEvent.setOrderList(convert2PlatformOrder(sysData.getShopId(),
                                    surSungOrderSearchResult.getOrders()));

                            // 推送至平台
                            EventResult nextSyncEvent = erpUserHandler.handleEvent(syncChannelOrderEvent);
                            if (nextSyncEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                // // TODO: 2016-09-27  
                            }

                        }
                    }
                }

                if (totalCount > 0) {// 轮询若无数据，则不记录日志
                    syncLog(failedOrders, successOrders, totalCount, erpUserInfo, erpInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("channel Order sync for SurSung end!");

    }

    public void syncLog(List failedOrders,
                        List successOrders, int totalCount,
                        ERPUserInfo erpUserInfo, ERPInfo erpInfo) {

        ChannelOrderSyncLog channelOrderSyncLog = new ChannelOrderSyncLog();
        channelOrderSyncLog.setUserType(erpUserInfo.getErpUserType());
        channelOrderSyncLog.setProviderType(erpInfo.getErpType());
        channelOrderSyncLog.setCustomerId(erpUserInfo.getCustomerId());
        channelOrderSyncLog.setSyncTime(new Date());
        channelOrderSyncLog.setTotalCount(totalCount);
        if (totalCount > 0) {
            int successCount = successOrders.size(), failedCount = failedOrders.size();

        } else {

        }

        channelOrderSyncLogRepository.save(channelOrderSyncLog);


    }

    /**
     * 将聚水潭的渠道订单转换为平台的订单
     *
     * @param shopId
     * @param surSungOrders
     * @return
     */
    private List<Order> convert2PlatformOrder(int shopId, List<SurSungOrder> surSungOrders) {
        // 订单过滤，过滤掉erp中的平台订单；
        // 过滤方式1：获取订单号，根据此订单号从erp推送日志中查询，如果存在，则过滤掉；
        // 过滤方式2：获取订单中的店铺id，查询此店铺id，如果和系统参数的店铺id一致，则表示是平台订单，过滤掉；


        List<Order> orderList = new ArrayList<>();
        surSungOrders.forEach(surSungOrder -> {

            if (surSungOrder.getShopId() != shopId) {// 过滤方式2

                Order order = new Order();

                order.setOrderId(surSungOrder.getSoId());
//            order.setMemberId();
                order.setUserLoginName(surSungOrder.getShopBuyerId());
//            order.setUserNickname("");
                order.setConfirm(1);
                order.setOrderStatus(1);//0：活动；1：完成；-1：作废，关闭；

//                0：未支付；
//                1：已支付；
//                2：部分付款；
//                3：部分退款；
//                4：全额退款；
//                5：售后退款中
                order.setPayStatus(1);


//                0：未发货；
//                1：已发货；
//                2：部分发货；
//                3：部分退货；
//                4：已退货；
                order.setShipStatus(1);// TODO: 2016-09-26
//            order.setWeight(0);
//            order.setOrderName("");
                order.setItemNum(surSungOrder.getSurSungOrderItems().size());
//            order.setCreateTime("");// TODO: 2016-09-26
//            order.setBuyerName("");
//            order.setPayNumber("");

//            order.setCurrency("");

                order.setShipName(surSungOrder.getReceiverName());
                order.setShipArea(surSungOrder.getReceiverState() + "/" + surSungOrder.getReceiverCity() + "/"
                        + surSungOrder.getReceiverDistrict());// /省/市/区
                order.setProvince(surSungOrder.getReceiverState());
                order.setCity(surSungOrder.getReceiverCity());
                order.setDistrict(surSungOrder.getReceiverDistrict());
                order.setShipAddr(surSungOrder.getReceiverAddress());
//            order.setShipZip("");
                order.setShipTel(surSungOrder.getReceiverPhone());
//            order.setShipEmail();
                order.setShipMobile(surSungOrder.getReceiverMobile());
                order.setCostItem(surSungOrder.getPayAmount() - surSungOrder.getFreight());
                order.setCostFreight(surSungOrder.getFreight());
                order.setFinalAmount(surSungOrder.getPayAmount());
//            order.setPmtAmount(0.0);
                order.setMemo(surSungOrder.getBuyerMessage());
//            order.setRemark("");
//            order.setPrintStatus(0);
//            order.setPaymentName("");
//            order.setPayType(1);
//            order.setCustomerId(1);
//            order.setSupplierId(1);
//            order.setLogiName("");
//            order.setLogiNo("");
//            order.setLogiCode("");
                order.setPayTime(surSungOrder.getOrderDate());
//            order.setUnionOrderId("");
//            order.setReceiveStatus(0);
//            order.setIsTax(0);
//            order.setTaxCompany("");
//            order.setBuyerPid("");
//            order.setOnlinePayAmount(0);
//            order.setLastUpdateTime("");

                List<OrderItem> orderItems = new ArrayList<>();
                surSungOrder.getSurSungOrderItems().forEach(surSungOrderItem -> {
                    OrderItem orderItem = new OrderItem();

                    orderItem.setName(surSungOrderItem.getName());
//                orderItem.setItemId(0);
                    orderItem.setOrderId(surSungOrder.getSoId());
//                orderItem.setUnionOrderId("");
                    orderItem.setProductBn(surSungOrderItem.getSkuId());
//                orderItem.setCost(0);
                    orderItem.setPrice(surSungOrderItem.getBasePrice());
                    orderItem.setAmount(surSungOrderItem.getAmount());
                    orderItem.setNum(surSungOrderItem.getQty());
//                orderItem.setSendNum(0);
//                orderItem.setRefundNum(0);
//                orderItem.setSupplierId(0);
//                orderItem.setCustomerId(0);
//                orderItem.setGoodBn("");
                    orderItem.setStandard(surSungOrderItem.getPropertiesValue());
//                orderItem.setBrief("");
//                orderItem.setShipStatus(0);
                    orderItems.add(orderItem);

                });

                order.setOrderItems(orderItems);
                orderList.add(order);
            }
        });
        return orderList;
    }
}
