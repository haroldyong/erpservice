package com.huobanplus.erpprovider.gjbbc.common;

import lombok.Data;

/**
 * Created by hxh on 2017-08-15.
 */
@Data
public class GjbbcSysData {
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
     * 仓库代码
     */
    private String warehouseCode;
    /**
     * 订单网址
     */
    private String pWeb;
    /**
     * 发货人信息 逗号分隔的字符串;发件人姓名,发件人电话，发货人国家代码，发货人省、市、区代码,发货人地址,,
     */
    private String senderInfo;
}
