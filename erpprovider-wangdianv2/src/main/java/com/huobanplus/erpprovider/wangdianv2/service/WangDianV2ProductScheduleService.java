/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpprovider.wangdianv2.service;

import com.huobanplus.erpprovider.wangdianv2.handler.WangDianV2ProductHandler;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.InventorySyncLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2017-02-24.
 */
@Service
public class WangDianV2ProductScheduleService {

    private static final Log log = LogFactory.getLog(WangDianV2ProductScheduleService.class);
    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private InventorySyncLogService inventorySyncLogService;
    @Autowired
    private WangDianV2ProductHandler wangDianV2ProductHandler;
    @Autowired
    private ERPRegister erpRegister;

    @Scheduled(cron = "0 0 0/3 * * ? *")// 每隔三小时执行一次，因为旺店通的查询时间最大间隔为三小时
    @Transactional
    public void syncInventory() {
        Date now = new Date();
        log.info("order inventory sync for wangdianv2 start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.WANGDIANV2);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            if (detailConfig.getErpBaseConfig().getIsSyncInventory() == 1) {

            }
        }
    }

}
