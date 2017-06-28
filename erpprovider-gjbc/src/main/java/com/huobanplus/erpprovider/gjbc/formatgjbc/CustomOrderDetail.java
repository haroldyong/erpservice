/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gjbc.formatgjbc;

import lombok.Data;

/**
 * Created by montage on 2017/6/27.
 */
@Data
public class CustomOrderDetail {

    private int goodsOrder;

    private String goodsName;

    private String goodsModel;

    private String codeTs;

    private double grossWeight;

    private double unitPrice;

    private String goodsUnit;

    private String originCountry;

    private int goodsCount;

    private String currency;
}
