/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 * 管易查询实体
 */
@Data
public class GYStockSearch {

    /**
     * 页码
     */
    @JSONField(name = "page_no")
    private Integer pageNo;

    /**
     * 	每页大小
     */
    @JSONField(name = "page_size")
    private Integer pageSize;

    /**
     *  修改时间开始段
     */
    @JSONField(name = "start_date")
    private String startDate;

    /**
     *  修改时间结束段
     */
    @JSONField(name = "end_date")
    private String endDate;

    /**
     * 商品条码
     */
    @JSONField(name = "barcode")
    private String barCode;

}
