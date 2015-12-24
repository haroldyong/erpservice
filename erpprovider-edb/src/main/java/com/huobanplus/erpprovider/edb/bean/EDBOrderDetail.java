/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by liual on 2015-10-23.
 */
@Setter
@Getter
public class EDBOrderDetail {
    /**
     * edb平台订单编号
     */
    private String tid;
    /**
     * 仓库编号
     */
    @JSONField(name = "storage_id")
    private String storageId;
    /**
     * 交易编号
     */
    @JSONField(name = "transaction_id")
    private String transactionId;
    /**
     * 客户编号
     */
    @JSONField(name = "customer_id")
    private String edbCustomerId;
    /**
     * 分销商编号
     */
    @JSONField(name = "distributor_id")
    private String edbDistributorId;
    /**
     * 店铺名称
     */
    @JSONField(name = "shop_name")
    private String shopName;
    /**
     * 外部平台订单编号
     */
    @JSONField(name = "out_tid")
    private String outTid;
    /**
     * 外部平台付款单号
     */
    @JSONField(name = "out_pay_tid")
    private String outPayTid;
    /**
     * 店铺代码
     */
    @JSONField(name = "shopid")
    private String shopId;
    /**
     * 订单渠道
     */
    @JSONField(name = "order_channel")
    private String orderChannel;
    /**
     * 订单来源
     */
    @JSONField(name = "order_from")
    private String orderFrom;
    /**
     * 买家id（昵称）
     */
    @JSONField(name = "buyer_id")
    private String buyerId;
    /**
     * 买家姓名
     */
    @JSONField(name = "buyer_name")
    private String buyerName;
    /**
     * 订单类型
     */
    private String type;
    /**
     * 处理状态
     */
    private String status;
    /**
     * 收货人姓名
     */
    @JSONField(name = "receiver_name")
    private String receiverName;
    /**
     * 收货人手机
     */
    @JSONField(name = "receiver_mobile")
    private String receiverMobile;
    /**
     * 电话
     */
    private String phone;
    private String city;
    private String province;
    /**
     * 区
     */
    private String district;
    /**
     * 地址
     */
    private String address;
    /**
     * 邮编
     */
    private String post;
    private String email;
    /**
     * 商品总金额
     */
    @JSONField(name = "pro_totalfee")
    private double proTotalFee;
    /**
     * 订单总金额
     */
    @JSONField(name = "order_totalfee")
    private double orderTotalFee;
    /**
     * 退款总金额
     */
    @JSONField(name = "refund_totalfee")
    private Double refundTotalFee;
    /**
     * 优惠金额
     */
    @JSONField(name = "discount_fee")
    private Double discountFee;
    /**
     * 整单优惠金额
     */
    @JSONField(name = "order_disfee")
    private Double orderDisFee;
    /**
     * 快递公司
     */
    private String express;
    /**
     * 快递单号
     */
    @JSONField(name = "express_no")
    private String expressNo;
    /**
     * 快递公司编码
     */
    @JSONField(name = "express_coding")
    private String expressCode;
    /**
     * 配送方式
     */
    @JSONField(name = "sending_type")
    private String sendingType;
    /**
     * 实收运费
     */
    @JSONField(name = "real_income_freight")
    private Double realIncomeFreight;
    /**
     * 实付运费
     */
    @JSONField(name = "real_pay_freight")
    private Double realPayFreight;
    /**
     * 运费说明
     */
    @JSONField(name = "freight_explain")
    private String freightExplain;
    /**
     * 下单时间
     */
    @JSONField(name = "tid_time")
    private String tidTime;
    /**
     * 获取时间
     */
    @JSONField(name = "get_time")
    private String getTime;
    /**
     * 付款时间
     */
    @JSONField(name = "pay_time")
    private String payTime;
    /**
     * 完成时间
     */
    @JSONField(name = "finish_time")
    private String finishTime;
    /**
     * 订单修改时间
     */
    @JSONField(name = "modity_time")
    private String modifyTime;
    @JSONField(name = "pay_status")
    private String payStatus;
    /**
     * 发货状态
     */
    @JSONField(name = "delivery_status")
    private String deliveryStatus;
    /**
     * 系统备注
     */
    @JSONField(name = "system_remarks")
    private String systemRemark;
    /**
     * 产品数量
     */
    @JSONField(name = "product_num")
    private int productNum;
    /**
     * 平台发货状态
     */
    @JSONField(name = "plat_send_status")
    private String platSendStatus;
    /**
     * 最后一次退货时间
     */
    @JSONField(name = "last_returned_time")
    private String lastReturnedTime;
    /**
     * 最后一次退款时间
     */
    @JSONField(name = "last_refund_time")
    private String lastRefundTime;
    @JSONField(name = "tid_item")
    private List<EDBOrderItemDetail> orderItemDetails;

}
