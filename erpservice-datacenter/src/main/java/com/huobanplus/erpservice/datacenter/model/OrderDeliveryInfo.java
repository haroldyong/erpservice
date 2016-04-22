/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 订单发货信息
 * Created by allan on 4/21/16.
 */
@Data
public class OrderDeliveryInfo extends BaseInfo {
    @JSONField(name = "OrderNo")
    private String orderId;
    @JSONField(name = "LogiName")
    private String logiName;
    @JSONField(name = "LogiNo")
    private String logiNo;
    @JSONField(name = "LogiMoney")
    private double logiMoney;
    @JSONField(name = "LogiCode")
    private String logiCode;
    @JSONField(name = "Remark")
    private String remark;
    /**
     * 发货数量序列化字段（itemId,发货数量|itemid,发货数量，itemId为订单内容OrderItem的主键id
     */
    @JSONField(deserialize = false, serialize = false)
    private String deliverItemsStr;
}
