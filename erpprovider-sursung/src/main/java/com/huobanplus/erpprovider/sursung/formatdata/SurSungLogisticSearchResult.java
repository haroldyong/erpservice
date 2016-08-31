/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-08-31.
 */
@Data
public class SurSungLogisticSearchResult {

    /**
     * 0,代表成功，其他代表失败
     */
    @JSONField(name = "code")
    private String code;

    /**
     * 是否成功
     */
    @JSONField(name = "issuccess")
    private boolean isSuccess;

    /**
     * 提示信息
     */
    @JSONField(name = "msg")
    private String msg;

    /**
     * 返回个数
     */
    @JSONField(name = "page_size")
    private int pageSize;

    /**
     * 第几页
     */
    @JSONField(name = "page_index")
    private int pageIndex;

    /**
     * 是否还有下一页
     */
    @JSONField(name = "has_next")
    private boolean hasNext;

    /**
     * 订单列表
     */
    @JSONField(name = "orders")
    private List<SurSungLogistic> orders;
}
