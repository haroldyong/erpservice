/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpservice.datacenter.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiongliu on 2017-01-16.
 */
@Data
public class ProductInfo {

    /**
     * 货品sku
     */
    private String skuId;

    /**
     * 仓库代码
     */
    private String warehouseCode;

    public static void main(String[] args) {
        List<ProductInfo> productInfoList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setSkuId("sku-" + i);
            productInfo.setWarehouseCode("code-" + i);
            productInfoList.add(productInfo);
        }

        System.out.println(JSON.toJSONString(productInfoList));
    }
}
