/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpuser.hotsupplier.handler.impl;

import com.huobanplus.erpservice.datacenter.model.ProInventoryInfo;
import com.huobanplus.erpservice.datacenter.model.ProductSearchInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpuser.hotsupplier.handler.SupGoodHandler;
import com.huobanplus.erpuser.huobanmall.handler.HBGoodHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by allan on 8/3/16.
 */
@Service
public class SupGoodHandlerImpl implements SupGoodHandler {
    @Autowired
    private HBGoodHandler hbGoodHandler;

    @Override
    public EventResult syncProInventory(List<ProInventoryInfo> proInventoryInfoList, ERPUserInfo erpUserInfo) {
        return hbGoodHandler.syncProInventory(proInventoryInfoList, erpUserInfo);
    }

    @Override
    public EventResult obtainProductListInfo(ProductSearchInfo productSearchInfos, ERPUserInfo erpUserInfo) {
        return hbGoodHandler.obtainProductListInfo(productSearchInfos, erpUserInfo);
    }
}
