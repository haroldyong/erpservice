/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-09-16.
 */
@Data
public class SurSungOrderSearch {

    /**
     * 店铺编号
     */
    @JSONField(name = "shop_id")
    private Integer shopId;

    /**
     * 第几页，从1 开始 必须
     */
    @JSONField(name = "page_index")
    private Integer pageIndex;

    /**
     * 默认 30，最大不超过 30 必须
     */
    @JSONField(name = "page_size")
    private Integer pageSize;

    /**
     * 修改起始时间，和 结束时间必须同时存在，时间间隔不能超过七天
     */
    @JSONField(name = "modified_begin")
    private String modifiedBegin;

    /**
     * 修改结束时间，和 结束时间必须同时存在，时间间隔不能超过七天
     */
    @JSONField(name = "modified_end")
    private String modifiedEnd;

    /**
     * 订单编号,多个用逗号分隔 最多50
     */
    @JSONField(name = "o_ids")
    private String oIds;

    /**
     * 订单状态，可选：WaitPay:待付款,Delivering:发货中, ,WaitConfirm:已付款待审核,WaitDeliver:已审核待配快递,Sent:已发货
     */
    @JSONField(name = "status")
    private String status;

    /**
     * *,代表所有字段，可以指定订单模型的字段和明细字段,多字段用逗号分隔
     */
    @JSONField(name = "flds")
    private String flds;

    /**
     * 平台订单号号，如淘宝订单号，京东订单号，多个用逗号分隔
     */
    @JSONField(name = "so_ids")
    private String soIds;

    /**
     * 买家账号,最多50
     */
    @JSONField(name = "shop_buyer_ids")
    private List<String> shopBuyerIds;

    /**
     * 收货人,最多50
     */
    @JSONField(name = "receiver_names")
    private List<String> receiverNames;
}
