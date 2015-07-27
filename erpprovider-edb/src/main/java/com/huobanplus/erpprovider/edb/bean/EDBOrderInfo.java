package com.huobanplus.erpprovider.edb.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by allan on 2015/7/27.
 */
public class EDBOrderInfo {
    /**
     * 订单编号
     */
    private String tid;
    /**
     * 外部平台单号
     */
    private String outTid;
    /**
     * 店铺编号（E店宝中档案管理—基本档案—店铺设置查看）
     * <p>required:true</p>
     */
    private String shopId;
    /**
     * 仓库编码（E店宝中档案管理—仓库档案—仓库设置查看）
     */
    private int storageId;
    /**
     * 买家ID
     */
    private String buyerId;
    /**
     * 买家留言
     */
    private String buyerMsg;
    /**
     * 买家邮件地址
     */
    private String buyerEmail;
    /**
     * 买家支付宝账号
     */
    private String buyerAlipay;
    /**
     * 客服备注
     */
    @JacksonXmlProperty(localName = "seller_remark")
    private String sellerRemark;
    /**
     * 收货人姓名
     */
    private String consignee;
    /**
     * 收货地址
     */
    private String address;
    /**
     * 收货人邮编
     */
    private String postcode;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 联系手机
     */
    private String mobilPhone;
    /**
     * 收货人省份
     */
    private String province;
    /**
     * 收货人城市
     */
    private String city;
    /**
     * 收货人地区
     */
    private String area;
    /**
     * 实收运费
     */
    private double actualFreightGet;
    /**
     * 实收参考价:订单实收金额
     */
    private double actual_RP;
    /**
     * 配送方式
     */
    private String shipMethod;
    /**
     * 快递公司名（（E店宝中档案管理—仓库档案—快递公司设置查看））
     * <p>required:true</p>
     */
    private String express;
    /**
     * 开票情况(是否已开发票0：无1：有)默认0
     */
    private int isInvoiceOpened;
    /**
     * 发票类型
     */
    private String invoiceType;
    /**
     * 开票金额
     */
    private double invoiceMoney;
    /**
     * 发票抬头
     */
    private String invoiceTitle;
    /**
     * 发票内容
     */
    private String invoiceMsg;
    /**
     * 订单类型（等货订单囤货订单换货订单其他订单预售订单正常订单中断订单）默认：正常订单
     */
    private String orderType;
    /**
     * 处理状态（未确认已财务审核已归档已确认已作废）默认：未确认
     */
    private String processStatus;
    /**
     * 付款状态（待退款部分退款待退款全部退款待退款所有交易关闭未付款已付款已退款部分退款已退款全部退款已退款所有）默认：未付款
     */
    private String payStatus;
    /**
     * 发货状态（待退货部分退货待退货全部退货待退货所有退货到货部分退货退货到货全部退货退货到货所有未发货已发货）默认：未发货
     */
    private String deliverStatus;
    /**
     * 是否货到付款
     */
    private int isCOD;
    /**
     * 货到付款服务费
     */
    private double serverCostCOD;
    /**
     * 订单总金额
     */
    private double orderTotalMoney;
    /**
     * 产品总金额
     */
    private double productTotalMoney;
    /**
     * 支付方式
     */
    private String payMethod;
    /**
     * 支付佣金
     */
    private double payCommission;
    /**
     * 支付积分
     */
    private int payScore;
    /**
     * 返点积分
     */
    private int returnScore;
    /**
     * 优惠金额:订单总优惠金额
     */
    private double favorableMoney;
    /**
     * 支付宝交易号
     */
    private String alipayTransactionNo;
    /**
     * 外部平台付款单号
     */
    private String outPayNo;
    /**
     * 外部平台快递方式
     */
    private String outExpressMethod;
    /**
     * 外部平台快递订单状态
     */
    private String outOrderStatus;
    /**
     * 订货日期（订货日期距当前时间不可超过一个月）
     */
    private String orderDate;
    /**
     * 付款日期
     */
    private String payDate;
    /**
     * 完成日期
     */
    private String finishDate;
    /**
     * 平台类型
     */
    private String platType;
    /**
     * 分销商编号
     */
    private String distributorNo;
    /**
     * 物流公司
     */
    private String wuLiu;
    /**
     * 物流单号
     */
    private String wuLiuNo;
    /**
     * 终端类型(电脑-手机-电话)
     */
    private String terminalType;
    /**
     * 内部便签
     */
    private String inMemo;
    /**
     * 其他备注
     */
    private String otherRemark;
    /**
     * 实付运费
     */
    private double actualFreightPay;
    /**
     * 预配货日期
     */
    private String shipDatePlan;
    /**
     * 预计发货日期
     */
    private String deliverDatePlan;
    /**
     * 是否积分换购（0：不是1：是）默认：0
     */
    private int isScorePay;
    /**
     * 是否需要开发票（0：不需要1：需要）默认0
     */
    private int isNeedInvoice;
    /**
     * 条形码
     */
    private String barCode;
    /**
     * 网店品名
     */
    private String productTitle;
    /**
     * 网店规格
     */
    private String standard;
    /**
     * 外部单价
     */
    private double outPrice;
    /**
     * 优惠金额:单品的优惠金额
     */
    private double favoriteMoney;
    /**
     * 订货数量
     */
    private double orderGoodsNum;
    /**
     * 赠品数量
     */
    private int giftNum;
    /**
     * 成交单价
     */
    private double costPrice;
    /**
     * 产品缺货情况
     */
    private String productStockout;
    /**
     * 是否预订（0：否1：是）默认：0
     */
    private int isBook;
    /**
     * 是否预售
     */
    private int isPresell;
    /**
     * 是否赠品（0：否1：是）默认：0
     */
    private int isGift;
    /**
     * 加权平均单价
     */
    private double avgPrice;
    /**
     * 产品运费
     */
    private double productFreight;
    /**
     * 外部平台产品Id
     */
    private String outProductId;
    /**
     * 外部平台条形码
     */
    private String outBarCode;
    /**
     * 产品简介
     */
    private String productIntro;
}
