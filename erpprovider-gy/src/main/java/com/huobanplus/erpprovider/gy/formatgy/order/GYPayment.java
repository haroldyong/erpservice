package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * 支付信息对象
 * Created by elvis on 2016/5/9.
 */
@Data
public class GYPayment {

    /**
     * "payment":
     */
    private double payment;

    /**
     * "paytime": "2014-12-17 09:54:22"
     */
    private Date paytime;

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
