package com.huobanplus.erpprovider.lz.formatlz;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class LzOrderPayInfo {
    /**
     * 	支付交易号 支付公司提供
     */
    @JSONField(name = "payment_id")
    private String    payment_id;
    /**
     * 	支付企业名称
     */
    @JSONField(name = "payment_company_name")
    private String    payment_company_name;
    /**
     * 	电商平台域名
     */
    @JsonIgnore
    @JSONField(name = "platform_domain")
    private String    platform_domain;
    /**
     * 电商平台编号 海关编号
     */
    @JSONField(name = "platform_id")
    private String    platform_id;
    /**
     * 		电商平台 海关名称
     */
    @JSONField(name = "platform_name")
    private String    platform_name;
    /**
     * 		支付金额 单位：分 币种：通常为人民币
     */
    @JSONField(name = "pay_amount")
    private int    pay_amount	;
    /**
     * 支付币制代码 142
     */
    @JSONField(name = "pay_currency_code")
    private String     pay_currency_code;
    /**
     * 	人民币金额 单位：分
     */
    @JsonIgnore
    @JSONField(name = "pay_rmb_amount")
    private String     pay_rmb_amount;


}
