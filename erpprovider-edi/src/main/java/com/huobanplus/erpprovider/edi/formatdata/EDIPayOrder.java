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
public class EDIPayOrder {

    /**
     *
     */
    @JSONField(name = "paytime")
    private String paytime;

    /**
     *
     */
    @JSONField(name = "paymentNo")
    private String paymentNo;

    /**
     *
     */
    @JSONField(name = "orderSeqNo")
    private String orderSeqNo;

    /**
     *
     */
    @JSONField(name = "source")
    private String source;

    /**
     *
     */
    @JSONField(name = "idnum")
    private String idnum;

    /**
     *
     */
    @JSONField(name = "name")
    private String name;

    /**
     *
     */
    @JSONField(name = "merId")
    private String merId;
}
