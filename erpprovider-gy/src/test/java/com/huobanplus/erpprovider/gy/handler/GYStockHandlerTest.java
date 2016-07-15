package com.huobanplus.erpprovider.gy.handler;

import com.huobanplus.erpprovider.gy.GYTestBase;
import com.huobanplus.erpprovider.gy.search.GYStockSearch;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wuxiongliu on 2016/6/23.
 */
public class GYStockHandlerTest extends GYTestBase {

    @Autowired
    private GYStockHandler gyStockHandler;

    @Test
    public void testStockQuery(){
        GYStockSearch gyStockSearch = new GYStockSearch();
        EventResult eventResult = gyStockHandler.stockQuery(gyStockSearch,mockGySysData);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());

    }
}
