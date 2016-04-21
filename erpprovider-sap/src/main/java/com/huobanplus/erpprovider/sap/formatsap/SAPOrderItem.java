package com.huobanplus.erpprovider.sap.formatsap;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/4/21.
 */
@Data
public class SAPOrderItem {

    private Integer itemId;
    private String orderId;
    private String unionOrderId;
    private String productBn;
    private String name;
    /**
     * 成本价
     */
    private double cost;
    /**
     * 市场价
     */
    private double price;
    /**
     * 销售价
     */
    private double amount;
    /**
     * 购买数量
     */
    private int num;

}
