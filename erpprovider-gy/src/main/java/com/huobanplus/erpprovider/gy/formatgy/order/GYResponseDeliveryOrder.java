package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Data
public class GYResponseDeliveryOrder {
    /**
     * 发货单号
     */
    @JSONField(name= "code")
    private String code;

    /**
     * 仓库代码
     */
    @JSONField(name= "warehouse_code")
    private String warehouseCode;

    /**
     * 仓库名称
     */
    @JSONField(name= "warehouse_name")
    private String warehouseName;

    /**
     * 物流公司代码
     */
    @JSONField(name= "express_code")
    private String expressCode;

    /**
     * 物流公司名称
     */
    @JSONField(name= "express_name")
    private String expressName;

    /**
     * 物流单号
     */
    @JSONField(name= "mail_no")
    private String mailNo;

    /**
     * 是否已打印物流单
     */
    @JSONField(name= "printExpress")
    private boolean printExpress;

    /**
     * 是否已打印发货单
     */
    @JSONField(name= "printDeliveryList")
    private boolean printDeliveryList;

    /**
     * 是否已扫描
     */
    @JSONField(name= "scan")
    private boolean scan;

    /**
     * 是否已称重
     */
    @JSONField(name= "weight")
    private boolean weight;

    /**
     * 是否已发货
     */
    @JSONField(name= "delivery")
    private boolean delivery;
}
