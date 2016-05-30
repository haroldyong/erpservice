package com.huobanplus.erpprovider.iscs.formatiscs;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016/4/28.
 */
@Data
public class ISCSPurchaseOrder {

    /**
     * 网仓仓库Id
     */
    @JSONField(name = "stock_id")
    private String stockId;

    /**
     * 货主
     */
    @JSONField(name = "owner_id")
    private String ownerId;

    /**
     * 采购单号
     */
    @JSONField(name = "purchase_no")
    private String purchaseNo;

    /**
     * 运输商
     */
    @JSONField(name = "transporter_id")
    private String transporterId;

    /**
     * 运单号
     */
    @JSONField(name = "out_sid")
    private String outSid;

    /**
     * 供应商ID
     */
    @JSONField(name = "supplier_id")
    private String supplierId;

    /**
     * 建立日期
     */
    @JSONField(name = "create_date")
    private String createTime;

    /**
     * 计划发货日期
     */
    @JSONField(name = "plan_ship_date")
    private String planShipDate;

    /**
     * 计划到货日期
     */
    @JSONField(name = "plan_arrival_date")
    private String planArrivalDate;

    /**
     * 备注
     */
    @JSONField(name = "remark")
    private String remark;

    /**
     * 审核日期
     */
    @JSONField(name = "audit_date")
    private String auditDate;

    /**
     * 采购条目
     */
    @JSONField(name = "skus")
    private List<ISCSPurchaseOrderItem> purchaseOrderItem;

}
