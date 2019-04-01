package com.huobanplus.erpprovider.lz.formatlz;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LzOrderInfo {

    /**
     * 订单号
     */
    @JSONField(name = "order_id")
    private String order_id;
    /**
     * 支付单号
     */
    @JSONField(name = "payment_id")
    private String payment_id;
    /**
     * 支付方式 电商平台（货主）宝跨境,支付宝跨境 etc的int值
     */
    @JsonIgnore
    @JSONField(name = "pay_method_id")
    private int pay_method_id;
    /**
     * 购买人帐号
     */
    @JSONField(name = "purchaser_acount")
    private String purchaser_acount;
    /**
     * 购买人姓名
     */
    @JSONField(name = "buyer_name")
    private String buyer_name;
    /**
     * 购买人联系方式
     */
    @JSONField(name = "purchaser_tel")
    private String purchaser_tel;
    /**
     * 购买人地址
     */
    @JSONField(name = "purchaser_address")
    private String purchaser_address;
    /**
     * 使用的优惠券金额 单位：分 币种：人民币
     */
    @JSONField(name = "coupon_amount")
    private BigDecimal coupon_amount;
    /**
     * 商品总件数
     */
    @JSONField(name = "total_count")
    private int total_count;
    /**
     * 下单时间 格式：yyyy-MM-dd HH:mm:ss
     */
    @JSONField(name = "order_create_time")
    private String order_create_time;
    /**
     * 支付时间 格式：yyyy-MM-dd HH:mm:ss
     */
    @JSONField(name = "pay_time")
    private String pay_time;
    /**
     * 第三方在网仓注册的店铺ID
     */
    @JsonIgnore
    @JSONField(name = "shop_id")
    private String shop_id;
    /**
     * 物流公司编码 SF，ZTO，EMS等 见物流公司编码表
     */
    @JSONField(name = "transport_service_code")
    private String transport_service_code;
    /**
     * 物流单号
     */
    @JSONField(name = "transport_order_id")
    private String transport_order_id;
    /**
     * 物流企业id，用于与mm对接的同步物流状态接口
     */
    @JsonIgnore
    @JSONField(name = "logis_company_id")
    private int logis_company_id;
    /**
     * RSA加密的身份证号
     */
    @JSONField(name = "receiver_id")
    private String receiver_id;
    /**
     * 加密的身份证正反面照片URL
     */
    @JsonIgnore
    @JSONField(name = "id_pics")
    private List<String> id_pics;
    /**
     * 收件人邮编
     */
    @JSONField(name = "receiver_zip")
    private String receiver_zip;
    /**
     * 收件人省份
     */
    @JSONField(name = "receiver_province")
    private String receiver_province;
    /**
     * 收件人城市
     */
    @JSONField(name = "receiver_city")
    private String receiver_city;
    /**
     * 收件人县区
     */
    @JSONField(name = "receiver_county")
    private String receiver_county;
    /**
     * 收件人地址
     */
    @JSONField(name = "receiver_address")
    private String receiver_address;
    /**
     * 收件人姓名
     */
    @JSONField(name = "receiver_name")
    private String receiver_name;
    /**
     * 收件人手机
     */
    @JSONField(name = "receiver_mobile")
    private String receiver_mobile;
    /**
     * 收件人固定电话
     */
    @JSONField(name = "receiver_phone")
    private String receiver_phone;
    /**
     * 快递公司大头笔
     */
    @JsonIgnore
    @JSONField(name = "logistic_mark")
    private String logistic_mark;
    /**
     * 渠道信息
     */
    @JsonIgnore
    @JSONField(name = "owner_type")
    private int owner_type;
    /**
     * 渠道Id
     */
    @JsonIgnore
    @JSONField(name = "channel_id")
    private String channel_id;
    /**
     * 渠道名称
     */
    @JsonIgnore
    @JSONField(name = "channel_name")
    private String channel_name;
    /**
     * 主商品名称
     */
    @JSONField(name = "main_goods_name")
    private String main_goods_name;
    /**
     * 包装类型 0：海淘 1：中性 2：拼团 3：考拉工厂店
     */
    @JsonIgnore
    @JSONField(name = "neutral_package")
    private int neutral_package;
    /**
     * 订单备注 目前为预留字段
     */
    @JsonIgnore
    @JSONField(name = "remark")
    private String remark;
    /**
     * 个人物品申报单预录入号码
     */
    @JsonIgnore
    @JSONField(name = "goods_delcare_id")
    private String goods_delcare_id;
    /**
     * 杭州或郑州海关放行方式
     */
    @JsonIgnore
    @JSONField(name = "customs_release_method")
    private String customs_release_method;
    /**
     * 订单货款 单位：分 币种：人民币 杭州秀品保税仓使用
     */
    @JSONField(name = "order_goods_amount")
    private BigDecimal order_goods_amount;
    /**
     * 订单实付金额 单位：分 币种：人民币 杭州秀品保税仓使用
     */
    @JSONField(name = "order_total_amount")
    private BigDecimal order_total_amount;
    /**
     * 订单税款 单位：分 币种：人民币 杭州秀品保税仓使用
     */
    @JSONField(name = "order_tax_amount")
    private BigDecimal order_tax_amount;
    /**
     * 物流标记，0：普通 1：次日达标记
     */
    @JSONField(name = "logistic_condition")
    private int logistic_condition;
    /**
     * 自选物流标记，0：没有自选物流，1：有自选物流
     */
    @JSONField(name = "logistic_type")
    private int logistic_type;
    /**
     * 支付相关信息 个人物品清关时必填
     */
    @JSONField(name = "pay_info")
    private LzOrderPayInfo pay_info;
    /**
     * 净重（ 理论重量） 单位：千克
     */
    @JSONField(name = "net_weight")
    private int net_weight;
    /**
     * 商品明细
     */
    @JSONField(name = "order_items")
    private List<LzOrderItem> order_items;
//    /**
//     * 包材推荐信息
//     */
//    @JSONField(name = "pack_mats")
//    private List<OrderPackMatsDetail> pack_mats;
    /**
     * 进口类型
     */
    @JsonIgnore
    @JSONField(name = "import_type")
    private int import_type;
    /**
     * 费用信息
     */
    @JSONField(name = "fee_info")
    private LzOrderFeeInfo fee_info;
    /**
     * 清关口岸代码
     */
    @JsonIgnore
    @JSONField(name = "decl_port")
    private String decl_port;
    /**
     * 发票数据
     */
//    @JSONField(name = "invoice_flags")
//    private OrderInvoiceFlag invoice_flags;
    /**
     * 商家id
     */
    @JSONField(name = "merchant_id")
    private int merchant_id;
    /**
     * 给中外运使用的, value="netease", 中外运使用该字段+stock_id来标识客户
     */
    @JsonIgnore
    @JSONField(name = "e_storage_no")
    private String e_storage_no;
    /**
     * 币种编码, 中外运专用字段
     */
    @JsonIgnore
    @JSONField(name = "bargain_currency")
    private String bargain_currency;
    /**
     * 中外运专用
     */
    @JsonIgnore
    @JSONField(name = "supply_channel")
    private String supply_channel;
    /**
     * 溯源码值
     */
    @JsonIgnore
    @JSONField(name = "source_value")
    private String source_value;
    /**
     * 溯源码图值
     */
    @JsonIgnore
    @JSONField(name = "source_graph_value")
    private String source_graph_value;
    /**
     * 否	应发货时间
     */
    @JsonIgnore
    @JSONField(name = "last_out_time")
    private String last_out_time;
    /**
     * 否	应揽收时间
     */
    @JsonIgnore
    @JSONField(name = "order_collect_time")
    private String order_collect_time;
}
