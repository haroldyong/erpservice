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

package com.huobanplus.erpprovider.baison.handler;

import com.huobanplus.erpprovider.baison.common.BaisonSysData;
import com.huobanplus.erpprovider.baison.formatdata.BaisonStockSearch;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-12-27.
 */
public interface BaisonGoodsHandler {

    /**
     * 查询商品库存
     *
     * @param baisonStockSearchList
     * @param baisonSysData
     * @return
     */
    EventResult queryGoodsStock(List<BaisonStockSearch> baisonStockSearchList, BaisonSysData baisonSysData);
}
