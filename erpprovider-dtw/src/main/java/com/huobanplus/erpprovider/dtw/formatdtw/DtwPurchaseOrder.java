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

package com.huobanplus.erpprovider.dtw.formatdtw;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-12-12.
 */
@Data
public class DtwPurchaseOrder {

    @JSONField(name = "PassKey")
    private String passKey;

    @JSONField(name = "Items")
    private List<DtwPurchaseOrderItem> dtwPurchaseOrderItems;

    /**
     * 电商企业收货订单号\采购订单号
     */
    @JSONField(name = "Msgid")
    private String msgId;

    /**
     * 供应商编码（电商平台下的商家备案编号）
     */
    @JSONField(name = "Supplier")
    private String supplier;

    /**
     * 电商企业编码(电商企业在跨境平台备案编码)
     */
    @JSONField(name = "eCommerceCode")
    private String eCommerceCode;

    /**
     * 电商企业名称
     */
    @JSONField(name = "eCommerceName")
    private String eCommerceName;

    /**
     * 提单号
     */
    @JSONField(name = "Hawb")
    private String hawb;

    /**
     * 主运单号
     */
    @JSONField(name = "Mawb")
    private String mawb;

}
