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

package com.huobanplus.erpservice.datacenter.model;

import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-12-12.
 * 采购单
 */
@Data
public class PurchaseOrder {

    /**
     * 收货单号
     */
    private String receiveNo;

    /**
     * 供应商编码
     */
    private String supplierId;

    /**
     * 提单号
     */
    private String bolNo;

    /**
     * 采购单明细
     */
    private List<PurchaseOrderItem> items;

}
