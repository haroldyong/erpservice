package com.huobanplus.erpservice.datacenter.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 重推支付宝订单
 * @Author hxh
 * @Date 2018/1/2 11:15
 */
@Getter
@Setter
public class AlipayOrder {
    private String orderId;
    /**
     * 支付成功后伙伴商城平台返回的支付单号
     */
    private String payNumber;
    /**
     * 线上支付金额
     */
    private double onlinePayAmount;
    /**
     * 支付宝商户号
     */
    private String aliPartner;
    /**
     * 电商企业编码(电商企业在跨境平台备案编码)
     */
    private String eCommerceCode;
    /**
     * 电商企业名称
     */
    private String eCommerceName;
    /**
     * 支付宝key
     */
    private String aliKey;
}
