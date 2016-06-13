package com.huobanplus.erpprovider.kaola.formatkaola;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/13.
 */
@Data
public class KaoLaGoodsImage {

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     *  图片地址
     */
    private String imageUrl;

    /**
     *  图片类型
     */
    private int imageType;

    /**
     * 图片显示顺序
     */
    private int orderValue;

    /**
     * 70x70 图片地址
     */
    private String imageUrlFor70;

    /**
     * 430x430 图片地址
     */
    private String imageUrlFor430;
}
