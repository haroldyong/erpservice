/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.common;

/**
 * Created by wuxiongliu on 2016-08-30.
 */
public class SurSungConstant {

    /**
     * 订单上传接口
     */
    public static final String ORDER_PUSH = "orders.upload";

    /**
     * 订单取消接口 从平台到ERP
     */
    public static final String ORDER_CANCEL_TO_ERP = "orders.cancel.upload";

    /**
     * 订单取消接口 从ERP到平台
     */
    public static final String ORDER_CANCEL_TO_PALTFORM = "cancel.order";

    /**
     * 订单发货信息查询接口
     */
    public static final String LOGISTIC_QUERY = "logistic.query";

    /**
     * 订单发货接口 ERP到平台
     */
    public static final String LOGISTIC_UPLOAD = "logistics.upload";

    /**
     * 库存信息查询
     */
    public static final String INVENTORY_QUERY = "inventory.query";

    /**
     *  库存信息erp推送到平台
     */
    public static final String INVENTORY_UPLOAD = "inventory. upload";

    /**
     * 商品上传接口
     */
    public static final String ITEM_UPLOAD = "item.upload";

    /**
     * 退货退款接口
     */
    public static final String AFTERSALE_UPLOAD = "aftersale.upload";


}
