/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.formatdtw;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-07-27.
 */
@Data
@JacksonXmlRootElement(localName = "xml")
public class WeixinCustomer {

    /**
     * 签名
     */
    @JacksonXmlProperty(localName = "sign")
    private String sign;

    /**
     * 微信分配的公众账号ID
     */
    @JacksonXmlProperty(localName = "appid")
    private String appid;

    /**
     * 微信支付分配的商户号
     */
    @JacksonXmlProperty(localName = "mch_id")
    private String mchId;

    /**
     * 商户系统内部的订单号
     */
    @JacksonXmlProperty(localName = "out_trade_no")
    private String outTradeNo;

    /**
     * 微信支付返回的订单号
     */
    @JacksonXmlProperty(localName = "transaction_id")
    private String transactionId;

    /**
     * NO 无需上报海关
     * GUANGZHOU 广州
     * HANGZHOU 杭州
     * NINGBO 宁波
     * ZHENGZHOU_BS 郑州（保税物流中心）
     * CHONGQING 重庆
     * XIAN 西安
     * SHANGHAI 上海
     * ZHENGZHOU_ZH 郑州（综保区）
     */
    @JacksonXmlProperty(localName = "customs")
    private String customs;

    /**
     * 商户在海关登记的备案号，customs非NO，此参数必填
     */
    @JacksonXmlProperty(localName = "mch_customs_no")
    private String mchCustomsNo;

    /**
     * 商户子订单号，如有拆单/重新报关则必传
     */
    @JacksonXmlProperty(localName = "sub_order_no")
    private String subOrderNo;

    /**
     * 微信支付订单支付时使用的币种，暂只支持人民币CNY，如有拆单/重新报关则必传
     */
    @JacksonXmlProperty(localName = "fee_type")
    private String feeType;

    /**
     * 子订单金额，以分为单位，不能超过原订单金额，order_fee=transport_fee+product_fee（应付金额=物流费+商品价格）
     */
    @JacksonXmlProperty(localName = "order_fee")
    private String orderFee;

    /**
     *物流费用，以分为单位
     */
    @JacksonXmlProperty(localName = "transport_fee")
    private String transportFee;

    /**
     * 商品费用，以分为单位，
     */
    @JacksonXmlProperty(localName = "product_fee")
    private String productFee;

    /**
     * 关税，以分为单位
     */
    @JacksonXmlProperty(localName = "duty")
    private String duty;

    /**
     * 暂只支持身份证，该参数是指用户信息，商户若有用户信息，可上送，系统将以商户上传的数据为准，进行海关通关报备；
     */
    @JacksonXmlProperty(localName = "cert_type")
    private String certType;

    /**
     * 身份证号，该参数是指用户信息，商户若有用户信息，可上送，系统将以商户上传的数据为准，进行海关通关报备；
     */
    @JacksonXmlProperty(localName = "cert_id")
    private String certId;

    /**
     * 用户姓名，该参数是指用户信息，商户若有用户信息，可上送，系统将以商户上传的数据为准，进行海关通关报备；
     */
    @JacksonXmlProperty(localName = "name")
    private String name;

    private String device_info;

    private String body;

    private String nonce_str;
}
