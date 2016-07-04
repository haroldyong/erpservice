/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * 订单详细返回结果（用来转换为xml返回）
 * Created by liual on 2015-08-26.
 */
@JacksonXmlRootElement(localName = "Order")
public class NSOrderDetailResult {
    private int Result;
    private String Cause;
    private String OrderNo;
    private String DateTime;
    private String BuyerId;
    private String BuyerName;
    private int CardType;
    private String IDCard;
    private String Country;
    private String Province;
    private String City;
    private String Town;
    private String Adr;
    private String Zip;
    private String Email;
    private String Phone;
    private double Total;
    private String Currency;
    private double Postage;
    private String PayAccount;
    private String PayID;
    private String logisticsName;
    private String chargetype;
    private String CustomerRemark;
    private String Remark;
    private String InvoiceTitle;
    private List<NSOrderItemResult> orderItemResults;

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    public String getCause() {
        return Cause;
    }

    public void setCause(String cause) {
        Cause = cause;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getBuyerId() {
        return BuyerId;
    }

    public void setBuyerId(String buyerId) {
        BuyerId = buyerId;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public int getCardType() {
        return CardType;
    }

    public void setCardType(int cardType) {
        CardType = cardType;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getTown() {
        return Town;
    }

    public void setTown(String town) {
        Town = town;
    }

    public String getAdr() {
        return Adr;
    }

    public void setAdr(String adr) {
        Adr = adr;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String zip) {
        Zip = zip;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public double getPostage() {
        return Postage;
    }

    public void setPostage(double postage) {
        Postage = postage;
    }

    public String getPayAccount() {
        return PayAccount;
    }

    public void setPayAccount(String payAccount) {
        PayAccount = payAccount;
    }

    public String getPayID() {
        return PayID;
    }

    public void setPayID(String payID) {
        PayID = payID;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getChargetype() {
        return chargetype;
    }

    public void setChargetype(String chargetype) {
        this.chargetype = chargetype;
    }

    public String getCustomerRemark() {
        return CustomerRemark;
    }

    public void setCustomerRemark(String customerRemark) {
        CustomerRemark = customerRemark;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getInvoiceTitle() {
        return InvoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        InvoiceTitle = invoiceTitle;
    }

    public List<NSOrderItemResult> getOrderItemResults() {
        return orderItemResults;
    }

    public void setOrderItemResults(List<NSOrderItemResult> orderItemResults) {
        this.orderItemResults = orderItemResults;
    }
}
