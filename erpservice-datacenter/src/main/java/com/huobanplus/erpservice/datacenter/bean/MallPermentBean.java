package com.huobanplus.erpservice.datacenter.bean;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by allan on 2015/7/10.
 */
@Entity
@Table(name = "Mall_Payments")
public class MallPermentBean {
    /**
     * 付款单号
     */
    @Id
    @Column(name = "Payment_Id")
    private String paymentId;
    /**
     * 订单号
     */
    @ManyToOne
    @JoinColumn(name = "Order_Id")
    private MallOrderBean order;
    /**
     * 会员Id
     */
    @Column(name = "Member_Id")
    private int memberId;
    /**
     * 收款账号
     */
    @Column(name = "Account")
    private String account;
    /**
     * 收款银行
     */
    @Column(name = "Bank")
    private String bank;
    /**
     * 支付账户
     */
    @Column(name = "Pay_Account")
    private String payAccount;
    /**
     * 货币
     */
    @Column(name = "Currency")
    private String currency;
    /**
     * 支付金额
     */
    @Column(name = "Money")
    private BigDecimal money;
    /**
     * 支付网关费用
     */
    @Column(name = "Paycost")
    private BigDecimal payCost;
    /**
     * 当前货币类型对应的金钱数
     */
    @Column(name = "Cur_Money")
    private BigDecimal curMoney;
    /**
     * 付款类型
     */
    @Column(name = "Pay_Type")
    private int payType;
    /**
     * 支付方式名称
     */
    @Column(name = "Paymethod")
    private String payMethod;
    /**
     * 付款IP
     */
    @Column(name = "Ip")
    private String ip;
    /**
     * 支付开始时间
     */
    @Column(name = "T_Begin")
    private Date beginTime;
    /**
     * 支付结束时间
     */
    @Column(name = "T_End")
    private Date endTime;
    /**
     * 状态
     */
    @Column(name = "Status")
    private String status;
    /**
     * 备注
     */
    @Column(name = "Memo")
    private String memo;
    /**
     * 外部交易号
     */
    @Column(name = "Trade_No")
    private String tradeNo;
    /**
     * 商户ID
     */
    @Column(name = "Customer_Id")
    private int customerId;
    /**
     * 微信OpenId
     */
    @Column(name = "Wx_OpenID")
    private String wxOpenId;
    /**
     * 微信是否关注
     */
    @Column(name = "Wx_IsSubscribe")
    private int wxIsSubscribe;
    /**
     * 在线支付方式
     */
    @Column(name = "Online_PayType")
    private int onlinePayType;
    /**
     * 代付人Id
     */
    @Column(name = "Op_MemberId")
    private int payAgentId;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public MallOrderBean getOrder() {
        return order;
    }

    public void setOrder(MallOrderBean order) {
        this.order = order;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getPayCost() {
        return payCost;
    }

    public void setPayCost(BigDecimal payCost) {
        this.payCost = payCost;
    }

    public BigDecimal getCurMoney() {
        return curMoney;
    }

    public void setCurMoney(BigDecimal curMoney) {
        this.curMoney = curMoney;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public int getWxIsSubscribe() {
        return wxIsSubscribe;
    }

    public void setWxIsSubscribe(int wxIsSubscribe) {
        this.wxIsSubscribe = wxIsSubscribe;
    }

    public int getOnlinePayType() {
        return onlinePayType;
    }

    public void setOnlinePayType(int onlinePayType) {
        this.onlinePayType = onlinePayType;
    }

    public int getPayAgentId() {
        return payAgentId;
    }

    public void setPayAgentId(int payAgentId) {
        this.payAgentId = payAgentId;
    }
}
