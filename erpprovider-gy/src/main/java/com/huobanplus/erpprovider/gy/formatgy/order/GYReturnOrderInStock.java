package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 * 管易退货单入库实体
 */
@Data
public class GYReturnOrderInStock {

    /**
     * 退货单单据编号
     */
    @JSONField(name = "code")
    private String code;

    /**
     * 退入仓库代码
     */
    @JSONField(name = "warehouse_code")
    private String wareHouseCode;

    /**
     * 退回快递代码
     */
    @JSONField(name = "express_code")
    private String expressCode;

    /**
     * 退回快递运单号
     */
    @JSONField(name = "express_no")
    private String expressNo;
}
