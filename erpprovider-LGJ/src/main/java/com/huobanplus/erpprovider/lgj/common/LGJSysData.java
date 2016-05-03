/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.lgj.common;

import lombok.Data;

/**
 * Created by elvis on 4/28/16.
 */
@Data
public class LGJSysData {
    /**
     * 登录用户名 (由礼管家提供)
     */
    private String username;

    /**
     *登录密码 (由礼管家提供)
     */
    private String password;
    /**
     *接口帐号 (由礼管家提供)
     */
    private String apiName;
    /**
     *接口密码 (由礼管家提供)
     */
    private String apiSecret;
    /**
     * 服务器地址
     */
    private String host;
}
