package com.huobanplus.erpprovider.dtw.formatdtw;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/13.
 */
@Data
public class DtwOrderItem {

    /**
     * 供应商编码
     */
    @JSONField(name = "Supplier")
    private String supplier;

    /**
     * 项次（行号，最大50）(必填)
     */
    @JSONField(name = "Msgitem")
    private int msgitem;

    /**
     * 产品编码(最长50个字符)(必填)
     */
    @JSONField(name = "Partno")
    private String partno;

    /**
     * 货物名称(必填)
     */
    @JSONField(name = "PartName")
    private String partName;

    /**
     * 货物规格
     */
    @JSONField(name = "Spec")
    private String spec;

    /**
     * 批次
     */
    @JSONField(name = "Batch")
    private String batch;

    /**
     * 数量(必填)
     */
    @JSONField(name = "Qty")
    private double qty;

    /**
     * 计量单位，海关三字代码(必填)
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
     * 金额
     */
    @JSONField(name = "Amount")
    private double amount;

}
