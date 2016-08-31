/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-08-31.
 */
@Data
public class SurSungLogisticSearch {

    /**
     * 店铺编号
     */
    @JSONField(name = "shop_id")
    private Integer shopId;

    /**
     * 第几页，从1 开始
     */
    @JSONField(name = "page_index")
    private Integer pageIndex;

    /**
     * 默认 50，最大不超过 50
     */
    @JSONField(name = "page_size")
    private Integer pageSize;

    /**
     * 修改起始时间，和 结束时间必须同时存在，时间间隔不能超过七天
     */
    @JSONField(name = "modified_begin")
    private String modifiedBegin;

    /**
     * 修改结束时间，和 结束时间必须同时存在，时间间隔不能超过七天
     */
    @JSONField(name = "modified_end")
    private String modifiedEnd;

    /**
     * 内部订单号
     */
    @JSONField(name = "o_ids")
    private List<Long> oIds;

    /**
     * 平台订单号号
     */
    @JSONField(name = "so_ids")
    private List<String> soIds;
}
