package com.huobanplus.erpservice.datacenter.model;

import lombok.Data;

/**
 * 获取物流轨迹信息
 */
@Data
public class GetTrackingInfo extends BaseInfo {
    /**
     * 订单id
     */
    private String orderId;

}
