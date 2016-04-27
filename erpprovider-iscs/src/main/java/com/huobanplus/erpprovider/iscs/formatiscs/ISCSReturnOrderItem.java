package com.huobanplus.erpprovider.iscs.formatiscs;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by hzbc on 2016/4/26.
 */
@Data
public class ISCSReturnOrderItem {

    /**
     * 货号
     */
    @JSONField(name = "product_code")
    private String productCode;
    /**
     * 数量
     */
    @JSONField(name = "qty")
    private int num;

    /**
     * 店铺id
     */
    @JSONField(name = "shop_id")
    private int shopId;

    /**
     * 货主
     */
    @JSONField(name = "owner_id")
    private int ownerId;


}
