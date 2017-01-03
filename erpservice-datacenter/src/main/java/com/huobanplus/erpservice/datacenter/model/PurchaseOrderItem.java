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

package com.huobanplus.erpservice.datacenter.model;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016-12-12.
 * 采购单明细
 */
@Data
public class PurchaseOrderItem {

    /**
     * 产品编码
     */
    private String productBn;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品规格
     */
    private String standard;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 商品编码
     */
    private String goodsBn;

    /**
     * 数量
     */
    private int qty;

    /**
     * 单位 三字代码
     */
    private String unit;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 原产国
     */
    private String originCountry;

    /**
     * 金额
     */
    private double amount;

    /**
     * 单价
     */
    private Double price;

}
