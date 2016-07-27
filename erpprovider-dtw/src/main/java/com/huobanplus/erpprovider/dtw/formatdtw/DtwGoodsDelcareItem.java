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

/**
 * Created by wuxiongliu on 2016/6/30.
 */
@Data
public class DtwGoodsDelcareItem {
    /**
     * 商品序号，与发货订单或综合订单项次保持一致(最长50个字符)。(必填)
     */
    @JSONField(name = "goodsOrder")
    private int goodsOrder;

    /**
     * 行邮税号(必填),4月8号后改用商品HScode:填写商品对应的HS编码
     */
    @JSONField(name = "codeTs")
    private String codeTs;

    /**
     * 商品货号，对应订单上的产品编码(必填)
     */
    @JSONField(name = "goodsItemNo")
    private String goodsItemNo;

    /**
     * 物品名称(必填)
     */
    @JSONField(name = "goodsName")
    private String goodsName;

    /**
     * 商品规格，型号(必填)
     */
    @JSONField(name = "goodsModel")
    private String goodsModel;

    /**
     * 成交币制（三字代码）(必填)
     */
    @JSONField(name = "tradeCurr")
    private String tradeCurr;

    /**
     * 成交总价（包裹实际成交的金额）(必填)
     */
    @JSONField(name = "tradeTotal")
    private double tradeTotal;

    /**
     * 申报单价(必填)
     */
    @JSONField(name = "declPrice")
    private double declPrice;

    /**
     * 申报总价（物品申报的价值）(必填)
     */
    @JSONField(name = "declTotalPrice")
    private double declTotalPrice;

    /**
     * 用途
     */
    @JSONField(name = "useTo")
    private String useTo;

    /**
     * 申报数量
     */
    @JSONField(name = "declareCount")
    private double declareCount;

    /**
     * 申报计量单位（三字代码）
     */
    @JSONField(name = "goodsUnit")
    private String goodsUnit;

    /**
     * 商品毛重
     */
    @JSONField(name = "goodsGrossWeight")
    private double goodsGrossWeight;

    /**
     * 第一单位(必填)
     */
    @JSONField(name = "firstUnit")
    private String firstUnit;

    /**
     * 第一数量(必填)
     */
    @JSONField(name = "firstCount")
    private double firstCount;

    /**
     * 第二单位
     */
    @JSONField(name = "secondUnit")
    private String secondUnit;

    /**
     * 第二数量
     */
    @JSONField(name = "secondCount")
    private double secondCount;

    /**
     * 产销国（三字代码）(必填)
     */
    @JSONField(name = "originCountry")
    private String originCountry;
}
