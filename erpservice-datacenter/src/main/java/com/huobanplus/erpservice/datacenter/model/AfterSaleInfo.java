/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpservice.datacenter.model;

import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-11-11.
 */
@Data
public class AfterSaleInfo {

    private String afterSaleId;
    private String orderId;
    private String type;
    private String logiCompany;
    private String logiNo;
    private int afterStatus;
    // 发货状态
    private int shipStatus;
    private String remark;
    private String questionType;
    // 原始订单金额
    private double totalAmount;
    // 卖家应退金额
    private double refund;
    // 卖家应补偿金额
    private double payment;

    private List<AfterSaleItem> items;

}
