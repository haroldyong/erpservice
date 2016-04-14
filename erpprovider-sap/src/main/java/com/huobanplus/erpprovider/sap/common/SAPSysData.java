/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.common;

import lombok.Data;

/**
 * sap系统参数
 * Created by allan on 4/11/16.
 */
@Data
public class SAPSysData {
    /**
     * 服务器
     */
    private String host;
    /**
     * 系统编号
     */
    private String sysNo;
    /**
     * sap集团
     */
    private String client;
    /**
     * 用户名
     */
    private String jcoUser;
    /**
     * 登录密码
     */
    private String jcoPass;

    /**
     * SAP路由
     */
    private String sapRouter;
}
