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

import lombok.Data;

/**
 * Created by wuxiongliu on 2016-12-27.
 */
@Data
public class BaisonStockSearch {

    /**
     * 商品编号
     */
    private String spdm;

    /**
     * 颜色代码
     */
    private String gg1dm;

    /**
     * 尺码代码
     */
    private String gg2dm;

    /**
     * 仓库代码
     */
    private String ckdm;
}
