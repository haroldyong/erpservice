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

package com.huobanplus.erpservice.datacenter.entity.logs;

import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wuxiongliu on 2016-12-12.
 */

@Entity
@Table(name = "ERP_PurchaseOrderDetailSync_Log")
@Setter
@Getter
public class PurchaseOrderSyncLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 使用者商户id
     */
    private int customerId;
    /**
     * ERP提供者类型
     */
    private ERPTypeEnum.ProviderType providerType;
    /**
     * ERP使用者类型
     */
    private ERPTypeEnum.UserType userType;
    /**
     * 最后一次同步状态
     */
    private OrderSyncStatus.DetailSyncStatus detailSyncStatus;
    /**
     * 收货单号
     */
    private String receiveNo;

    /**
     * 提单号
     */
    private String blno;

    /**
     * 供应商编码
     */
    private String supplierId;
    /**
     * 订单详细信息
     */
    @Lob
    private String purchaseOrderJson;
    /**
     * 事件信息
     */
    @Lob
    private String erpSysData;
    @Temporal(TemporalType.TIMESTAMP)
    private Date syncTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 错误信息（不在页面显示,只作为一个记录）
     */
    @Lob
    private String errorMsg;
}
