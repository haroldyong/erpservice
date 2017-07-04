package com.huobanplus.erpprovider.gjbc.formatgjbc;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 订单商品明细
 * Created by montage on 2017/6/26.
 */
@Data
public class GjbcGoodsItemsInfo {
    /**
     * 商品序号
     * 电商平台在全国版申报订单上对应的商品序号，必填，无值退单。订单是否走BC，是必填，否则可空
     */
    @JSONField(name = "goods_seq")
    private int goods_seq;
    /**
     * 商品条形码
     * 可空，不建议为空
     */
    @JSONField(name = "goods_barcode")
    private String goods_barcode;
    /**
     * 税号
     * 个人物品必填，BC可空
     */
    @JSONField(name = "goods_ptcode")
    private String goods_ptcode;
    /**
     * 实际商品计量单位
     */
    @JSONField(name = "goods_size")
    private String goods_size;
    /**
     * 商品发定计量单位
     * BC，是必填，否则可空
     */
    @JSONField(name = "goods_unit")
    private String goods_unit;
    /**
     * 商品发定计量单位下的商品数量
     * BC，是必填，否则可空
     */
    @JSONField(name = "goods_hg_num")
    private double goods_hg_num;
    /**
     * 单件商品的重量
     */
    @JSONField(name = "goods_gweight")
    private double goods_gweight;
    /**
     * 物品名称
     */
    @JSONField(name = "goods_name")
    private String goods_name;
    /**
     * 品牌
     */
    @JSONField(name = "brand")
    private String brand;
    /**
     * 规格型号
     */
    @JSONField(name = "goods_spec")
    private String goods_spec;
    /**
     * 数量
     */
    @JSONField(name = "goods_num")
    private String goods_num;
    /**
     * 单价
     */
    @JSONField(name = "goods_price")
    private double goods_price;
    /**
     * 原产国代码
     */
    @JSONField(name = "ycg_code")
    private String ycg_code;
    /**
     * 商品HS编码
     * BC必填，无值退单，否则可空
     */
    @JSONField(name = "hs_code")
    private String hs_code;
    /**
     * 商品单价币制
     * 可空，为空自动取原产国代码
     */
    @JSONField(name = "curr")
    private String curr;
    /**
     * 商品第二法定计量单位下的商品数量
     * BC，是必填，否则可空
     */
    @JSONField(name = "goods_hg_num2")
    private double goods_hg_num2;
    /**
     * 商品小计
     * BC，是必填，否则可空
     */
    @JSONField(name = "goods_total")
    private double goods_total;
    /**
     * 商品平台货号
     * 可空，不建议为空
     */
    @JSONField(name = "goods_commonid")
    private String goods_commonid;
}
