package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by elvis on 2016/5/6.
 * 管易订单实体
 */
@Data
public class GYOrder {

    /**
     * order_type_code 	String 				订单类型code
     */
    @JSONField(name = "order_type_code")
    private String OrderTypeCode;

    /**
     * refund 	Number 		0-非退款 ，1--退款（退款中）； 		退款状态
     */
    @JSONField(name = "refund")
    private int refund;

    /**
     * cod 	Boolean 				货到付款
     */
    @JSONField(name = "cod")
    private boolean cod;

    /**
     * order_settlement_code 	String 				结算方式code
     */
    @JSONField(name = "order_settlement_code")
    private String orderSettlementCode;

    /**
     * platform_code 	String 				平台单号
     */
    @JSONField(name = "platform_code")
    private String platformCode;

    /**
     * shop_code 	String 	是 			店铺code
     */
    @JSONField(name = "shop_code")
    private String shopCode;

    /**
     * express_code 	String 	是 			物流公司code
     */
    @JSONField(name = "express_code")
    private String expressCode;

    /**
     * warehouse_code 	String 	是 			仓库code
     */
    @JSONField(name = "warehouse_code")
    private String warehouseCode;

    /**
     * vip_code 	String 	是 			会员code
     */
    @JSONField(name = "vip_code")
    private String vipCode;

    /**
     * vip_name 	String 				会员名称
     */
    @JSONField(name = "vip_name")
    private String vipName;

    /**
     * receiver_name 	String 				收货人
     */
    @JSONField(name = "receiver_name")
    private String receiverName;

    /**
     * receiver_address 	String 				收货地址
     */
    @JSONField(name = "receiver_address")
    private String receiverAddress;

    /**
     * receiver_zip 	String 				收货邮编
     */
    @JSONField(name = "receiver_zip")
    private String receiverZip;


    /**
     * receiver_mobile 	String 		手机和电话必填一项 		收货人手机
     */
    @JSONField(name = "receiver_mobile")
    private String receiverMobile;

    /**
     * receiver_phone 	String 		手机和电话必填一项 		收货人电话
     */
    @JSONField(name = "receiver_phone")
    private String receiverPhone;

    /**
     * receiver_province 	String 				收货人省份
     */
    @JSONField(name = "receiver_province")
    private String receiverProvince;

    /**
     * receiver_city 	String 				收货人城市
     */
    @JSONField(name = "receiver_city")
    private String receiverCity;

    /**
     * receiver_district 	String 				收货人区域
     */
    @JSONField(name = "receiver_district")
    private String receiverDistrict;


    /**
     * tag_code 	String 				标记code
     */
    @JSONField(name = "tag_code")
    private String tagCode;

    /**
     * deal_datetime 	String 	是 	2015-03-17 15:03:45 		拍单时间
     */
    @JSONField(name = "deal_datetime")
    private String dealDatetime;

    /**
     * pay_datetime 	String 		2015-03-17 15:03:45 		付款时间
     */
    @JSONField(name = "pay_datetime")
    private String payDatetime;


    /**
     * business_man_code 	String 				业务员code
     */
    @JSONField(name = "business_man_code")
    private String businessManCode;

    /**
     * post_fee 	Price 			0.0 	物流费用
     */
    @JSONField(name = "post_fee")
    private double postFee;

    /**
     * cod_fee 	Price 				到付服务费
     */
    @JSONField(name = "cod_fee")
    private double codFee;

    /**
     * discount_fee 	Price 				让利金额
     */
    @JSONField(name = "discount_fee")
    private double discountFee;

    /**
     * plan_delivery_date 	Date 				预计发货日期
     */
    @JSONField(name = "plan_delivery_date")
    private String planDeliveryDate;

    /**
     * buyer_memo 	String 				买家留言
     */
    @JSONField(name = "buyer_memo")
    private String buyerMemo;

    /**
     * seller_memo 	String 				卖家备注
     */
    @JSONField(name = "seller_memo")
    private String sellerMemo;

    /*
     *seller_memo_late 	String 				二次备注
     */
    @JSONField(name = "seller_memo_late")
    private String sellerMemoLate;

    /**
     * vipIdCard 	String 				身份证号
     */
    @JSONField(name = "vipIdCard")
    private String vipIdCard;

    /**
     * vipRealName 	String 				真实姓名
     */
    @JSONField(name = "vipRealName")
    private String vipRealName;

    /**
     * vipEmail 	String 				电子邮箱
     */
    @JSONField(name = "vipEmail")
    private String vipEmail;

    /*
     *invoices[] 	List 	是 			发票信息数组
     */
    @JSONField(name = "invoices")
    private List<GYInvoice> invoices;

    /*
     *details[] 	List 				商品信息数组
     */
    @JSONField(name = "details")
    private List<GYOrderItem> details;

    /*
     *payments[] 	List 	是 			支付信息数组
     */
    @JSONField(name = "payments")
    private List<GYPayment> payments;
}
