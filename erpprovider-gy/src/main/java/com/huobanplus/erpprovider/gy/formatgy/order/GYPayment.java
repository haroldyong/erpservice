/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

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
    private Date payTime;

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
