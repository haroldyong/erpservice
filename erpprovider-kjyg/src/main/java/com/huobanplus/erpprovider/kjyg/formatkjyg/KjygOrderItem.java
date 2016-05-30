package com.huobanplus.erpprovider.kjyg.formatkjyg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Data
public class KjygOrderItem {

    /**
     *  货号
     */
    @JSONField(name = "spe")
    private String spe;

    /**
     * 售价
     */
    @JSONField(name = "price")
    private String price;

    /**
     * 数量
     */
    @JSONField(name = "amount")
    private String amount;
}
