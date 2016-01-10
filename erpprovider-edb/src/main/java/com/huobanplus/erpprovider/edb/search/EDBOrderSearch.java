/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.search;

import com.huobanplus.erpprovider.edb.common.EDBEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created by allan on 12/25/15.
 */
@Setter
@Getter
public class EDBOrderSearch {
    private String dateType;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private EDBEnum.PayStatusEnum payStatus;
    private EDBEnum.OrderStatusEnum orderStatus;
    private String shopId;
    private String storageId;
    private int pageNo;
    private int pageSize;
}
