package com.huobanplus.erpprovider.kaola.formatkaola;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/5/11.
 */

@Data
public class KaoLaOrderItem {

    /**
     * 商品id
     */
    private String goodsId;

    /**
     *  商品skuId
     */
    private String skuId;

    /**
     * 购买数量
     */
    private int buyAmount;
}
