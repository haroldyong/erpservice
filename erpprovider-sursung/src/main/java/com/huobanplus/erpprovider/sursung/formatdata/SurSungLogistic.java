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

import java.util.List;

/**
 * Created by wuxiongliu on 2016-08-31.
 */
@Data
public class SurSungLogistic {
    /**
     * ERP内部订单号
     */
    @JSONField(name = "o_id")
    private String oId;

    /**
     * 线上订单号
     */
    @JSONField(name = "so_id")
    private String soId;

    /**
     * 发货日期
     */
    @JSONField(name = "send_date")
    private String sendDate;

    /**
     * 快递单号
     */
    @JSONField(name = "l_id")
    private String logiNo;

    /**
     * 快递公司
     */
    @JSONField(name = "logistics_company")
    private String logisticsCompany;

    /**
     * 商品信息
     */
    @JSONField(name = "items")
    private List<SurSungLogisticItem> items;
}
