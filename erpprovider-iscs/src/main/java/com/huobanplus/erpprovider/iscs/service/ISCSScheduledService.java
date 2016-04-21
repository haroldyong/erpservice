/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.iscs.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.iscs.common.ISCSConstant;
import com.huobanplus.erpprovider.iscs.common.ISCSSysData;
import com.huobanplus.erpprovider.iscs.handler.ISCSOrderHandler;
import com.huobanplus.erpprovider.iscs.search.ISCSOrderSearch;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderShipSyncLog;
import com.huobanplus.erpservice.datacenter.jsonmodel.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.OrderShipSyncLogService;
import com.huobanplus.erpservice.datacenter.service.logs.ShipSyncFailureOrderService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 网仓的轮训服务
 * <p>
 * Created by allan on 4/21/16.
 */
@Service
public class ISCSScheduledService {
    private static final Log log = LogFactory.getLog(ISCSScheduledService.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderShipSyncLogService orderShipSyncLogService;
    @Autowired
    private ShipSyncFailureOrderService shipSyncFailureOrderService;
    @Autowired
    private ISCSOrderHandler orderHandler;

    /**
     * 同步发货状态
     * <p>
     * 1.如果第一次是第一次同步,以配置的开始时间为发货时间的开始时间
     * 2.如果同步过,则以上次同步记录的时间为开始时间
     * <p>
     * 结束时间均为同步开始时间
     */
    @Scheduled(cron = "0 0 */2 * * ?")
    public void syncOrderShip() {
        Date now = new Date();
        try {
            log.info("网仓发货同步开始:" + StringUtil.DateFormat(now, StringUtil.TIME_PATTERN));
            List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.ISCS);
            for (ERPDetailConfigEntity detailConfig : detailConfigs) {
                log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "开始获取订单数据进行同步");
                ISCSSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), ISCSSysData.class);
                //当前页索引
                int currentPageIndex = 1;
                //是否是第一次同步
                OrderShipSyncLog lastSyncLog = orderShipSyncLogService.findTop(detailConfig.getCustomerId(), ERPTypeEnum.ProviderType.ISCS);
                Date beginTime = lastSyncLog == null ? StringUtil.DateFormat(sysData.getBeginTime(), StringUtil.DATE_PATTERN) : lastSyncLog.getSyncTime();

                ISCSOrderSearch orderSearch = new ISCSOrderSearch();
                orderSearch.setStockId(sysData.getStockId());
                orderSearch.setShopId(sysData.getShopId());
                orderSearch.setBeginTime(StringUtil.DateFormat(beginTime, StringUtil.TIME_PATTERN));
                orderSearch.setEndTime(StringUtil.DateFormat(now, StringUtil.TIME_PATTERN));
                orderSearch.setPageIndex(currentPageIndex);
                orderSearch.setPageSize(ISCSConstant.PAGE_SIZE);

                //第一次获取
                EventResult eventResult = orderHandler.getOrderDeliveryInfo(sysData, orderSearch);
                if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                    JSONObject result = (JSONObject) eventResult.getData();
                    int totalResult = result.getInteger("total_count");
                    JSONArray resultArray = result.getJSONArray("trades");
                    List<OrderDeliveryInfo> deliveryInfoList = new ArrayList<>();

                    addDeliveryInfo(resultArray, deliveryInfoList);
                    //进行后面几次的获取
                    int totalPage = totalResult / ISCSConstant.PAGE_SIZE;
                    if (totalResult % ISCSConstant.PAGE_SIZE != 0) {
                        totalPage++;
                    }
                    if (totalPage > 1) {
                        currentPageIndex++;
                        //取下一笔数据
                        for (int i = currentPageIndex; i <= totalPage; i++) {
                            orderSearch.setPageIndex(currentPageIndex);
                            EventResult nextEventResult = orderHandler.getOrderDeliveryInfo(sysData, orderSearch);
                            if (nextEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                JSONObject nextResult = (JSONObject) nextEventResult.getData();
                                JSONArray nextResultArray = nextResult.getJSONArray("trades");
                                addDeliveryInfo(nextResultArray, deliveryInfoList);
                            }
                        }
                    }
                }
                // TODO: 4/22/16 到使用者那里批量发货

                //存记录
                OrderShipSyncLog orderShipSyncLog = new OrderShipSyncLog();
                // TODO: 4/22/16  
            }
        } catch (Exception e) {

        }
    }

    private void addDeliveryInfo(JSONArray resultArray, List<OrderDeliveryInfo> orderDeliveryInfoList) {
        for (Object o : resultArray) {
            JSONObject deliverJson = (JSONObject) o;
            int flag = deliverJson.getInteger("flag");//是否全部发完,全部发完才需要推送到伙伴商城执行发货
            if (flag == 1) {
                OrderDeliveryInfo deliveryInfo = new OrderDeliveryInfo();
                deliveryInfo.setOrderId(deliverJson.getString("order_no"));
                deliveryInfo.setLogiName(deliverJson.getString("transporter_id"));
                deliveryInfo.setLogiNo(deliverJson.getString("out_ids"));
                orderDeliveryInfoList.add(deliveryInfo);
            }
        }
    }
}
