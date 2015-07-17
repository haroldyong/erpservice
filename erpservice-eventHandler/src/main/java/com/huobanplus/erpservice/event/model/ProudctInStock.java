package com.huobanplus.erpservice.event.model;

import java.io.Serializable;

/**
 * 产品库存信息
 */
public class ProudctInStock implements Serializable {

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

}
