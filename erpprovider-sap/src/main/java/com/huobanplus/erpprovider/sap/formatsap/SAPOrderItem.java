/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.formatsap;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/4/21.
 */
@Data
public class SAPOrderItem {

    private Integer itemId;
    private String orderId;
    private String unionOrderId;
    private String productBn;
    private String name;
    /**
     * 成本价
     */
    private double cost;
    /**
     * 销售价
     */
    private double price;
    /**
     * 总金额
     */
    private double amount;
    /**
     * 购买数量
     */
    private int num;

}
