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
public class LogisticResponse {

    /**
     *
     */
    @JSONField(name = "IF_OrderCode")
    private String orderCode;

    /**
     *
     */
    @JSONField(name = "ResultCode")
    private int resultCode;

    /**
     *
     */
    @JSONField(name = "ResultMsg")
    private String resultMsg;
}
