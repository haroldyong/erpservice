/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 货品退货信息
 * Created by allan on 6/29/16.
 */
@Setter
@Getter
public class ProReturnInfo {
    /**
     * 商品编码
     */
    private String goodBn;
    /**
     * 货品编码
     */
    private String productBn;
    /**
     * 退货数量
     */
    private int returnNum;
    /**
     * 价格
     */
    private double price;
}
