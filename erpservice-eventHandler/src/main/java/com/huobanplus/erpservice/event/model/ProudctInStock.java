package com.huobanplus.erpservice.event.model;

import java.io.Serializable;

/**
 * 产品库存信息
 */
public class ProudctInStock extends BaseBean {

    //产品分类
    private String productNo;
    //品牌编码
    private String brandNo;
    //品牌名称
    private String brandName;
    //规格
    private String standard;
    //分类编码
    private String sortNo;
    //分类名称
    private String sortName;
    //条形码
    private String barCode;
    //备用条码
    private String barCodeRemark;
    //产品明细编码
    private String proitemNo;
    //产品名称
    private String productName;
    //厂家货号
    private String verderNo;
    //上架日期
    private String groundDate;
    //建档日期
    private String bookBuildDate;
    //供应商
    private String supplier;
    //产品状态
    private String productState;
    //是否套装
    private String isSuit;
    //销售价
    private String salePrice;
    //成本价
    private String cost;
    //含税价格
    private String taxCost;
    //实物库存
    private String entityStock;
    //可销库存
    private String sellStock;
    //台账库存
    private String standbookStock;
    //消耗数量
    private String useNum;
    //总数量
    private String totalNum;
    //日期类型
    private String dateType;
    //开始时间
    private String beginTime;
    //结束时间
    private String endTime;
    //库房ID可传多库房，以逗号隔开。如：,2,3
    private String storeId;
    //是否累计负库存
    private String iscutStore;
    //显示第几页,默认显示第页
    private int pageNo;
    //每次显示条数
    private String pageSize;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getBrandNo() {
        return brandNo;
    }

    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getBarCodeRemark() {
        return barCodeRemark;
    }

    public void setBarCodeRemark(String barCodeRemark) {
        this.barCodeRemark = barCodeRemark;
    }

    public String getProitemNo() {
        return proitemNo;
    }

    public void setProitemNo(String proitemNo) {
        this.proitemNo = proitemNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVerderNo() {
        return verderNo;
    }

    public void setVerderNo(String verderNo) {
        this.verderNo = verderNo;
    }

    public String getGroundDate() {
        return groundDate;
    }

    public void setGroundDate(String groundDate) {
        this.groundDate = groundDate;
    }

    public String getBookBuildDate() {
        return bookBuildDate;
    }

    public void setBookBuildDate(String bookBuildDate) {
        this.bookBuildDate = bookBuildDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    public String getIsSuit() {
        return isSuit;
    }

    public void setIsSuit(String isSuit) {
        this.isSuit = isSuit;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTaxCost() {
        return taxCost;
    }

    public void setTaxCost(String taxCost) {
        this.taxCost = taxCost;
    }

    public String getEntityStock() {
        return entityStock;
    }

    public void setEntityStock(String entityStock) {
        this.entityStock = entityStock;
    }

    public String getSellStock() {
        return sellStock;
    }

    public void setSellStock(String sellStock) {
        this.sellStock = sellStock;
    }

    public String getStandbookStock() {
        return standbookStock;
    }

    public void setStandbookStock(String standbookStock) {
        this.standbookStock = standbookStock;
    }

    public String getUseNum() {
        return useNum;
    }

    public void setUseNum(String useNum) {
        this.useNum = useNum;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getIscutStore() {
        return iscutStore;
    }

    public void setIscutStore(String iscutStore) {
        this.iscutStore = iscutStore;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
