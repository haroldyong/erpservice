package com.huobanplus.erpprovider.gjbc.common;

import lombok.Data;

/**
 * Created by montage on 2017/6/28.
 */
@Data
public class GjbcSysData {
    /**
     * 请求地址
     */
    private String requestUrl;
    /**
     * 账号名称
     */
    private String name;
    /**
     * 验证码
     */
    private String key;
    /**
     * 电商企业编码(电商企业在跨境平台备案编码)
     */
    private String eCommerceCode;
    /**
     * 电商企业名称
     */
    private String eCommerceName;
//    /**
//     * 海关接口地址
//     */
//    private String customUrl;
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
     * 订单网址
     */
    private String pWeb;
    /**
     * 发货人信息 逗号分隔的字符串;发件人姓名,发件人城市,发件人地址,发件人电话,发件人国别
     */
    private String senderInfo;
}
