package com.huobanplus.erpprovider.edb.util;

/**
 * 常量工具类
 */
public class Constant {
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
    public static final String TOKEN = "801b75b095f44178be6f37cf769acf37";
    public static final String FORMAT = "json";
    public static final String V = "2.0";
    public static final String SLENCRY = "0";
    public static final String IP = "112.16.89.50";
    public static final String TIMESTAMP_PATTERN = "yyyyMMddHHmm";

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
}
