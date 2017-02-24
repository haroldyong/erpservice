/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpprovider.wangdianv2.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2017-02-24.
 */

@Data
public class WangDianV2OrderSearch {

    /**
     * status	int	是	订单状态
     * 0 所有订单
     * 5已取消
     * 10待付款
     * 15等未付
     * 16延时审核
     * 19预订单前处理
     * 20前处理
     * 21委外前处理
     * 22抢单前处理
     * 25预订单
     * 27待抢单
     * 30待客审
     * 35待财审
     * 40待递交仓库
     * 45递交仓库中
     * 50已递交仓库
     * 53未确认
     * 55已确认
     * 95已发货
     * 100已签收
     * 105部分打款
     * 110已完成
     */
    @JSONField(name = "status")
    private Integer status;

    /**
     * 查询起始时间,格式 年-月-日 时:分:秒
     */
    @JSONField(name = "start_time")
    private String startTime;

    /**
     * 查询结束时间,格式同上
     */
    @JSONField(name = "end_time")
    private String endTime;

    /**
     * 分页查询的页码，从0开始
     */
    @JSONField(name = "page_no")
    private Integer pageNo;

    /**
     * 分页大小，最大100，默认40
     */
    @JSONField(name = "page_size")
    private Integer pageSize;

    /**
     * 货品信息是否返回图片url，0不返回,1返回
     */
    @JSONField(name = "img_url")
    private String imgUrl;

    /**
     * 订单编号
     */
    @JSONField(name = "trade_no")
    private String tradeNo;

    /**
     * 仓库编号
     */
    @JSONField(name = "warehouse_no")
    private String warehouseNo;

    /**
     * 0 使用订单中的税率 1 使用单品中的税率
     */
    @JSONField(name = "goodstax")
    private Integer goodstax;

    /**
     * 0 没有任何限制(默认值) 1 物流单号不为空才返回 2 只返回物流单号为空的
     */
    @JSONField(name = "has_logistics_no")
    private String hasLogisticsNo;

    /**
     * src	int	否	1 返回订单的交易流水单号 0 不返回
     */
    @JSONField(name = "src")
    private String src;

    /**
     * 原始订单编号
     */
    @JSONField(name = "src_tid")
    private String srcTid;
}
