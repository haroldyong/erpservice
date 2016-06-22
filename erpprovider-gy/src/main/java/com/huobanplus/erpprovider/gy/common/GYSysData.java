/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.common;

import lombok.Data;

/**
 * Created by elvis on 4/19/16.
 */
@Data
public class GYSysData {

    /**
     *管易接口请求地址
     */
    private String requestUrl;

    /**
     *接入管易标识
     */
    private String appKey;

    /**
     *
     */
    private String sessionkey;

    /**
     *请求方法
     */
    private String method;

    /*
     *签名信息
     */
    private String secret;


}
