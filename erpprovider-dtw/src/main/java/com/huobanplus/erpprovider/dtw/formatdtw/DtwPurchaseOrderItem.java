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

package com.huobanplus.erpprovider.dtw.formatdtw;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-12-12.
 */
@Data
public class DtwPurchaseOrderItem {

    /**
     * 项次
     */
    @JSONField(name = "Msgitem")
    private Integer msgItem;

    /**
     * 产品编号
     */
    @JSONField(name = "Partno")
    private String partNo;

    /**
     * 货物名称
     */
    @JSONField(name = "PartName")
    private String partName;

    /**
     * 货物规格
     */
    @JSONField(name = "Spec")
    private String spec;

    /**
     * 发票号
     */
    @JSONField(name = "InvoiceNo")
    private String invoiceNo;

    /**
     * 商品海关编码
     */
    @JSONField(name = "HsCode")
    private String hsCode;

    /**
     * 批次
     */
    @JSONField(name = "Batch")
    private String batch;

    /**
     * 数量
     */
    @JSONField(name = "Qty")
    private int qty;

    /**
     * 单位
     */
    @JSONField(name = "Unit")
    private String unit;

    /**
     * 备用字段
     */
    @JSONField(name = "Dref1")
    private String dref1;

    /**
     * 备用字段
     */
    @JSONField(name = "Dref2")
    private String dref2;

    /**
     * 备用字段
     */
    @JSONField(name = "Dref3")
    private String dref3;

    /**
     * 备用字段
     */
    @JSONField(name = "Dref4")
    private String dref4;

    /**
     * 币种编码
     */
    @JSONField(name = "Currency")
    private String currency;

    /**
     * 原产国（海关国别三字代码）
     */
    @JSONField(name = "OriginCountry")
    private String originCountry;

    /**
     * 金额
     */
    @JSONField(name = "Amount")
    private double amount;

}
