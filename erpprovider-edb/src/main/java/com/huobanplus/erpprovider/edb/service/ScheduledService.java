/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.common.EDBEnum;
import com.huobanplus.erpprovider.edb.formatedb.EDBOrderDetail;
import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpprovider.edb.search.EDBOrderSearch;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.OrderScheduledLog;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.OrderScheduledLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushOrderListInfoEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by allan on 12/24/15.
 */
@Service
public class ScheduledService {
    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderScheduledLogService scheduledLogService;
    @Autowired
    private ERPRegister erpRegister;
    @Autowired
    private EDBOrderHandler edbOrderHandler;

    /**
     * 获取订单列表轮训服务
     */
//    @Scheduled
    public void syncOrderShip() {
        Date now = new Date();
        //得到所有配置过edb信息的商家,准备获取数据
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.EDB);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            boolean result = true;
            int currentPageIndex = 1;
            EDBSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), EDBSysData.class);

            EDBOrderSearch edbOrderSearch = new EDBOrderSearch();
            edbOrderSearch.setBeginTime(sysData.getBeginTime());
            edbOrderSearch.setEndTime(now);
            edbOrderSearch.setShipStatus(EDBEnum.ShipStatusEnum.ALL_DELIVER);
            edbOrderSearch.setPlatformStatus(EDBEnum.PlatformStatus.PAYED);
            edbOrderSearch.setProceStatus(EDBEnum.OrderStatusEnum.ACTIVE);

//            OrderScheduledLog lastLog = scheduledLogService.findFirst(detailConfig.getCustomerId());
//            LocalDateTime beginTime = lastLog == null ?
//                    now.minusMonths(10) :
//                    Jsr310Converters.DateToLocalDateTimeConverter.INSTANCE.convert(lastLog.getCreateTime());


            EventResult eventResult = edbOrderHandler.obtainOrderList(currentPageIndex, sysData, edbOrderSearch);

            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                JSONObject jsonObject = (JSONObject) eventResult.getData();
                JSONArray resultOrders = jsonObject.getJSONObject("items").getJSONArray("item");
                if (resultOrders.size() > 0) {
                    int totalResult = resultOrders.getJSONObject(0).getIntValue("总数量");//本次获取的总数据量
                    //推送给erp使用商户
                    ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                    ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                    PushOrderListInfoEvent pushOrderListInfoEvent = new PushOrderListInfoEvent(resultOrders.toJSONString());
                    EventResult pushResult = erpUserHandler.handleEvent(pushOrderListInfoEvent);

                    if (pushResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                        ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());

                        List<EDBOrderDetail> orderDetails = JSON.parseArray(resultOrders.toJSONString(), EDBOrderDetail.class);

                        int totalPage = totalResult / EDBConstant.PAGE_SIZE;
                        if (totalResult % totalPage != 0) {
                            totalPage++;
                        }
                        if (totalPage > 1) {
                            currentPageIndex++;
                            //取下一笔数据
                            for (int i = currentPageIndex; i <= totalPage; i++) {
                                EventResult nextResult = edbOrderHandler.obtainOrderList(currentPageIndex, sysData, edbOrderSearch);
                                if (nextResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                    JSONObject nextJsonObject = (JSONObject) nextResult.getData();
                                    JSONArray nextJsonArray = nextJsonObject.getJSONObject("items").getJSONArray("item");
                                    //推送给erp使用商户
                                    pushOrderListInfoEvent = new PushOrderListInfoEvent(resultOrders.toJSONString());
                                    EventResult nextPushResult = erpUserHandler.handleEvent(pushOrderListInfoEvent);
                                    if (nextPushResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                                        result = false;
                                        break;
                                    }
                                } else {
                                    result = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (result) {
                        //存入轮训记录表
                        OrderScheduledLog orderScheduledLog = new OrderScheduledLog();
                        orderScheduledLog.setCustomerId(detailConfig.getCustomerId());
                        orderScheduledLog.setNum(totalResult);
                        orderScheduledLog.setCreateTime(now);
                        scheduledLogService.save(orderScheduledLog);
                    }
                }
            }
        }

    }
}
