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
 * Created by wuxiongliu on 2016-08-31.
 */
@Data
public class SurSungLogisticItem {

    /**
     * 商品编码
     */
    @JSONField(name = "sku_id")
    private String sku_id;

    /**
     * 数量
     */
    @JSONField(name = "qty")
    private String qty;

}
