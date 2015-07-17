package com.huobanplus.erpservice.event.model;

import java.io.Serializable;

/**
 * 订单信息实体
 */
public class OrderInfo implements Serializable {

    //订单编号
    private String orderCode;
    //单据号
    private String numId;
    //单据类型,例如:Order订单/salesReturn退货单/stock_in_detail入库单/stock_out_detail出库单(请填入英文)
    private String tidType;
    //导入标记
    private String importMark;
    //订单编号(E店宝中订单编号)
    private String tid;
    //订单创建时间
    private String orderTime;
    //订单付款时间
    private String payTime;
    //系统写入时间
    private String inSysTime;
    //商家编码
    private String chlidBarCode;
    //数量
    private String num;
    //子节点
    private OrderInfo[] tradeNo;
    //快递单号
    private String expressNo;
    //快递公司名：需要在EDB中存在
    private String express;
    //订单净重
    private String weight;
    //收货地址
    private String address;
    //买家留言
    private String buyerMessage;
    //客服备注
    private String serviceRemarks;
    //原始订单编号/外部订单编号
    private String outTid;
    //条形码
    private String barCode;
    //网店品名
    private String productTitle;
    //网店规格
    private String standard;
    //产品类型（值为赠品）
    private String proType;
    //订货数量
    private int orderGoodsNum;
    //成交单价
    private double costPrice;
    //快递代码
    private String expressCode;
    //打印员
    private String printer;
    //配货员
    private String cargoOperator;
    //配货时间
    private String cargoTime;
    //打印时间
    private String printTime;
    //验货员
    private String inspecter;
    //是否验货后回传快递信息,验货后回传验货信息必须传,打印后回传快递信息传
    private String isInspectDelivery;
    //发货员
    private String deliveryOperator;
    //发货时间、验货时间
    private String deliveryTime;
    //毛重
    private float grossWeight;
    //内部便签
    private String internalNote;
    //原寄地代码
    private String originCode;
    //目的地代码
    private String destCode;
    //验货数量
    private String inspectionNum;
    //是否覆盖原来内容（ 0 叠加 1 覆盖）
    private int isCover;
    //日期类型支持下面几种,默认订货日期/订货日期/付款日期/发货日期/归档日期/预计归档日期/到货日期/订单修改日期/验货日期/核销日期/生成应收时间/称重时间/审单时间/取消时间/完成时间
    private String dateType;
    //开始时间
    private String beginTime;
    //结束时间
    private String endTime;
    //时间排序类型：审单时间
    private String orderType;
    //待退款部分退款/待退款全部退款/待退款所有/货到付款/交易关闭/未付款/已付款/已退款部分退款/已退款全部退款/已退款所有
    private String paymentStatus;
    //待退货部分退货/待退货全部退货/待退货所有/退货到货部分退货/退货到货全部退货/退货到货所有/未发货/已发货
    private String orderStatus;
    //未确认/已确认/已财务审核/已作废/已归档
    private String proceStatus;
    //待退款部分退款/待退款全部退款/等待买家付款/货到付款/交易成功/交易关闭/买家已付款/缺货订单未付款/已发货/已付款/已签收/交易成功/已取消/预退款
    private String platformStatus;
    //库房id、仓库编号
    private String storageId;
    //店铺id
    private String shopId;
    //发票打印情况(0:未打印，1:已打印)
    private String invoiceIsprint;
    //是否开发票 (0:未开/1:已开
    private String invoiceIsopen;
    //页码
    private String pageNo;
    //每页数量
    private String pageSize;
    //是否产品套装:3单品与套装:显示单品信息+套装信息;1单品与套装明细:显示单品信息+套装明细信息;2单品与套装以及套装明细:显示单品信息+套装信息+套装明细信息(默认)
    private int productInfoType;
    //交易编号
    private String transactionId;
    //客户编号
    private String customerId;
    //分销商编号
    private String distributorId;
    //店铺名称
    private String shopName;
    //外部平台付款单号
    private String outPayTid;
    //凭证单号
    private String voucherId;
    //流水号
    private String serialNum;
    //订单渠道
    private String orderChannel;
    //订单来源
    private String orderFrom;
    //买家ID
    private String buyerId;
    //买家姓名
    private String buyerName;
    //订单类型
    private String type;
    //处理状态
    private String status;
    //异常状态
    private String abnormalStatus;
    //合并状态
    private String mergeStatus;
    //收货人
    private String receiverName;
    //收货手机
    private String receiverMobile;
    //电话
    private String phone;
    //省
    private String province;
    //市
    private String city;
    //区
    private String district;
    //邮编
    private String post;
    //电子邮件
    private String email;
    //发票名称
    private String invoiceName;
    //开票情况
    private String invoiceSituation;
    //发票抬头
    private String invoiceTitle;
    //发票类型
    private String invoiceType;
    //开票内容
    private String invoiceContent;
    //产品总金额
    private String proTotalFee;
    //订单总金额
    private String orderTotalFee;
    //实收参考价
    private String referencePricePaid;
    //发票金额
    private String invoiceFee;
    //货到付款金额
    private String codFee;
    //其他费用
    private String otherFee;
    //退款总金额
    private String refundTotalFee;
    //优惠金额
    private String discountFee;
    //折扣
    private String discount;
    //渠道优惠金额
    private String channelDisfee;
    //商家优惠金额
    private String merchantDisFee;
    //整单优惠
    private String orderDisfee;
    //佣金
    private String commissionFee;
    //是否货到付款
    private String isCod;
    //是否积分换购
    private String pointPay;
    //消耗积分
    private String costPoint;
    //获得积分
    private String point;
    //上级积分
    private String superiorPoint;
    //提成金额
    private String royaltyFee;
    //外部积分
    private  String externalPoint;
    //线上快递公司
    private String onlineExpress;
    //配送方式
    private String sendingType;
    //实收运费
    private String realIncomefreight;
    //实付运费
    private String realPayFreight;
    //毛重运费
    private String grossWeightFreight;
    //净重运费
    private String netWeightWreight;
    //运费说明
    private String freightExplain;
    //总重量
    private String totalWeight;
    //订货时间
    private String tidTime;
    //获取时间
    private String getTime;
    //下单员
    private String orderCreater;
    //业务员
    private String businessMan;
    //到款员
    private String paymentReceivedOperator;
    //到款时间
    private String paymentReceivedTime;
    //审单员
    private String reviewOrdersOperator;
    //审单时间
    private String reviewOrdersTime;
    //财务审核人
    private String financeReviewOperator;
    //财务审核时间
    private String financeReviewTime;
    //预打印员
    private String advancePrinter;
    //是否打印
    private String isPrint;
    //预配货员
    private String advDistributer;
    //预配货时间
    private String advDistributTime;
    //取消员
    private String cancelOperator;
    //取消时间
    private String cancelTime;
    //反取消员
    private String revokeCanceler;
    //反取消时间
    private String revokeCancelTime;
    //打包员
    private String packager;
    //打包时间
    private String packTime;
    //称重员
    private String weighOperator;
    //称重时间
    private String weighTime;
    //预计发货时间
    private String bookDeliveryTime;
    //锁定员
    private String locker;
    //锁定时间
    private String lockTime;
    //预计归档时间
    private String bookFileTime;
    //归档员
    private String fileOperator;
    //归档时间
    private String fileTime;
    //完成时间
    private String finishTime;
    //订单修改时间
    private String modityTime;
    //促销标记
    private String isPromotion;
    //满足的促销方案
    private String promotionPlan;
    //外部平台促销详情
    private String outPromotionDetail;
    //到货日期
    private String goodReceiveTime;
    //生成应收时间
    private String receiveTime;
    //核销日期
    private String verificatyTime;
    //启用智能仓库时间
    private String enableInteStoTime;
    //启用智能快递时间
    private String enableInteDeliveryTime;
    //支付宝账户
    private String alipayId;
    //支付宝状态
    private String alipayStatus;
    //支付方式
    private String payMothed;
    //付款状态
    private String payStatus;
    //汇率
    private String rate;
    //币种
    private String currency;
    //发货状态
    private String deliveryStatus;
    //分销商便签
    private String distributorMark;
    //系统备注
    private String systemRemarks;
    //其他备注
    private String otherRemarks;
    //短信通知
    private String message;
    //短信发送时间
    private String messageTime;
    //是否缺货
    private String isStock;
    //相关订单
    private String relatedOrders;
    //相关订单类型
    private String relatedOrdersType;
    //第三方快递名称
    private String deliveryName;
    //是否新客户
    private String isNewCustomer;
    //分销商等级
    private String distributorLevel;
    //货到付款服务费
    private String codServiceFee;
    //快递代收金额
    private String expressColFee;
    //产品数量
    private String productNum;
    //产品条形码
    private String sku;
    //单品条数
    private String itemNum;
    //单品数量
    private String singleNum;
    //旗帜颜色
    private String flagColor;
    //是否插旗
    private String isFlag;
    //淘宝快递订单状态
    private String taobaoDeliveryOrderStatus;
    //淘宝快递状态
    private String taobaoDeliveryStatus;
    //淘宝快递方式
    private String taobaoDeliveryMethod;
    //处理订单需要的时间戳
    private String orderProcessTime;
    //是否中断
    private String isBreak;
    //中断员
    private String breaker;
    //中断时间
    private String breakTime;
    //中断说明
    private String breakExplain;
    //平台发货状态
    private String platSendStatus;
    //平台类型
    private String platType;
    //是否预售
    private String isAdvSale;
    //省编码
    private String provincCode;
    //市编码
    private String cityCode;
    //区编码
    private String areaCode;
    //最后一次退货时间
    private String lastReturnedTime;
    //最后一次退款时间
    private String lastRefundTime;
    //配送中心名称
    private String deliverCentre;
    //配送站点名称
    private String deliverStation;
    //是否送货前通知
    private String  isPreDeliveryNotice;
    //送货时间
    private String  jdDeliveryTime;
    //分拣代码
    private String  sortingCode;
    //货到付款结算凭证号
    private String codSettlementVouchernumber;
    //总数
    private String totalNum;
    //买家邮件地址
    private String buyerEmail;
    //买家支付宝账号
    private String buyerAlipay;
    //收货人姓名
    private String consignee;
    //收货人省份
    private String privince;
    //收货人城市
    private String citys;
    //收货人地区
    private String area;
    //开票金额
    private double invoiceMoney;
    //支付佣金
    private double payCommission;
    //支付积分
    private int payScore;
    //返点积分
    private int returnScore;
    //支付宝交易号
    private String alipayTransactionNo;
    //外部平台快递方式
    private String outExpressMethod;
    //外部平台快递订单状态
    private String outOrderStatus;
    //订货日期（订货日期距当前时间不可超过一个月）
    private String orderDate;
    //付款日期
    private String payDate;
    //完成日期
    private String finishDate;
    //物流公司
    private String wuLiu;
    //物流单号
    private String wuLiuNo;
    //终端类型
    private String terminalType;
    //外部单价
    private double outPrice;
    //赠品数量
    private String giftNum;
    //产品缺货情况
    private String productStockout;
    //是否预订
    private int isBook;
    //是否赠品
    private int isGift;
    //加权平均单价
    private double avgPrice;
    //产品运费
    private double productFreight;
    //外部平台产品Id
    private String outProductId;
    //外部平台条形码
    private String outBarCode;
    //产品简介
    private String productIntro;

}
