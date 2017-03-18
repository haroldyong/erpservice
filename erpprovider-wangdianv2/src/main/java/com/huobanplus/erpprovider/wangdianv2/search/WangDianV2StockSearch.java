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

package com.huobanplus.erpprovider.wangdianv2.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2017-02-24.
 */
@Data
public class WangDianV2StockSearch {

    /**
     * 仓库编号(不指定则返回所有仓库)
     */
    @JSONField(name = "warehouse_no")
    private String warehouseNo;

    /**
     * 货品的商家编码(不指定则返回所有商品)
     */
    @JSONField(name = "spec_no")
    private String specNo;

    /**
     * 条形码
     */
    @JSONField(name = "barcode")
    private String barcode;

    /**
     * 开始时间（最后更新时间）
     */
    @JSONField(name = "start_time")
    private String startTime;

    /**
     * 结束时间（最后更新时间）
     */
    @JSONField(name = "end_time")
    private String endTime;

    /**
     * 页号,从0页开始,默认为0
     */
    @JSONField(name = "page_no")
    private int pageNo;

    /**
     * 分页大小,默认40,最大100
     */
    @JSONField(name = "page_size")
    private int pageSize;
}
