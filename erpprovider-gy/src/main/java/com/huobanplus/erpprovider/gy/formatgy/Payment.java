package com.huobanplus.erpprovider.gy.formatgy;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 支付信息对象
 * Created by elvis on 2016/5/9.
 */
public class Payment {

    /**
     * "payment":
     */
    private String payment;

    /**
     * "paytime": "2014-12-17 09:54:22"
     */
    private String paytime;

    /**
     * "account":
     */
    private String account;

    /**
     * "pay_type_code":       样例："1012014021233754645",
     */
    @JSONField(name = "pay_type_code")
    private String payTypeCode;


    /**
     * pay_code
     */
    @JSONField(name="pay_code")
    private String payCode;
}
