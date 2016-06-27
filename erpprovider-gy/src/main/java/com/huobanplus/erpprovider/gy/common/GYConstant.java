/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.common;

/**
 * Created by elvis on 4/21/16.
 */
public class GYConstant {
    /**
     * 轮训的页码
     */
    public static final int PAGE_SIZE = 100;  // 管易平台请求pageSize上限：100

// 请求方法

    // 订单接口
    public static final String ORDER_ADD = "gy.erp.trade.add";

    public static final String ORDER_QUERY = "gy.erp.trade.get";

    public static final String HISTORY_ORDER_QUERY = "gy.erp.trade.history.get";

    public static final String ORDER_MEMO_UPDATE = "gy.erp.trade.memo.update";

    public static final String ORDER_REFUND_STATE_UPDATE = "gy.erp.trade.refund.update";

    // 发货单接口
    public static final String DELIVERY_QUERY ="gy.erp.trade.deliverys.get";

    public static final String HISTORY_DELIVERY_QUERY ="gy.erp.trade.deliverys.history.get";

    public static final String DELIVERY_INFO_UPDATE = "gy.erp.trade.deliverys.update";


    //退货单接口
    public static final String RETURN_ORDER_QUERY ="gy.erp.trade.return.get";

    public static final String RETUR_ORDER_ADD = "gy.erp.trade.return.add";

    public static final String RETURN_ORDER_IN_STOCK = "gy.erp.trade.return.approve";

    // 退款单接口
    public static final String REFUND_ORDER_ADD = "gy.erp.trade.refund.add";

    public static final String REFUND_ORDER_QUERY = "gy.erp.trade.refund.get";

//库存接口

    // 库存查询接口
    public static final String STOCK_QUERY = "gy.erp.stock.get";

// 商品接口

    public static final String GOODS_QUERY = "gy.erp.items.get";

    public static final String GOODS_ADD = "gy.erp.item.add";

    public static final String GOODS_UPDATE = "gy.erp.item.update";

    public static final String GOODS_DELETE = "gy.erp.item.delete";

    public static final String GOODS_SKU_ADD = "gy.erp.item.sku.add";

    public static final String GOODS_SKU_UPDATE = "gy.erp.item.sku.update";

    public static final String GOODS_SKU_DELETE = "gy.erp.item.sku.delete";

    public static final String GOODS_BARCODE_ADD = "gy.erp.item.barcode.add";



}
