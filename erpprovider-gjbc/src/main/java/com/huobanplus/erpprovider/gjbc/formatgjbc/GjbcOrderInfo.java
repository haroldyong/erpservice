package com.huobanplus.erpprovider.gjbc.formatgjbc;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by hxh on 2017-06-26.
 */
@Data
public class GjbcOrderInfo {
    @JSONField(name = "is_bc")
    private int is_bc;
    @JSONField(name = "order_sn")
    private String order_sn;
    @JSONField(name = "pfreight_no")
    private String pfreight_no;
    @JSONField(name = "express_num")
    private String express_num;
    @JSONField(name = "sender_name")
    private String sender_name;
    @JSONField(name = "sender_city")
    private String sender_city;
    @JSONField(name = "sender_address")
    private String sender_address;
    @JSONField(name = "sender_phone")
    private String sender_phone;
    @JSONField(name = "sender_country_code")
    private String sender_country_code;
    @JSONField(name = "buyer_name")
    private String buyer_name;
    @JSONField(name = "buyer_phone")
    private String buyer_phone;
    @JSONField(name = "order_name")
    private String order_name;
    @JSONField(name = "order_idcard")
    private String order_idcard;
    @JSONField(name = "order_phone")
    private String order_phone;
    @JSONField(name = "customs_insured")
    private double customs_insured;
    @JSONField(name = "customs_discount")
    private double customs_discount;
    @JSONField(name = "order_uname")
    private String order_uname;
    @JSONField(name = "province_code")
    private String province_code;
    @JSONField(name = "buyer_address")
    private String buyer_address;
    @JSONField(name = "buyer_idcard")
    private String buyer_idcard;
    @JSONField(name = "curr")
    private int curr;
    @JSONField(name = "p_name")
    private String p_name;
    @JSONField(name = "p_no")
    private String p_no;
    @JSONField(name = "p_time")
    private int p_time;
    @JSONField(name = "sh_fee")
    private double sh_fee;
    @JSONField(name = "cus_tax")
    private double cus_tax;
    @JSONField(name = "pweb")
    private String pweb;
    @JSONField(name = "web_name")
    private String web_name;
    @JSONField(name = "re_no")
    private String re_no;
    @JSONField(name = "re_no_qg")
    private String re_no_qg;
    @JSONField(name = "re_name")
    private String re_name;
    @JSONField(name = "express_code")
    private String express_code;
    @JSONField(name = "pkg_gweight")
    private double pkg_gweight;
    @JSONField(name = "goods_nweight")
    private double goods_nweight;
    @JSONField(name = "order_amount")
    private double order_amount;
    @JSONField(name = "order_goods")
    private GjbcGoodsItemsInfo[] order_goods;

}