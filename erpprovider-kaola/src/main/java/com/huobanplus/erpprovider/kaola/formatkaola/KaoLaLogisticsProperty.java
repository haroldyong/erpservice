package com.huobanplus.erpprovider.kaola.formatkaola;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/13.
 */
@Data
public class KaoLaLogisticsProperty {

    /**
     * 商品id
     */
    private String goodsId;

    /**
     *  物流属性ID
     */
    private String logisticsPropertyValueID;

    /**
     *  物流属性值
     */
    private String logisticsPropertyValue;

    /**
     * 重量
     */
    private double logisticsWeight;

    /**
     * 物流体积
     */
    private double logisticsSize;
}
