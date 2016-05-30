package com.huobanplus.erpprovider.kaola.search;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/5/9.
 */
@Data
public class KaoLaGoodsInfoSearch {

    /**
     * 渠道商ID
     */
    private Long channelId;

    /**
     * 商品skuId
     */
    private String skuId;

    /**
     * 查询方式: 0表示返回全部信息、1表示只返回商品id、价格和库存
     */
    private int queryType;

    /**
     *  时间戳
     */
    private String timeStamp;

}
