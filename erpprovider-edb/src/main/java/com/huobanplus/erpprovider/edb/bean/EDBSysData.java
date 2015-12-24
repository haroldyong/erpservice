/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.bean;


import lombok.Getter;
import lombok.Setter;

/**
 * Created by allan on 2015/8/4.
 */
@Getter
@Setter
public class EDBSysData {
    private String requestUrl;
    private String dbHost;
    private String appKey;
    private String appSecret;
    private String token;
    private String format;
    private String v;
    private String slencry;
    private String ip;
    private String shopId;
    private String storageId;
}
