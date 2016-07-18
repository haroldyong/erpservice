/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.formatdtw;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-07-18.
 * 顾客面单数据
 */
@Data
public class DtwWayBill {

    /**
     * 身份认证Key(必填)
     */
    @JSONField(name= "PassKey")
    private String passKey;

    /**
     * 电商企业发货订单号(必填)
     */
    @JSONField(name= "Msgid")
    private String msgid;

    /**
     * 运单号（快递单号）(必填)
     */
    @JSONField(name= "BillNo")
    private String billNo;

    /**
     * 发货人名称(必填)
     */
    @JSONField(name= "Shipper")
    private String shipper;

    /**
     * 发货人省份
     */
    @JSONField(name= "ShipperPro")
    private String shipperPro;

    /**
     * 发货人市
     */
    @JSONField(name= "ShipperCity")
    private String shipperCity;

    /**
     * 发货人区县
     */
    @JSONField(name= "ShipperDistrict")
    private String shipperDistrict;

    /**
     * 发货人地址(必填)
     */
    @JSONField(name= "ShipperAddress")
    private String shipperAddress;

    /**
     * 发货人手机
     */
    @JSONField(name= "ShipperMobile")
    private String shipperMobile;

    /**
     * 发货人电话
     */
    @JSONField(name= "ShipperTel")
    private String shipperTel;

    /**
     * 发货人所在国三字代码(必填)
     */
    @JSONField(name= "ShipperCountry")
    private String shipperCountry;

    /**
     * 发货人邮编
     */
    @JSONField(name= "ShipperZip")
    private String shipperZip;

    /**
     * 收货人名称(必填)
     */
    @JSONField(name= "Consignee")
    private String consignee;

    /**
     * 收件人地址(必填)
     */
    @JSONField(name= "ConsigneeAdd")
    private String consigneeAdd;

    /**
     * 收货人手机(手机与电话二选一)
     */
    @JSONField(name= "ConsigneeMobile")
    private String consigneeMobile;

    /**
     * 收货人电话(手机与电话二选一)
     */
    @JSONField(name= "ConsigneeTel")
    private String consigneeTel;

    /**
     * 收货人所在国(进口为中国三字代码)(必填)
     */
    @JSONField(name= "ConsigneeCountry")
    private String consigneeCountry;

    /**
     * 收货人邮编
     */
    @JSONField(name= "ConsigneeZip")
    private String consigneeZip;

    /**
     * 毛重(必填)
     */
    @JSONField(name= "Weight")
    private double weight;

    /**
     * 件数(必填)
     */
    @JSONField(name= "Qty")
    private int qty;

    /**
     * 进出口标志(必填) 73:进口，69:出口
     */
    @JSONField(name= "IEType")
    private int IEType;

    /**
     * 集货/备货(必填) 1:集货，2:备货
     */
    @JSONField(name= "StockFlag")
    private int stockFlag;

    /**
     * 批次号
     */
    @JSONField(name= "LotNo")
    private String lotNo;

    /**
     * 净重(必填)
     */
    @JSONField(name= "NetWeight")
    private double netWeight;

    /**
     * 总运单号
     */
    @JSONField(name= "TotalBillNo")
    private String totalBillNo;

    /**
     * 电商企业编码(必填),电商企业在跨境平台备案编码
     */
    @JSONField(name= "eCommerceCode")
    private String eCommerceCode;

    /**
     * 电商企业名称(必填),电商企业在跨境平台备名称
     */
    @JSONField(name= "eCommerceName")
    private String eCommerceName;
}
