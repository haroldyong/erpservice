package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016/6/20.
 */
@Data
public class GYResponseOrder {

    /**
     * 订单编号
     */
    @JSONField(name= "code")
    private String code;

    /**
     * 订单类型
     */
    @JSONField(name= "order_type_name")
    private String orderTypeName;

    /**
     * 平台单号
     */
    @JSONField(name= "platform_code")
    private String platformCode;

    /**
     * 制单时间
     */
    @JSONField(name= "createtime")
    private String createtime;

    /**
     * 拍单时间
     */
    @JSONField(name= "dealtime")
    private String dealtime;

    /**
     * 支付时间
     */
    @JSONField(name= "paytime")
    private String paytime;

    /**
     * 是否为货到付款
     */
    @JSONField(name= "cod")
    private boolean cod;

    /**
     * 是否已审核
     */
    @JSONField(name= "approve")
    private boolean approve;

    /**
     * 发货状态
     */
    @JSONField(name= "delivery_state")
    private double deliveryState;

    /**
     * 仓库代码
     */
    @JSONField(name= "warehouse_code")
    private String warehouseCode;

    /**
     * 仓库名称
     */
    @JSONField(name= "warehouse_name")
    private String warehouseName;

    /**
     * 店铺代码
     */
    @JSONField(name= "shop_code")
    private String shopCode;

    /**
     * 店铺名称
     */
    @JSONField(name= "shop_name")
    private String shopName;

    /**
     * 物流公司代码
     */
    @JSONField(name= "express_code")
    private String expressCode;

    /**
     * 物流公司名称
     */
    @JSONField(name= "express_name")
    private String expressName;

    /**
     * 买家留言
     */
    @JSONField(name= "buyer_memo")
    private String buyerMemo;

    /**
     * 卖家备注
     */
    @JSONField(name= "seller_memo")
    private String sellerMemo;

    /**
     * 二次备注
     */
    @JSONField(name= "seller_memo_late")
    private String sellerMemoLate;

    /**
     * 会员代码
     */
    @JSONField(name= "vip_code")
    private String vipCode;

    /**
     * 会员名称
     */
    @JSONField(name= "vip_name")
    private String vipName;

    /**
     * 收件人姓名
     */
    @JSONField(name= "receiver_name")
    private String receiverName;

    /**
     * 收件人电话
     */
    @JSONField(name= "receiver_phone")
    private String receiverPhone;

    /**
     * 收件人手机
     */
    @JSONField(name= "receiver_mobile")
    private String receiverMobile;

    /**
     * 收件邮编
     */
    @JSONField(name= "receiver_zip")
    private String receiverZip;

    /**
     * 收件区域信息
     */
    @JSONField(name= "receiver_area")
    private String receiverArea;

    /**
     * 收件详细地址
     */
    @JSONField(name= "receiver_address")
    private String receiverAddress;

    /**
     * 支付流水号
     */
    @JSONField(name= "payCode")
    private String payCode;

    /**
     * 身份证号
     */
    @JSONField(name= "vipIdCard")
    private String vipIdCard;

    /**
     * 真实姓名
     */
    @JSONField(name= "vipRealName")
    private String vipRealName;

    /**
     * 电子邮箱
     */
    @JSONField(name= "vipEmail")
    private String vipEmail;

    /**
     * 订单金额
     */
    @JSONField(name= "amount")
    private double amount;

    /**
     * 货款金额
     */
    @JSONField(name= "payment_amount")
    private double paymentAmount;

    /**
     * 物流费用
     */
    @JSONField(name= "post_fee")
    private double postFee;

    /**
     * 让利金额
     */
    @JSONField(name= "discount_fee")
    private double discountFee;

    /**
     * 实付金额
     */
    @JSONField(name= "payment")
    private double payment;

    /**
     * 商品数量
     */
    @JSONField(name= "qty")
    private double qty;

    /**
     * 标准重量
     */
    @JSONField(name= "weight_origin")
    private double weightOrigin;

    /**
     * 物流成本
     */
    @JSONField(name= "post_cost")
    private double postCost;

    /**
     * 平台旗帜
     */
    @JSONField(name= "platform_flag")
    private double platformFlag;

    /**
     * 业务员
     */
    @JSONField(name= "business_man")
    private String businessMan;

    @JSONField(name = "details")
    private List<GYResponseOrderItem> details;

    @JSONField(name = "payments")
    private List<GYResponsePayment> payments;

    @JSONField(name = "invoices")
    private List<GYResponseInvoice> invoices;

    @JSONField(name = "deliverys")
    private List<GYResponseDeliveryOrder> deliverys;
}
