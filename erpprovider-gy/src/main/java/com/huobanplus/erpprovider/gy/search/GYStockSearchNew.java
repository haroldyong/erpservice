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
 * Created by allan on 8/2/16.
 */
@Data
public class GYStockSearchNew {
    /**
     * 页码
     */
    @JSONField(name = "page_no")
    private Integer pageNo;

    /**
     * 每页大小
     */
    @JSONField(name = "page_size")
    private Integer pageSize;
    /**
     * 修改时间-开始端
     */
    @JSONField(name = "start_date")
    private String startDate;
    /**
     * 修改时间-结束端
     */
    @JSONField(name = "end_date")
    private String endDate;
    /**
     * 商品条码
     */
    private String barcode;

    /**
     * 仓库代码
     */
    @JSONField(name = "warehouse_code")
    private String warehouseCode;
}
