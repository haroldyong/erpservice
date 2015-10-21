/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.util;

/**
 * 常量工具类
 */
public class Constant {
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
    public static final String SIGN_TIMESTAMP = "TimeStamp";

    /**
     * sign签名
     */
    public static final String SIGN_PARAM = "Sign";

    /**
     * 时间格式1
     * yyyy-MM-dd HH:mm:dd
     */
    public final static String TIME_FORMAT_ONE = "yyyy-MM-dd HH:mm:dd";

    /**
     * 获得订单列表
     */
    public final static String OBTAIN_ORDER_LIST = "mOrderSearch";

    /**
     * 获得订单详情
     */
    public final static String OBTAIN_ORDER_DETAIL = "mGetOrder";

    /**
     * 发货通知
     */
    public final static String DELIVER_INFO = "mSndGoods";

    /**
     * 获得商品列表
     */
    public final static String OBTAIN_GOOD_LIST = "mGetGoods";

    /**
     * 库存同步
     */
    public final static String SYNC_INVENTORY = "mSysGoods";
}
