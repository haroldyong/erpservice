package com.huobanplus.erpprovider.pineapple.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by hxh on 2017-06-16.
 */
@Data
public class BLPOrderListResult {

    @JsonProperty("OrderNos")
    private List<String> orderNos;

    @JsonProperty("Page")
    private int page;

    @JsonProperty("Size")
    private int size;

    @JsonProperty("numtotalorder")
    private int numTotalOrder;
}
