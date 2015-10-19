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
