/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.formatdtw;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016-08-18.
 */
@Data
public class CustomOrderHead {

    private String eCommerceCode;

    private String eCommerceName;

    private String ieFlag;

    private String payType;

    private String payCompanyCode;

    private String payNumber;

    private double orderTotalAmount;

    private String orderNo;

    private double orderTaxAmount;

    private double orderGoodsAmount;

    private double feeAmount;

    private double insureAmount;

    private String companyName;

    private String companyCode;

    private String tradeTime;

    private String currCode;

    private double totalAmount;

    private String consigneeEmail;

    private String consigneeTel;

    private String consignee;

    private String consigneeAddress;

    private int totalCount;

    private String postMode;

    private String senderCountry;

    private String senderName;

    private String purchaserId;

    private String logisCompanyName;

    private String logisCompanyCode;

    private String zipCode;

    private String note;

    private String wayBills;

    private String rate;

    private String userProcotol;
}
