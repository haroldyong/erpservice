package com.huobanplus.erpservice.eventhandler.model;

import lombok.Data;

/**
 * <b>类描述：</b>发货通知信息
 */
@Data
public class DeliveryInfo {
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 快递公司名称
     */
    private String logiName;
    /**
     * 货运单号
     */
    private String logiNo;
    /**
     * 运费
     */
    private int freight;
    /**
     * 备注
     */
    private String remark;
    /**
     * 发货数量序列化字段（itemId,发货数量|itemid,发货数量，itemId为订单内容OrderItem的主键id
     */
    private String dicDeliverItemsStr;
}
