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
     */
    public ProductInfo edbProductDetailInfoUpdate(){return new ProductInfo();}

    /**
     * 获取产品分类信息
     */
    public ProductClass edbProductClassGet(){return new ProductClass();}

    /**
     * 获取产品库存信息
     * @return
     */
    public ProudctInStock edbProductGet(){return new ProudctInStock();}

    /**
     * 更新主产品信息
     * @return
     */
    public ProductInfo edbProductBaseInfoUpdate(){return new ProductInfo();}

    /**
     * 增加产品明细信息
     * @return
     */
    public ProductInfo edbProductDetailAdd(){return new ProductInfo();}

    /**
     * 获取产品基本产品信息
     * @return
     */
    public ProductInfo edbProductBaseInfoGet(){return new ProductInfo();}
}
