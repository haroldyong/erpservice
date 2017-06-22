package com.huobanplus.erpprovider.pineapple.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by hxh on 2017-06-16.
 */
@Data
public class BLPOrderListResult {

    @JSONField(name="OrderNos")
    private List<String> orderNos;

    @JSONField(name="Page")
    private int page;

    @JSONField(name="Size")
    private int size;

    @JSONField(name="numtotalorder")
    private int numTotalOrder;
}
