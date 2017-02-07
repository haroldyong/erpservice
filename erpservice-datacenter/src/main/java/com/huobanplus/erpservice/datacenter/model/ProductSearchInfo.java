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
 * Created by wuxiongliu on 2017-01-16.
 */
@Data
public class ProductSearchInfo {

    private Integer pageIndex;
    private Integer pageSize;
    /**
     * 商品新建时间开始时间
     */
    private String beginTime;
    /**
     * 商品新建时间结束时间
     */
    private String endTime;

    /**
     * 商品更新时间开始时间
     */
    private String beginUpdateTime;

    /**
     * 商品更新时间结束时间
     */
    private String endUpdateTime;

    private String orderBy;

}
