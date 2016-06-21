package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Data
public class GYOrderRefundUpdate {

    /**
     * 平台单号 	是
     */
    @JSONField(name = "tid")
    private String tid;

    /**
     * 子订单号 	是
     */
    @JSONField(name = "oid")
    private String oid;

    /**
     *  退款状态 0、取消退款 1、标识退款  	是
     */
    @JSONField(name = "refund_state")
    private int refundState;
}
