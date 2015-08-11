package com.huobanplus.erpprovider.edb.util;

/**
 * 常量工具类
 */
public class Constant {

    /**
     * 请求地址
     */
    public final static String REQUEST_URI = "http://vip802.6x86.com/edb2/rest/index.aspx";

    //系统级参数
    public static final String DB_HOST = "edb_a88888";
    public static final String APP_KEY = "6f55e36b";
    public static final String APP_SECRET = "adeaac8b252e4ed6a564cdcb1a064082";
    public static final String TOKEN = "a266066b633c429890bf4df1690789a3";
    public static final String FORMAT = "json";
    public static final String V = "2.0";
    public static final String SLENCRY = "0";
    public static final String IP = "117.79.148.228";
    public static final String TIMESTAMP_PATTERN = "yyyyMMddHHmm";

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
