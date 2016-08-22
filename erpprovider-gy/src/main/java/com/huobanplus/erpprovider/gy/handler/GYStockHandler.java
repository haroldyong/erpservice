/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.handler;

import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.search.GYStockSearch;
import com.huobanplus.erpprovider.gy.search.GYStockSearchNew;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
public interface GYStockHandler {

    /**
     * 库存查询
     *
     * @param gyStockSearch 库存查询实体
     * @param gySysData     管易系统参数
     * @return
     */
    EventResult stockQuery(GYStockSearch gyStockSearch, GYSysData gySysData);

    EventResult stockQueryNew(GYStockSearchNew stockSearchNew, GYSysData gySysData);
}
