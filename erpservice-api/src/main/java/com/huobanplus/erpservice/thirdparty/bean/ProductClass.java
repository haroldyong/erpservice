package com.huobanplus.erpservice.thirdparty.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 商品分类
 */
@XmlRootElement
public class ProductClass extends EDBBaseBean {

    //分类编码
    private String classCode;
    //分类名称
    private String className;
    //上级编码
    private String parentClassCode;
    //是否包装
    private boolean isPack;
    //包装费
    private float packingCharges;
    //毛重比例
    private float roughWeightRatio;
    //是否嵌套
    private boolean isSuit;
    //分类类型
    private String sortType;
    //子节点
    private ProductClass[] classCodes;

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParentClassCode() {
        return parentClassCode;
    }

    public void setParentClassCode(String parentClassCode) {
        this.parentClassCode = parentClassCode;
    }

    public boolean isPack() {
        return isPack;
    }

    public void setIsPack(boolean isPack) {
        this.isPack = isPack;
    }

    public float getPackingCharges() {
        return packingCharges;
    }

    public void setPackingCharges(float packingCharges) {
        this.packingCharges = packingCharges;
    }

    public float getRoughWeightRatio() {
        return roughWeightRatio;
    }

    public void setRoughWeightRatio(float roughWeightRatio) {
        this.roughWeightRatio = roughWeightRatio;
    }

    public boolean isSuit() {
        return isSuit;
    }

    public void setIsSuit(boolean isSuit) {
        this.isSuit = isSuit;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public ProductClass[] getClassCodes() {
        return classCodes;
    }

    public void setClassCodes(ProductClass[] classCodes) {
        this.classCodes = classCodes;
    }
}
