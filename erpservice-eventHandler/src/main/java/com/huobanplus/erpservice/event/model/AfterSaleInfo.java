package com.huobanplus.erpservice.event.model;

import java.io.Serializable;

/**
 * 售后信息
 */
public class AfterSaleInfo extends BaseBean {

    //单据的日期类型,可选值有：创建日期、预退货日期、退货到货日期、退款申请日期、退款审核日期,最后修改日期.
    private String dateType;
    //开始日期.对应日期类型结束始时间,格式yyyyMMdd
    private String startTime;
    //结束日期.对应日期类型的开始时间,格式yyyyMMdd.
    private String endTime;
    //问题编号。售后问题单据的编号
    private String aftersaleId;
    //店铺编号
    private String shopId;
    //当前页数
    private String pageNo;
    //每页显示条数
    private String pageSize;
    //订单编号
    private String saleOrderId;
    //问题编号
    private String afterSaleId;
    //寄出仓库
    private String sendStorage;
    //创建时间
    private String createTime;
    //退货申请时间
    private String returnCreateTime;
    //退货到货时间
    private String returnConfirmTime;
    //退款申请时间
    private String refundCreateTime;
    //退款审核时间
    private String refundConfirmTime;
    //退货状态
    private String returnStatus;
    //退款状态
    private String refundStatus	;
    //处理进度
    private String processStatus;
    //退回物流公司
    private String returnLofisticsCompany;
    //退回物流单号
    private String returnLogisticsNumber;
    //是否退回发票
    private String isReturnInvoice;
    //退回发票单号
    private String returnInvoiceNum;
    //商品退款总额
    private String totalGoodsRefundFee;
    //额外退款金额
    private String otherRefundFee;
    //退款路径
    private String refundTo;
    //买家id
    private String buyerId;
    //最后修改时间
    private String lastAlterTime;
    //外部平台单号
    private String outTid;
    //整单问题类型
    private String wholeQuestionType;
    //客服备注
    private String serviceRemarks;
    //问题单据类型
    private String aftersaleType;
    //子节点
    private AfterSaleInfo aftersale_id [];

}
