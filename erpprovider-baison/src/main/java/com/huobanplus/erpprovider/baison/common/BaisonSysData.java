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

package com.huobanplus.erpprovider.baison.common;

import lombok.Data;

@Data
public class BaisonSysData {


    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * appkey
     */
    private String baisonAppkey;

    /**
     * secretKey
     */
    private String baisonAppSecret;

    /**
     * 版本号
     */
    private String version = "2.0";

    /**
     * 店铺代码
     */
    private String baisonShopCode;

    /**
     * 仓库代码
     */
    private String baisonWarehouseCode;

}