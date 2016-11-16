/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.common;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Data
public class SurSungSysData {

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 合作方编号
     */
    private String partnerId;

    /**
     * 接入密钥
     */
    private String partnerKey;

    /**
     * 服务授权码
     */
    private String token;

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 店铺id
     */
    private int shopId;

    /**
     * 指定同步的订单字符串，逗号分隔
     */
    private String syncShopId;

}
