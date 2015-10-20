/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.providerapi.service;

import java.util.Map;

/**
 * 基层处理信息接口
 * 1、处理erp请求信息事件
 */
public interface HTBaseHandler {

    /**
     * ERP提交数据信息变更请求
     * @param url post请求
     * @param map
     * @return json格式，
     */
    String dataChangedNotice(String url, Map map);

    /**
     *
     * @param url get请求
     * @return json格式
     */
    String obtainInfo(String url);
}
