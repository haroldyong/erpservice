package com.huobanplus.erpprovider.gy.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Data
public class GYOrderSearch {

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
     * 时间类型 默认为0, 0、创建时间 1、拍单时间 2、付款时间
     */
    @JSONField(name = "date_type")
    private Date dateType;

    /**
     * 订单类型 默认为0, 0、全部 1、未审核 2、已审核
     */
    @JSONField(name = "order_state")
    private int orderState;

    /**
     * 仓库代码
     */
    @JSONField(name = "warehouse_code")
    private String wareHouseCode;

    /**
     * 店铺代码
     */
    @JSONField(name = "shop_code")
    private String shopCode;

    /**
     * 会员名称
     */
    @JSONField(name = "vip_name")
    private String vipName;

    /**
     * 平台单号
     */
    @JSONField(name = "platform_code")
    private String platformCode;

    /**
     * 收件手机
     */
    @JSONField(name = "receiver_mobile")
    private String receiverMobile;

}
