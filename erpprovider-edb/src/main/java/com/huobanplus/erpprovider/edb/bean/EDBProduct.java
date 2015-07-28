package com.huobanplus.erpprovider.edb.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by allan on 2015/7/28.
 */
public class EDBProduct {
    /**
     * 产品分类
     */
    @JacksonXmlProperty(localName = "product_no")
    private String productNo;
    /**
     * 品牌编码
     */
    @JacksonXmlProperty(localName = "brand_no")
    private String brandNo;
    /**
     * 品牌名称
     */
    @JacksonXmlProperty(localName = "brand_name")
    private String brandName;
    /**
     * 规格
     */
    @JacksonXmlProperty(localName = "standard")
    private String standard;
    /**
     * 分类编码
     */
    @JacksonXmlProperty(localName = "sort_no")
    private String sortNo;
    /**
     * 分类名称
     */
    @JacksonXmlProperty(localName = "sort_name")
    private String sortName;
    /**
     * 条形码
     */
    @JacksonXmlProperty(localName = "bar_code")
    private String barCode;
    /**
     * 备用条码
     */
    private String barCodeRemark;
    /**
     * 产品明细编号
     */
    private String proItemNo;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 厂家货号
     */
    private String verderNo;
    /**
     * 上架日期
     */
    private String groundDate;
    /**
     * 建档日期
     */
    private String bookBuildDate;
    /**
     * 供应商
     */
    private String supplier;
    /**
     * 产品状态
     */
    private String productState;
    /**
     * 是否套装
     */
    private int isSuit;
    /**
     * 销售价
     */
    private String salePrice;
    /**
     * 成本价
     */
    private String cost;
    /**
     * 含税成本价
     */
    private String taxCost;
    /**
     * 实物库存
     */
    private int entityStock;
    /**
     * 可销库存
     */
    private int sellStock;
    /**
     * 台账库存
     */
    private int standBookStock;
    /**
     * 消耗数量
     */
    private int use_num;
    /**
     * 总数量
     */
    private int totalNum;
}
