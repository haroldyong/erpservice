/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.util;

/**
 * 常量工具类
 */
public class EDBConstant {
    public final static String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";


    /**
     * 请求地址
     */
    public final static String REQUEST_URI = "http://vip128.edb05.com/rest/index.aspx";

    public final static String REQUEST_URI_TEST = "http://vip802.6x86.com/edb2/rest/index.aspx";

    //系统级参数
    public static final String DB_HOST = "edb_a74188";
    public static final String APP_KEY = "0a35f9c2";
    public static final String APP_SECRET = "500b77ff0cc44829a458998729ff36af";
    public static final String TOKEN = "f8624c70178e40ef9aa6cf0a34043622";
    public static final String FORMAT = "JSON";
    public static final String V = "2.0";
    public static final String SLENCRY = "0";
    public static final String IP = "112.16.89.50";
    public static final String TIMESTAMP_PATTERN = "yyyyMMddHHmm";
    public static final String SHOP_ID = "58";
    public static final String STORAGE_ID = "32";

    public static final String DB_HOST_TEST = "edb_a88888";
    public static final String APP_KEY_TEST = "6f55e36b";
    public static final String APP_SECRET_TEST = "adeaac8b252e4ed6a564cdcb1a064082";
    public static final String TOKEN_TEST = "a266066b633c429890bf4df1690789a3";
    public static final String FORMAT_TEST = "json";
    public static final String V_TEST = "2.0";
    public static final String SLENCRY_TEST = "0";
    public static final String IP_TEST = "117.79.148.228";
    public static final String TIMESTAMP_PATTERN_TEST = "yyyyMMddHHmm";

    //请求方法
    /**
     * 写入订单
     */
    public static final String CREATE_ORDER = "edbTradeAdd";
    /**
     * 得到商品库存信息
     */
    public static final String GET_PRO_INFO = "edbProductGet";
    /**
     * 得到订单信息
     */
    public static final String GET_ORDER_INFO = "edbTradeGet";
    /**
     * 订单状态更新
     */
    public static final String ORDER_STATUS_UPDATE = "edbTradeImportStatusUpdate";
    /**
     * 订单发货
     */
    public static final String ORDER_DELIVER = "edbTradeDeliveryBatch";
    /**
     * 订单业务状态更新
     */
    public static final String ORDER_UPDATE = "edbTradeUpdate";

    public static final String CANCEL_ORDER = "edbTradeCancel";

    public static final String ENCODING = "utf-8";

    public static final int PAGE_SIZE = 20;
}
