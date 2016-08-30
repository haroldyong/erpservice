/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-08-30.
 */
@Data
public class SursungPay {

    /**
     * 外部支付单号,最大50
     */
    @JSONField(name = "outer_pay_id")
    private String outerPayId;

    /**
     * 支付日期
     */
    @JSONField(name = "pay_date")
    private String payDate;

    /**
     * 支付方式，最大10个汉字或20个字符
     */
    @JSONField(name = "payment")
    private String payment;

    /**
     * 卖家支付账号，最大 50
     */
    @JSONField(name = "seller_account")
    private String sellerAccount;

    /**
     * 买家支付账号，最大 50
     */
    @JSONField(name = "buyer_account")
    private String buyerAccount;

    /**
     * 支付金额
     */
    @JSONField(name = "amount")
    private double amount;
}
