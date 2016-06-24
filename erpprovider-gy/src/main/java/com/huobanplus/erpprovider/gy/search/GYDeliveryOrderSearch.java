package com.huobanplus.erpprovider.gy.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Data
public class GYDeliveryOrderSearch {

    /**
     * 页码
     */
    @JSONField(name= "page_no")
    private Integer pageNo;

    /**
     * 每页大小
     */
    @JSONField(name= "page_size")
    private Integer pageSize;

    /**
     * 单据编号
     */
    @JSONField(name= "code")
    private String code;

    /**
     * 开始_创建时间
     */
    @JSONField(name= "start_create")
    private String beginTime;

    /**
     * 结束_创建时间
     */
    @JSONField(name= "end_create")
    private String endTime;

    /**
     * 开始-发货时间
     */
    @JSONField(name= "start_delivery_date")
    private String startDeliveryDate;

    /**
     * 结束-发货时间
     */
    @JSONField(name= "end_delivery_date")
    private String endDeliveryDate;

    /**
     * 仓库代码
     */
    @JSONField(name= "warehouse_code")
    private String warehouseCode;

    /**
     * 店铺代码
     */
    @JSONField(name= "shop_code")
    private String shopCode;

    /**
     * 平台单号
     */
    @JSONField(name= "outer_code")
    private String outerCode;

    /**
     * 查询物流单的打印状态
     */
    @JSONField(name= "print")
    private Double print;

    /**
     * 扫描状态
     */
    @JSONField(name= "scan")
    private Double scan;

    /**
     * 是否货到付款
     */
    @JSONField(name= "cod")
    private Double cod;

    /**
     * 会员名称
     */
    @JSONField(name= "vip_name")
    private String vipName;

    /**
     * 发货状态
     */
    @JSONField(name= "delivery")
    private Integer delivery;

    /**
     * 物流单号
     */
    @JSONField(name= "mail_no")
    private String mailNo;
}
