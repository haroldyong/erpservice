package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 用于退货单
 * Created by elvis on 2016/5/31.
 */
public class GYReturnOrder {

//    type_code 	String 	是 			退货类型代码
//    shop_code 	String 	是 			店铺代码
//    vip_code 	String 	是 			会员代码
//    note 	String 				退货单备注
//    trade_code 	String 				关联的销售订单的单据编号
//    trade_platform_code 	String 				关联的销售订单的外部单号
//    item_detail[] 	List 	是 			退入商品列表

    /**
     * type_code 	String 	是 			退货类型代码
      */
    @JSONField(name = "type_code")
    private String typeCode;
    /**
     * shop_code 	String 	是 			店铺代码
     */
    @JSONField(name = "shop_code")
    private String shopCode;
    /**
     * vip_code 	String 	是 			会员代码
     */
    @JSONField(name = "vip_code")
    private String vipCode;
    /**
     * note 	String 				退货单备注
     */
    @JSONField(name = "note")
    private String note;
    /**
     * trade_code 	String 				关联的销售订单的单据编号
     */
    @JSONField(name = "trade_code")
    private String tradeCode;
    /**
     * trade_platform_code 	String 				关联的销售订单的外部单号
     */
    @JSONField(name = "trade_platform_code")
    private String tradePlatformCode;
    /**
     * item_detail[] 	List 	是 			退入商品列表
     */
    @JSONField(name = "item_detail")
    private List<GYReturnOrderItem> itemDetails;
}
