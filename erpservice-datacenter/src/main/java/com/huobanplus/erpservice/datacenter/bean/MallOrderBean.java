package com.huobanplus.erpservice.datacenter.bean;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ����ʵ��
 * Created by allan on 2015/7/10.
 */
@Entity
@Table(name = "Mall_Orders")
public class MallOrderBean {
    /**
     * ����id
     */
    @Id
    @Column(name = "Order_Id")
    private String orderId;
    /**
     * ͬ����ǣ�1��ʾ��ץȡ
     */
    @Column(name = "Sync_Status")
    private int syncStatus;
    /**
     * ��Աid
     */
    @Column(name = "Member_Id")
    private int memberId;
    /**
     * ����״̬
     */
    @Column(name = "Status")
    private int orderStatus;
    /**
     * ֧��״̬
     */
    @Column(name = "Pay_Status")
    private int payStatus;
    /**
     * ����״̬
     */
    @Column(name = "Ship_Status")
    private int shipStatus;
    /**
     * �û�״̬
     */
    @Column(name = "User_Status")
    private int memberStatus;
    /**
     * �Ƿ���Ҫ����
     */
    @Column(name = "Is_Delivery")
    private int isDelivery;
    /**
     * ���ͷ�ʽid
     */
    @Column(name = "Shipping_Id")
    private int deliverMethodId;
    /**
     * ���ͷ�ʽ
     */
    @Column(name = "Shipping")
    private String deliverMethod;
    /**
     * ��������
     */
    @Column(name = "Shipping_Area")
    private String deliverArea;
    /**
     * ����
     */
    @Column(name = "Weight")
    private float weight;
    /**
     * ��������
     */
    @Column(name = "Order_Name")
    private String orderName;
    /**
     * ��Ʒ����
     */
    @Column(name = "Itemnum")
    private int itemNum;
    /**
     * �ʱ��
     */
    @Column(name = "Acttime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actTime;
    /**
     * ����ʱ��
     */
    @Column(name = "Createtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * ����ip
     */
    @Column(name = "Ip")
    private String createIP;
    /**
     * �ջ���
     */
    @Column(name = "Ship_Name")
    private String shipName;
    /**
     * �ջ�������
     */
    @Column(name = "Ship_Area")
    private String shipArea;
    /**
     * �ջ��˵�ַ
     */
    @Column(name = "Ship_Addr")
    private String shipAddr;
    /**
     * �ջ����ʱ�
     */
    @Column(name = "Ship_Zip")
    private String shipZip;
    /**
     * �ջ��˵绰
     */
    @Column(name = "Ship_Tel")
    private String shipTel;
    /**
     * �ջ�������
     */
    @Column(name = "Ship_Email")
    private String shipEmail;
    /**
     * �ջ��˽����ͻ�ʱ��
     */
    @Column(name = "Ship_Time")
    private String shipTime;
    /**
     * �ջ����ֻ�
     */
    @Column(name = "Ship_Mobile")
    private String shipMobile;
    /**
     * ��Ʒ�ܽ��
     */
    @Column(name = "Cost_Item")
    private BigDecimal costItem;
    /**
     * �Ƿ񿪷�Ʊ
     */
    @Column(name = "Is_Tax")
    private int isTax;
    /**
     * ˰��
     */
    @Column(name = "Cost_Tax")
    private BigDecimal costTax;
    /**
     * ��Ʊ��˾̧ͷ
     */
    @Column(name = "Tax_Company")
    private String taxCompany;
    /**
     * ���ͷ���
     */
    @Column(name = "Cost_Freight")
    private BigDecimal costFreight;
    /**
     * �Ƿ񱣼�
     */
    @Column(name = "Is_Protect")
    private int isProtect;
    /**
     * ���۷���
     */
    @Column(name = "Cost_Protect")
    private BigDecimal costProtect;
    /**
     * ֧��������
     */
    @Column(name = "Cost_Payment")
    private BigDecimal costPayment;
    /**
     * ���û���
     */
    @Column(name = "Score_U")
    private BigDecimal scoreU;
    /**
     * �ۿ�ʡ�µĽ��
     */
    @Column(name = "Discount")
    private BigDecimal discount;
    /**
     * �Ƿ�ʹ���Ż�
     */
    @Column(name = "Use_Pmt")
    private String usePmt;
    /**
     * �����ܽ��
     */
    @Column(name = "Total_Amount")
    private BigDecimal totalAmount;
    /**
     * ���ս��
     */
    @Column(name = "Final_Amount")
    private BigDecimal finalAmount;
    /**
     * �Żݽ��
     */
    @Column(name = "Pmt_Amount")
    private BigDecimal pmtAmount;
    /**
     * �����֧���Ľ��
     */
    @Column(name = "Payed")
    private BigDecimal payed;
    /**
     * ������ע
     */
    @Column(name = "Memo")
    private String memo;
    /**
     * ���䶯ʱ��
     */
    @Column(name = "Last_Change_Time")
    private Date lastChangeTime;
    /**
     * �̻�Id
     */
    @Column(name = "Customer_Id")
    private int customerId;
    /**
     * �Ƿ��������
     */
    @Column(name = "Cash_OnDly")
    private int cashOnDly;
    /**
     * ����֧����ʽ
     */
    @Column(name = "Online_PayType")
    private int onlinePayType;
    /**
     * ���ֵ��ý��
     */
    @Column(name = "Score_U_Amount")
    private BigDecimal scoreUAmount;
    /**
     * ������ID
     */
    @Column(name = "Op_MemberId")
    private int payAgentId;
    /**
     * ��������
     */
    @Column(name = "Op_Score_U")
    private BigDecimal payAgentScore;
    /**
     * �������ֽ��
     */
    @Column(name = "Op_Score_U_Amount")
    private BigDecimal payAgentScoreAmount;
    /**
     * ������
     */
    @Column(name = "Op_Payed")
    private BigDecimal payAgentPayed;
    /**
     * �����Ѹ����
     */
    @Column(name = "Has_Payed")
    private BigDecimal hasPayed;
    /**
     * �����Ѹ�����
     */
    @Column(name = "Has_PayedScore")
    private BigDecimal hasPayedScore;
    /**
     * ����֧�����
     */
    @Column(name = "Online_Amount")
    private BigDecimal onlineAmount;
    /**
     * ������ý��
     */
    @Column(name = "Hongbao_Amount")
    private BigDecimal hongbaoAmount;
    /**
     * ֧��ʱ��
     */
    @Column(name = "Paytime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payTime;
    /**
     * ������Ʒ�ջ��ֻ�
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
