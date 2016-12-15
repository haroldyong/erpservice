/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.common;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Data
public class DtwSysData {

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * passkey
     */
    private String passKey;

    /**
     * 电商企业编码(电商企业在跨境平台备案编码)
     */
    private String eCommerceCode;

    /**
     * 电商企业名称
     */
    private String eCommerceName;


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


}
