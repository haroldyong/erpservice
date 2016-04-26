package com.huobanplus.erpprovider.iscs.formatiscs;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016/4/26.
 */
@Data
public class ISCSReturnOrder {

    /**
     * 退货订单号
     */
    @JSONField(name = "order_return_no")
    private String orderReturnNo;

    /**
     * 对应销售订单号
     */
    @JSONField(name = "order_no")
    private String orderNo;

    /**
     * 网仓仓库id
     */
    @JSONField(name = "stock_id")
    private int stockId;

    /**
     *  退货运输商
     */
    @JSONField(name = "transport_id")
    private int transporterId;

    /**
     * 退货运单号
     */
    @JSONField(name = "out_sid")
    private String outSid;

    /**
     * 买家昵称
     */
    @JSONField(name = "buyer_nick")
    private String buyerNick;

    /**
     * 收货人姓名
     */
    @JSONField(name = "receiver_name")
    private String receiverName;

    /**
     * 收货人手机号
     */
    @JSONField(name = "receiver_mobile")
    private String receiverMobile;

    /**
     * 收货人的电话号码
     */
    @JSONField(name = "receiver_phone")
    private String receiverPhone;

    /**
     * 备注
     */
    @JSONField(name = "return_note")
    private String returnNote;

    /**
     * 店铺id
     */
    @JSONField(name = "shop_id")
    private int shopId;

    @JSONField(name = "skus")
    private List<ISCSReturnOrderItem> iscsReturnOrderItems;


}
