package com.huobanplus.erpservice.datacenter.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.persistence.Embeddable;

/**
 * 清关
 */
@Data
@Embeddable
public class CancelOrderInfo extends BaseInfo {
    /**
     * 订单编号，联合单号
     */
    @SerializedName("orderId")
    public String orderId;
    /**
     * 清单编号
     */
    @SerializedName("invtNo")
    public String invtNo;
    /**
     * 海关总署清关状态码
     */
    @SerializedName("customsStatus")
    public int customsStatus;
    /**
     * 国检清关状态码
     */
    @SerializedName("nationStatus")
    public int nationStatus;
    /**
     * 总署清清单回执时间
     */
    @SerializedName("returnTime")
    public String returnTime;
    /**
     * 总署回执描述
     */
    @SerializedName("customsRemark")
    public String customsRemark;
    /**
     * 国检回执描述
     */
    @SerializedName("nationRemark")
    public String nationRemark;
}