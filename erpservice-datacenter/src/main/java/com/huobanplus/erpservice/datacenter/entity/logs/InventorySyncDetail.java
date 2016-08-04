/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.entity.logs;

import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by allan on 8/2/16.
 */
@Entity
@Table(name = "ERP_InventorySync_Detail")
@Setter
@Getter
public class InventorySyncDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private InventorySyncLog inventorySyncLog;

    /**
     * 同步状态
     */
    private OrderSyncStatus.InventorySyncStatus inventorySyncStatus;
    /**
     * 货品
     */
    private String productBn;
    /**
     * 货品名称
     */
    private String productName;
    /**
     * 是否下架（停用）
     */
    private int marketable;

    /**
     * 库存数
     */
    private int inventory;
    /**
     * 可配数
     */
    private int pickInventory;
    /**
     * 可销数
     */
    private int salableInventory;
    /**
     * 在途数
     */
    private int onRoadInventory;
    /**
     * 失败原因
     */
    private String errorMsg;
}
