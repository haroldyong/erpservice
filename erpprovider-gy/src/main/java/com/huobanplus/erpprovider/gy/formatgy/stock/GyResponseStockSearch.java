package com.huobanplus.erpprovider.gy.formatgy.stock;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-07-12.
 */
@Data
public class GyResponseStockSearch {

    /**
     *  总记录数
     */
    @JSONField(name = "total")
    private int total;

    /**
     *  库存明细
     */
    @JSONField(name = "stocks")
    private List<GYResponseStock> stocks;
}
