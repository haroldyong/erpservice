package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * Created by wuxiongliu on 2016/6/20.
 * 管易支付信息实体  响应实体（针对订单查询返回结果）
 */
@Data
public class GYResponsePayment {

    /**
     * 支付类型
     */
    @JSONField(name= "pay_type_name")
    private String payTypeName;

    /**
     * 支付时间
     */
    @JSONField(name= "paytime")
    private Date paytime;

    /**
     * 支付金额
     */
    @JSONField(name= "payment")
    private double payment;
}
