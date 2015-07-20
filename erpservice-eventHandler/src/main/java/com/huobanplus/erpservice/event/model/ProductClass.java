package com.huobanplus.erpservice.event.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/16.
 * 产品类别
 */
public class ProductClass extends BaseBean {
    //类型编码
    private String classCode;
    //类型名称
    private String className;
    //分类类型产品/半成品/原料
    private String sortType;
    //是否套装true/false
    private String isSuit;
    //所属仓库名称
    private String storeName;
    //是否获取下级分类
    private String getChildNodes;
    //父类型编号
    private String parentClassCode;

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

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getIsSuit() {
        return isSuit;
    }

    public void setIsSuit(String isSuit) {
        this.isSuit = isSuit;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getGetChildNodes() {
        return getChildNodes;
    }

    public void setGetChildNodes(String getChildNodes) {
        this.getChildNodes = getChildNodes;
    }

    public String getParentClassCode() {
        return parentClassCode;
    }

    public void setParentClassCode(String parentClassCode) {
        this.parentClassCode = parentClassCode;
    }
}
