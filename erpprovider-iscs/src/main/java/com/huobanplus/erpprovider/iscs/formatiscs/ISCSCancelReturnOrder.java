package com.huobanplus.erpprovider.iscs.formatiscs;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/4/26.
 */
@Data
public class ISCSCancelReturnOrder {

    /**
     * 网仓id
     */
    @JSONField(name = "stock_id")
    private int stockId;

    /**
     * 店铺id
     */
    @JSONField(name = "shop_id")
    private int shopId;

    /**
     * [慎用]取消该单号对应的所有退货单
     */
    @JSONField(name = "order_return_no")
    private String orderReturnNo;

    /**
     * 网仓退换货单号 orderReturnNo和iscsOrderReturnNo不能同时为空
     */
    @JSONField(name = "iscs_order_return_no")
    private String iscsOrderReturnNo;

    /**
     * 备注
     */
    @JSONField(name = "remark")
    private String remark;
}
