/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.erpevent;

import com.huobanplus.erpservice.eventhandler.model.ProductInfo;

/**
 * <b>类描述：商品信息事件<b/>
 */
public class ProductInfoEvent extends ERPBaseEvent {

    /**
     * 商品信息实体
     */
    private ProductInfo productInfo;

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
}
