package com.huobanplus.erpservice.event;

import com.huobanplus.erpservice.event.model.ProductClass;
import com.huobanplus.erpservice.event.model.ProductInfo;
import com.huobanplus.erpservice.event.model.ProudctInStock;

/**
 *  商品处理事件
 */
public class GoodEvent extends ERPEvent {


    /**
     * 更新产品明细信息
     * @param productInfo
     */
    public int edbProductDetailInfoUpdate(ProductInfo productInfo){return 0;}

    /**
     * 获取产品分类信息
     * @param productClass
     */
    public ProductClass edbProductClassGet(ProductClass productClass){return null;}

    /**
     * 获取产品库存信息
     * @param proudctInStock
     * @return
     */
    public ProudctInStock edbProductGet(ProudctInStock proudctInStock){return null;}

    /**
     * 更新主产品信息
     * @param productInfo
     * @return
     */
    public ProductInfo edbProductBaseInfoUpdate(ProductInfo productInfo){return null;}

    /**
     * 增加产品明细信息
     * @param productInfo
     * @return
     */
    public ProductInfo edbProductDetailAdd(ProductInfo productInfo){return null;}

    /**
     * 获取产品基本产品信息
     * @param productInfo
     * @return
     */
    public ProductInfo edbProductBaseInfoGet(ProductInfo productInfo){return null;}
}
