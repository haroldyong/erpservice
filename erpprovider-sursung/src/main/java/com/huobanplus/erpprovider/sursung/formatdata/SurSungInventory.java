/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-09-08.
 * 库存model
 */
@Data
public class SurSungInventory {

    /**
     * 商品skuid
     */
    @JSONField(name = "sku_id")
    private String skuId;

    /**
     * 库存数量
     */
    @JSONField(name = "qty")
    private int qty;

    /**
     * 更新时间
     */
    @JSONField(name = "modified")
    private String modified;
}
