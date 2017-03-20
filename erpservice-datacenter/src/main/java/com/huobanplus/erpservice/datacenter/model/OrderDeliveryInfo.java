/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 订单发货信息
 * Created by allan on 4/21/16.
 */
@Data
@Embeddable
public class OrderDeliveryInfo extends BaseInfo {
    //    @JSONField(name = "OrderNo")
    @SerializedName("OrderNo")
    private String orderId;
    //    @JSONField(name = "LogiName")
    @SerializedName("LogiName")
    private String logiName;
    //    @JSONField(name = "LogiNo")
    @SerializedName("LogiNo")
    private String logiNo;
    //    @JSONField(name = "LogiMoney")
    @SerializedName("LogiMoney")
    private double freight;
    //    @JSONField(name = "LogiCode")
    @SerializedName("LogiCode")
    private String logiCode;
    //    @JSONField(name = "Remark")
    @SerializedName("Remark")
    private String remark;
    /**
     * 发货数量序列化字段（productBn,发货数量|productBn,发货数量，productBn为货品的编号
     */
//    @JSONField(deserialize = false, serialize = false)
    @SerializedName("DeliverItemsStr")
    @Column(length = 2000)
    private String deliverItemsStr;
}
