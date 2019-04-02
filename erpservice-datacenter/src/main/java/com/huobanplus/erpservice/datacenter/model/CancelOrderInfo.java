package com.huobanplus.erpservice.datacenter.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.persistence.Embeddable;


@Data
@Embeddable
public class CancelOrderInfo  extends BaseInfo {
    @SerializedName("orderId")
    private String orderId;

    @SerializedName("reason")
    private String reason;
}