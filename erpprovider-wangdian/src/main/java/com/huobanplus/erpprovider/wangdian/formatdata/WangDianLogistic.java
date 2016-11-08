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

package com.huobanplus.erpprovider.wangdian.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-11-07.
 */
@Data
public class WangDianLogistic {

    /**
     * 订单号
     */
    @JSONField(name = "OrderCode")
    private String orderCode;

    /**
     *
     */
    @JSONField(name = "TradeNO")
    private String tradeNo;

    /**
     * 物流公司代码
     */
    @JSONField(name = "ErpLogisticCode")
    private String logisticCode;

    /**
     * 物流公司名称
     */
    @JSONField(name = "LogisticName")
    private String logisticName;

    /**
     * 物流单号
     */
    @JSONField(name = "PostID")
    private String postId;

    /**
     * 发货时间
     */
    @JSONField(name = "SndTime")
    private String sendTime;
}
