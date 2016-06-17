package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Data
public class GYDeliveryOrderUpdate {

    /**
     * 单据编号
     */
    @JSONField(name = "code")
    private String code;

    /**
     * 物流公司代码
     */
    @JSONField(name = "express_code")
    private String expressCode;

    /**
     * 运单号
     */
    @JSONField(name = "mail_no")
    private String mailNo;

    /**
     * 称重重量
     */
    @JSONField(name = "weight_qty")
    private String weightQty;

    /**
     * 商品信息数组
     */
    @JSONField(name = "deliverys_state_paramlist")
    private List<GYDeliveryState> deliveryStates;
}
