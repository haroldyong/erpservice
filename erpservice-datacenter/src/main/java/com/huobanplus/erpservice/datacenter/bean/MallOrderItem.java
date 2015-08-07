package com.huobanplus.erpservice.datacenter.bean;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品实体
 */
@Entity
@Table(name = "Mall_OrderItem")
public class MallOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * 仓库编号
     */
    private String storageId;
    /**
     * 订单编号
     */
    private String tid;
    /**
     * 产品明细编号
     */
    private String proDetailCode;
    /**
     * 网店产品名称
     */
    private String proName;
    /**
     * 规格
     */
    private String specification;
    /**
     * 条形码
     */
    private String barcode;
    /**
     * 套装条形码
     */
    private String combineBarCode;
    /**
     * 是否取消
     */
    private String isCancel;
    /**
     * 是否预定
     */
    private String isScheduled;
    /**
     * 产品缺货情况
     */
    private String stockSituation;
    /**
     * 是否预售产品
     */
    private String isBookPro;
    /**
     * 是否组合
     */
    private String isCombination;
    /**
     * 是否赠品
     */
    private String isGifts;
    /**
     * 赠品数量
     */
    private int giftNum;
    /**
     * 预分配库存
     */
    private int bookStorage;
    /**
     * 订货数量
     */
    private int proNum;
    /**
     * 发货数量
     */
    private int sendNum;
    /**
     * 退货数量
     */
    private int refundNum;
    /**
     * 退货到货数量
     */
    private int refundReNum;
    /**
     * 验货数量
     */
    private int inspectionNum;
    /**
     * 当期库存
     */
    private int timeInventory;
    /**
     * 成本价
     */
    private double costPrice;
    /**
     * 销售单价
     */
    private double sellPrice;
    /**
     * 加权平均单价
     */
    private double averagePrice;
    /**
     * 原始价格
     */
    private double originalPrice;
    /**
     * 软件销售单价
     */
    private double sysPrice;
    /**
     * 运费
     */
    private String freight;
    /**
     * 单品优惠金额
     */
    private double itemDiscountFee;
    /**
     * 验货日期
     */
    private Date inspectionTime;
    /**
     * 重量
     */
    private String weight;
    /**
     * 店铺编号
     */
    private String shopId;
    /**
     * 外部平台单号
     */
    private String outTid;
    /**
     * 外部平台产品id
     */
    private String outProId;
    /**
     * 外部平台产品sku_id
     */
    private String outProSkuId;
    /**
     * 产品简介
     */
    private String proExplain;
    /**
     * 买家留言
     */
    private String buyerMemo;
    /**
     * 卖家备注
     */
    private String sellerRemark;
    /**
     * 配货员
     */
    private String distributer;
    /**
     * 配货时间
     */
    private Date distributTime;
    /**
     * 备用条码
     */
    private String secondBarcode;
    /**
     * 产品编号
     */
    private String productNo;
    /**
     * 品牌编号
     */
    private String brandNumber;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 台账库存
     */
    private int bookInventory;
    /**
     * 产品规格
     */
    private String productSpecification;
    /**
     * 打折金额
     */
    private String discountAmount;
    /**
     * 抵扣分摊金额
     */
    private String creditAmount;
    /**
     * MD5加密值
     */
    private String MD5Encryption;
    /**
     * 关联订单
     */
    @ManyToOne
    @JoinColumn(name = "Order_Code")
    private MallOrderBean orderBean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getProDetailCode() {
        return proDetailCode;
    }

    public void setProDetailCode(String proDetailCode) {
        this.proDetailCode = proDetailCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCombineBarCode() {
        return combineBarCode;
    }

    public void setCombineBarCode(String combineBarCode) {
        this.combineBarCode = combineBarCode;
    }

    public String getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(String isCancel) {
        this.isCancel = isCancel;
    }

    public String getIsScheduled() {
        return isScheduled;
    }

    public void setIsScheduled(String isScheduled) {
        this.isScheduled = isScheduled;
    }

    public String getStockSituation() {
        return stockSituation;
    }

    public void setStockSituation(String stockSituation) {
        this.stockSituation = stockSituation;
    }

    public String getIsBookPro() {
        return isBookPro;
    }

    public void setIsBookPro(String isBookPro) {
        this.isBookPro = isBookPro;
    }

    public String getIsCombination() {
        return isCombination;
    }

    public void setIsCombination(String isCombination) {
        this.isCombination = isCombination;
    }

    public String getIsGifts() {
        return isGifts;
    }

    public void setIsGifts(String isGifts) {
        this.isGifts = isGifts;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }

    public int getBookStorage() {
        return bookStorage;
    }

    public void setBookStorage(int bookStorage) {
        this.bookStorage = bookStorage;
    }

    public int getProNum() {
        return proNum;
    }

    public void setProNum(int proNum) {
        this.proNum = proNum;
    }

    public int getSendNum() {
        return sendNum;
    }

    public void setSendNum(int sendNum) {
        this.sendNum = sendNum;
    }

    public int getRefundNum() {
        return refundNum;
    }

    public void setRefundNum(int refundNum) {
        this.refundNum = refundNum;
    }

    public int getRefundReNum() {
        return refundReNum;
    }

    public void setRefundReNum(int refundReNum) {
        this.refundReNum = refundReNum;
    }

    public int getInspectionNum() {
        return inspectionNum;
    }

    public void setInspectionNum(int inspectionNum) {
        this.inspectionNum = inspectionNum;
    }

    public int getTimeInventory() {
        return timeInventory;
    }

    public void setTimeInventory(int timeInventory) {
        this.timeInventory = timeInventory;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getSysPrice() {
        return sysPrice;
    }

    public void setSysPrice(double sysPrice) {
        this.sysPrice = sysPrice;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public double getItemDiscountFee() {
        return itemDiscountFee;
    }

    public void setItemDiscountFee(double itemDiscountFee) {
        this.itemDiscountFee = itemDiscountFee;
    }

    public Date getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(Date inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getOutTid() {
        return outTid;
    }

    public void setOutTid(String outTid) {
        this.outTid = outTid;
    }

    public String getOutProId() {
        return outProId;
    }

    public void setOutProId(String outProId) {
        this.outProId = outProId;
    }

    public String getOutProSkuId() {
        return outProSkuId;
    }

    public void setOutProSkuId(String outProSkuId) {
        this.outProSkuId = outProSkuId;
    }

    public String getProExplain() {
        return proExplain;
    }

    public void setProExplain(String proExplain) {
        this.proExplain = proExplain;
    }

    public String getBuyerMemo() {
        return buyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        this.buyerMemo = buyerMemo;
    }

    public String getSellerRemark() {
        return sellerRemark;
    }

    public void setSellerRemark(String sellerRemark) {
        this.sellerRemark = sellerRemark;
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

    public String getSecondBarcode() {
        return secondBarcode;
    }

    public void setSecondBarcode(String secondBarcode) {
        this.secondBarcode = secondBarcode;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getBrandNumber() {
        return brandNumber;
    }

    public void setBrandNumber(String brandNumber) {
        this.brandNumber = brandNumber;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getBookInventory() {
        return bookInventory;
    }

    public void setBookInventory(int bookInventory) {
        this.bookInventory = bookInventory;
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getMD5Encryption() {
        return MD5Encryption;
    }

    public void setMD5Encryption(String MD5Encryption) {
        this.MD5Encryption = MD5Encryption;
    }

    public MallOrderBean getOrderBean() {
        return orderBean;
    }

    public void setOrderBean(MallOrderBean orderBean) {
        this.orderBean = orderBean;
    }
}
