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
     * 订单id
     */
    @Id
    @Column(name = "Order_Code")
    private String orderCode;
    /**
     * 单据号
     */
    @Column(name = "Num_Id")
    private String numId;
    /**
     * 单据类型,例如:Order订单/salesReturn退货单/stock_in_detail入库单/stock_out_detail出库单(请填入英文)
     */
    @Column(name = "Tid_Type")
    private String tidType;
    /**
     * 导入标记
     */
    @Column(name = "Import_Mark")
    private String importMark;

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }

    /**
     * 同步标记，1表示已抓取
     */
    @Column(name = "Sync_Status")
    private int syncStatus;
    /**
     * 订单编号(E店宝中订单编号)
     */
    @Column(name = "Tid")
    private String tid;
    /**
     * 订单创建时间
     */
    @Column(name = "Order_Time")
    private Date orderTime;
    /**
     * 订单付款时间
     */
    @Column(name = "Pay_Time")
    private Date payTime;
    /**
     * 系统写入时间
     */
    @Column(name = "In_Sys_Time")
    private Date inSysTime;
    /**
     * 商家编码
     */
    @Column(name = "Chlid_Bar_Code")
    private String chlidBarCode;
    /**
     * 订单数量
     */
    @Column(name = "Num")
    private String num;
    /**
     * 快递单号
     */
    @Column(name = "Express_No")
    private String expressNo;
    /**
     * 快递公司名：需要在EDB中存在
     */
    @Column(name = "Express")
    private String express;
    /**
     * 是否需要发货
     */
    @Column(name = "Is_Delivery")
    private int isDelivery;

    public int getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(int isDelivery) {
        this.isDelivery = isDelivery;
    }

    /**
     * 订单净重
     */
    @Column(name = "Weight")
    private String weight;
    /**
     * 收货地址
     */
    @Column(name = "Address")
    private String address;
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
     * 原始订单编号/外部订单编号
     */
    @Column(name = "Out_Tid")
    private String outTid;
    /**
     * 商品、订单扫码
     */
    @Column(name = "Bar_Code")
    private String barCode;
    /**
     * 网店品名
     */
    @Column(name = "Product_Title")
    private String productTitle;
    /**
     * 网店规格
     */
    @Column(name = "Standard")
    private String standard;
    /**
     * 产品类型（值为赠品）
     */
    @Column(name = "Pro_Type")
    private String proType;
    /**
     * 订货数量
     */
    @Column(name = "Order_Goods_Num")
    private int orderGoodsNum;
    /**
     * 成交单价
     */
    @Column(name = "Cost_Price")
    private double costPrice;
    /**
     * 快递代码
     */
    @Column(name = "Express_Code")
    private String expressCode;
    /**
     * 打印员
     */
    @Column(name = "Printer")
    private String printer;
    /**
     * 客户状态
     */
    @Column(name = "Customer_Status")
    private String customerStatus;

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    /**
     * 配货员
     */
    @Column(name = "Cargo_Operator")
    private String cargoOperator;

    /**
     * 配货时间
     */
    @Column(name = "Cargo_Time")
    private Date cargoTime;
    /**
     * 打印时间
     */
    @Column(name = "Print_Time")
    private Date printTime;
    /**
     * 验货员
     */
    @Column(name = "Inspecter")
    private String inspecter;
    /**
     * 是否验货后回传快递信息,验货后回传验货信息必须传,打印后回传快递信息传
     */
    @Column(name = "Is_Inspect_Delivery")
    private String isInspectDelivery;
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
     * 验货时间
     */
    @Column(name = "Inspect_Time")
    private Date inspectTime;

    public Date getInspectTime() {
        return inspectTime;
    }

    public void setInspectTime(Date inspectTime) {
        this.inspectTime = inspectTime;
    }

    /**
     * 毛重
     */
    @Column(name = "Gross_Weight")
    private float grossWeight;
    /**
     * 内部便签
     */
    @Column(name = "Internal_Note")
    private String internalNote;
    /**
     * 原寄地代码
     */
    @Column(name = "Origin_Code")
    private String originCode;
    /**
     * 目的地代码
     */
    @Column(name = "Dest_Code")
    private String destCode;

    /**
     * 代付人ID
     */
    @Column(name = "Pay_Agent_Id")
    private String payAgentId;

    public String getPayAgentId() {
        return payAgentId;
    }

    public void setPayAgentId(String payAgentId) {
        this.payAgentId = payAgentId;
    }

    /**
     * 验货数量
     */
    @Column(name = "In_spection_Num")
    private String inspectionNum;
    /**
     * 是否覆盖原来内容（ 0 叠加 1 覆盖）
     */
    @Column(name = "Is_Cover")
    private int isCover;
    /**
     * 日期类型支持下面几种,
     * 默认订货日期/订货日期/付款日期/发货日期/归档日期/预计归档日期
     * 到货日期/订单修改日期/验货日期/核销日期/生成应收时间/称重时间
     * 审单时间/取消时间/完成时间
     */
    @Column(name = "Date_Type")
    private String dateType;
    /**
     * 开始时间
     */
    @Column(name = "Begin_Time")
    private Date beginTime;
    /**
     * 结束时间
     */
    @Column(name = "End_Time")
    private Date endTime;
    /**
     * 时间排序类型：审单时间
     */
    @Column(name = "Order_Type")
    private String orderType;
    /**
     * 待退款部分退款/待退款全部退款/待退款所有/货到付款/交易关闭
     * 未付款/已付款/已退款部分退款/已退款全部退款/已退款所有
     */
    @Column(name = "Payment_Status")
    private String paymentStatus;
    /**
     *订单状态
     */
    @Column(name = "Order_Status")
    private String orderStatus;
    /**
     * 未确认/已确认/已财务审核/已作废/已归档
     */
    @Column(name = "Proce_Status")
    private String proceStatus;
    /**
     * 平台状态
     */
    @Column(name = "Platform_Status")
    private String platformStatus;
    /**
     * 库房id、仓库编号
     */
    @Column(name = "Storage_Id")
    private String storageId;
    /**
     * 店铺id
     */
    @Column(name = "Shop_Id")
    private String shopId;
    /**
     * 发票打印情况(0:未打印，1:已打印)
     */
    @Column(name = "Invoice_Isprint")
    private String invoiceIsprint;
    /**
     * 是否保价
     */
    @Column(name = "Is_Protect")
    private String isProtect;

    /**
     * 保价费用
     */
    @Column(name = "Cost_Protect")
    private double costProtect;

    public String getIsProtect() {
        return isProtect;
    }

    public void setIsProtect(String isProtect) {
        this.isProtect = isProtect;
    }

    public double getCostProtect() {
        return costProtect;
    }

    public void setCostProtect(double costProtect) {
        this.costProtect = costProtect;
    }


    /**
     * 是否开发票 (0:未开/1:已开
     */
    @Column(name = "Invoice_Isopen")
    private int invoiceIsopen;
    /**
     * 页码
     */
    @Column(name = "PageNo")
    private String pageNo;
    /**
     * 每页数量
     */
    @Column(name = "Page_Size")
    private String pageSize;
    /**
     * 是否产品套装:3单品与套装:显示单品信息+套装信息;1单品与套装明细:显示单品信息+套装明细信息;
     * 2单品与套装以及套装明细:显示单品信息+套装信息+套装明细信息(默认)
     */
    @Column(name = "Product_InfoType")
    private int productInfoType;
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
     * 订单名称
     */
    @Column(name = "Order_Name")
    private String orderName;

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

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
     * 最终金额
     */
    @Column(name = "Final_Amount")
    private double finalAmount;

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    /**
     * 税金
     */
    @Column(name = "Cost_Tax")
    private double costTax;

    public double getCostTax() {
        return costTax;
    }

    public void setCostTax(double costTax) {
        this.costTax = costTax;
    }

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
     * 是否启用优惠
     */
    @Column(name = "Use_Pmt")
    private String usePmt;

    public String getUsePmt() {
        return usePmt;
    }

    public void setUsePmt(String usePmt) {
        this.usePmt = usePmt;
    }

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
     * 代付积分
     */
    @Column(name = "Pay_Agent_Score")
    private double payAgentScore;

    /**
     * 代付积分金额
     */
    @Column(name = "Pay_Agent_Score_Amount")
    private double payAgentScoreAmount;

    /**
     * 订单已付金额
     */
    @Column(name = "Has_Payed")
    private double hasPayed;

    /**
     * 订单已付积分
     */
    @Column(name = "Has_Payed_Score")
    private double hasPayedScore;

    public double getHasPayedScore() {
        return hasPayedScore;
    }

    public void setHasPayedScore(double hasPayedScore) {
        this.hasPayedScore = hasPayedScore;
    }

    public double getHasPayed() {
        return hasPayed;
    }

    public void setHasPayed(double hasPayed) {
        this.hasPayed = hasPayed;
    }

    /**
     * 余额代付
     */
    @Column(name = "Pay_Agent_Payed")
    private double payAgentPayed;

    public double getPayAgentPayed() {
        return payAgentPayed;
    }

    public void setPayAgentPayed(double payAgentPayed) {
        this.payAgentPayed = payAgentPayed;
    }

    public double getPayAgentScoreAmount() {
        return payAgentScoreAmount;
    }

    public void setPayAgentScoreAmount(double payAgentScoreAmount) {
        this.payAgentScoreAmount = payAgentScoreAmount;
    }

    public double getPayAgentScore() {
        return payAgentScore;
    }

    public void setPayAgentScore(double payAgentScore) {
        this.payAgentScore = payAgentScore;
    }

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
     * 配送方式编号
     */
    @Column(name = "Sending_Type_Id")
    private String sendingTypeId;

    /**
     * 配送费用
     */
    @Column(name = "Cost_Freight")
    private double costFreight;

    public double getCostFreight() {
        return costFreight;
    }

    public void setCostFreight(double costFreight) {
        this.costFreight = costFreight;
    }

    /**
     * 配送区域
     */
    @Column(name = "Sending_Area")
    private String sendingArea;

    public String getSendingArea() {
        return sendingArea;
    }

    public void setSendingArea(String sendingArea) {
        this.sendingArea = sendingArea;
    }

    public String getSendingTypeId() {
        return sendingTypeId;
    }

    public void setSendingTypeId(String sendingTypeId) {
        this.sendingTypeId = sendingTypeId;
    }

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
     * 订货时间
     */
    @Column(name = "Tid_Time")
    private Date tidTime;
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
     * 创建IP
     */
    @Column(name = "Create_IP")
    private String createIP;

    public String getCreateIP() {
        return createIP;
    }

    public void setCreateIP(String createIP) {
        this.createIP = createIP;
    }

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
     * 用余额支付的金额
     */
    @Column(name = "Payed")
    private double payed;

    public double getPayed() {
        return payed;
    }

    public void setPayed(double payed) {
        this.payed = payed;
    }

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
     * 订单备注
     */
    @Column(name = "Memo")
    private String memo;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 是否中断
     */
    @Column(name = "Is_Break")
    private String isBreak;

    /**
     * 抵用积分
     */
    @Column(name = "Score_U")
    private float scoreU;

    public float getScoreU() {
        return scoreU;
    }

    public void setScoreU(float scoreU) {
        this.scoreU = scoreU;
    }

    public String getOnlinePayType() {
        return onlinePayType;
    }

    public void setOnlinePayType(String onlinePayType) {
        this.onlinePayType = onlinePayType;
    }

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
    /**
     * 总数
     */
    @Column(name = "Total_Num")
    private String totalNum;
    /**
     * 买家邮件地址
     */
    @Column(name = "Buyer_Email")
    private String buyerEmail;
    /**
     * 买家支付宝账号
     */
    @Column(name = "Buyer_Alipay")
    private String buyerAlipay;
    /**
     * 收货人姓名
     */
    @Column(name = "Consignee")
    private String consignee;
    /**
     * 收货人省份
     */
    @Column(name = "Privince")
    private String privince;
    /**
     * 收货人城市
     */
    @Column(name = "Citys")
    private String citys;
    /**
     * 收货人地区
     */
    @Column(name = "Area")
    private String area;
    /**
     * 开票金额
     */
    @Column(name = "Invoice_Money")
    private double invoiceMoney;
    /**
     * 支付佣金
     */
    @Column(name = "Pay_Commission")
    private double payCommission;
    /**
     * 支付积分
     */
    @Column(name = "Pay_Score")
    private int payScore;
    /**
     * 积分抵用金额
     */
    @Column(name = "Score_UAmount")
    private double scoreUAmount;

    public double getScoreUAmount() {
        return scoreUAmount;
    }

    public void setScoreUAmount(double scoreUAmount) {
        this.scoreUAmount = scoreUAmount;
    }

    /**
     * 返点积分
     */
    @Column(name = "Return_Score")
    private int returnScore;
    /**
     * 支付宝交易号
     */
    @Column(name = "Alipay_Transaction_No")
    private String alipayTransactionNo;
    /**
     * 外部平台快递方式
     */
    @Column(name = "Out_Express_Method")
    private String outExpressMethod;
    /**
     * 外部平台快递订单状态
     */
    @Column(name = "Out_Order_Status")
    private String outOrderStatus;
    /**
     * 订货日期（订货日期距当前时间不可超过一个月）
     */
    @Column(name = "Order_Date")
    private Date orderDate;
    /**
     * 付款日期
     */
    @Column(name = "Pay_Date")
    private Date payDate;
    /**
     * 完成日期
     */
    @Column(name = "Finish_Date")
    private Date finishDate;
    /**
     * 物流公司
     */
    @Column(name = "Wu_Liu")
    private String wuLiu;
    /**
     * 物流单号
     */
    @Column(name = "Wu_Liu_No")
    private String wuLiuNo;
    /**
     * 终端类型
     */
    @Column(name = "Terminal_Type")
    private String terminalType;

    /**
     * 虚拟商品收货手机
     */
    @Column(name = "Virtual_RecMobile")
    private String virtualRecMobile;

    public String getVirtualRecMobile() {
        return virtualRecMobile;
    }

    public void setVirtualRecMobile(String virtualRecMobile) {
        this.virtualRecMobile = virtualRecMobile;
    }

    /**
     * 外部单价
     */
    @Column(name = "Out_Price")
    private double outPrice;

    /**
     * 在线支付金额
     */
    @Column(name = "Online_Amount")
    private double onlineAmount;

    public double getOnlineAmount() {
        return onlineAmount;
    }

    public void setOnlineAmount(double onlineAmount) {
        this.onlineAmount = onlineAmount;
    }

    /**
     * 赠品数量
     */
    @Column(name = "Gift_Num")
    private int giftNum;
    /**
     * 产品缺货情况
     */
    @Column(name = "Product_Stockout")
    private String productStockout;
    /**
     * 是否预订
     */
    @Column(name = "Is_Book")
    private int isBook;
    /**
     * 是否赠品
     */
    @Column(name = "Is_Gift")
    private int isGift;
    /**
     * 加权平均单价
     */
    @Column(name = "Avg_Price")
    private double avgPrice;
    /**
     * 产品运费
     */
    @Column(name = "Product_Freight")
    private double productFreight;
    /**
     * 外部平台产品Id
     */
    @Column(name = "Out_Product_Id")
    private String outProductId;
    /**
     * 外部平台条形码
     */
    @Column(name = "Out_Bar_Code")
    private String outBarCode;
    /**
     * 产品简介
     */
    @Column(name = "Product_Intro")
    private String productIntro;
    /**
     * 国家
     */
    @Column(name = "Country")
    private String country;

    /**
     * 在线支付类型
     */
    @Column(name = "Online_Pay_Type")
    private String onlinePayType;

    /**
     * 客户备注
     */
    @Column(name = "Customer_Remark")
    private String customerRemark;

    /**
     * 红包抵用金额
     */
    @Column(name = "Hongbao_Amount")
    private double hongbaoAmount;

    public double getHongbaoAmount() {
        return hongbaoAmount;
    }

    public void setHongbaoAmount(double hongbaoAmount) {
        this.hongbaoAmount = hongbaoAmount;
    }

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

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getInSysTime() {
        return inSysTime;
    }

    public void setInSysTime(Date inSysTime) {
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

    public Date getCargoTime() {
        return cargoTime;
    }

    public void setCargoTime(Date cargoTime) {
        this.cargoTime = cargoTime;
    }

    public Date getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Date printTime) {
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

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

    public Date getTidTime() {
        return tidTime;
    }

    public void setTidTime(Date tidTime) {
        this.tidTime = tidTime;
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
