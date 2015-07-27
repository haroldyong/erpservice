package com.huobanplus.erpprovider.edb.util;

/**
 * 常量工具类
 */
public class Constant {

    /**
     * 请求地址
     */
    public final static String REQUEST_URI = "http://vip802.6x86.com/rest/index.aspx";
    /**
     * 签名的方式：默认为MD5
     */
    public static final String SIGN_TYPE = "MD5";

    /**
     * 平台秘钥
     */
    public static final String SIGN_SECRET = "secret";

    /**
     * 平台接入码
     */
    public static final String SIGN_U_CODE = "uCode";

    /**
     * 请求方法名
     */
    public static final String SIGN_M_TYPE = "mType";

    /**
     * 请求时时间戳
     */
    public static final String SIGN_TIME_STAMP = "timeStamp";

    //系统级参数
    public static final String DB_HOST = "edb_a88888";
    public static final String APP_KEY = "6f55e36b";
    public static final String FORMAT = "XML";
    public static final String V = "2.0";
    public static final String SLENCRY = "0";
    public static final String IP = "117.79.148.228";

    //请求方法
    public static final String CREATE_ORDER = "edbTradeAdd";
}
