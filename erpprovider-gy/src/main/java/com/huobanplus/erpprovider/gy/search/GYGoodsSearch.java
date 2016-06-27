package com.huobanplus.erpprovider.gy.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 * 管易商品查询实体
 */
@Data
public class GYGoodsSearch {

    /**
     *  页码
     */
    @JSONField(name = "page_no")
    private Integer pageNo;

    /**
     *  每页大小
     */
    @JSONField(name = "page_size")
    private Integer pageSize;

    /**
     *  商品代码
     */
    @JSONField(name = "code")
    private String code;
}
