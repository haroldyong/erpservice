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

/**
 * Created by wuxiongliu on 2016-11-11.
 */
@Data
public class AfterSaleItem {

    // 订单id
    public String orderId;

    // 货品编号
    public String skuId;
    // 退货数量
    public int returnNum;

    // sku退款金额
    public double amount;
    // 可选：退货，换货，补发，或者其他
    public String type;
    // 名称
    public String name;

    public String pic;
    // 规格属性
    public String properties;

}
