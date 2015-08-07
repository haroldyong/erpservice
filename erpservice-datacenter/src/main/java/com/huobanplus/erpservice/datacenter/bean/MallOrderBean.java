package com.huobanplus.erpservice.datacenter.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 订单实体
 * Created by allan on 2015/7/10.
 */
@Entity
@Table(name = "Mall_Orders")
public class MallOrderBean   {

    /**
     * 库房id、仓库编号
     */
    @Column(name = "Storage_Id")
    private String storageId;

    /**
     * 订单编号(E店宝中订单编号)
     */
    @Column(name = "Tid")
    private String tid;

    /**
     * 交易编号
     */
    @Column(name = "Transaction_Id")
    private String transactionId;

    /**
     * 客户编号
     */
    @Column(name = "Customer_Id")
    private String customerId;

    /**
     * 分销商编号
     */
    @Column(name = "Distributor_Id")
    private String distributorId;

    /**
     * 店铺名称
     */
    @Column(name = "Shop_Name")
    private String shopName;

    /**
     * 原始订单编号/外部订单编号
     */
    @Column(name = "Out_Tid")
    private String outTid;

    /**
     * 外部平台付款单号
     */
    @Column(name = "Out_Pay_Tid")
    private String outPayTid;

    /**
     * 凭证单号
     */
    @Column(name = "Voucher_Id")
    private String voucherId;

    /**
     * 店铺id
     */
    @Column(name = "Shop_Id")
    private String shopId;

    /**
     * 流水号
     */
    @Column(name = "Serial_Num")
    private String serialNum;

    /**
     * 订单渠道
     */
    @Column(name = "Order_Channel")
    private String orderChannel;

    /**
     * 订单来源
     */
    @Column(name = "Order_From")
    private String orderFrom;

    /**
     * 买家ID
     */
    @Column(name = "Buyer_Id")
    private String buyerId;

    /**
     * 买家姓名
     */
    @Column(name = "Buyer_Name")
    private String buyerName;

    /**
     * 订单类型
     */
    @Column(name = "Type")
    private String type;

    /**
     * 处理状态
     */
    @Column(name = "Status")
    private String status;

    /**
     * 异常状态
     */
    @Column(name = "Abnormal_Status")
    private String abnormalStatus;

    /**
     * 合并状态
     */
    @Column(name = "Merge_Status")
    private String mergeStatus;

    /**
     * 收货人
     */
    @Column(name = "Receiver_Name")
    private String receiverName;

    /**
     * 收货手机
     */
    @Column(name = "Receiver_Mobile")
    private String receiverMobile;

    /**
     * 电话
     */
    @Column(name = "Phone")
    private String phone;
    /**
     * 省
     */
    @Column(name = "Province")
    private String province;
    /**
     * 市
     */
    @Column(name = "City")
    private String city;
    /**
     * 区
     */
    @Column(name = "District")
    private String district;

    /**
     * 地址
     */
    @Column(name = "Address")
    private String address;

    /**
     * 邮编
     */
    @Column(name = "Post")
    private String post;
    /**
     * 电子邮件
     */
    @Column(name = "Email")
    private String email;

    /**
     * 是否开发票 (0:未开/1:已开
     */
    @Column(name = "Is_bill")
    private int isBill;

    /**
     * 发票名称
     */
    @Column(name = "Invoice_Name")
    private String invoiceName;
    /**
     * 开票情况
     */
    @Column(name = "Invoice_Situation")
    private int invoiceSituation;
    /**
     * 发票抬头
     */
    @Column(name = "Invoice_Title")
    private String invoiceTitle;

    /**
     * 发票类型
     */
    @Column(name = "Invoice_Type")
    private String invoiceType;
    /**
     * 开票内容
     */
    @Column(name = "Invoice_Content")
    private String invoiceContent;

    /**
     * 产品总金额
     */
    @Column(name = "Pro_Total_Fee")
    private double proTotalFee;
    /**
     * 订单总金额
     */
    @Column(name = "Order_Total_Fee")
    private double orderTotalFee;
    /**
     * 实收参考价
     */
    @Column(name = "Reference_Price_Paid")
    private double referencePricePaid;

    /**
     * 发票金额
     */
    @Column(name = "Invoice_Fee")
    private double invoiceFee;
    /**
     * 货到付款金额
     */
    @Column(name = "Cod_Fee")
    private String codFee;
    /**
     * 其他费用
     */
    @Column(name = "Other_Fee")
    private String otherFee;
    /**
     * 退款总金额
     */
    @Column(name = "Refund_Total_Fee")
    private String refundTotalFee;

    /**
     * 优惠金额
     */
    @Column(name = "Discount_Fee")
    private double discountFee;
    /**
     * 折扣
     */
    @Column(name = "Discount")
    private double discount;
    /**
     * 渠道优惠金额
     */
    @Column(name = "Channel_Disfee")
    private String channelDisfee;
    /**
     * 商家优惠金额
     */
    @Column(name = "Merchant_Dis_Fee")
    private String merchantDisFee;
    /**
     * 整单优惠
     */
    @Column(name = "Order_Disfee")
    private double orderDisfee;
    /**
     * 佣金
     */
    @Column(name = "Commission_Fee")
    private double commissionFee;
    /**
     * 是否货到付款
     */
    @Column(name = "Is_Cod")
    private int isCod;
    /**
     * 是否积分换购
     */
    @Column(name = "Point_Pay")
    private int pointPay;
    /**
     * 消耗积分
     */
    @Column(name = "Cost_Point")
    private double costPoint;
    /**
     * 获得积分
     */
    @Column(name = "Point")
    private String point;
    /**
     * 上级积分
     */
    @Column(name = "Superior_Point")
    private String superiorPoint;
    /**
     * 提成金额
     */
    @Column(name = "Royalty_Fee")
    private String royaltyFee;
    /**
     * 外部积分
     */
    @Column(name = "External_Point")
    private String externalPoint;

    /**
     * 快递单号
     */
    @Column(name = "Express_No")
    private String expressNo;

    /**
     * 赠品添加
     */
    @Column(name = "Trade_Gifadd")
    private String tradeGifadd;

    public String getTradeGifadd() {
        return tradeGifadd;
    }

    public void setTradeGifadd(String tradeGifadd) {
        this.tradeGifadd = tradeGifadd;
    }

    /**
     * 快递公司名：需要在EDB中存在
     */
    @Column(name = "Express")
    private String express;

    /**
     * 快递代码
     */
    @Column(name = "Express_Coding")
    private String expressCoding;

    /**
     * 线上快递公司
     */
    @Column(name = "Online_Express")
    private String onlineExpress;
    /**
     * 配送方式
     */
    @Column(name = "Sending_Type")
    private String sendingType;

    /**
     * 实收运费
     */
    @Column(name = "Real_Income_Freight")
    private double realIncomefreight;
    /**
     * 实付运费
     */
    @Column(name = "Real_Pay_Freight")
    private double realPayFreight;
    /**
     * 毛重运费
     */
    @Column(name = "Gross_Weight_Freight")
    private double grossWeightFreight;
    /**
     * 净重运费
     */
    @Column(name = "Net_Weight_Wreight")
    private String netWeightWreight;
    /**
     * 运费说明
     */
    @Column(name = "Freight_Explain")
    private String freightExplain;
    /**
     * 总重量
     */
    @Column(name = "Total_Weight")
    private double totalWeight;

    /**
     * 订单净重
     */
    @Column(name = "Tid_Net_Weight")
    private double tidNetWeight;

    /**
     * 订货时间
     */
    @Column(name = "Tid_Time")
    private Date tidTime;

    /**
     * 订单付款时间
     */
    @Column(name = "Pay_Time")
    private Date payTime;

    /**
     * 获取时间
     */
    @Column(name = "Get_Time")
    private Date getTime;
    /**
     * 下单员
     */
    @Column(name = "Order_Creater")
    private String orderCreater;
    /**
     * 业务员
     */
    @Column(name = "Business_Man")
    private String businessMan;
    /**
     * 到款员
     */
    @Column(name = "Payment_Received_Operator")
    private String paymentReceivedOperator;
    /**
     * 到款时间
     */
    @Column(name = "Payment_Received_Time")
    private Date paymentReceivedTime;
    /**
     * 审单员
     */
    @Column(name = "Review_Orders_Operator")
    private String reviewOrdersOperator;
    /**
     * 审单时间
     */
    @Column(name = "Review_Orders_Time")
    private Date reviewOrdersTime;
    /**
     * 财务审核人
     */
    @Column(name = "Finance_Review_Operator")
    private String financeReviewOperator;
    /**
     * 财务审核时间
     */
    @Column(name = "Finance_Review_Time")
    private Date financeReviewTime;
    /**
     * 预打印员
     */
    @Column(name = "Advance_Printer")
    private String advancePrinter;

    /**
     * 打印员
     */
    @Column(name = "Printer")
    private String printer;

    /**
     * 打印时间
     */
    @Column(name = "Print_Time")
    private Date printTime;

    /**
     * 是否打印
     */
    @Column(name = "Is_Print")
    private String isPrint;
    /**
     * 预配货员
     */
    @Column(name = "Adv_Distributer")
    private String advDistributer;
    /**
     * 预配货时间
     */
    @Column(name = "Adv_DistributTime")
    private Date advDistributTime;

    /**
     * 配货员
     */
    @Column(name = "Distributer")
    private String distributer;

    /**
     * 配货时间
     */
    @Column(name = "Distribut_Time")
    private Date distributTime;

    /**
     * 验货员
     */
    @Column(name = "Inspecter")
    private String inspecter;

    /**
     * 验货时间
     */
    @Column(name = "Inspect_Time")
    private Date inspectTime;

    /**
     * 取消员
     */
    @Column(name = "Cancel_Operator")
    private String cancelOperator;
    /**
     * 取消时间
     */
    @Column(name = "Cancel_Time")
    private Date cancelTime;
    /**
     * 反取消员
     */
    @Column(name = "Revoke_Canceler")
    private String revokeCanceler;
    /**
     * 反取消时间
     */
    @Column(name = "Revoke_Cancel_Time")
    private Date revokeCancelTime;
    /**
     * 打包员
     */
    @Column(name = "Packager")
    private String packager;
    /**
     * 打包时间
     */
    @Column(name = "Pack_Time")
    private Date packTime;
    /**
     * 称重员
     */
    @Column(name = "Weigh_Operator")
    private String weighOperator;
    /**
     * 称重时间
     */
    @Column(name = "Weigh_Time")
    private Date weighTime;
    /**
     * 预计发货时间
     */
    @Column(name = "Book_Delivery_Time")
    private Date bookDeliveryTime;

    /**
     * 发货员
     */
    @Column(name = "Delivery_Operator")
    private String deliveryOperator;
    /**
     * 发货时间
     */
    @Column(name = "Delivery_Time")
    private Date deliveryTime;


    /**
     * 锁定员
     */
    @Column(name = "Locker")
    private String locker;
    /**
     * 锁定时间
     */
    @Column(name = "LockTime")
    private Date lockTime;
    /**
     * 预计归档时间
     */
    @Column(name = "Book_File_Time")
    private Date bookFileTime;
    /**
     * 归档员
     */
    @Column(name = "File_Operator")
    private String fileOperator;
    /**
     * 归档时间
     */
    @Column(name = "File_Time")
    private Date fileTime;
    /**
     * 完成时间
     */
    @Column(name = "Finish_Time")
    private Date finishTime;
    /**
     * 订单修改时间
     */
    @Column(name = "Modity_Time")
    private Date modityTime;
    /**
     * 促销标记
     */
    @Column(name = "Is_Promotion")
    private String isPromotion;
    /**
     * 满足的促销方案
     */
    @Column(name = "Promotion_Plan")
    private String promotionPlan;
    /**
     * 外部平台促销详情
     */
    @Column(name = "Out_Promotion_Detail")
    private String outPromotionDetail;
    /**
     * 到货日期
     */
    @Column(name = "Good_Receive_Time")
    private Date goodReceiveTime;
    /**
     * 生成应收时间
     */
    @Column(name = "Receive_Time")
    private Date receiveTime;
    /**
     * 核销日期
     */
    @Column(name = "Verificaty_Time")
    private Date verificatyTime;
    /**
     * 启用智能仓库时间
     */
    @Column(name = "Enable_Inte_Sto_Time")
    private Date enableInteStoTime;
    /**
     * 启用智能快递时间
     */
    @Column(name = "Enable_Inte_Delivery_Time")
    private Date enableInteDeliveryTime;
    /**
     * 支付宝账户
     */
    @Column(name = "Alipay_Id")
    private String alipayId;
    /**
     * 支付宝状态
     */
    @Column(name = "Alipay_Status")
    private String alipayStatus;
    /**
     * 付款状态
     */
    @Column(name = "Pay_Status")
    private String payStatus;
    /**
     * 支付方式
     */
    @Column(name = "Pay_Mothed")
    private String payMothed;

    /**
     * 外部平台状态
     */
    @Column(name = "Platform_Status")
    private String platformStatus;


    /**
     * 汇率
     */
    @Column(name = "Rate")
    private String rate;
    /**
     * 币种
     */
    @Column(name = "Currency")
    private String currency;
    /**
     * 发货状态
     */
    @Column(name = "Delivery_Status")
    private String deliveryStatus;

    /**
     * 买家留言
     */
    @Column(name = "Buyer_Message")
    private String buyerMessage;
    /**
     * 客服备注
     */
    @Column(name = "Service_Remarks")
    private String serviceRemarks;

    /**
     * 内部便签
     */
    @Column(name = "Inner_Lable")
    private String innerLable;

    /**
     * 分销商便签
     */
    @Column(name = "Distributor_Mark")
    private String distributorMark;
    /**
     * 系统备注
     */
    @Column(name = "System_Remarks")
    private String systemRemarks;
    /**
     * 其他备注
     */
    @Column(name = "Other_Remarks")
    private String otherRemarks;
    /**
     * 短信通知
     */
    @Column(name = "Message")
    private String message;
    /**
     * 短信发送时间
     */
    @Column(name = "Message_Time")
    private Date messageTime;
    /**
     * 是否缺货
     */
    @Column(name = "Is_Stock")
    private String isStock;
    /**
     * 相关订单
     */
    @Column(name = "Related_Orders")
    private String relatedOrders;
    /**
     * 相关订单类型
     */
    @Column(name = "Related_Orders_Type")
    private String relatedOrdersType;

    /**
     * 导入标记
     */
    @Column(name = "Import_Mark")
    private String importMark;

    /**
     * 第三方快递名称
     */
    @Column(name = "Delivery_Name")
    private String deliveryName;

    /**
     * 是否新客户
     */
    @Column(name = "Is_New_Customer")
    private String isNewCustomer;
    /**
     * 分销商等级
     */
    @Column(name = "Distributor_Level")
    private String distributorLevel;
    /**
     * 货到付款服务费
     */
    @Column(name = "Cod_Service_Fee")
    private double codServiceFee;
    /**
     * 快递代收金额
     */
    @Column(name = "Express_Col_Fee")
    private String expressColFee;
    /**
     * 产品数量
     */
    @Column(name = "Product_Num")
    private String productNum;
    /**
     * 产品条形码
     */
    @Column(name = "Sku")
    private String sku;

    /**
     * 单品条数
     */
    @Column(name = "item_Num")
    private int itemNum;
    /**
     * 单品数量
     */
    @Column(name = "Single_Num")
    private String singleNum;
    /**
     * 旗帜颜色
     */
    @Column(name = "Flag_Color")
    private String flagColor;
    /**
     * 是否插旗
     */
    @Column(name = "Is_Flag")
    private String isFlag;
    /**
     * 淘宝快递订单状态
     */
    @Column(name = "Taobao_Delivery_Order_Status")
    private String taobaoDeliveryOrderStatus;
    /**
     * 淘宝快递状态
     */
    @Column(name = "Taobao_Delivery_Status")
    private String taobaoDeliveryStatus;
    /**
     * 淘宝快递方式
     */
    @Column(name = "Taobao_Delivery_Method")
    private String taobaoDeliveryMethod;

    /**
     * 处理订单需要的时间戳
     */
    @Column(name = "Order_Process_Time")
    private long orderProcessTime;

    /**
     * 是否中断
     */
    @Column(name = "Is_Break")
    private String isBreak;

    /**
     * 中断员
     */
    @Column(name = "Breaker")
    private String breaker;
    /**
     * 中断时间
     */
    @Column(name = "Break_Time")
    private Date breakTime;
    /**
     * 中断说明
     */
    @Column(name = "Break_Explain")
    private String breakExplain;
    /**
     * 平台发货状态
     */
    @Column(name = "Plat_Send_Status")
    private String platSendStatus;
    /**
     * 平台类型
     */
    @Column(name = "Plat_Type")
    private String platType;
    /**
     * 是否预售
     */
    @Column(name = "Is_AdvSale")
    private int isAdvSale;
    /**
     * 省编码
     */
    @Column(name = "Provinc_Code")
    private String provincCode;
    /**
     * 市编码
     */
    @Column(name = "City_Code")
    private String cityCode;
    /**
     * 区编码
     */
    @Column(name = "Area_Code")
    private String areaCode;
    /**
     * 最后一次退货时间
     */
    @Column(name = "Last_Returned_Time")
    private Date lastReturnedTime;
    /**
     * 最后一次退款时间
     */
    @Column(name = "Last_Refund_Time")
    private Date lastRefundTime;
    /**
     * 配送中心名称
     */
    @Column(name = "Deliver_Centre")
    private String deliverCentre;
    /**
     * 配送站点名称
     */
    @Column(name = "Deliver_Station")
    private String deliverStation;
    /**
     * 是否送货前通知
     */
    @Column(name = "Is_PreDelivery_Notice")
    private String isPreDeliveryNotice;
    /**
     * 送货时间
     */
    @Column(name = "Jd_Delivery_Time")
    private Date jdDeliveryTime;
    /**
     * 分拣代码
     */
    @Column(name = "Sorting_Code")
    private String sortingCode;
    /**
     * 货到付款结算凭证号
     */
    @Column(name = "Cod_Settlement_Vouchernumber")
    private String codSettlementVouchernumber;
    //END


    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
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

    public String getOutTid() {
        return outTid;
    }

    public void setOutTid(String outTid) {
        this.outTid = outTid;
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getIsBill() {
        return isBill;
    }

    public void setIsBill(int isBill) {
        this.isBill = isBill;
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

    public double getInvoiceFee() {
        return invoiceFee;
    }

    public void setInvoiceFee(double invoiceFee) {
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
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

    public double getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(double commissionFee) {
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

    public double getCostPoint() {
        return costPoint;
    }

    public void setCostPoint(double costPoint) {
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

    public String getExpressCoding() {
        return expressCoding;
    }

    public void setExpressCoding(String expressCoding) {
        this.expressCoding = expressCoding;
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

    public double getGrossWeightFreight() {
        return grossWeightFreight;
    }

    public void setGrossWeightFreight(double grossWeightFreight) {
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

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getTidNetWeight() {
        return tidNetWeight;
    }

    public void setTidNetWeight(double tidNetWeight) {
        this.tidNetWeight = tidNetWeight;
    }

    public Date getTidTime() {
        return tidTime;
    }

    public void setTidTime(Date tidTime) {
        this.tidTime = tidTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
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

    public Date getPaymentReceivedTime() {
        return paymentReceivedTime;
    }

    public void setPaymentReceivedTime(Date paymentReceivedTime) {
        this.paymentReceivedTime = paymentReceivedTime;
    }

    public String getReviewOrdersOperator() {
        return reviewOrdersOperator;
    }

    public void setReviewOrdersOperator(String reviewOrdersOperator) {
        this.reviewOrdersOperator = reviewOrdersOperator;
    }

    public Date getReviewOrdersTime() {
        return reviewOrdersTime;
    }

    public void setReviewOrdersTime(Date reviewOrdersTime) {
        this.reviewOrdersTime = reviewOrdersTime;
    }

    public String getFinanceReviewOperator() {
        return financeReviewOperator;
    }

    public void setFinanceReviewOperator(String financeReviewOperator) {
        this.financeReviewOperator = financeReviewOperator;
    }

    public Date getFinanceReviewTime() {
        return financeReviewTime;
    }

    public void setFinanceReviewTime(Date financeReviewTime) {
        this.financeReviewTime = financeReviewTime;
    }

    public String getAdvancePrinter() {
        return advancePrinter;
    }

    public void setAdvancePrinter(String advancePrinter) {
        this.advancePrinter = advancePrinter;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public Date getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
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

    public String getDistributer() {
        return distributer;
    }

    public void setDistributer(String distributer) {
        this.distributer = distributer;
    }

    public Date getDistributTime() {
        return distributTime;
    }

    public void setDistributTime(Date distributTime) {
        this.distributTime = distributTime;
    }

    public String getInspecter() {
        return inspecter;
    }

    public void setInspecter(String inspecter) {
        this.inspecter = inspecter;
    }

    public Date getInspectTime() {
        return inspectTime;
    }

    public void setInspectTime(Date inspectTime) {
        this.inspectTime = inspectTime;
    }

    public String getCancelOperator() {
        return cancelOperator;
    }

    public void setCancelOperator(String cancelOperator) {
        this.cancelOperator = cancelOperator;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getRevokeCanceler() {
        return revokeCanceler;
    }

    public void setRevokeCanceler(String revokeCanceler) {
        this.revokeCanceler = revokeCanceler;
    }

    public Date getRevokeCancelTime() {
        return revokeCancelTime;
    }

    public void setRevokeCancelTime(Date revokeCancelTime) {
        this.revokeCancelTime = revokeCancelTime;
    }

    public String getPackager() {
        return packager;
    }

    public void setPackager(String packager) {
        this.packager = packager;
    }

    public Date getPackTime() {
        return packTime;
    }

    public void setPackTime(Date packTime) {
        this.packTime = packTime;
    }

    public String getWeighOperator() {
        return weighOperator;
    }

    public void setWeighOperator(String weighOperator) {
        this.weighOperator = weighOperator;
    }

    public Date getWeighTime() {
        return weighTime;
    }

    public void setWeighTime(Date weighTime) {
        this.weighTime = weighTime;
    }

    public Date getBookDeliveryTime() {
        return bookDeliveryTime;
    }

    public void setBookDeliveryTime(Date bookDeliveryTime) {
        this.bookDeliveryTime = bookDeliveryTime;
    }

    public String getDeliveryOperator() {
        return deliveryOperator;
    }

    public void setDeliveryOperator(String deliveryOperator) {
        this.deliveryOperator = deliveryOperator;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getLocker() {
        return locker;
    }

    public void setLocker(String locker) {
        this.locker = locker;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public Date getBookFileTime() {
        return bookFileTime;
    }

    public void setBookFileTime(Date bookFileTime) {
        this.bookFileTime = bookFileTime;
    }

    public String getFileOperator() {
        return fileOperator;
    }

    public void setFileOperator(String fileOperator) {
        this.fileOperator = fileOperator;
    }

    public Date getFileTime() {
        return fileTime;
    }

    public void setFileTime(Date fileTime) {
        this.fileTime = fileTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getModityTime() {
        return modityTime;
    }

    public void setModityTime(Date modityTime) {
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

    public Date getGoodReceiveTime() {
        return goodReceiveTime;
    }

    public void setGoodReceiveTime(Date goodReceiveTime) {
        this.goodReceiveTime = goodReceiveTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getVerificatyTime() {
        return verificatyTime;
    }

    public void setVerificatyTime(Date verificatyTime) {
        this.verificatyTime = verificatyTime;
    }

    public Date getEnableInteStoTime() {
        return enableInteStoTime;
    }

    public void setEnableInteStoTime(Date enableInteStoTime) {
        this.enableInteStoTime = enableInteStoTime;
    }

    public Date getEnableInteDeliveryTime() {
        return enableInteDeliveryTime;
    }

    public void setEnableInteDeliveryTime(Date enableInteDeliveryTime) {
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

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayMothed() {
        return payMothed;
    }

    public void setPayMothed(String payMothed) {
        this.payMothed = payMothed;
    }

    public String getPlatformStatus() {
        return platformStatus;
    }

    public void setPlatformStatus(String platformStatus) {
        this.platformStatus = platformStatus;
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

    public String getInnerLable() {
        return innerLable;
    }

    public void setInnerLable(String innerLable) {
        this.innerLable = innerLable;
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

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
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

    public String getImportMark() {
        return importMark;
    }

    public void setImportMark(String importMark) {
        this.importMark = importMark;
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

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
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

    public long getOrderProcessTime() {
        return orderProcessTime;
    }

    public void setOrderProcessTime(long orderProcessTime) {
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

    public Date getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(Date breakTime) {
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

    public Date getLastReturnedTime() {
        return lastReturnedTime;
    }

    public void setLastReturnedTime(Date lastReturnedTime) {
        this.lastReturnedTime = lastReturnedTime;
    }

    public Date getLastRefundTime() {
        return lastRefundTime;
    }

    public void setLastRefundTime(Date lastRefundTime) {
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

    public Date getJdDeliveryTime() {
        return jdDeliveryTime;
    }

    public void setJdDeliveryTime(Date jdDeliveryTime) {
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
}
