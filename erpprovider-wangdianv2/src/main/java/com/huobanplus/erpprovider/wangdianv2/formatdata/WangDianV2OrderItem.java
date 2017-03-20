/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.wangdianv2.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class WangDianV2OrderItem {
    /**
     * 平台订单货品表主键,子订单唯一标识
     */
    @JSONField(name = "oid")
    private String oid;

    /**
     * 购买数量。取值范围:大于零的整数
     */
    @JSONField(name = "num")
    private int num;

    /**
     * 标价,折扣前的价格
     */
    @JSONField(name = "price")
    private double price;

    /**
     * 平台子订单状态，
     * 10未确认
     * 20待尾款
     * 30待发货
     * 40部分发货
     * 50已发货
     * 60已签收
     * 70已完成
     * 80已退款
     * 90已关闭(子订单状态可以和主订单不一样，比如其中一个子订单退款完成，其状态是80，但主订单仍然是待发货)
     */
    @JSONField(name = "status")
    private int status;

    /**
     * 退款状态0无退款1取消退款,2已申请退款,3等待退货,4等待收货,5退款成功
     */
    @JSONField(name = "refund_status")
    private int refundStatus;

    /**
     * 平台上货品的ID(货品表主键)goods_id和spec_id不能同时为空
     */
    @JSONField(name = "goods_id")
    private String goodsId;

    /**
     * 平台上规格的ID(规格表主键)goods_id和spec_id不能同时为空
     */
    @JSONField(name = "spec_id")
    private String specId;

    /**
     * 货品编码(商家编码)
     */
    @JSONField(name = "goods_no")
    private String goodsNo;

    /**
     * 规格编码(商家编码)
     */
    @JSONField(name = "spec_no")
    private String specNo;

    /**
     * 平台货品名称
     */
    @JSONField(name = "goods_name")
    private String goodsName;

    /**
     * 平台规格名称
     */
    @JSONField(name = "spec_name")
    private String specName;

    /**
     * 客服调整总金额(大于0加价，小于0减价，是折扣来源的一部分)
     */
    @JSONField(name = "adjust_amount")
    private String adjustAmount;

    /**
     * 下单总折扣，客户下单时折扣(比如促销打折，不包含客服调整、分摊折扣)
     */
    @JSONField(name = "discount")
    private String discount;

    /**
     * 分摊总折扣，由总订单分摊而来，一般是付款时产生，如使用优惠券
     */
    @JSONField(name = "share_discount")
    private String shareDiscount;

    /**
     * 平台类目
     */
    @JSONField(name = "cid")
    private String cid;
}