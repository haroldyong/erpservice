package com.huobanplus.erpservice.event.model;

/**
 * 事件中携带的erp相关信息，用于erphandler选择合适的erp-provider处理
 * Created by allan on 2015/7/13.
 */
public class ERPInfo {
    //todo erp相关信息，如类型，名称，验证信息等
    private String type;//erp类型
    private String name;//ERP名称
    private String validation;//验证信息
}
