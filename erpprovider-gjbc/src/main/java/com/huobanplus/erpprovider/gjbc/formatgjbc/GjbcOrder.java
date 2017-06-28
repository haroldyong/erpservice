package com.huobanplus.erpprovider.gjbc.formatgjbc;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 订单数据明细
 * Created by montage on 2017/6/26.
 */
@Data
public class GjbcOrder {

    /**
     * 是否BC订单 是:1,否或者空:0
     */
    @JSONField(name = "is_bc")
    private int is_bc;

    /**
     * 订单编号
     */
    @JSONField(name = "order_sn")
    private String  order_sn;

    /**
     * 总运单号(可空)
     */
    @JSONField(name = "pfreight_no")
    private String pfreight_no;

    /**
     * 快件单号(可空)
     */
    @JSONField(name = "express_num")
    private String express_num;

    /**
     * 发件人姓名
     */
    @JSONField(name = "sender_name")
    private String sender_name;

    /**
     * 发件人城市
     */
    @JSONField(name = "sender-city")
    private String sender_city;

    /**
     * 发件人地址
     */
    @JSONField(name = "sender_address")
    private String sender_address;

    /**
     * 发件人电话
     */
    @JSONField(name = "sender_phone")
    private String sender_phone;

    /**
     * 发件人国别
     */
    @JSONField(name = "sender_country_code")
    private String sender_country_code;

    /**
     * 收件人姓名
     */
    @JSONField(name = "buyer_name")
    private String buyer_name;

    /**
     * 收件人联系电话
     */
    @JSONField(name = "buyer_phone")
    private String buyer_phone;

    /**
     * 订购人姓名（BC:必填 , 其他:可空）
     */
    @JSONField(name = "order_name")
    private String order_name;

    /**
     * 订购人身份证号码（BC:必填 , 其他:可空）
     */
    @JSONField(name = "order_idcard")
    private String order_idcard;

    /**
     * 订购人电话（BC:必填 , 其他:可空）
     */
    @JSONField(name = "order_phone")
    private String order_phone;

    /**
     * 保价费 快递投保的金额，可为0不可空，BC，是必填，否则可空
     */
    @JSONField(name = "customs_insured")
    private double customs_insured;

    /**
     * 非现金抵扣金额
     * 电商平台提供的优惠抵扣金额，如代金券，积分等，BC，是必填，否则可空
     */
    @JSONField(name = "customs_discount")
    private double customs_discount;

    /**
     * 订购人账户名
     * 订购人在电商平台订购商品时使用的用户名，BC，是必填，否则可空
     */
    @JSONField(name = "order_uname")
    private String order_uname;

    /**
     * 收件人省市区代码
     * 空值时，通过地址获取
     */
    @JSONField(name = "province_code")
    private String province_code;

    /**
     * 收件人地址
     * 省^^^市^^^区^^^街道^^^具体地址
     */
    @JSONField(name = "buyer_address")
    private String buyer_address;

    /**
     * 收件人身份证号
     * 必须为18位
     */
    @JSONField(name = "buyer_idcard")
    private String buyer_idcard;

    /**
     * 币制代码
     */
    @JSONField(name = "curr")
    private int curr;

    /**
     * 支付企业名称
     * BC，是必填，否则可空
     */
    @JSONField(name = "p_name")
    private String p_name;

    /**
     * 支付号
     * BC，是必填，否则可空
     */
    @JSONField(name = "p_no")
    private String p_no;

    /**
     * 支付时间
     * BC，是必填，否则可空
     */
    @JSONField(name = "p_time")
    private int p_time;

    /**
     * 运费(可空)
     */
    @JSONField(name = "sh_fee")
    private double sh_fee;

    /**
     * 税费
     */
    @JSONField(name = "cus_tax")
    private double cus_tax;

    /**
     * 订单网址
     * Http://www...，BC，是必填，否则可空
     */
    @JSONField(name = "pweb")
    private String pweb;

    /**
     * 网址名称
     * 订单是否走BC，是必填，否则可空
     */
    @JSONField(name = "web_name")
    private String web_name;

    /**
     * 商家广州备案号
     * 电商平台在广州海关申报的备案号，10位，BC，是必填，否则可空
     */
    @JSONField(name = "re_no")
    private String re_no;

    /**
     * 商家全国备案号
     * 电商平台在海关总署全国版申报的备案号，10位，BC，是必填，否则可空
     */
    @JSONField(name = "re_no_qg")
    private String re_no_qg;

    /**
     * 商家备案名称
     * BC，是必填，否则可空
     */
    @JSONField(name = "re_name")
    private String re_name;

    /**
     * 快递企业代码
     * 当express_num不为空时，必填，否则可空，详细参考快递企业代码说明；
     * 1 顺丰， 2 申通， 3 百世汇通，4 邮政小包， 5 圆通， 7 全峰， 8 天天
     */
    @JSONField(name = "express_code")
    private String express_code;

    /**
     * 订单总毛重
     * 整个订单包裹的总重量，单位 KG
     */
    @JSONField(name = "pkg_gweight")
    private double pkg_gweight;

    /**
     * 订单总净重
     * 订单所有商品不包含快递包装的重量，单位 KG
     */
    @JSONField(name = "goods_nweight")
    private double goods_nweight;

    /**
     * 订单总价
     * 订单所有商品的总价不包含运费与税金，单位 RMB
     */
    @JSONField(name = "order_amount")
    private double order_amount;


}
