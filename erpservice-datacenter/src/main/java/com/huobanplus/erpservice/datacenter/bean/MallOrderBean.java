package com.huobanplus.erpservice.datacenter.bean;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体
 * Created by allan on 2015/7/10.
 */
@Entity
@Table(name = "Mall_Orders")
public class MallOrderBean {
    /**
     * 订单id
     */
    @Id
    @Column(name = "Order_Id")
    private String orderId;
    /**
     * 同步标记，1表示已抓取
     */
    @Column(name = "Sync_Status")
    private int syncStatus;
    /**
     * 会员id
     */
    @Column(name = "Member_Id")
    private int memberId;
    /**
     * 订单状态
     */
    @Column(name = "Status")
    private int orderStatus;
    /**
     * 支付状态
     */
    @Column(name = "Pay_Status")
    private int payStatus;
    /**
     * 发货状态
     */
    @Column(name = "Ship_Status")
    private int shipStatus;
    /**
     * 用户状态
     */
    @Column(name = "User_Status")
    private int memberStatus;
    /**
     * 是否需要发货
     */
    @Column(name = "Is_Delivery")
    private int isDelivery;
    /**
     * 配送方式id
     */
    @Column(name = "Shipping_Id")
    private int deliverMethodId;
    /**
     * 配送方式
     */
    @Column(name = "Shipping")
    private String deliverMethod;
    /**
     * 配送区域
     */
    @Column(name = "Shipping_Area")
    private String deliverArea;
    /**
     * 重量
     */
    @Column(name = "Weight")
    private float weight;
    /**
     * 订单名称
     */
    @Column(name = "Order_Name")
    private String orderName;
    /**
     * 商品数量
     */
    @Column(name = "Itemnum")
    private int itemNum;
    /**
     * 活动时间
     */
    @Column(name = "Acttime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actTime;
    /**
     * 创建时间
     */
    @Column(name = "Createtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 创建ip
     */
    @Column(name = "Ip")
    private String createIP;
    /**
     * 收货人
     */
    @Column(name = "Ship_Name")
    private String shipName;
    /**
     * 收货人区域
     */
    @Column(name = "Ship_Area")
    private String shipArea;
    /**
     * 收货人地址
     */
    @Column(name = "Ship_Addr")
    private String shipAddr;
    /**
     * 收货人邮编
     */
    @Column(name = "Ship_Zip")
    private String shipZip;
    /**
     * 收货人电话
     */
    @Column(name = "Ship_Tel")
    private String shipTel;
    /**
     * 收货人邮箱
     */
    @Column(name = "Ship_Email")
    private String shipEmail;
    /**
     * 收货人建议送货时间
     */
    @Column(name = "Ship_Time")
    private String shipTime;
    /**
     * 收货人手机
     */
    @Column(name = "Ship_Mobile")
    private String shipMobile;
    /**
     * 商品总金额
     */
    @Column(name = "Cost_Item")
    private BigDecimal costItem;
    /**
     * 是否开发票
     */
    @Column(name = "Is_Tax")
    private int isTax;
    /**
     * 税金
     */
    @Column(name = "Cost_Tax")
    private BigDecimal costTax;
    /**
     * 开票公司抬头
     */
    @Column(name = "Tax_Company")
    private String taxCompany;
    /**
     * 配送费用
     */
    @Column(name = "Cost_Freight")
    private BigDecimal costFreight;
    /**
     * 是否保价
     */
    @Column(name = "Is_Protect")
    private int isProtect;
    /**
     * 保价费用
     */
    @Column(name = "Cost_Protect")
    private BigDecimal costProtect;
    /**
     * 支付手续费
     */
    @Column(name = "Cost_Payment")
    private BigDecimal costPayment;
    /**
     * 抵用积分
     */
    @Column(name = "Score_U")
    private BigDecimal scoreU;
    /**
     * 折扣省下的金额
     */
    @Column(name = "Discount")
    private BigDecimal discount;
    /**
     * 是否使用优惠
     */
    @Column(name = "Use_Pmt")
    private String usePmt;
    /**
     * 订单总金额
     */
    @Column(name = "Total_Amount")
    private BigDecimal totalAmount;
    /**
     * 最终金额
     */
    @Column(name = "Final_Amount")
    private BigDecimal finalAmount;
    /**
     * 优惠金额
     */
    @Column(name = "Pmt_Amount")
    private BigDecimal pmtAmount;
    /**
     * 用余额支付的金额
     */
    @Column(name = "Payed")
    private BigDecimal payed;
    /**
     * 订单备注
     */
    @Column(name = "Memo")
    private String memo;
    /**
     * 最后变动时间
     */
    @Column(name = "Last_Change_Time")
    private Date lastChangeTime;
    /**
     * 商户Id
     */
    @Column(name = "Customer_Id")
    private int customerId;
    /**
     * 是否货到付款
     */
    @Column(name = "Cash_OnDly")
    private int cashOnDly;
    /**
     * 在线支付方式
     */
    @Column(name = "Online_PayType")
    private int onlinePayType;
    /**
     * 积分抵用金额
     */
    @Column(name = "Score_U_Amount")
    private BigDecimal scoreUAmount;
    /**
     * 代付人ID
     */
    @Column(name = "Op_MemberId")
    private int payAgentId;
    /**
     * 代付积分
     */
    @Column(name = "Op_Score_U")
    private BigDecimal payAgentScore;
    /**
     * 代付积分金额
     */
    @Column(name = "Op_Score_U_Amount")
    private BigDecimal payAgentScoreAmount;
    /**
     * 余额代付
     */
    @Column(name = "Op_Payed")
    private BigDecimal payAgentPayed;
    /**
     * 订单已付金额
     */
    @Column(name = "Has_Payed")
    private BigDecimal hasPayed;
    /**
     * 订单已付积分
     */
    @Column(name = "Has_PayedScore")
    private BigDecimal hasPayedScore;
    /**
     * 在线支付金额
     */
    @Column(name = "Online_Amount")
    private BigDecimal onlineAmount;
    /**
     * 红包抵用金额
     */
    @Column(name = "Hongbao_Amount")
    private BigDecimal hongbaoAmount;
    /**
     * 支付时间
     */
    @Column(name = "Paytime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payTime;
    /**
     * 虚拟商品收货手机
     */
    @Column(name = "Virtual_RecMobile")
    private String virtualRecMobile;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(int shipStatus) {
        this.shipStatus = shipStatus;
    }

    public int getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(int memberStatus) {
        this.memberStatus = memberStatus;
    }

    public int getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(int isDelivery) {
        this.isDelivery = isDelivery;
    }

    public int getDeliverMethodId() {
        return deliverMethodId;
    }

    public void setDeliverMethodId(int deliverMethodId) {
        this.deliverMethodId = deliverMethodId;
    }

    public String getDeliverMethod() {
        return deliverMethod;
    }

    public void setDeliverMethod(String deliverMethod) {
        this.deliverMethod = deliverMethod;
    }

    public String getDeliverArea() {
        return deliverArea;
    }

    public void setDeliverArea(String deliverArea) {
        this.deliverArea = deliverArea;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public Date getActTime() {
        return actTime;
    }

    public void setActTime(Date actTime) {
        this.actTime = actTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateIP() {
        return createIP;
    }

    public void setCreateIP(String createIP) {
        this.createIP = createIP;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipArea() {
        return shipArea;
    }

    public void setShipArea(String shipArea) {
        this.shipArea = shipArea;
    }

    public String getShipAddr() {
        return shipAddr;
    }

    public void setShipAddr(String shipAddr) {
        this.shipAddr = shipAddr;
    }

    public String getShipZip() {
        return shipZip;
    }

    public void setShipZip(String shipZip) {
        this.shipZip = shipZip;
    }

    public String getShipTel() {
        return shipTel;
    }

    public void setShipTel(String shipTel) {
        this.shipTel = shipTel;
    }

    public String getShipEmail() {
        return shipEmail;
    }

    public void setShipEmail(String shipEmail) {
        this.shipEmail = shipEmail;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public String getShipMobile() {
        return shipMobile;
    }

    public void setShipMobile(String shipMobile) {
        this.shipMobile = shipMobile;
    }

    public BigDecimal getCostItem() {
        return costItem;
    }

    public void setCostItem(BigDecimal costItem) {
        this.costItem = costItem;
    }

    public int getIsTax() {
        return isTax;
    }

    public void setIsTax(int isTax) {
        this.isTax = isTax;
    }

    public BigDecimal getCostTax() {
        return costTax;
    }

    public void setCostTax(BigDecimal costTax) {
        this.costTax = costTax;
    }

    public String getTaxCompany() {
        return taxCompany;
    }

    public void setTaxCompany(String taxCompany) {
        this.taxCompany = taxCompany;
    }

    public BigDecimal getCostFreight() {
        return costFreight;
    }

    public void setCostFreight(BigDecimal costFreight) {
        this.costFreight = costFreight;
    }

    public int getIsProtect() {
        return isProtect;
    }

    public void setIsProtect(int isProtect) {
        this.isProtect = isProtect;
    }

    public BigDecimal getCostProtect() {
        return costProtect;
    }

    public void setCostProtect(BigDecimal costProtect) {
        this.costProtect = costProtect;
    }

    public BigDecimal getCostPayment() {
        return costPayment;
    }

    public void setCostPayment(BigDecimal costPayment) {
        this.costPayment = costPayment;
    }

    public BigDecimal getScoreU() {
        return scoreU;
    }

    public void setScoreU(BigDecimal scoreU) {
        this.scoreU = scoreU;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getUsePmt() {
        return usePmt;
    }

    public void setUsePmt(String usePmt) {
        this.usePmt = usePmt;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public BigDecimal getPmtAmount() {
        return pmtAmount;
    }

    public void setPmtAmount(BigDecimal pmtAmount) {
        this.pmtAmount = pmtAmount;
    }

    public BigDecimal getPayed() {
        return payed;
    }

    public void setPayed(BigDecimal payed) {
        this.payed = payed;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCashOnDly() {
        return cashOnDly;
    }

    public void setCashOnDly(int cashOnDly) {
        this.cashOnDly = cashOnDly;
    }

    public int getOnlinePayType() {
        return onlinePayType;
    }

    public void setOnlinePayType(int onlinePayType) {
        this.onlinePayType = onlinePayType;
    }

    public BigDecimal getScoreUAmount() {
        return scoreUAmount;
    }

    public void setScoreUAmount(BigDecimal scoreUAmount) {
        this.scoreUAmount = scoreUAmount;
    }

    public int getPayAgentId() {
        return payAgentId;
    }

    public void setPayAgentId(int payAgentId) {
        this.payAgentId = payAgentId;
    }

    public BigDecimal getPayAgentScore() {
        return payAgentScore;
    }

    public void setPayAgentScore(BigDecimal payAgentScore) {
        this.payAgentScore = payAgentScore;
    }

    public BigDecimal getPayAgentScoreAmount() {
        return payAgentScoreAmount;
    }

    public void setPayAgentScoreAmount(BigDecimal payAgentScoreAmount) {
        this.payAgentScoreAmount = payAgentScoreAmount;
    }

    public BigDecimal getPayAgentPayed() {
        return payAgentPayed;
    }

    public void setPayAgentPayed(BigDecimal payAgentPayed) {
        this.payAgentPayed = payAgentPayed;
    }

    public BigDecimal getHasPayed() {
        return hasPayed;
    }

    public void setHasPayed(BigDecimal hasPayed) {
        this.hasPayed = hasPayed;
    }

    public BigDecimal getHasPayedScore() {
        return hasPayedScore;
    }

    public void setHasPayedScore(BigDecimal hasPayedScore) {
        this.hasPayedScore = hasPayedScore;
    }

    public BigDecimal getOnlineAmount() {
        return onlineAmount;
    }

    public void setOnlineAmount(BigDecimal onlineAmount) {
        this.onlineAmount = onlineAmount;
    }

    public BigDecimal getHongbaoAmount() {
        return hongbaoAmount;
    }

    public void setHongbaoAmount(BigDecimal hongbaoAmount) {
        this.hongbaoAmount = hongbaoAmount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getVirtualRecMobile() {
        return virtualRecMobile;
    }

    public void setVirtualRecMobile(String virtualRecMobile) {
        this.virtualRecMobile = virtualRecMobile;
    }
}
