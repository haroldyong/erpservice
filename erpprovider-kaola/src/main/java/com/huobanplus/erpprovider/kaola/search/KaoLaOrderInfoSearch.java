package com.huobanplus.erpprovider.kaola.search;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/5/10.
 */
@Data
public class KaoLaOrderInfoSearch {

    /**
     * 渠道商Id
     */
    private Long channelId;

    /**
     * 分销商订单Id
     */
    private String thirdPartOrderId;

    /**
     * 时间戳
     */
    private String timeStamp;

}
