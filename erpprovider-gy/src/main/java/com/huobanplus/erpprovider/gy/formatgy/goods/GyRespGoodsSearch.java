package com.huobanplus.erpprovider.gy.formatgy.goods;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-07-12.
 */
@Data
public class GyRespGoodsSearch {

    /**
     *  查询总记录数
     */
    @JSONField(name = "total")
    private Integer total;

    /**
     *  商品明细
     */
    @JSONField(name = "items")
    private List<GYResponseGoodsItem> items;

}
