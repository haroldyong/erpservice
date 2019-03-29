package com.huobanplus.erpprovider.lz.formatlz;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class LzOrderFeeInfo {
    /**
     * 订单对应产生的税费 币种: 人民币 单位:分
     */
    @JSONField(name = "tax")
    private String tax;
    /**
     * 运费 币种: 人民币 单位:分
     */
    @JSONField(name = "freight")
    private String freight;
    /**
     * 保费 币种: 人民币 单位:分
     */
    @JSONField(name = "insurance")
    private String insurance;
    /**
     * 购抵扣金额 币种: 人民币 单位:分
     */
    @JSONField(name = "deduction_amount")
    private String deduction_amount;
    /**
     * 抵扣金额说明抵扣金额为0时填”无”，String类型
     */
    @JsonIgnore
    @JSONField(name = "deduction_note")
    private String deduction_note;
}
