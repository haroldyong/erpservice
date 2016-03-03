/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.formatedb;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liual on 2015-10-26.
 */
@Setter
@Getter
public class EDBOrderItemDetail {
    /**
     * 仓库编号
     */
    @JSONField(name = "storage_id")
    private String storageId;
    /**
     * 订单编号
     */
    private String tid;
    /**
     * 产品名称
     */
    @JSONField(name = "pro_name")
    private String proName;
    /**
     * 规格
     */
    private String specification;
    /**
     * 条形码（货号）
     */
    private String barcode;
    /**
     * 订货数量
     */
    @JSONField(name = "pro_num")
    private int proNum;
    /**
     * 发货数量
     */
    @JSONField(name = "send_num")
    private String sendNum;

    /**
     * 退货数量
     */
    @JSONField(name = "refund_num")
    private String refundNum;
    /**
     * 退货到货数量
     */
    @JSONField(name = "refund_renum")
    private int refundReNum;
    /**
     * 成本价
     */
    @JSONField(name = "cost_price")
    private double costPrice;
    /**
     * 销售价
     */
    @JSONField(name = "sell_price")
    private double sellPrice;
    /**
     * 店铺id
     */
    @JSONField(name = "shopid")
    private String shopId;
    /**
     * 外部平台订单编号
     */
    @JSONField(name = "out_tid")
    private String outTid;
    /**
     * 外部平台货品id
     */
    @JSONField(name = "out_proid")
    private String outProId;

}
