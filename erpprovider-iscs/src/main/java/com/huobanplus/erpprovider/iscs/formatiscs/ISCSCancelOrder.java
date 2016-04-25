package com.huobanplus.erpprovider.iscs.formatiscs;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/4/22.
 */
@Data
public class ISCSCancelOrder {

    /**
     * 仓库id
     */
    @JSONField(name = "stock_id")
    private int stockId;

    /**
     *  店铺Id
     */
    @JSONField(name = "shop_id")
    private int shopId;

    /**
     *  订单号
     */
    @JSONField(name = "order_no")
    private String orderNum;

    /**
     * 备注
     */
    @JSONField(name = "remark")
    private String remark;
}
