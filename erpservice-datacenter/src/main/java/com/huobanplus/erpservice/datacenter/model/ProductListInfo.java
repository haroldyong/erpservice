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

import java.util.List;

/**
 * Created by wuxiongliu on 2017-01-16.
 */
@Data
public class ProductListInfo extends BaseInfo {

    /**
     * 总记录数
     */
    private int recordCount;
    /**
     * 当前页
     */
    private int pageIndex;
    /**
     * 每页大小
     */
    private int pageSize;
    /**
     * 是否有下一页
     */
    private boolean hasNext;

    /**
     * 货品列表
     */
    private List<ProductInfo> products;
}
