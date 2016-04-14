package com.huobanplus.erpprovider.sap.formatsap;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuzheng on 2016/4/14.
 */
@Setter
@Getter
public class SAPSaleOrderInfo {

    private String orderType; //订单类型
    private String orderSaleFrom;//售达方

    private String numId; //订单号  来自微商城

    private String customName; //顾客名称 来自微商城
    private String customTel; //顾客联系方式 来自微商城
    private String city; //城市 来自微商城

    private String postalcode; //邮政编码 来自微商城
    private String deliveryAddr; //送货地址 来自微商城


    private final String sellOrg = "1000"; // 销售组织
    private final String distributWay = "15"; //分销渠道

    private String goodsInfo;// 产品组
    private String materialCode; //物料编码
    private String orderNum; //订单数量
    private String organization; //单位

    private String provederFactory;//交货工厂 正常订单固定为：8000 退货订单固定为：1000

    private final String goodsAddr = "1017"; //库存地点
    private String discount;  //折扣金额  条件类型固定为：ZWTZ金额*-1
    private boolean invoiceIsopen; //是否需要发票  微商城需要按订单记录顾客是否需要发票，并在订单接口传输到SAP
    private String invoiceTitle; //发票抬头  个人或公司
    private String sapSallId; //SAP销售订单号 微商城需要按单记录对应SAP销售订单号

    private String logiNo;//物流单号 微商城需要按单记录对应物流单号

    private String goodsOrg; //产品组 能录入微商城


}
