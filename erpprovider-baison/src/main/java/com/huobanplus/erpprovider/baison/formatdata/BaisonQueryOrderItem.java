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

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2017-01-06.
 */
@Data
public class BaisonQueryOrderItem {

    /**
     * 商品货号
     */
    @JSONField(name = "goods_sn")
    private String goods_sn;

    /**
     * 商品条码
     */
    @JSONField(name = "barcode")
    private String barcode;

    /**
     * 颜色代码
     */
    @JSONField(name = "color_code")
    private String color_code;

    /**
     * 颜色名称
     */
    @JSONField(name = "color_name")
    private String color_name;

    /**
     * 尺码代码
     */
    @JSONField(name = "size_code")
    private String size_code;

    /**
     * 尺码名称
     */
    @JSONField(name = "size_name")
    private String size_name;

    /**
     * 商品数量
     */
    @JSONField(name = "goods_number")
    private String goods_number;

    /**
     * 品牌ID
     */
    @JSONField(name = "brand_id")
    private String brand_id;


    /**
     * 品牌名称
     */
    @JSONField(name = "brand_name")
    private String brand_name;
}
