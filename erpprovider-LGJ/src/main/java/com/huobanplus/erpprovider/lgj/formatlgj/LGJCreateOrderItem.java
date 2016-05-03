package com.huobanplus.erpprovider.lgj.formatlgj;

import lombok.Data;

/**
 * Created by elvis on 2016/4/29.
 */
@Data
public class LGJCreateOrderItem {
    /**
     *商品编号
     */
    private String skuId;
    /**
     *购买数量
     */
    private String num;
}
