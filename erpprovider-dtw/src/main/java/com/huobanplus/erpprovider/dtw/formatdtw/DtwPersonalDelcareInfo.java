/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.formatdtw;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016/6/30.
 */
@Data
public class DtwPersonalDelcareInfo {

    /**
     * 身份认证Key(必填)
     */
    @JSONField(name = "PassKey")
    private String passKey;

    /**
     * 电商企业发货订单号(必填)
     */
    @JSONField(name = "Msgid")
    private String msgid;

    /**
     * 预录入号
     */
    @JSONField(name = "preEntryNumber")
    private String preEntryNumber;

    /**
     * 电商平台名称（电商平台在跨境电商通关服务平台的备案名称）
     */
    @JSONField(name = "companyName")
    private String companyName;

    /**
     * 电商平台代码（电商平台在跨境电商通关服务的备案编号）
     */
    @JSONField(name = "companyCode")
    private String companyCode;

    /**
     * 进口类型（0一般进口，1保税进口）(必填)
     */
    @JSONField(name = "importType")
    private int importType;

    /**
     * 订单编号(必填)
     */
    @JSONField(name = "orderNo")
    private String orderNo;

    /**
     * 分运单号（选填）
     */
    @JSONField(name = "wayBill")
    private String wayBill;

    /**
     * 件数(必填)
     */
    @JSONField(name = "packNo")
    private int packNo;

    /**
     * 毛重（千克）
     */
    @JSONField(name = "grossWeight")
    private double grossWeight;

    /**
     * 净重（千克）(必填)
     */
    @JSONField(name = "netWeight")
    private double netWeight;

    /**
     * 备注
     */
    @JSONField(name = "remark")
    private String remark;

    /**
     * 发件人(必填)
     */
    @JSONField(name = "senderName")
    private String senderName;

    /**
     * 收件人姓名(必填)
     */
    @JSONField(name = "consignee")
    private String consignee;

    /**
     * 发件人城市
     */
    @JSONField(name = "senderCity")
    private String senderCity;

    /**
     * 支付人证件类型,1-身份证（试点期间只能是身份证）,2-护照,3-其他
     */
    @JSONField(name = "paperType")
    private String paperType;

    /**
     * 支付人证件号
     */
    @JSONField(name = "paperNumber")
    private String paperNumber;

    /**
     * 购买人电话，海关监管对象的电话,对应订单中的购买人联系电话
     */
    @JSONField(name = "purchaserTelNumber")
    private String purchaserTelNumber;

    /**
     * 订购人证件类型,1-身份证；2-其它
     */
    @JSONField(name = "buyerIdType")
    private String buyerIdType;

    /**
     * 订购人证件号码,海关监控对象的身份证号,对应订单购买人证件号码
     */
    @JSONField(name = "buyerIdNumber")
    private String buyerIdNumber;

    /**
     * 订购人姓名
     */
    @JSONField(name = "buyerName")
    private String buyerName;

    /**
     * 申报金额,表体所有商品总价的和+运费+保费。(必填)
     */
    @JSONField(name = "worth")
    private double worth;

    /**
     * 运费，交易过程中商家向用户征收的运费，免邮模式填写0
     */
    @JSONField(name = "feeAmount")
    private double feeAmount;

    /**
     * 保费，商家向用户征收的保价费用，无保费可填写0
     */
    @JSONField(name = "insureAmount")
    private double insureAmount;

    /**
     * 币制(必填)
     */
    @JSONField(name = "currCode")
    private String currCode;

    /**
     * 主要货物名称(必填)
     */
    @JSONField(name = "mainGName")
    private String mainGName;

    /**
     * 贸易国别（起运地）（三字代码）(必填)
     */
    @JSONField(name = "tradeCountry")
    private String tradeCountry;

    /**
     * 发件人国别（三字代码）(必填)
     */
    @JSONField(name = "senderCountry")
    private String senderCountry;

    /**
     * 明细列表
     */
    @JSONField(name = "Items")
    private List<DtwGoodsDelcareItem> Items;

    /**
     * 电商企业编码(必填),电商企业在跨境平台备案编码
     */
    @JSONField(name = "eCommerceCode")
    private String eCommerceCode;

    /**
     * 电商企业名称(必填),电商企业在跨境平台备名称
     */
    @JSONField(name = "eCommerceName")
    private String eCommerceName;
}
