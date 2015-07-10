package com.huobanplus.erpservice.htcomponent.service;

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
