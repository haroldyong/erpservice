package com.huobanplus.erpprovider.gjbc.common;

import lombok.Data;

/**
 * Created by montage on 2017/6/27.
 */

@Data
public class GjbcData {
    private String seller_name;
    private String api_key;
    private String mark;
    private String confirm;
    private String Order;

    /**
     * 电商企业编码(电商企业在跨境平台备案编码)
     */
    private String eCommerceCode;

    /**
     * 电商企业名称
     */
    private String eCommerceName;
    /**
     * 海关接口地址
     */
    private String customUrl;
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

    /**
     * 海关接口rsa加密公钥
     */
    private String rsaPublicKey;
    /**
     * 海关接口rsa加密私钥
     */
    private String rsaPrivateKey;
    /**
     * 海关接口
     */
    private String aesKey;
    /**
     * 发货人信息 以";"隔开 : 发货人名称,发货人省份,发货人市,发货人区县,发货人地址,发货人手机
     */
    private String senderInfo;
}
