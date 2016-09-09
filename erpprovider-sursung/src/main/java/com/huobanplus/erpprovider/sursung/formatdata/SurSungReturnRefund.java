/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-09-08.
 */
@Data
public class SurSungReturnRefund {
    /**
     * 店铺编号
     */
    @JSONField(name = "shop_id")
    private int shopId;

    /**
     * 退货退款单号，平台唯一
     */
    @JSONField(name = "outer_as_id")
    private String outerAsId;

    /**
     * 平台订单号
     */
    @JSONField(name = "so_id")
    private String soId;

    /**
     * 售后类型:普通退货，其他，拒收退货等
     */
    @JSONField(name = "type")
    private String type;

    /**
     * 快递公司
     */
    @JSONField(name = "logistics_company")
    private String logiCompany;

    /**
     * 物流单号
     */
    @JSONField(name = "l_id")
    private String logiNo;

    /**
     * WAIT_SELLER_AGREE:买家已经申请退款，等待卖家同意;
     * WAIT_BUYER_RETURN_GOODS:卖家已经同意退款，等待买家退货;
     * WAIT_SELLER_CONFIRM_GOODS:买家已经退货，等待卖家确认收货;
     * SELLER_REFUSE_BUYER:卖家拒绝退款;
     * CLOSED:退款关闭,SUCCESS:退款成功
     */
    @JSONField(name = "shop_status")
    private String shopStatus;

    /**
     * 备注
     */
    @JSONField(name = "remark")
    private String remark;

    /**
     * BUYER_NOT_RECEIVED:买家未收到货;
     * BUYER_RECEIVED:买家已收到货;
     * BUYER_RETURNED_GOODS:买家已退货;
     * SELLER_RECEIVED:卖家已收到退货
     */
    @JSONField(name = "good_status")
    private String goodStatus;

    /**
     * 问题类型
     */
    @JSONField(name = "question_type")
    private String questionType;

    /**
     * 原单据总金额
     */
    @JSONField(name = "total_amount")
    private double totalAmount;

    /**
     * 卖家应退金额
     */
    @JSONField(name = "refund")
    private double refund;

    /**
     * 买家应补偿金额
     */
    @JSONField(name = "payment")
    private double payment;

    /**
     * 明细
     */
    @JSONField(name = "items")
    private List<SurSungReturnRefundItem> items;
}
