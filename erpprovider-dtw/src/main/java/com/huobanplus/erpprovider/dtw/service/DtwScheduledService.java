/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwStockItem;
import com.huobanplus.erpprovider.dtw.handler.DtwOrderHandler;
import com.huobanplus.erpprovider.dtw.search.DtwStockSearch;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.OrderShipSyncLogService;
import com.huobanplus.erpservice.datacenter.service.logs.ShipSyncDeliverInfoService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.InventoryEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.InventoryInfo;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2016-07-12.
 */
@Service
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

    @Autowired
    private DtwOrderHandler dtwOrderHandler;

    @Scheduled(cron = "0 */1 * * * ? ")
    @Transactional
    public synchronized void syncStock() {
        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.GY);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "start to sync stock");
            try {
                ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                DtwSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), DtwSysData.class);


                // 从平台获取所有的商品信息
                // 遍历所有的结果，查询每个商品的库存

                DtwStockSearch stockSearch = new DtwStockSearch();
                stockSearch.setECommerceCode(sysData.getECommerceCode());
                stockSearch.setECommerceName(sysData.getECommerceName());
                stockSearch.setPartNo("");
                stockSearch.setPassKey(sysData.getPassKey());

                EventResult stockResult = dtwOrderHandler.stockQuery(stockSearch, sysData);
                InventoryEvent inventoryEvent = new InventoryEvent();
                inventoryEvent.setErpInfo(erpInfo);
                inventoryEvent.setErpUserInfo(erpUserInfo);

                ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                if (stockResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                    // 推送回给平台
                    InventoryInfo inventoryInfo = new InventoryInfo();
                    inventoryInfo.setGoodBn("");// FIXME: 2016-07-12
                    inventoryInfo.setProBn(stockSearch.getPartNo());
                    List<DtwStockItem> dtwStockItems = (List<DtwStockItem>) stockResult.getData();
                    int stock = 0;
                    for (DtwStockItem dtwStockItem : dtwStockItems) {
                        stock += dtwStockItem.getQty();
                    }
                    inventoryInfo.setStock(stock);
                    EventResult syncResult = erpUserHandler.handleEvent(inventoryEvent);
                }

            } catch (Exception e) {
                log.error(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "发生错误", e);
            }
            log.info("DTW stock sync end");
        }
    }

}
