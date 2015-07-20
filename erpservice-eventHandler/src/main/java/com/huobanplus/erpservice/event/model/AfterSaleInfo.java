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

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAftersaleId() {
        return aftersaleId;
    }

    public void setAftersaleId(String aftersaleId) {
        this.aftersaleId = aftersaleId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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

    public String getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(String saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    public String getAfterSaleId() {
        return afterSaleId;
    }

    public void setAfterSaleId(String afterSaleId) {
        this.afterSaleId = afterSaleId;
    }

    public String getSendStorage() {
        return sendStorage;
    }

    public void setSendStorage(String sendStorage) {
        this.sendStorage = sendStorage;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReturnCreateTime() {
        return returnCreateTime;
    }

    public void setReturnCreateTime(String returnCreateTime) {
        this.returnCreateTime = returnCreateTime;
    }

    public String getReturnConfirmTime() {
        return returnConfirmTime;
    }

    public void setReturnConfirmTime(String returnConfirmTime) {
        this.returnConfirmTime = returnConfirmTime;
    }

    public String getRefundCreateTime() {
        return refundCreateTime;
    }

    public void setRefundCreateTime(String refundCreateTime) {
        this.refundCreateTime = refundCreateTime;
    }

    public String getRefundConfirmTime() {
        return refundConfirmTime;
    }

    public void setRefundConfirmTime(String refundConfirmTime) {
        this.refundConfirmTime = refundConfirmTime;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getReturnLofisticsCompany() {
        return returnLofisticsCompany;
    }

    public void setReturnLofisticsCompany(String returnLofisticsCompany) {
        this.returnLofisticsCompany = returnLofisticsCompany;
    }

    public String getReturnLogisticsNumber() {
        return returnLogisticsNumber;
    }

    public void setReturnLogisticsNumber(String returnLogisticsNumber) {
        this.returnLogisticsNumber = returnLogisticsNumber;
    }

    public String getIsReturnInvoice() {
        return isReturnInvoice;
    }

    public void setIsReturnInvoice(String isReturnInvoice) {
        this.isReturnInvoice = isReturnInvoice;
    }

    public String getReturnInvoiceNum() {
        return returnInvoiceNum;
    }

    public void setReturnInvoiceNum(String returnInvoiceNum) {
        this.returnInvoiceNum = returnInvoiceNum;
    }

    public String getTotalGoodsRefundFee() {
        return totalGoodsRefundFee;
    }

    public void setTotalGoodsRefundFee(String totalGoodsRefundFee) {
        this.totalGoodsRefundFee = totalGoodsRefundFee;
    }

    public String getOtherRefundFee() {
        return otherRefundFee;
    }

    public void setOtherRefundFee(String otherRefundFee) {
        this.otherRefundFee = otherRefundFee;
    }

    public String getRefundTo() {
        return refundTo;
    }

    public void setRefundTo(String refundTo) {
        this.refundTo = refundTo;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getLastAlterTime() {
        return lastAlterTime;
    }

    public void setLastAlterTime(String lastAlterTime) {
        this.lastAlterTime = lastAlterTime;
    }

    public String getOutTid() {
        return outTid;
    }

    public void setOutTid(String outTid) {
        this.outTid = outTid;
    }

    public String getWholeQuestionType() {
        return wholeQuestionType;
    }

    public void setWholeQuestionType(String wholeQuestionType) {
        this.wholeQuestionType = wholeQuestionType;
    }

    public String getServiceRemarks() {
        return serviceRemarks;
    }

    public void setServiceRemarks(String serviceRemarks) {
        this.serviceRemarks = serviceRemarks;
    }

    public String getAftersaleType() {
        return aftersaleType;
    }

    public void setAftersaleType(String aftersaleType) {
        this.aftersaleType = aftersaleType;
    }

    public AfterSaleInfo[] getAftersale_id() {
        return aftersale_id;
    }

    public void setAftersale_id(AfterSaleInfo[] aftersale_id) {
        this.aftersale_id = aftersale_id;
    }
}
