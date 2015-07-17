package com.huobanplus.erpservice.event.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/16.
 * 产品类别
 */
public class ProductClass implements Serializable {
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
}
