package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 * 管易订单备注实体
 */
@Data
public class GYOrderMemo {

    /**
     * 平台单号  是
     */
    @JSONField(name = "tid")
    private String tid;

    /**
     * 备注 是
     */
    @JSONField(name = "memo")
    private String memo;
}
