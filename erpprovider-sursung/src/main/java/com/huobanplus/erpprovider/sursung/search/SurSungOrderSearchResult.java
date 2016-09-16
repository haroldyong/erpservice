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
import com.huobanplus.erpprovider.sursung.formatdata.SurSungOrder;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-09-16.
 */
@Data
public class SurSungOrderSearchResult {

    /**
     *
     */
    @JSONField(name = "code")
    private int code;

    /**
     *
     */
    @JSONField(name = "issuccess")
    private boolean isSuccess;

    /**
     *
     */
    @JSONField(name = "has_next")
    private boolean hasNext;

    /**
     *
     */
    @JSONField(name = "msg")
    private String msg;

    /**
     *
     */
    @JSONField(name = "page_size")
    private int pageSize;

    /**
     *
     */
    @JSONField(name = "page_index")
    private int pageIndex;

    /**
     *
     */
    @JSONField(name = "data_count")
    private int dataCount;

    /**
     *
     */
    @JSONField(name = "page_count")
    private int pageCount;

    /**
     *
     */
    @JSONField(name = "orders")
    private List<SurSungOrder> orders;
}
