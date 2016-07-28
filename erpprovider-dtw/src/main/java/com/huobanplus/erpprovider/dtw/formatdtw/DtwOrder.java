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
 * Created by wuxiongliu on 2016/6/13.
 */
@Data
public class DtwOrder {

    @JSONField(name = "PassKey")
    private String passKey;

    /**
     * 预录入号 允许为空 自行推送个人物品申报信息时需要此选项
     */
    private String preEntryNumber;

    /**
     * 进口类型（0一般进口，1保税进口）(必填)
     */
    @JSONField(name = "importType")
    private int importType;

    /**
     * 订单类型（1：普通订单：与快递已经完成对接，2：综合订单：委托大田与快递公司对接）
     */
    @JSONField(name = "OrderType")
    private int orderType;

    /**
     * 电商企业发货单号(必填)
     */
    @JSONField(name = "Msgid")
    private String msgid;

    /**
     * 支付类型
     */
    @JSONField(name = "payType")
    private int payType;

    /**
     * 支付公司编码
     */
    @JSONField(name = "payCompanyCode")
    private String payCompanyCode;

    /**
     * 支付单号
     */
    @JSONField(name = "payNumber")
    private String payNumber;

    /**
     * 订单总金额
     */
    @JSONField(name = "orderTotalAmount")
    private double orderTotalAmount;

    /**
     * 订单货款
     */
    @JSONField(name = "orderGoodsAmount")
    private double orderGoodsAmount;

    /**
     * 订单号
     */
    @JSONField(name = "orderNo")
    private String orderNo;

    /**
     * 订单税款 交易过程中商家向用户征收的税款，免税模式填写0
     */
    @JSONField(name = "orderTaxAmount")
    private double orderTaxAmount;

    /**
     * 总件数
     */
    @JSONField(name = "totalCount")
    private int totalCount;

    /**
     * 成交总价
     */
    @JSONField(name = "totalAmount")
    private double totalAmount;

    /**
     * 物流企业编号(必填)
     */
    @JSONField(name = "logisCompanyCode")
    private String logisCompanyCode;

    /**
     * 物流企业名称(必填)
     */
    @JSONField(name = "logisCompanyName")
    private String logisCompanyName;

    /**
     * 购买人ID
     */
    @JSONField(name = "purchaserId")
    private String purchaserId;

    /**
     * 发货人名称
     */
    @JSONField(name = "Shipper")
    private String shipper;

    /**
     * 发货人省份 允许为空
     */
    @JSONField(name = "ShipperPro")
    private String shipperPro;

    /**
     * 发货人市 允许为空
     */
    @JSONField(name = "ShipperCity")
    private String shipperCity;

    /**
     * 发货人区县 允许为空
     */
    @JSONField(name = "ShipperDistrict")
    private String shipperDistrict;

    /**
     * 发货人地址
     */
    @JSONField(name = "ShipperAddress")
    private String shipperAddress;

    /**
     * 发货人手机 允许为空
     */
    @JSONField(name = "ShipperMobile")
    private String shipperMobile;

    /**
     * 允许为空
     */
    @JSONField(name = "ShipperTel")
    private String shipperTel;

    /**
     * 发货人所在国（三字代码）
     */
    @JSONField(name = "ShipperCountry")
    private String shipperCountry;

    /**
     * 发货人邮编
     */
    @JSONField(name = "ShipperZip")
    private String shipperZip;

    /**
     * 收货人名称
     */
    @JSONField(name = "Consignee")
    private String consignee;

    /**
     * 收货人省份
     */
    @JSONField(name = "ConsigneePro")
    private String consigneePro;

    /**
     * 收货人市
     */
    @JSONField(name = "ConsigneeCity")
    private String consigneeCity;

    /**
     * 收货人区县
     */
    @JSONField(name = "ConsigneeDistrict")
    private String consigneeDistrict;

    /**
     * 收件人地址
     */
    @JSONField(name = "ConsigneeAdd")
    private String consigneeAdd;

    /**
     * 收货人手机(手机与电话二选一)
     */
    @JSONField(name = "ConsigneeMobile")
    private String consigneeMobile;

    /**
     * 收货人电话(手机与电话二选一)
     */
    @JSONField(name = "ConsigneeTel")
    private String consigneeTel;

    /**
     * 收货人所在国(进口为中国(142))
     */
    @JSONField(name = "ConsigneeCountry")
    private String consigneeCountry;

    /**
     * 收货人邮编
     */
    @JSONField(name = "ConsigneeZip")
    private String consigneeZip;

    /**
     * 毛重
     */
    @JSONField(name = "Weight")
    private double weight;

    /**
     * 批次号
     */
    @JSONField(name = "LotNo")
    private String lotNo;

    /**
     * 净重
     */
    @JSONField(name = "NetWeight")
    private double netWeight;

    /**
     * 进出口标记（I进口,O出口）
     */
    private int ieFlag;

    /**
     * 订单明细
     */
    @JSONField(name = "Items")
    List<DtwOrderItem> dtwOrderItems;

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
