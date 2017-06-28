package com.huobanplus.erpprovider.gjbc.formatgjbc;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by hxh on 2017-06-26.
 */
@Data
public class GjbcGoodsItemsInfo {
    @JSONField(name = "goods_seq")
    private int goods_seq;
    @JSONField(name = "goods_barcode")
    private String goods_barcode;
    @JSONField(name = "goods_ptcode")
    private String goods_ptcode;
    @JSONField(name = "goods_size")
    private String goods_size;
    @JSONField(name = "goods_unit")
    private String goods_unit;
    @JSONField(name = "goods_hg_num")
    private double goods_hg_num;
    @JSONField(name = "goods_gweight")
    private double goods_gweight;
    @JSONField(name = "goods_name")
    private String goods_name;
    @JSONField(name = "brand")
    private String brand;
    @JSONField(name = "goods_spec")
    private String goods_spec;
    @JSONField(name = "goods_num")
    private String goods_num;
    @JSONField(name = "goods_price")
    private double goods_price;
    @JSONField(name = "ycg_code")
    private String ycg_code;
    @JSONField(name = "hs_code")
    private String hs_code;
    @JSONField(name = "curr")
    private String curr;
    @JSONField(name = "goods_hg_num2")
    private double goods_hg_num2;
    @JSONField(name = "goods_total")
    private double goods_total;
    @JSONField(name = "goods_commonid")
    private int goods_commonid;
}
