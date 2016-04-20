/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.iscs.formatiscs;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by allan on 4/20/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ISCSCreateOrder {
    /**
     * 导入订单数量
     */
    private int count;
    /**
     * 导入订单数据
     */
    @JSONField(name = "trades")
    private List<ISCSCreateOrderInfo> createOrderInfoList;
}
