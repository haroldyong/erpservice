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

package com.huobanplus.erpprovider.baison.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2017-01-16.
 */
@Data
public class BaisonStockResp {

    @JSONField(name = "spdm")
    private String productBn;

    @JSONField(name = "gg1dm")
    private String colorCode;

    @JSONField(name = "gg2dm")
    private String sizeCode;

    @JSONField(name = "ckdm")
    private String warehouseCode;

    @JSONField(name = "sl")
    private int number;

}
