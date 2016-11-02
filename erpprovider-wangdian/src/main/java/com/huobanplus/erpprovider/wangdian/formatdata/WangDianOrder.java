/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.wangdian.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-11-02.
 */
@Data
public class WangDianOrder {

    /**
     * 出入库类型标记（0普通入库，1普通出库，2采购入库，3销售订单） 必须
     */
    @JSONField(name = "OutInFlag")
    private int outInFlag;

    /**
     * 外部单据编号 必须
     */
    @JSONField(name = "IF_OrderCode")
    private String iFOrderCode;

    /**
     * 仓库编号(销售出库时非空) 必须
     */
    @JSONField(name = "WarehouseNO")
    private String warehouseNO;

    /**
     * 备注
     */
    @JSONField(name = "Remark")
    private String remark;

    /**
     * 出入库原因
     */
    @JSONField(name = "TheCause")
    private String theCause;

    /**
     * 供应商编号
     */
    @JSONField(name = "ProviderNO")
    private String providerNO;

    /**
     * 供应商名称
     */
    @JSONField(name = "ProviderName")
    private String providerName;

    /**
     * 供应商联系人
     */
    @JSONField(name = "LinkMan")
    private String linkMan;

    /**
     * 联系人电话
     */
    @JSONField(name = "LinkManTel")
    private String linkManTel;

    /**
     * 联系人地址
     */
    @JSONField(name = "LinkManAdr")
    private String linkManAdr;

    /**
     * 业务员编号
     */
    @JSONField(name = "RegOperatorNO")
    private String regOperatorNO;

    /**
     * 货款合计(销售出库时非空) 必须
     */
    @JSONField(name = "GoodsTotal")
    private double goodsTotal;

    /**
     * 优惠金额
     */
    @JSONField(name = "FavourableTotal")
    private String favourableTotal;

    /**
     * 其他费用
     */
    @JSONField(name = "OtherFee")
    private String otherFee;

    /**
     * 货到付款标记，0为不需要货到付款，1为需要货到付款
     */
    @JSONField(name = "COD_Flag")
    private int codFlag;

    /**
     * 订单付款金额（含运费）（出库时非空）
     */
    @JSONField(name = "OrderPay")
    private double orderPay;

    /**
     * 运费（出库时非空）
     */
    @JSONField(name = "LogisticsPay")
    private double logisticsPay;

    /**
     * 物流公司编号
     */
    @JSONField(name = "LogisticsCode")
    private String logisticsCode;

    /**
     * 订单所属店铺名称（出库时非空）
     */
    @JSONField(name = "ShopName")
    private String shopName;

    /**
     * 客户平台昵称
     */
    @JSONField(name = "NickName")
    private String nickName;

    /**
     * 收货人姓名（出库时非空）
     */
    @JSONField(name = "BuyerName")
    private String buyerName;

    /**
     * 收货人邮编（出库时非空）
     */
    @JSONField(name = "BuyerPostCode")
    private String buyerPostCode;

    /**
     * 收货人联系方式
     */
    @JSONField(name = "BuyerTel")
    private String buyerTel;

    /**
     * 收货人所在省（出库非空）
     */
    @JSONField(name = "BuyerProvince")
    private String buyerProvince;

    /**
     * 收货人所在市（出库非空）
     */
    @JSONField(name = "BuyerCity")
    private String buyerCity;

    /**
     * 收货人所在区、县（出库非空）
     */
    @JSONField(name = "BuyerDistrict")
    private String buyerDistrict;

    /**
     * 收货地址（出库非空）
     */
    @JSONField(name = "BuyerAdr")
    private String buyerAdr;

    /**
     * 收货人emaill
     */
    @JSONField(name = "BuyerEmail")
    private String buyerEmail;

    /**
     * 是否需要发票，1需要，0不需要
     */
    @JSONField(name = "NeedInvoice")
    private int needInvoice;

    /**
     * 发票抬头
     */
    @JSONField(name = "InvoiceTitle")
    private String invoiceTitle;

    /**
     * 发票内容
     */
    @JSONField(name = "InvoiceContent")
    private String invoiceContent;

    /**
     * 货品详细的行项目数量
     */
    @JSONField(name = "ItemCount")
    private String itemCount;

    /**
     * 付款时间
     */
    @JSONField(name = "PayTime")
    private String payTime;

    /**
     * 交易时间
     */
    @JSONField(name = "TradeTime")
    private String tradeTime;

    /**
     * 支付单号
     */
    @JSONField(name = "ChargeID")
    private String chargeID;

    /**
     * 货品明细
     */
    @JSONField(name = "orderItem")
    private String orderItem;
}
