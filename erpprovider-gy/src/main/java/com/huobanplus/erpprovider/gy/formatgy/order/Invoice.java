package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by admin on 2016/5/9.
 */
@Data
public class Invoice {

    /**
     * invoice_type:
     */
    @JSONField(name = "invoice_type")
    private String invoiceType;

    /**
     * invoice_title:
     */
    @JSONField(name = "invoice_title")
    private String invoiceTitle;


    /**
     * invoice_content
     */
    @JSONField(name = "invoice_content")
    private String invoiceContent;

    /**
     * invoice_amount:
     */
    @JSONField(name = "invoice_amount")
    private String invoiceAmount;


    /**
     * bill_amount:
     */
    @JSONField(name = "bill_amount")
    private String billAmount;
}
