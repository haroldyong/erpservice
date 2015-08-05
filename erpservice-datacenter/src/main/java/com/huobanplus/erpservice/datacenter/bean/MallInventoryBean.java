package com.huobanplus.erpservice.datacenter.bean;


import javax.persistence.*;
import java.util.Date;

/**
 * <p>类描述：<p/>
 * 库存信息
 */
@Entity
@Table(name = "Mall_Inventorys")
public class MallInventoryBean {

    /**
     * 库存单号
     */
    @Id
    @Column(name = "Inventory_No")
    private String inventoryNo;
    /**
     * 运费
     */
    @Column(name = "Freight")
    private double freight;
    /**
     * 运费分摊方式1:按产品数量2：按产品重量默认为1
     */
    @Column(name = "Freight_Avgway")
    private String freightAvgway;
    /**
     * 条形码
     */
    @Column(name = "Bar_Code")
    private String barCode;
    /**
     * 库存库数量
     */
    @Column(name = "storage_Num")
    private int storageNum;
    /**
     * 入库类型；全部:1/其他入库:10/产成品入库:11/原料入库:12/盘盈入库:13/维修入库:14/差错入库:15/退货入库:3/归还入库:4/调拨入库:6/正常入库:8/采购入库:9
     */
    @Column(name = "Type")
    private int type;
    /**
     * 日期类型--入库日期in_time,制单日期create_time,审核日期examine_time
     */
    @Column(name = "Date_Type")
    private String dateType;
    /**
     * 开始时间;所查订单的开始时间,和日期类型配合使用.
     */
    @Column(name = "Begin_Time")
    private Date beginTime;
    /**
     * 结束时间;所查订单的结束时间,和日期类型配合使用.如果没输入,则默认为此时此刻.
     */
    @Column(name = "End_Time")
    private Date endTime;
    /**
     * 仓库名称，用于查询指定仓库的入库单
     */
    @Column(name = "Storage_Name")
    private String storageName;
    /**
     * 库存状态.0:未审核；1：已审核；2：已作废
     */
    @Column(name = "Storage_Status")
    private int storageStatus;
    /**
     * 当前页，如果不填，默认为1
     */
    @Column(name = "Page_No")
    private int pageNo;
    /**
     * 页大小，如果不填，则默认为200.每页最多返回记录数为200.
     */
    @Column(name = "Page_Size")
    private int pageSize;
    /**
     * 导入标记:不导入,未导入,已导入,已处理,已取消
     */
    @Column(name = "Import_Mark")
    private String importMark;
    /**
     * 入库类型编号
     */
    @Column(name = "Type_No")
    private String typeNo;
    /**
     * 供应商编码
     */
    @Column(name = "Provider")
    private String provider;
    /**
     * 仓库
     */
    @Column(name = "Storage")
    private String storage;
    /**
     * 制单员
     */
    @Column(name = "Creater")
    private String creater;
    /**
     * 制单时间
     */
    @Column(name = "Create_Time")
    private Date createTime;
    /**
     * 入库时间
     */
    @Column(name = "In_Time")
    private Date inTime;
    /**
     * 质检员
     */
    @Column(name = "Quality_Inspctor")
    private String qualityInspctor;
    /**
     * 质检时间
     */
    @Column(name = "Inspct_Time")
    private Date inspctTime;
    /**
     * 质检结果
     */
    @Column(name = "Inspct_Result")
    private String inspctResult;
    /**
     * 审核人
     */
    @Column(name = "Examiner")
    private String examiner;
    /**
     * 审核时间
     */
    @Column(name = "Examine_Time")
    private Date examineTime;
    /**
     * 发货单号
     */
    @Column(name = "Send_Tid")
    private String sendTid;
    /**
     * 入库原因
     */
    @Column(name = "In_Reason")
    private String inReason;
    /**
     * 成本价格
     */
    @Column(name = "Cost")
    private double cost;
    /**
     * 来源单号
     */
    @Column(name = "Source_Tid")
    private String sourceTid;
    /**
     * 采购费用
     */
    @Column(name = "Purchase_Fee")
    private double purchaseFee;
    /**
     * 合同总额
     */
    @Column(name = "Contract_Money")
    private double contractMoney;
    /**
     * 相关单号
     */
    @Column(name = "Relevant_Tid")
    private String relevantTid;
    /**
     * 汇率
     */
    @Column(name = "Rate")
    private double rate;
    /**
     * 币种
     */
    @Column(name = "Currency")
    private String currency;
    /**
     * 外部合同号
     */
    @Column(name = "Out_Contract_Tid")
    private String outContractTid;
    /**
     * 物流公司
     */
    @Column(name = "Logistics")
    private String logistics;
    /**
     * 快递单号
     */
    @Column(name = "Express_Tid")
    private String expressTid;
    /**
     * 运费承担方
     */
    @Column(name = "Freight_Payer")
    private String freightPayer;
    /**
     * 入库备注
     */
    @Column(name = "Remarks")
    private String remarks;
    /**
     * 运费均摊模式;按产品数量，按产品重量
     */
    @Column(name = "Freight_Mode")
    private String freightMode;
    /**
     * 总记录数
     */
    @Column(name = "Total_Count")
    private int totalCount;
    /**
     * 仓库编码
     */
    @Column(name = "Storage_No")
    private String storageNo;
    /**
     * 来源：不对外提供，固定默认值
     */
    @Column(name = "List_Source")
    private String listSource;
    /**
     * 其他费用
     */
    @Column(name = "Other_Cost")
    private double otherCost;
    /**
     * 外部合同单号
     */
    @Column(name = "Out_Pact_No")
    private String outPactNo;
    /**
     * 产品明细编号
     */
    @Column(name = "Product_Item_No")
    private String productItemNo;
    /**
     * 库位编号
     */
    @Column(name = "Location_No")
    private String locationNo;
    /**
     * 批次
     */
    @Column(name = "Batch")
    private String batch;
    /**
     * 到期时间
     */
    @Column(name = "Expire_Time")
    private Date expireTime;
    /**
     * 出库类型（可在档案管理-仓库档案-出库类型设置中查看）
     */
    @Column(name = "Out_storage_Type")
    private String outstorageType;
    /**
     * 出库时间
     */
    @Column(name = "Out_Storage_Time")
    private Date outstorageTime;
    /**
     * 返厂供应商编号：在采购管理—供应商档案里查看
     */
    @Column(name = "Supplie_No")
    private String supplieNo;
    /**
     * 原始入库单号
     */
    @Column(name = "YS_In_Storage_No")
    private String YSInstorageNo;
    /**
     * 出库备注
     */
    @Column(name = "Out_Storage_Remark")
    private String outStorageRemark;
    /**
     * 出库价
     */
    @Column(name = "Out_Storage_Price")
    private double outstoragePrice;
    /**
     * 运费均摊
     */
    @Column(name = "Freight_Avg")
    private double freightAvg;
    /**
     * 出库状态
     */
    @Column(name = "Out_Storage_Status")
    private String outstorageStatus;
    /**
     * 出库类型名称
     */
    @Column(name = "Out_Store_Type_Name")
    private String outStoreTypeName;

    /**
     * 库存规格
     */
    @Column(name = "Sku_Id")
    private String skuId;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public String getFreightAvgway() {
        return freightAvgway;
    }

    public void setFreightAvgway(String freightAvgway) {
        this.freightAvgway = freightAvgway;
    }

    public String getInventoryNo() {
        return inventoryNo;
    }

    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public int getStorageNum() {
        return storageNum;
    }

    public void setStorageNum(int storageNum) {
        this.storageNum = storageNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public int getStorageStatus() {
        return storageStatus;
    }

    public void setStorageStatus(int storageStatus) {
        this.storageStatus = storageStatus;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getImportMark() {
        return importMark;
    }

    public void setImportMark(String importMark) {
        this.importMark = importMark;
    }

    public String getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(String typeNo) {
        this.typeNo = typeNo;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getQualityInspctor() {
        return qualityInspctor;
    }

    public void setQualityInspctor(String qualityInspctor) {
        this.qualityInspctor = qualityInspctor;
    }

    public Date getInspctTime() {
        return inspctTime;
    }

    public void setInspctTime(Date inspctTime) {
        this.inspctTime = inspctTime;
    }

    public String getInspctResult() {
        return inspctResult;
    }

    public void setInspctResult(String inspctResult) {
        this.inspctResult = inspctResult;
    }

    public String getExaminer() {
        return examiner;
    }

    public void setExaminer(String examiner) {
        this.examiner = examiner;
    }

    public Date getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(Date examineTime) {
        this.examineTime = examineTime;
    }

    public String getSendTid() {
        return sendTid;
    }

    public void setSendTid(String sendTid) {
        this.sendTid = sendTid;
    }

    public String getInReason() {
        return inReason;
    }

    public void setInReason(String inReason) {
        this.inReason = inReason;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getSourceTid() {
        return sourceTid;
    }

    public void setSourceTid(String sourceTid) {
        this.sourceTid = sourceTid;
    }

    public double getPurchaseFee() {
        return purchaseFee;
    }

    public void setPurchaseFee(double purchaseFee) {
        this.purchaseFee = purchaseFee;
    }

    public double getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(double contractMoney) {
        this.contractMoney = contractMoney;
    }

    public String getRelevantTid() {
        return relevantTid;
    }

    public void setRelevantTid(String relevantTid) {
        this.relevantTid = relevantTid;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOutContractTid() {
        return outContractTid;
    }

    public void setOutContractTid(String outContractTid) {
        this.outContractTid = outContractTid;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public String getExpressTid() {
        return expressTid;
    }

    public void setExpressTid(String expressTid) {
        this.expressTid = expressTid;
    }

    public String getFreightPayer() {
        return freightPayer;
    }

    public void setFreightPayer(String freightPayer) {
        this.freightPayer = freightPayer;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFreightMode() {
        return freightMode;
    }

    public void setFreightMode(String freightMode) {
        this.freightMode = freightMode;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getStorageNo() {
        return storageNo;
    }

    public void setStorageNo(String storageNo) {
        this.storageNo = storageNo;
    }

    public String getListSource() {
        return listSource;
    }

    public void setListSource(String listSource) {
        this.listSource = listSource;
    }

    public double getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(double otherCost) {
        this.otherCost = otherCost;
    }

    public String getOutPactNo() {
        return outPactNo;
    }

    public void setOutPactNo(String outPactNo) {
        this.outPactNo = outPactNo;
    }

    public String getProductItemNo() {
        return productItemNo;
    }

    public void setProductItemNo(String productItemNo) {
        this.productItemNo = productItemNo;
    }

    public String getLocationNo() {
        return locationNo;
    }

    public void setLocationNo(String locationNo) {
        this.locationNo = locationNo;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getOutstorageType() {
        return outstorageType;
    }

    public void setOutstorageType(String outstorageType) {
        this.outstorageType = outstorageType;
    }

    public Date getOutstorageTime() {
        return outstorageTime;
    }

    public void setOutstorageTime(Date outstorageTime) {
        this.outstorageTime = outstorageTime;
    }

    public String getSupplieNo() {
        return supplieNo;
    }

    public void setSupplieNo(String supplieNo) {
        this.supplieNo = supplieNo;
    }

    public String getYSInstorageNo() {
        return YSInstorageNo;
    }

    public void setYSInstorageNo(String YSInstorageNo) {
        this.YSInstorageNo = YSInstorageNo;
    }

    public String getOutStorageRemark() {
        return outStorageRemark;
    }

    public void setOutStorageRemark(String outStorageRemark) {
        this.outStorageRemark = outStorageRemark;
    }

    public double getOutstoragePrice() {
        return outstoragePrice;
    }

    public void setOutstoragePrice(double outstoragePrice) {
        this.outstoragePrice = outstoragePrice;
    }

    public double getFreightAvg() {
        return freightAvg;
    }

    public void setFreightAvg(double freightAvg) {
        this.freightAvg = freightAvg;
    }

    public String getOutstorageStatus() {
        return outstorageStatus;
    }

    public void setOutstorageStatus(String outstorageStatus) {
        this.outstorageStatus = outstorageStatus;
    }

    public String getOutStoreTypeName() {
        return outStoreTypeName;
    }

    public void setOutStoreTypeName(String outStoreTypeName) {
        this.outStoreTypeName = outStoreTypeName;
    }
}
