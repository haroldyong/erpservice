/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edi.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-09-19.
 */
@Data
public class EDILogiOrder {

    /**
     *
     */
    @JSONField(name = "logisticsNo")
    private String logisticsNo;

    /**
     *
     */
    @JSONField(name = "logisticsName")
    private String logisticsName;

    /**
     *
     */
    @JSONField(name = "logisticsCode")
    private String logisticsCode;

    /**
     *
     */
    @JSONField(name = "consignee")
    private String consignee;

    /**
     *
     */
    @JSONField(name = "province")
    private String province;

    /**
     *
     */
    @JSONField(name = "city")
    private String city;

    /**
     *
     */
    @JSONField(name = "district")
    private String district;

    /**
     *
     */
    @JSONField(name = "consigneeAddr")
    private String consigneeAddr;

    /**
     *
     */
    @JSONField(name = "consigneeTel")
    private String consigneeTel;

    /**
     *
     */
    @JSONField(name = "mailNo")
    private String mailNo;

    /**
     *
     */
    @JSONField(name = "goodsName")
    private String goodsName;
}
