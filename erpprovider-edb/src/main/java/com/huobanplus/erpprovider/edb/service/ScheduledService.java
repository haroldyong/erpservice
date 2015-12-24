/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.OrderScheduledLog;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.OrderScheduledLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    @Scheduled
    public void scheduledOrder() {
        LocalDateTime now = LocalDateTime.now();
        Date nowDate = Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(now);

        //得到所有配置过edb信息的商家,准备获取数据
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.EDB);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            boolean result = true;
            int currentPageIndex = 1;
            OrderScheduledLog lastLog = scheduledLogService.findFirst(detailConfig.getCustomerId());
            LocalDateTime beginTime = lastLog == null ?
                    now.minusMonths(1) :
                    Jsr310Converters.DateToLocalDateTimeConverter.INSTANCE.convert(lastLog.getCreateTime());

            EventResult eventResult = edbOrderHandler.obtainOrderList(currentPageIndex, beginTime, now, detailConfig);
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                JSONObject jsonObject = (JSONObject) eventResult.getData();
                JSONArray jsonArray = jsonObject.getJSONObject("items").getJSONArray("item");
                if (jsonArray.size() > 0) {
                    int totalResult = jsonArray.getJSONObject(0).getIntValue("总数量");//本次获取的总数据量
                    //推送给erp使用商户
                    ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                    ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                    EventResult pushResult = erpUserHandler.handleEvent(null);
                    if (pushResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {

                        int totalPage = totalResult / EDBConstant.PAGE_SIZE;
                        if (totalResult % totalPage != 0) {
                            totalPage++;
                        }
                        if (totalPage > 1) {
                            currentPageIndex++;
                            //取下一笔数据
                            for (int i = currentPageIndex; i <= totalPage; i++) {
                                EventResult nextResult = edbOrderHandler.obtainOrderList(currentPageIndex, beginTime, now, detailConfig);
                                if (nextResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                    JSONObject nextJsonObject = (JSONObject) nextResult.getData();
                                    JSONArray nextJsonArray = nextJsonObject.getJSONObject("items").getJSONArray("item");
                                    //推送给erp使用商户
                                    EventResult nextPushResult = erpUserHandler.handleEvent(null);
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
                        orderScheduledLog.setCreateTime(nowDate);
                        scheduledLogService.save(orderScheduledLog);
                    }
                }
            }
        }

    }
}
