package com.huobanplus.erpprovider.edb.bean;

/**
 * Created by allan on 2015/8/13.
 */
public class EDBOrderSearchForm {
    /**
     * 日期类型支持下面几种,默认订货日期/订货日期/付款日期/发货日期/归档日期/预计归档日期/到货日期/订单修改日期/验货日期/核销日期/生成应收时间/称重时间/审单时间/取消时间/完成时间
     */
    private String dataType;
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 时间排序类型：审单时间
     */
    private String orderType;
    /**
     * 待退款部分退款/待退款全部退款/待退款所有/货到付款/交易关闭/未付款/已付款/已退款部分退款/已退款全部退款/已退款所有
     */
    private String paymentStatus;
    /**
     * 待退货部分退货/待退货全部退货/待退货所有/退货到货部分退货/退货到货全部退货/退货到货所有/未发货/已发货
     */
    private String orderStatus;
    /**
     * 未确认/已确认/已财务审核/已作废/已归档
     */
    private String proceStatus;
    /**
     * 待退款部分退款/待退款全部退款/等待买家付款/货到付款/交易成功/交易关闭/买家已付款/缺货订单未付款/已发货/已付款/已签收/交易成功/已取消/预退款
     */
    private String platformStatus;
    /**
     * 库房id
     */
    private String storageId;
    /**
     * 店铺id
     */
    private String shopId;
    /**
     * 快递单号
     */
    private String expressNo;
    /**
     * 赠品添加
     */
    private String tradegifadd;
    /**
     * 快递公司
     */
    private String express;
    /**
     * 外部订单编号
     */
    private String orderId;
    /**
     * 发票打印情况(0:未打印，1:已打印)
     */
    private String invoiceIsPrint;
    /**
     * 是否开发票(0:未开/1:已开
     */
    private String invoiceIsOpen;
    /**
     * 页码
     */
    private String pageNo;
    /**
     * 每页数量
     */
    private String pageSize;
    /**
     * 导入标记:不导入,未导入,已处理,已导入,已取消
     */
    private String importMark;
    /**
     * 是否产品套装:3单品与套装:显示单品信息+套装信息;1单品与套装明细:显示单品信息+套装明细信息;2单品与套装以及套装明细:显示单品信息+套装信息+套装明细信息(默认)
     */
    private String productInfoType;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
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

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getTradegifadd() {
        return tradegifadd;
    }

    public void setTradegifadd(String tradegifadd) {
        this.tradegifadd = tradegifadd;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getInvoiceIsPrint() {
        return invoiceIsPrint;
    }

    public void setInvoiceIsPrint(String invoiceIsPrint) {
        this.invoiceIsPrint = invoiceIsPrint;
    }

    public String getInvoiceIsOpen() {
        return invoiceIsOpen;
    }

    public void setInvoiceIsOpen(String invoiceIsOpen) {
        this.invoiceIsOpen = invoiceIsOpen;
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

    public String getImportMark() {
        return importMark;
    }

    public void setImportMark(String importMark) {
        this.importMark = importMark;
    }

    public String getProductInfoType() {
        return productInfoType;
    }

    public void setProductInfoType(String productInfoType) {
        this.productInfoType = productInfoType;
    }
}
