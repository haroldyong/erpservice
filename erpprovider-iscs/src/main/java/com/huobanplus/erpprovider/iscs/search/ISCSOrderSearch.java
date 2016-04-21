/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.iscs.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 网仓发货查询条件
 * Created by allan on 4/21/16.
 */
@Data
public class ISCSOrderSearch {
    /**
     * 仓库id,在{@link com.huobanplus.erpprovider.iscs.common.ISCSSysData}中
     */
    @JSONField(name = "stock_id")
    private int stockId;
    /**
     * 店铺id,在{@link com.huobanplus.erpprovider.iscs.common.ISCSSysData}中
     */
    @JSONField(name = "shop_id")
    private int shopId;
    /**
     * 订单号
     */
    @JSONField(name = "order_no")
    private String orderNo;
    /**
     * 发货时间开始时间,orderNo有值时可不传
     */
    @JSONField(name = "begin _date")
    private String beginTime;
    /**
     * 发货时间结束时间,beginTime不为空时必传
     */
    @JSONField(name = "end _date")
    private String endTime;
    /**
     * 页索引,从1开始,当beginTime不为空时必传
     */
    @JSONField(name = "page_no")
    private int pageIndex;
    /**
     * 每页数量,当beginTime不为空时必传
     */
    @JSONField(name = "page_size")
    private int pageSize;
}
