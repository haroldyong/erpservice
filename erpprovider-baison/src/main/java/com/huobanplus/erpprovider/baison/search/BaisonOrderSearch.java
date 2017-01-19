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

package com.huobanplus.erpprovider.baison.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-12-26.
 */
@Data
public class BaisonOrderSearch {

    /**
     * 查询起始时间
     */
    @JSONField(name = "startModified")
    private String startModified;

    /**
     * 查询结束时间
     */
    @JSONField(name = "endModified")
    private String endModified;

    /**
     * 页码
     */
    @JSONField(name = "pageNo")
    private Integer pageNo;

    /**
     * 每页条数
     */
    @JSONField(name = "pageSize")
    private Integer pageSize;

    /**
     * 店铺代码
     */
    @JSONField(name = "sd_code")
    private String sdCode;

    /**
     * 订单编号
     */
    @JSONField(name = "orderSn")
    private String orderSn;

    /**
     * 查询所有字段（固定值:all）
     */
    @JSONField(name = "allowFields")
    private String allowFields;

    /**
     *
     */
    @JSONField(name = "fields")
    private String fields;
}
