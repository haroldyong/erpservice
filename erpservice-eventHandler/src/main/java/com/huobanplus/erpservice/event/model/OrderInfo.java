package com.huobanplus.erpservice.event.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单信息实体
 */
public class OrderInfo extends BaseBean {

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
    //订单数量
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
    private int invoiceIsopen;
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
    private int invoiceSituation;
    //发票抬头
    private String invoiceTitle;
    //发票类型
    private String invoiceType;
    //开票内容
    private String invoiceContent;
    //产品总金额
    private double proTotalFee;
    //订单总金额
    private double orderTotalFee;
    //实收参考价
    private double referencePricePaid;
    //发票金额
    private String invoiceFee;
    //货到付款金额
    private String codFee;
    //其他费用
    private String otherFee;
    //退款总金额
    private String refundTotalFee;
    //优惠金额
    private double discountFee;
    //折扣
    private String discount;
    //渠道优惠金额
    private String channelDisfee;
    //商家优惠金额
    private String merchantDisFee;
    //整单优惠
    private double orderDisfee;
    //佣金
    private String commissionFee;
    //是否货到付款
    private int isCod;
    //是否积分换购
    private int pointPay;
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
    private double realIncomefreight;
    //实付运费
    private double realPayFreight;
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
    private Date advDistributTime;
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
    private Date bookDeliveryTime;
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
    private double codServiceFee;
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
    private int isAdvSale;
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
    private Date orderDate;
    //付款日期
    private Date payDate;
    //完成日期
    private Date finishDate;
    //物流公司
    private String wuLiu;
    //物流单号
    private String wuLiuNo;
    //终端类型
    private String terminalType;
    //外部单价
    private double outPrice;
    //赠品数量
    private int giftNum;
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
    //国家
    private String country;
    //客户备注
    private String customerRemark;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getNumId() {
        return numId;
    }

    public void setNumId(String numId) {
        this.numId = numId;
    }

    public String getTidType() {
        return tidType;
    }

    public void setTidType(String tidType) {
        this.tidType = tidType;
    }

    public String getImportMark() {
        return importMark;
    }

    public void setImportMark(String importMark) {
        this.importMark = importMark;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getInSysTime() {
        return inSysTime;
    }

    public void setInSysTime(String inSysTime) {
        this.inSysTime = inSysTime;
    }

    public String getChlidBarCode() {
        return chlidBarCode;
    }

    public void setChlidBarCode(String chlidBarCode) {
        this.chlidBarCode = chlidBarCode;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public OrderInfo[] getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(OrderInfo[] tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public String getServiceRemarks() {
        return serviceRemarks;
    }

    public void setServiceRemarks(String serviceRemarks) {
        this.serviceRemarks = serviceRemarks;
    }

    public String getOutTid() {
        return outTid;
    }

    public void setOutTid(String outTid) {
        this.outTid = outTid;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public int getOrderGoodsNum() {
        return orderGoodsNum;
    }

    public void setOrderGoodsNum(int orderGoodsNum) {
        this.orderGoodsNum = orderGoodsNum;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getCargoOperator() {
        return cargoOperator;
    }

    public void setCargoOperator(String cargoOperator) {
        this.cargoOperator = cargoOperator;
    }

    public String getCargoTime() {
        return cargoTime;
    }

    public void setCargoTime(String cargoTime) {
        this.cargoTime = cargoTime;
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public String getInspecter() {
        return inspecter;
    }

    public void setInspecter(String inspecter) {
        this.inspecter = inspecter;
    }

    public String getIsInspectDelivery() {
        return isInspectDelivery;
    }

    public void setIsInspectDelivery(String isInspectDelivery) {
        this.isInspectDelivery = isInspectDelivery;
    }

    public String getDeliveryOperator() {
        return deliveryOperator;
    }

    public void setDeliveryOperator(String deliveryOperator) {
        this.deliveryOperator = deliveryOperator;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public float getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(float grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getInternalNote() {
        return internalNote;
    }

    public void setInternalNote(String internalNote) {
        this.internalNote = internalNote;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getDestCode() {
        return destCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }

    public String getInspectionNum() {
        return inspectionNum;
    }

    public void setInspectionNum(String inspectionNum) {
        this.inspectionNum = inspectionNum;
    }

    public int getIsCover() {
        return isCover;
    }

    public void setIsCover(int isCover) {
        this.isCover = isCover;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getProceStatus() {
        return proceStatus;
    }

    public void setProceStatus(String proceStatus) {
        this.proceStatus = proceStatus;
    }

    public String getPlatformStatus() {
        return platformStatus;
    }

    public void setPlatformStatus(String platformStatus) {
        this.platformStatus = platformStatus;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getInvoiceIsprint() {
        return invoiceIsprint;
    }

    public void setInvoiceIsprint(String invoiceIsprint) {
        this.invoiceIsprint = invoiceIsprint;
    }

    public int getInvoiceIsopen() {
        return invoiceIsopen;
    }

    public void setInvoiceIsopen(int invoiceIsopen) {
        this.invoiceIsopen = invoiceIsopen;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public int getProductInfoType() {
        return productInfoType;
    }

    public void setProductInfoType(int productInfoType) {
        this.productInfoType = productInfoType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOutPayTid() {
        return outPayTid;
    }

    public void setOutPayTid(String outPayTid) {
        this.outPayTid = outPayTid;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(String orderChannel) {
        this.orderChannel = orderChannel;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAbnormalStatus() {
        return abnormalStatus;
    }

    public void setAbnormalStatus(String abnormalStatus) {
        this.abnormalStatus = abnormalStatus;
    }

    public String getMergeStatus() {
        return mergeStatus;
    }

    public void setMergeStatus(String mergeStatus) {
        this.mergeStatus = mergeStatus;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public int getInvoiceSituation() {
        return invoiceSituation;
    }

    public void setInvoiceSituation(int invoiceSituation) {
        this.invoiceSituation = invoiceSituation;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public double getProTotalFee() {
        return proTotalFee;
    }

    public void setProTotalFee(double proTotalFee) {
        this.proTotalFee = proTotalFee;
    }

    public double getOrderTotalFee() {
        return orderTotalFee;
    }

    public void setOrderTotalFee(double orderTotalFee) {
        this.orderTotalFee = orderTotalFee;
    }

    public double getReferencePricePaid() {
        return referencePricePaid;
    }

    public void setReferencePricePaid(double referencePricePaid) {
        this.referencePricePaid = referencePricePaid;
    }

    public String getInvoiceFee() {
        return invoiceFee;
    }

    public void setInvoiceFee(String invoiceFee) {
        this.invoiceFee = invoiceFee;
    }

    public String getCodFee() {
        return codFee;
    }

    public void setCodFee(String codFee) {
        this.codFee = codFee;
    }

    public String getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(String otherFee) {
        this.otherFee = otherFee;
    }

    public String getRefundTotalFee() {
        return refundTotalFee;
    }

    public void setRefundTotalFee(String refundTotalFee) {
        this.refundTotalFee = refundTotalFee;
    }

    public double getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(double discountFee) {
        this.discountFee = discountFee;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getChannelDisfee() {
        return channelDisfee;
    }

    public void setChannelDisfee(String channelDisfee) {
        this.channelDisfee = channelDisfee;
    }

    public String getMerchantDisFee() {
        return merchantDisFee;
    }

    public void setMerchantDisFee(String merchantDisFee) {
        this.merchantDisFee = merchantDisFee;
    }

    public double getOrderDisfee() {
        return orderDisfee;
    }

    public void setOrderDisfee(double orderDisfee) {
        this.orderDisfee = orderDisfee;
    }

    public String getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(String commissionFee) {
        this.commissionFee = commissionFee;
    }

    public int getIsCod() {
        return isCod;
    }

    public void setIsCod(int isCod) {
        this.isCod = isCod;
    }

    public int getPointPay() {
        return pointPay;
    }

    public void setPointPay(int pointPay) {
        this.pointPay = pointPay;
    }

    public String getCostPoint() {
        return costPoint;
    }

    public void setCostPoint(String costPoint) {
        this.costPoint = costPoint;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getSuperiorPoint() {
        return superiorPoint;
    }

    public void setSuperiorPoint(String superiorPoint) {
        this.superiorPoint = superiorPoint;
    }

    public String getRoyaltyFee() {
        return royaltyFee;
    }

    public void setRoyaltyFee(String royaltyFee) {
        this.royaltyFee = royaltyFee;
    }

    public String getExternalPoint() {
        return externalPoint;
    }

    public void setExternalPoint(String externalPoint) {
        this.externalPoint = externalPoint;
    }

    public String getOnlineExpress() {
        return onlineExpress;
    }

    public void setOnlineExpress(String onlineExpress) {
        this.onlineExpress = onlineExpress;
    }

    public String getSendingType() {
        return sendingType;
    }

    public void setSendingType(String sendingType) {
        this.sendingType = sendingType;
    }

    public double getRealIncomefreight() {
        return realIncomefreight;
    }

    public void setRealIncomefreight(double realIncomefreight) {
        this.realIncomefreight = realIncomefreight;
    }

    public double getRealPayFreight() {
        return realPayFreight;
    }

    public void setRealPayFreight(double realPayFreight) {
        this.realPayFreight = realPayFreight;
    }

    public String getGrossWeightFreight() {
        return grossWeightFreight;
    }

    public void setGrossWeightFreight(String grossWeightFreight) {
        this.grossWeightFreight = grossWeightFreight;
    }

    public String getNetWeightWreight() {
        return netWeightWreight;
    }

    public void setNetWeightWreight(String netWeightWreight) {
        this.netWeightWreight = netWeightWreight;
    }

    public String getFreightExplain() {
        return freightExplain;
    }

    public void setFreightExplain(String freightExplain) {
        this.freightExplain = freightExplain;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getTidTime() {
        return tidTime;
    }

    public void setTidTime(String tidTime) {
        this.tidTime = tidTime;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getOrderCreater() {
        return orderCreater;
    }

    public void setOrderCreater(String orderCreater) {
        this.orderCreater = orderCreater;
    }

    public String getBusinessMan() {
        return businessMan;
    }

    public void setBusinessMan(String businessMan) {
        this.businessMan = businessMan;
    }

    public String getPaymentReceivedOperator() {
        return paymentReceivedOperator;
    }

    public void setPaymentReceivedOperator(String paymentReceivedOperator) {
        this.paymentReceivedOperator = paymentReceivedOperator;
    }

    public String getPaymentReceivedTime() {
        return paymentReceivedTime;
    }

    public void setPaymentReceivedTime(String paymentReceivedTime) {
        this.paymentReceivedTime = paymentReceivedTime;
    }

    public String getReviewOrdersOperator() {
        return reviewOrdersOperator;
    }

    public void setReviewOrdersOperator(String reviewOrdersOperator) {
        this.reviewOrdersOperator = reviewOrdersOperator;
    }

    public String getReviewOrdersTime() {
        return reviewOrdersTime;
    }

    public void setReviewOrdersTime(String reviewOrdersTime) {
        this.reviewOrdersTime = reviewOrdersTime;
    }

    public String getFinanceReviewOperator() {
        return financeReviewOperator;
    }

    public void setFinanceReviewOperator(String financeReviewOperator) {
        this.financeReviewOperator = financeReviewOperator;
    }

    public String getFinanceReviewTime() {
        return financeReviewTime;
    }

    public void setFinanceReviewTime(String financeReviewTime) {
        this.financeReviewTime = financeReviewTime;
    }

    public String getAdvancePrinter() {
        return advancePrinter;
    }

    public void setAdvancePrinter(String advancePrinter) {
        this.advancePrinter = advancePrinter;
    }

    public String getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(String isPrint) {
        this.isPrint = isPrint;
    }

    public String getAdvDistributer() {
        return advDistributer;
    }

    public void setAdvDistributer(String advDistributer) {
        this.advDistributer = advDistributer;
    }

    public Date getAdvDistributTime() {
        return advDistributTime;
    }

    public void setAdvDistributTime(Date advDistributTime) {
        this.advDistributTime = advDistributTime;
    }

    public String getCancelOperator() {
        return cancelOperator;
    }

    public void setCancelOperator(String cancelOperator) {
        this.cancelOperator = cancelOperator;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getRevokeCanceler() {
        return revokeCanceler;
    }

    public void setRevokeCanceler(String revokeCanceler) {
        this.revokeCanceler = revokeCanceler;
    }

    public String getRevokeCancelTime() {
        return revokeCancelTime;
    }

    public void setRevokeCancelTime(String revokeCancelTime) {
        this.revokeCancelTime = revokeCancelTime;
    }

    public String getPackager() {
        return packager;
    }

    public void setPackager(String packager) {
        this.packager = packager;
    }

    public String getPackTime() {
        return packTime;
    }

    public void setPackTime(String packTime) {
        this.packTime = packTime;
    }

    public String getWeighOperator() {
        return weighOperator;
    }

    public void setWeighOperator(String weighOperator) {
        this.weighOperator = weighOperator;
    }

    public String getWeighTime() {
        return weighTime;
    }

    public void setWeighTime(String weighTime) {
        this.weighTime = weighTime;
    }

    public Date getBookDeliveryTime() {
        return bookDeliveryTime;
    }

    public void setBookDeliveryTime(Date bookDeliveryTime) {
        this.bookDeliveryTime = bookDeliveryTime;
    }

    public String getLocker() {
        return locker;
    }

    public void setLocker(String locker) {
        this.locker = locker;
    }

    public String getLockTime() {
        return lockTime;
    }

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
    }

    public String getBookFileTime() {
        return bookFileTime;
    }

    public void setBookFileTime(String bookFileTime) {
        this.bookFileTime = bookFileTime;
    }

    public String getFileOperator() {
        return fileOperator;
    }

    public void setFileOperator(String fileOperator) {
        this.fileOperator = fileOperator;
    }

    public String getFileTime() {
        return fileTime;
    }

    public void setFileTime(String fileTime) {
        this.fileTime = fileTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getModityTime() {
        return modityTime;
    }

    public void setModityTime(String modityTime) {
        this.modityTime = modityTime;
    }

    public String getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(String isPromotion) {
        this.isPromotion = isPromotion;
    }

    public String getPromotionPlan() {
        return promotionPlan;
    }

    public void setPromotionPlan(String promotionPlan) {
        this.promotionPlan = promotionPlan;
    }

    public String getOutPromotionDetail() {
        return outPromotionDetail;
    }

    public void setOutPromotionDetail(String outPromotionDetail) {
        this.outPromotionDetail = outPromotionDetail;
    }

    public String getGoodReceiveTime() {
        return goodReceiveTime;
    }

    public void setGoodReceiveTime(String goodReceiveTime) {
        this.goodReceiveTime = goodReceiveTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getVerificatyTime() {
        return verificatyTime;
    }

    public void setVerificatyTime(String verificatyTime) {
        this.verificatyTime = verificatyTime;
    }

    public String getEnableInteStoTime() {
        return enableInteStoTime;
    }

    public void setEnableInteStoTime(String enableInteStoTime) {
        this.enableInteStoTime = enableInteStoTime;
    }

    public String getEnableInteDeliveryTime() {
        return enableInteDeliveryTime;
    }

    public void setEnableInteDeliveryTime(String enableInteDeliveryTime) {
        this.enableInteDeliveryTime = enableInteDeliveryTime;
    }

    public String getAlipayId() {
        return alipayId;
    }

    public void setAlipayId(String alipayId) {
        this.alipayId = alipayId;
    }

    public String getAlipayStatus() {
        return alipayStatus;
    }

    public void setAlipayStatus(String alipayStatus) {
        this.alipayStatus = alipayStatus;
    }

    public String getPayMothed() {
        return payMothed;
    }

    public void setPayMothed(String payMothed) {
        this.payMothed = payMothed;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDistributorMark() {
        return distributorMark;
    }

    public void setDistributorMark(String distributorMark) {
        this.distributorMark = distributorMark;
    }

    public String getSystemRemarks() {
        return systemRemarks;
    }

    public void setSystemRemarks(String systemRemarks) {
        this.systemRemarks = systemRemarks;
    }

    public String getOtherRemarks() {
        return otherRemarks;
    }

    public void setOtherRemarks(String otherRemarks) {
        this.otherRemarks = otherRemarks;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getIsStock() {
        return isStock;
    }

    public void setIsStock(String isStock) {
        this.isStock = isStock;
    }

    public String getRelatedOrders() {
        return relatedOrders;
    }

    public void setRelatedOrders(String relatedOrders) {
        this.relatedOrders = relatedOrders;
    }

    public String getRelatedOrdersType() {
        return relatedOrdersType;
    }

    public void setRelatedOrdersType(String relatedOrdersType) {
        this.relatedOrdersType = relatedOrdersType;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getIsNewCustomer() {
        return isNewCustomer;
    }

    public void setIsNewCustomer(String isNewCustomer) {
        this.isNewCustomer = isNewCustomer;
    }

    public String getDistributorLevel() {
        return distributorLevel;
    }

    public void setDistributorLevel(String distributorLevel) {
        this.distributorLevel = distributorLevel;
    }

    public double getCodServiceFee() {
        return codServiceFee;
    }

    public void setCodServiceFee(double codServiceFee) {
        this.codServiceFee = codServiceFee;
    }

    public String getExpressColFee() {
        return expressColFee;
    }

    public void setExpressColFee(String expressColFee) {
        this.expressColFee = expressColFee;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getSingleNum() {
        return singleNum;
    }

    public void setSingleNum(String singleNum) {
        this.singleNum = singleNum;
    }

    public String getFlagColor() {
        return flagColor;
    }

    public void setFlagColor(String flagColor) {
        this.flagColor = flagColor;
    }

    public String getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag;
    }

    public String getTaobaoDeliveryOrderStatus() {
        return taobaoDeliveryOrderStatus;
    }

    public void setTaobaoDeliveryOrderStatus(String taobaoDeliveryOrderStatus) {
        this.taobaoDeliveryOrderStatus = taobaoDeliveryOrderStatus;
    }

    public String getTaobaoDeliveryStatus() {
        return taobaoDeliveryStatus;
    }

    public void setTaobaoDeliveryStatus(String taobaoDeliveryStatus) {
        this.taobaoDeliveryStatus = taobaoDeliveryStatus;
    }

    public String getTaobaoDeliveryMethod() {
        return taobaoDeliveryMethod;
    }

    public void setTaobaoDeliveryMethod(String taobaoDeliveryMethod) {
        this.taobaoDeliveryMethod = taobaoDeliveryMethod;
    }

    public String getOrderProcessTime() {
        return orderProcessTime;
    }

    public void setOrderProcessTime(String orderProcessTime) {
        this.orderProcessTime = orderProcessTime;
    }

    public String getIsBreak() {
        return isBreak;
    }

    public void setIsBreak(String isBreak) {
        this.isBreak = isBreak;
    }

    public String getBreaker() {
        return breaker;
    }

    public void setBreaker(String breaker) {
        this.breaker = breaker;
    }

    public String getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
    }

    public String getBreakExplain() {
        return breakExplain;
    }

    public void setBreakExplain(String breakExplain) {
        this.breakExplain = breakExplain;
    }

    public String getPlatSendStatus() {
        return platSendStatus;
    }

    public void setPlatSendStatus(String platSendStatus) {
        this.platSendStatus = platSendStatus;
    }

    public String getPlatType() {
        return platType;
    }

    public void setPlatType(String platType) {
        this.platType = platType;
    }

    public int getIsAdvSale() {
        return isAdvSale;
    }

    public void setIsAdvSale(int isAdvSale) {
        this.isAdvSale = isAdvSale;
    }

    public String getProvincCode() {
        return provincCode;
    }

    public void setProvincCode(String provincCode) {
        this.provincCode = provincCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getLastReturnedTime() {
        return lastReturnedTime;
    }

    public void setLastReturnedTime(String lastReturnedTime) {
        this.lastReturnedTime = lastReturnedTime;
    }

    public String getLastRefundTime() {
        return lastRefundTime;
    }

    public void setLastRefundTime(String lastRefundTime) {
        this.lastRefundTime = lastRefundTime;
    }

    public String getDeliverCentre() {
        return deliverCentre;
    }

    public void setDeliverCentre(String deliverCentre) {
        this.deliverCentre = deliverCentre;
    }

    public String getDeliverStation() {
        return deliverStation;
    }

    public void setDeliverStation(String deliverStation) {
        this.deliverStation = deliverStation;
    }

    public String getIsPreDeliveryNotice() {
        return isPreDeliveryNotice;
    }

    public void setIsPreDeliveryNotice(String isPreDeliveryNotice) {
        this.isPreDeliveryNotice = isPreDeliveryNotice;
    }

    public String getJdDeliveryTime() {
        return jdDeliveryTime;
    }

    public void setJdDeliveryTime(String jdDeliveryTime) {
        this.jdDeliveryTime = jdDeliveryTime;
    }

    public String getSortingCode() {
        return sortingCode;
    }

    public void setSortingCode(String sortingCode) {
        this.sortingCode = sortingCode;
    }

    public String getCodSettlementVouchernumber() {
        return codSettlementVouchernumber;
    }

    public void setCodSettlementVouchernumber(String codSettlementVouchernumber) {
        this.codSettlementVouchernumber = codSettlementVouchernumber;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerAlipay() {
        return buyerAlipay;
    }

    public void setBuyerAlipay(String buyerAlipay) {
        this.buyerAlipay = buyerAlipay;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPrivince() {
        return privince;
    }

    public void setPrivince(String privince) {
        this.privince = privince;
    }

    public String getCitys() {
        return citys;
    }

    public void setCitys(String citys) {
        this.citys = citys;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getInvoiceMoney() {
        return invoiceMoney;
    }

    public void setInvoiceMoney(double invoiceMoney) {
        this.invoiceMoney = invoiceMoney;
    }

    public double getPayCommission() {
        return payCommission;
    }

    public void setPayCommission(double payCommission) {
        this.payCommission = payCommission;
    }

    public int getPayScore() {
        return payScore;
    }

    public void setPayScore(int payScore) {
        this.payScore = payScore;
    }

    public int getReturnScore() {
        return returnScore;
    }

    public void setReturnScore(int returnScore) {
        this.returnScore = returnScore;
    }

    public String getAlipayTransactionNo() {
        return alipayTransactionNo;
    }

    public void setAlipayTransactionNo(String alipayTransactionNo) {
        this.alipayTransactionNo = alipayTransactionNo;
    }

    public String getOutExpressMethod() {
        return outExpressMethod;
    }

    public void setOutExpressMethod(String outExpressMethod) {
        this.outExpressMethod = outExpressMethod;
    }

    public String getOutOrderStatus() {
        return outOrderStatus;
    }

    public void setOutOrderStatus(String outOrderStatus) {
        this.outOrderStatus = outOrderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getWuLiu() {
        return wuLiu;
    }

    public void setWuLiu(String wuLiu) {
        this.wuLiu = wuLiu;
    }

    public String getWuLiuNo() {
        return wuLiuNo;
    }

    public void setWuLiuNo(String wuLiuNo) {
        this.wuLiuNo = wuLiuNo;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public double getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(double outPrice) {
        this.outPrice = outPrice;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }

    public String getProductStockout() {
        return productStockout;
    }

    public void setProductStockout(String productStockout) {
        this.productStockout = productStockout;
    }

    public int getIsBook() {
        return isBook;
    }

    public void setIsBook(int isBook) {
        this.isBook = isBook;
    }

    public int getIsGift() {
        return isGift;
    }

    public void setIsGift(int isGift) {
        this.isGift = isGift;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public double getProductFreight() {
        return productFreight;
    }

    public void setProductFreight(double productFreight) {
        this.productFreight = productFreight;
    }

    public String getOutProductId() {
        return outProductId;
    }

    public void setOutProductId(String outProductId) {
        this.outProductId = outProductId;
    }

    public String getOutBarCode() {
        return outBarCode;
    }

    public void setOutBarCode(String outBarCode) {
        this.outBarCode = outBarCode;
    }

    public String getProductIntro() {
        return productIntro;
    }

    public void setProductIntro(String productIntro) {
        this.productIntro = productIntro;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCustomerRemark() {
        return customerRemark;
    }

    public void setCustomerRemark(String customerRemark) {
        this.customerRemark = customerRemark;
    }
}
