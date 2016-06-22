package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Data
public class GYRefundOrder {

    /**
     * 退款单单号
     */
    @JSONField(name = "refund_code")
    private String refundCode;

    /**
     * 退款单种类 是	0-仅退款;1-退货退款
     */
    @JSONField(name = "refund_type")
    private int refundType;

    /**
     * 退款原因
     */
    @JSONField(name = "refund_reason")
    private String refundReason;

    /**
     * 关联订单单号
     */
    @JSONField(name = "trade_code")
    private String tradeCode;

    /**
     * 店铺代码 是
     */
    @JSONField(name = "shop_code")
    private String shopCode;

    /**
     * 会员代码 是
     */
    @JSONField(name = "vip_code")
    private String vipCode;

    /**
     * 单据类型代码
     */
    @JSONField(name = "type_code")
    private String typeCode;

    /**
     * 退款支付方式代码
     */
    @JSONField(name = "payment_type_code")
    private String paymentTypeCode;

    /**
     * 退款金额 是
     */
    @JSONField(name = "amount")
    private double amount;

    /**
     * 备注
     */
    @JSONField(name = "note")
    private String note;

    /**
     * 退款商品列表 必填
     */
    @JSONField(name = "item_detail")
    private List<GYRefundOrderItem> itemDetails;


}
