package com.huobanplus.erpprovider.iscs.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/4/25.
 */
@Data
public class ISCSOrderStatusSearch {
    /**
     * 仓库id,在{@link com.huobanplus.erpprovider.iscs.common.ISCSSysData}中
     */
    @JSONField(name = "stock_id")
    private int stockId;

    /**
     * 商铺id,在{@link com.huobanplus.erpprovider.iscs.common.ISCSSysData}中
     */
    @JSONField(name = "shop_id")
    private int shopId;

    /**
     * 订单号
     */
    @JSONField(name = "order_no")
    private String orderNo;

    /**
     * 时间类型：1订单最后更新时间(默认)，2付款时间,3发货时间,4系统创建时间,5平台创建时间
     */
    @JSONField(name = "time_type")
    private String timeType;

    /**
     * 订单状态
     */
    @JSONField(name = "status")
    private int status;

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
