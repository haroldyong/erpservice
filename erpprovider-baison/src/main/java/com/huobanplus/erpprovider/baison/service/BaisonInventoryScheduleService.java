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

package com.huobanplus.erpprovider.baison.service;

import com.huobanplus.erpprovider.baison.handler.BaisonGoodsHandler;
import com.huobanplus.erpservice.datacenter.model.ProInventoryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-12-26.
 */
@Service
public class BaisonInventoryScheduleService {

    @Autowired
    private BaisonGoodsHandler baisonGoodsHandler;

    public void syncInventory() {

        // 批量查询平台的商品信息
        // 批量查询erp中的商品库存信息
        // 批量转化为库存同步实体
        // 批量同步回平台
    }

    public void captureInventory() {

    }

    private List<ProInventoryInfo> convert2InventoryInfo() {
        return null;
    }
}
