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

package com.huobanplus.erpprovider.baison.handler;

import com.huobanplus.erpprovider.baison.BaisonTestBase;
import com.huobanplus.erpprovider.baison.formatdata.BaisonStockSearch;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiongliu on 2017-01-10.
 */
public class BaisonGoodsHandlerTest extends BaisonTestBase {

    @Autowired
    private BaisonGoodsHandler baisonGoodsHandler;

    @Test
    public void testQueryGoodsStock() {
        BaisonStockSearch baisonStockSearch = new BaisonStockSearch();
        baisonStockSearch.setProductBn("KAJE82537");
//        baisonStockSearch.setGg1dm("C00738");
//        baisonStockSearch.setGg2dm("XL");
        baisonStockSearch.setWarehouseCode("fulelp");

        List<BaisonStockSearch> baisonStockSearchList = new ArrayList<>();
        baisonStockSearchList.add(baisonStockSearch);
//        baisonStockSearchList.add(baisonStockSearch);
        EventResult eventResult = baisonGoodsHandler.queryGoodsStock(baisonStockSearchList, mockBaisonSysData);

        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
    }
}
