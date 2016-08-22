/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.formatgy.stock;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-07-12.
 */
@Data
public class GyResponseStockSearch {

    /**
     * 总记录数
     */
    @JSONField(name = "total")
    private int total;

    /**
     * 库存明细
     */
    @JSONField(name = "stocks")
    private List<GYStockInfo> stocks;
}
