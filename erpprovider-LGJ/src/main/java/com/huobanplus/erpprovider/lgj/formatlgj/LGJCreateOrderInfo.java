/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.lgj.formatlgj;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;


/**
 * 创建订单主体
 * Created by allan on 4/19/16.
 */
@Data
public class LGJCreateOrderInfo {
    /**
     * 第三方订单号
     */
    private String thirdsn;

    /**
     * 礼管家平台订单号
     */
    private String ordersn;

    @JSONField(name = "skus")
    private List<LGJCreateOrderItem> createOrderItems;
    /**
     * 订单金额
     */
    @JSONField(name = "order_amount")
    private int orderAmount;
    /**
     * 收货人姓名
     */
    private String name;
    /**
     * 收货人联系电话
     */
    private String mobile;
    /**
     * 收货人省 一级地址
     */
    private String province;
    /**
     * 收货人市 二级地址
     */
    private String city;
    /**
     * 收货人国家 三级地址
     */
    private String county;
    /**
     * 四级地址(如果该地区有四级地址，则必须传递四级地址)
     */
    private String town;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 请求方法名
     */
    private String func;
    /**
     * 请求Token
     */
    private String token;

}
