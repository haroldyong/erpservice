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

package com.huobanplus.erpprovider.baison.formatdata;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2017-01-06.
 */
@Data
public class BaisonQueryOrder {

    @JSONField(name = "order_sn")
    private String orderSn;

    @JSONField(name = "shipping_status")
    private String shippingStatus;

    @JSONField(name = "shipping_code")
    private String shippingCode;

    @JSONField(name = "shipping_sn")
    private String shippingSn;

    @JSONField(name = "shipping_fee")
    private double shippingFee;

    @JSONField(name = "shipping_name")
    private String shippingName;


    /**
     * jsonArray 形式的字符串列表
     */
    @JSONField(name = "orderDetailGets")
    private List<JSONObject> orderDetails;

}
