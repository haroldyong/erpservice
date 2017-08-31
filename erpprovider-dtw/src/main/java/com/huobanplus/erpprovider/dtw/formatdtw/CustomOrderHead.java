/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.formatdtw;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-08-18.
 */
@Data
public class CustomOrderHead {

    /**
     * 电商企业编码
     */
    @JacksonXmlProperty(localName = "eCommerceCode")
    private String commerceCode;

    /**
     * 电商企业名称
     */
    @JacksonXmlProperty(localName = "eCommerceName")
    private String commerceName;

    /**
     * 进出口标志 I:进口E:出口
     */
    @JacksonXmlProperty(localName = "ieFlag")
    private String ieFlag;

    /**
     * 支付类型 01:银行卡支付 02:余额支付 03:其他
     */
    @JacksonXmlProperty(localName = "payType")
    private String payType;

    /**
     * 支付平台在跨境平台备案编号（新增）
     */
    @JacksonXmlProperty(localName = "payCompanyCode")
    private String payCompanyCode;

    /**
     * 对接总署版必填,支付平台在跨境平台备案名称
     */
    @JacksonXmlProperty(localName = "payCompanyName")
    private String payCompanyName;

    /**
     * 支付成功后，支付平台反馈给电商平台的支付单号
     */
    @JacksonXmlProperty(localName = "payNumber")
    private String payNumber;

    /**
     * 订单总金额 =货款+订单税款+运费+保费
     */
    @JacksonXmlProperty(localName = "orderTotalAmount")
    private double orderTotalAmount;

    /**
     * 订单编号
     */
    @JacksonXmlProperty(localName = "orderNo")
    private String orderNo;

    /**
     * 订单税款 交易过程中商家向用户征收的税款，按缴税新政计算填写
     */
    @JacksonXmlProperty(localName = "orderTaxAmount")
    private double orderTaxAmount;

    /**
     * 订单货款  与成交总价一致，按以电子订单的实际销售价格作为完税价格
     */
    @JacksonXmlProperty(localName = "orderGoodsAmount")
    private double orderGoodsAmount;

    /**
     * 非现金抵扣价
     */
    @JacksonXmlProperty(localName = "discount")
    private double discount;

    /**
     * 运费 交易过程中商家向用户征收的运费，免邮
     */
    @JacksonXmlProperty(localName = "feeAmount")
    private double feeAmount;

    /**
     * 保费 商家向用户征收的保价费用，无保费可填写0
     */
    @JacksonXmlProperty(localName = "insureAmount")
    private double insureAmount;

    /**
     * 电商平台备案名称
     */
    @JacksonXmlProperty(localName = "companyName")
    private String companyName;

    /**
     * 电商平台备案编号
     */
    @JacksonXmlProperty(localName = "companyCode")
    private String companyCode;

    /**
     * 成交时间 格式：2014-02-18 15:58:11
     */
    @JacksonXmlProperty(localName = "tradeTime")
    private String tradeTime;

    /**
     * 成交币制
     */
    @JacksonXmlProperty(localName = "currCode")
    private String currCode;

    /**
     * 成交总价 与订单货款一致
     */
    @JacksonXmlProperty(localName = "totalAmount")
    private double totalAmount;

    /**
     * 收件人Email
     */
    @JacksonXmlProperty(localName = "consigneeEmail")
    private String consigneeEmail;

    /**
     * 收件人联系方式
     */
    @JacksonXmlProperty(localName = "consigneeTel")
    private String consigneeTel;

    /**
     * 收件人姓名
     */
    @JacksonXmlProperty(localName = "consignee")
    private String consignee;

    /**
     * 收件人地址
     */
    @JacksonXmlProperty(localName = "consigneeAddress")
    private String consigneeAddress;

    /**
     * 总件数
     */
    @JacksonXmlProperty(localName = "totalCount")
    private int totalCount;

    /**
     * 发货方式（物流方式） 非必填
     */
    @JacksonXmlProperty(localName = "postMode")
    private String postMode;

    /**
     * 发件人国别 必填
     */
    @JacksonXmlProperty(localName = "senderCountry")
    private String senderCountry;

    /**
     * 发件人姓名
     */
    @JacksonXmlProperty(localName = "senderName")
    private String senderName;

    /**
     * 购买人ID
     */
    @JacksonXmlProperty(localName = "purchaserId")
    private String purchaserId;

    /**
     * 物流企业名称
     */
    @JacksonXmlProperty(localName = "logisCompanyName")
    private String logisCompanyName;

    /**
     * 物流企业编号
     */
    @JacksonXmlProperty(localName = "logisCompanyCode")
    private String logisCompanyCode;

    /**
     * 邮编
     */
    @JacksonXmlProperty(localName = "zipCode")
    private String zipCode;

    /**
     * 备注
     */
    @JacksonXmlProperty(localName = "note")
    private String note;

    /**
     * 运单号列表
     */
    @JacksonXmlProperty(localName = "wayBills")
    private String wayBills;

    /**
     * 汇率 人民币，统一填写1
     */
    @JacksonXmlProperty(localName = "rate")
    private String rate;

    /**
     * 个人委托申报协议
     */
    @JacksonXmlProperty(localName = "userProcotol")
    private String userProcotol;
}
