package com.huobanplus.erpprovider.iscs.search;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by wuxiongliu on 2016/4/25.
 */
public class ISCSOrderWeightSearch {

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
     * 称重时间 开始时间
     */
    @JSONField(name = "begin_date")
    private String beginTime;

    /**
     * 称重时间 结束时间
     */
    @JSONField(name = "end_date")
    private String endTime;

    /**
     * 页码 BEGIN_ DATE不为空时必传
     */
    @JSONField(name = "page_no")
    private int pageNo;

    /**
     * 每页行数BEGIN_ DATE不为空时必传
     */
    @JSONField(name = "page_size")
    private int pageSize;
}
