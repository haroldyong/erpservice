package com.huobanplus.erpservice.event.model;

import java.io.Serializable;

/**
 * 商品信息实体
 */
public class ProductInfo extends BaseBean {

    //商品编号
    private String productNo;
    //商品明细编号
    private String productDetailNo;
    //条形码
    private String barCode;
    //规格
    private String specification;
    //颜色
    private String color;
    //尺码
    private String size;
    //单位
    private String unit;
    //重量
    private String weight;
    //产品状态
    private String productStatus;
    //销售价格
    private String sellPrice;
    //参考进货价
    private String contrastPurchasePrice;
    //最低进货价
    private String miniPurchasePrice;
    //成本价
    private String cost;
    //含税成本价
    private String costTax;
    //进货价说明
    private String purchasePriceEx;
    //装箱数
    private String boxNum;
    //有效期
    private String periodValidity;
    //产品明细图片
    private String picture;
    //是否消耗品
    private String isConsump;
    //消耗周期
    private String consumpCycle;
    //不单发产品
    private String isSingleSend;
    //物料属性
    private String attribute;
    //采购类型
    private String purchaseType;
    //是否航空
    private String isFlight;
    //产品名称ID
    private String productNameId;
    //产品名称
    private String productName;
    //产品URL
    private String productUrl;
    //采购周期
    private String purchaseCycle;
    //采购批量
    private String purchaseBatch;
    //销量计算方式
    private String saleCalculation;
    //是否包材
    private String isPackMaterials;
    //备注
    private String remark;
    //操作人IP
    private String userIP;
    //品牌名称
    private String brandName;
    //分类名称
    private String sortName;
    //供应商
    private String supplier;
    //商家编码
    private String outerId;
    //市场价
    private String marketPrice;
    //产品介绍
    private String productintro;
    //厂家货号
    private String factoryItem;
    //是否包装
    private String isPack;
    //包装费
    private String packingCharges;
    //毛重比例
    private String roughWeightRatio;
    //长
    private String length;
    //宽
    private String wide;
    //高
    private String high;
    //成品属性
    private String productAttribute;
    //生产级别
    private String createLevel;
    //物流级别
    private String logisticLevel;
    //存储级别
    private String storageLevel;
    //包装级别
    private String packLevel;
    //操作人ip,用于记录日志
    private String wfpid;
    //新品到期时间
    private String newExpireDate;
    //单品数量
    private String suitItemCount;
    //打包编号
    private String packCode;
    //打包规格
    private String packStandard;
    //产品积分
    private String integral;
    //是否积分换购
    private String isUseIntegral;
    //换购积分
    private String buyIntegral;
    //最近进货价
    private String minCost;
    //最高进货价
    private String highestCost;
    //平均价
    private String avgCost;
    //预计到货日期
    private String avgcostExplain;
    //是否删除
    private String isDeleted;
    //是否停用
    private String isStop;
    //积分系数
    private String integralScale;
    //存货属性
    private String stockAttribute;
    //需要发货数量
    private String lowestStock;
    //尺码名称
    private String sizeName;
    //颜色名称
    private String colorName;
    //自定义属性
    private String customAtt;
    //主商品系统ID
    private String  itemId;
    //主商品名称
    private String itemName;
    //是否为多规格
    private String isSku;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductDetailNo() {
        return productDetailNo;
    }

    public void setProductDetailNo(String productDetailNo) {
        this.productDetailNo = productDetailNo;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getContrastPurchasePrice() {
        return contrastPurchasePrice;
    }

    public void setContrastPurchasePrice(String contrastPurchasePrice) {
        this.contrastPurchasePrice = contrastPurchasePrice;
    }

    public String getMiniPurchasePrice() {
        return miniPurchasePrice;
    }

    public void setMiniPurchasePrice(String miniPurchasePrice) {
        this.miniPurchasePrice = miniPurchasePrice;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCostTax() {
        return costTax;
    }

    public void setCostTax(String costTax) {
        this.costTax = costTax;
    }

    public String getPurchasePriceEx() {
        return purchasePriceEx;
    }

    public void setPurchasePriceEx(String purchasePriceEx) {
        this.purchasePriceEx = purchasePriceEx;
    }

    public String getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(String boxNum) {
        this.boxNum = boxNum;
    }

    public String getPeriodValidity() {
        return periodValidity;
    }

    public void setPeriodValidity(String periodValidity) {
        this.periodValidity = periodValidity;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIsConsump() {
        return isConsump;
    }

    public void setIsConsump(String isConsump) {
        this.isConsump = isConsump;
    }

    public String getConsumpCycle() {
        return consumpCycle;
    }

    public void setConsumpCycle(String consumpCycle) {
        this.consumpCycle = consumpCycle;
    }

    public String getIsSingleSend() {
        return isSingleSend;
    }

    public void setIsSingleSend(String isSingleSend) {
        this.isSingleSend = isSingleSend;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getIsFlight() {
        return isFlight;
    }

    public void setIsFlight(String isFlight) {
        this.isFlight = isFlight;
    }

    public String getProductNameId() {
        return productNameId;
    }

    public void setProductNameId(String productNameId) {
        this.productNameId = productNameId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getPurchaseCycle() {
        return purchaseCycle;
    }

    public void setPurchaseCycle(String purchaseCycle) {
        this.purchaseCycle = purchaseCycle;
    }

    public String getPurchaseBatch() {
        return purchaseBatch;
    }

    public void setPurchaseBatch(String purchaseBatch) {
        this.purchaseBatch = purchaseBatch;
    }

    public String getSaleCalculation() {
        return saleCalculation;
    }

    public void setSaleCalculation(String saleCalculation) {
        this.saleCalculation = saleCalculation;
    }

    public String getIsPackMaterials() {
        return isPackMaterials;
    }

    public void setIsPackMaterials(String isPackMaterials) {
        this.isPackMaterials = isPackMaterials;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getProductintro() {
        return productintro;
    }

    public void setProductintro(String productintro) {
        this.productintro = productintro;
    }

    public String getFactoryItem() {
        return factoryItem;
    }

    public void setFactoryItem(String factoryItem) {
        this.factoryItem = factoryItem;
    }

    public String getIsPack() {
        return isPack;
    }

    public void setIsPack(String isPack) {
        this.isPack = isPack;
    }

    public String getPackingCharges() {
        return packingCharges;
    }

    public void setPackingCharges(String packingCharges) {
        this.packingCharges = packingCharges;
    }

    public String getRoughWeightRatio() {
        return roughWeightRatio;
    }

    public void setRoughWeightRatio(String roughWeightRatio) {
        this.roughWeightRatio = roughWeightRatio;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWide() {
        return wide;
    }

    public void setWide(String wide) {
        this.wide = wide;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getProductAttribute() {
        return productAttribute;
    }

    public void setProductAttribute(String productAttribute) {
        this.productAttribute = productAttribute;
    }

    public String getCreateLevel() {
        return createLevel;
    }

    public void setCreateLevel(String createLevel) {
        this.createLevel = createLevel;
    }

    public String getLogisticLevel() {
        return logisticLevel;
    }

    public void setLogisticLevel(String logisticLevel) {
        this.logisticLevel = logisticLevel;
    }

    public String getStorageLevel() {
        return storageLevel;
    }

    public void setStorageLevel(String storageLevel) {
        this.storageLevel = storageLevel;
    }

    public String getPackLevel() {
        return packLevel;
    }

    public void setPackLevel(String packLevel) {
        this.packLevel = packLevel;
    }

    public String getWfpid() {
        return wfpid;
    }

    public void setWfpid(String wfpid) {
        this.wfpid = wfpid;
    }

    public String getNewExpireDate() {
        return newExpireDate;
    }

    public void setNewExpireDate(String newExpireDate) {
        this.newExpireDate = newExpireDate;
    }

    public String getSuitItemCount() {
        return suitItemCount;
    }

    public void setSuitItemCount(String suitItemCount) {
        this.suitItemCount = suitItemCount;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public String getPackStandard() {
        return packStandard;
    }

    public void setPackStandard(String packStandard) {
        this.packStandard = packStandard;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getIsUseIntegral() {
        return isUseIntegral;
    }

    public void setIsUseIntegral(String isUseIntegral) {
        this.isUseIntegral = isUseIntegral;
    }

    public String getBuyIntegral() {
        return buyIntegral;
    }

    public void setBuyIntegral(String buyIntegral) {
        this.buyIntegral = buyIntegral;
    }

    public String getMinCost() {
        return minCost;
    }

    public void setMinCost(String minCost) {
        this.minCost = minCost;
    }

    public String getHighestCost() {
        return highestCost;
    }

    public void setHighestCost(String highestCost) {
        this.highestCost = highestCost;
    }

    public String getAvgCost() {
        return avgCost;
    }

    public void setAvgCost(String avgCost) {
        this.avgCost = avgCost;
    }

    public String getAvgcostExplain() {
        return avgcostExplain;
    }

    public void setAvgcostExplain(String avgcostExplain) {
        this.avgcostExplain = avgcostExplain;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsStop() {
        return isStop;
    }

    public void setIsStop(String isStop) {
        this.isStop = isStop;
    }

    public String getIntegralScale() {
        return integralScale;
    }

    public void setIntegralScale(String integralScale) {
        this.integralScale = integralScale;
    }

    public String getStockAttribute() {
        return stockAttribute;
    }

    public void setStockAttribute(String stockAttribute) {
        this.stockAttribute = stockAttribute;
    }

    public String getLowestStock() {
        return lowestStock;
    }

    public void setLowestStock(String lowestStock) {
        this.lowestStock = lowestStock;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getCustomAtt() {
        return customAtt;
    }

    public void setCustomAtt(String customAtt) {
        this.customAtt = customAtt;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getIsSku() {
        return isSku;
    }

    public void setIsSku(String isSku) {
        this.isSku = isSku;
    }
}
