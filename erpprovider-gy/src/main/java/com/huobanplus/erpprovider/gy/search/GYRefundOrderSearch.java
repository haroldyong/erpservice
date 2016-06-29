package com.huobanplus.erpprovider.gy.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 * 管易退货单查询实体
 */
@Data
public class GYRefundOrderSearch {

    /**
     * 页码
     */
    @JSONField(name = "page_no")
    private Integer pageNo;

    /**
     * 每页大小
     */
    @JSONField(name = "page_size")
    private Integer pageSize;

    /**
     * 开始时间
     */
    @JSONField(name = "start_date")
    private String beginTime;

    /**
     * 结束时间
     */
    @JSONField(name = "end_date")
    private String endTime;


    /**
     * 单据编号
     */
    @JSONField(name = "code")
    private String code;

    /**
     * 平台单号
     */
    @JSONField(name = "platfrom_code")
    private String platfromCode;// FIXME: 2016/6/17

    /**
     * 退款单号
     */
    @JSONField(name = "refund_code")
    private String refundCode;

    /**
     * 退款类型代码
     */
    @JSONField(name = "type_code")
    private String typeCode;

    /**
     * 会员名称
     */
    @JSONField(name = "vip_name")
    private String vipName;

    /**
     * 退款种类 	1-仅退款;2-退货退款
     */
    @JSONField(name = "refund_type")
    private String refundType;

    /**
     * 退款方式代码
     */
    @JSONField(name = "payment_type_code")
    private String paymentTypeCode;

    /**
     * 店铺代码
     */
    @JSONField(name = "shop_code")
    private String shopCode;

    /**
     * 作废状态 0-未作废;1-已作废
     */
    @JSONField(name = "cancel")
    private Integer cancel;

}
