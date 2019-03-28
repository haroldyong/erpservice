package com.huobanplus.erpprovider.lz.common;

import lombok.Data;

@Data
public class LzSysData {
    /**
     * 请求地址
     */
    private String requestUrl;
    /**
     * 账号名称
     */
    private String name;
    /**
     * 电商企业编码(电商企业在跨境平台备案编码)
     */
    private String eCommerceCode;
    /**
     * 电商企业名称
     */
    private String eCommerceName;
    /**
     * 微信支付企业名称
     */
    private String wxPaymentCompanyName;

    /**
     * 微信公众账号ID
     */
    private String weiXinAppId;

    /**
     * 微信 商户号
     */
    private String weixinMchId;

    /**
     * 微信秘钥
     */
    private String weixinKey;

    /**
     * 支付宝支付企业名称
     */
    private String aliPaymentCompanyName;

    /**
     * 支付宝商户号
     */
    private String aliPartner;

    /**
     * 支付宝key
     */
    private String aliKey;

//    /**
//     * 海关接口rsa加密公钥
//     */
//    private String rsaPublicKey;
//    /**
//     * 海关接口rsa加密私钥
//     */
//    private String rsaPrivateKey;
//    /**
//     * 海关接口
//     */
//    private String aesKey;


    /**
     * 是否需要同步库存（1：需要 0：不需要）
     */
    private Integer status;

    /**
     * WMS服务商分配
     */
    private String wmsId;
    /**
     * WMS服务商分配
     */
    private String storageId;


    private String merchantId;
}
