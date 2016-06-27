package com.huobanplus.erpprovider.gy.formatgy.goods;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 * 管易删除商品规格实体
 */
@Data
public class GYDeleteSku {

    /**
     * 商品id
     */
    @JSONField(name = "item_id")
    private String itemId;

    /**
     *  规格id
     */
    @JSONField(name = "id")
    private String id;

    /**
     *  规格代码
     */
    @JSONField(name = "code")
    private String code;
}
