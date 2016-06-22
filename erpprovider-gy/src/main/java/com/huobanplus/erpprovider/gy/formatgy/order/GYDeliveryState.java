package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Data
public class GYDeliveryState {

    /**
     * 操作代码 0-打单 1-扫描 2-称重 是
     */
    @JSONField(name = "area_id")
    private String areaId;

    /**
     * 操作人 是
     */
    @JSONField(name = "operator")
    private String operator;

    /**
     * 操作时间 是
     */
    @JSONField(name = "operator_date")
    private Date operatorDate;

    /**
     * 备注
     */
    @JSONField(name = "note")
    private String note;
}
