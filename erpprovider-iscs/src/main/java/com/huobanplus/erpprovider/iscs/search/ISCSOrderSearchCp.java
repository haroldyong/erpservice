package com.huobanplus.erpprovider.iscs.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by hzbc on 2016/4/26.
 */
@Data
public class ISCSOrderSearchCp {

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
    @JSONField(name = "begin_create_date")
    private String beginTime;
    /**
     * 发货时间结束时间,beginTime不为空时必传
     */
    @JSONField(name = "end_create_date")
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
