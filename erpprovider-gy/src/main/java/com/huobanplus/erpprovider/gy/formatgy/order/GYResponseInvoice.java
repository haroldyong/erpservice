package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by wuxiongliu on 2016/6/20.
 */
public class GYResponseInvoice {
    /**
     * 发票类型
     */
    @JSONField(name= "invoice_type_name")
    private String invoiceTypeName;

    /**
     * 发票抬头
     */
    @JSONField(name= "invoice_title")
    private String invoiceTitle;

    /**
     * 发票内容
     */
    @JSONField(name= "invoice_content")
    private String invoiceContent;

    /**
     * 发票金额
     */
    @JSONField(name= "invoice_amount")
    private String invoiceAmount;
}
