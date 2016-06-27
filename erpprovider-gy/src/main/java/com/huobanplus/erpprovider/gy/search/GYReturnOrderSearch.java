package com.huobanplus.erpprovider.gy.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * Created by wuxiongliu on 2016/6/17.
 * 管易退货单查询实体
 */
@Data
public class GYReturnOrderSearch {

    /**
     * 页码
     */
    @JSONField(name = "page_no")
    private int pageNo;

    /**
     * 每页大小
     */
    @JSONField(name = "page_size")
    private int pageSize;

    /**
     * 开始时间
     */
    @JSONField(name = "start_date")
    private Date beginTime;

    /**
     * 结束时间
     */
    @JSONField(name = "end_date")
    private Date endTime;

    /**
     * 入库开始时间
     */
    @JSONField(name = "in_begin_time")
    private Date inBeginTime;

    /**
     * 入库结束时间
     */
    @JSONField(name = "in_end_time")
    private Date inEndTime;

    /**
     * 商品代码
     */
    @JSONField(name = "item_code")
    private String itemCode;

    /**
     * 店铺代码
     */
    @JSONField(name = "shop_code")
    private String shopCode;

    /**
     * 平台单号
     */
    @JSONField(name = "platform_code")
    private String platformCode;

    /**
     * 退换货类型代码
     */
    @JSONField(name = "return_type")
    private String returnType;

    /**
     * 物流单号
     */
    @JSONField(name = "express_no")
    private String expressNo;

    /**
     * 规格代码
     */
    @JSONField(name = "sku_code")
    private String skuCode;

    /**
     * 会员名称
     */
    @JSONField(name = "vip_name")
    private String vipName;

    /**
     * 发货状态 -1-发货失败，0-未发货，1-发货中，2-发货成功
     */
    @JSONField(name = "delivery")
    private int delivery;

    /**
     * 同意状态 	0-未同意，1-同意
     */
    @JSONField(name = "agree")
    private int agree;

    /**
     * 入库状态 	0-未入，1-已入
     */
    @JSONField(name = "receive")
    private int receive;

    /**
     * 作废状态 	0-未作废，1-已作废
     */
    @JSONField(name = "cancel")
    private int cancel;

    /**
     * 是否三无包裹 	0-不是，1-是
     */
    @JSONField(name = "no_parcel")
    private int noParcel;

    /**
     * 收货人
     */
    @JSONField(name = "receiver_name")
    private String receiverName;

    /**
     * 收货人手机号
     */
    @JSONField(name = "receiver_phone")
    private String receiverPhone;

    /**
     * 退入仓库代码
     */
    @JSONField(name = "warehousein_code")
    private String warehouseInCode;

    /**
     * 换出仓库代码
     */
    @JSONField(name = "warehouseout_code")
    private String warehouseOutCode;

}
