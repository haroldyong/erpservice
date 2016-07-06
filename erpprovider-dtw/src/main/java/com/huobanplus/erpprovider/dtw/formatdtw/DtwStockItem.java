package com.huobanplus.erpprovider.dtw.formatdtw;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-07-06.
 */
@Data
public class DtwStockItem {

    /**
     * 产品编码，实物外包装应该标注的产品编码(最长50个字符)。
     */
    @JSONField(name= "Partno")
    private String partNo;

    /**
     * 货物名称
     */
    @JSONField(name= "PartName")
    private String partName;

    /**
     * 数量
     */
    @JSONField(name= "Qty")
    private int qty;

    /**
     * 批次
     */
    @JSONField(name= "Batch")
    private String batch;

    /**
     * 发票号
     */
    @JSONField(name= "InvoiceNo")
    private String invoiceNo;

    /**
     * 供应商编码
     */
    @JSONField(name= "Supplier")
    private String supplier;

}
