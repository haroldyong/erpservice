package com.huobanplus.erpprovider.kaola.formatkaola;

import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016/6/13.
 */
@Data
public class KaoLaGoodsInfo {

    /**
     * 商品前台Id
     */
    private Integer goodsId;

    /**
     * HTML格式的描述xxxx", -- 商品详情
     */
    private String detail;

    /**
     *  商品税率
     */
    private double taxRate;

    /**
     * 商品库存
     */
    private int store;

    /**
     * 商品图片原图url
     */
    private String imgUrl;

    /**
     * 上架状态：1上架，0下架
     */
    private String onlineStatus;

    /**
     *  品牌名称
     */
    private String brandName;

    /**
     * 商品后台Id
     */
    private String productId;

    /**
     * 品牌国名称
     */
    private String brandCountryName;

    /**
     * 商品副标题，对商品的精简描述
     */
    private String subTitle;

    /**
     * 商品标题title和shorttitle一般一样,只是显示位置不一样
     */
    private String title;

    /**
     * 商品分类
     */
    private String category;

    /**
     * 渠道商批发价
     */
    private double price;

    /**
     * 考拉在售价，非促销价
     */
    private double sugestPrice;

    /**
     * 市场指导价，仅供参考
     */
    private double marketPrice;

    /**
     *
     */
    private List<KaoLaSkuProperty> skuProperty;

    /**
     * 商品短标题
     */
    private String shortTitle;

    /**
     * 物流属性
     */
    private KaoLaLogisticsProperty logisticsProperty;

    /**
     * 商品图片
     */
    private List<KaoLaGoodsImage> goodsImages;

    /**
     * skuId
     */
    private String skuId;

    /**
     * 对应考拉页面上的商品详情的属性，注意每个商品的属性可能都会不一样
     */
    private String goodsProperty;

    /**
     * 仓库信息
     */
    private String storage;


}
