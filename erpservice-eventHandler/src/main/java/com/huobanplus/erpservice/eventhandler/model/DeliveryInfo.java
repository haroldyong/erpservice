/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

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
