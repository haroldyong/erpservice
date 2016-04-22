/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.search;

import com.huobanplus.erpprovider.edb.common.EDBEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by allan on 12/25/15.
 */
@Setter
@Getter
public class EDBOrderSearch {
    /**
     * 日期类型支持下面几种,默认订货日期/订货日期/付款日期/发货日期/归档日期/预计归档日期/到货日期/订单修改日期/验货日期/核销日期/生成应收时间/称重时间/审单时间/取消时间/完成时间/打印时间
     */
    private String dateType;
    private String beginTime;
    private String endTime;
    private EDBEnum.PayStatusEnum payStatus;
    private EDBEnum.ShipStatusEnum shipStatus;
    private EDBEnum.PlatformStatus platformStatus;
    private EDBEnum.OrderStatusEnum proceStatus;
    private int pageIndex;
    private int pageSize;
    /**
     * 仓库id,在{@link com.huobanplus.erpprovider.edb.bean.EDBSysData}
     */
    private String storageId;
    /**
     * 店铺id,在{@link com.huobanplus.erpprovider.edb.bean.EDBSysData}
     */
    private String shopId;
    /**
     * 订单编号
     */
    private String orderId;
}
