package com.huobanplus.erpprovider.gjbbc.formatgjbbc;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 订单明细
 * Created by hxh on 2017-08-15.
 */
@Data
public class GjbbcOrderInfo {
    /**
     * 订单编号 不为空
     */
    @JSONField(name = "order_sn")
    private String orderSn;
    /**
     * 买家姓名 不为空
     */
    @JSONField(name = "buyer_name")
    private String buyerName;
    /**
     * 买家电话号码 不为空
     */
    @JSONField(name = "buyer_phone")
    private String buyerPhone;
    /**
     * 买家身份证号码 不为空
     */
    @JSONField(name = "buyer_idcard")
    private String buyerIdcard;
    /**
     * 国家、地区代码 不为空
     */
    @JSONField(name = "country_code")
    private String countryCode;
    /**
     * 省、市、区代码 不为空
     */
    @JSONField(name = "province_code")
    private String provinceCode;
    /**
     * 买家地址 不为空
     */
    @JSONField(name = "buyer_address")
    private String buyerAddress;
    /**
     * 发货人姓名 不为空
     */
    @JSONField(name = "sender_name")
    private String senderName;
    /**
     * 发货人联系电话 不为空
     */
    @JSONField(name = "sender_phone")
    private String senderPhone;
    /**
     * 发货人国家、地区代码 不为空
     */
    @JSONField(name = "sender_country_code")
    private String senderCountryCode;
    /**
     * 发货人省、市、区代码 不为空
     */
    @JSONField(name = "sender_province_code")
    private String senderProvinceCode;
    /**
     * 发货人地址 不为空
     */
    @JSONField(name = "sender_address")
    private String senderAddress;
    /**
     * 订单商品描述 不为空
     */
    @JSONField(name = "main_desc")
    private String mainDesc;
    /**
     * 支付企业名称 可空 没对接支付企业的需要填写
     */
    @JSONField(name = "pname")
    private String pName;
    /**
     * 支付单号 可空 没对接支付企业的需要填写
     */
    @JSONField(name = "pno")
    private String pNo;
    /**
     * 支付时间 可空 没对接支付企业的需要填写
     */
    @JSONField(name = "pTime")
    private long pTime;
    /**
     * 订单网址 可空 没对接支付企业的需要填写
     */
    @JSONField(name = "pweb")
    private String pWeb;
    /**
     * 订单税费 可空
     */
    @JSONField(name = "customs_tax")
    private double customsTax;
    /**
     * 订单运费 可空
     */
    @JSONField(name = "shipping_fee")
    private double shippingFee;
    /**
     * 非现金抵扣金额 可空
     */
    @JSONField(name = "customs_discount")
    private double customsDiscout;
    /**
     * 支付单标识 可空 1待提交；2已提交；3已通过；4未通过;5特殊处理
     */
    @JSONField(name = "pay_state")
    private int payState;
    /**
     * 订单商品明细数组
     */
    @JSONField(name = "order_goods")
    List<GjbbcOrderItem> orderGoods;
}
