package com.huobanplus.erpprovider.gy.formatgy.goods;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Data
public class GYDeleteGoods {

    /**
     *  商品id id,code必填一项
     */
    @JSONField(name = "id")
    private String id;

    /**
     *  商品代码
     */
    @JSONField(name = "code")
    private String code;
}
