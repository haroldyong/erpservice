package com.huobanplus.erpprovider.gy.handler;

import com.huobanplus.erpprovider.gy.GYTestBase;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.formatgy.goods.GYGoods;
import com.huobanplus.erpprovider.gy.formatgy.goods.GYSku;
import com.huobanplus.erpprovider.gy.search.GYGoodsSearch;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiongliu on 2016/6/22.
 */
public class GYGoodsHandlerTest extends GYTestBase {

    @Autowired
    private GYGoodsHandler gyGoodsHandler;

    private GYSysData mockGySysData;

    @Before
    public void setUp(){
        mockGySysData = new GYSysData();

        mockGySysData.setURL("https://demo.guanyierp.com/erpapi/rest/erp_open");
        mockGySysData.setAppKey("143158");
        mockGySysData.setSessionkey("58b9c91e195e4a28be107e1485264890");
        mockGySysData.setSecret("a4384907606e435bbf594c420760d29a");
    }

    @Test
    public void testGoodsQuery(){
        GYGoodsSearch gyGoodsSearch = new GYGoodsSearch();
        gyGoodsSearch.setCode("1011hot");
        gyGoodsHandler.goodsQuery(gyGoodsSearch,mockGySysData);
    }

    @Test
    public void testPushGoods(){
        GYGoods gyGoods = new GYGoods();
        gyGoods.setCode("1014hot");
        gyGoods.setName("芬达");
        gyGoods.setSimpleName("芬达饮料");
        gyGoods.setCategoryCode("1012");// 食品分类
        gyGoods.setSupplierCode("供应商0002");// 其他类供应商
        gyGoods.setStockStatusCode("");

        List<GYSku> gySkus = new ArrayList<>();
        GYSku gySku = new GYSku();
        gySku.setSkuCode("testskucode");
        gySku.setSkuName("testskuname");
        gySkus.add(gySku);

        gyGoods.setSkus(gySkus);
        gyGoodsHandler.pushGoods(gyGoods,mockGySysData);

    }

    @Test
    public void testUpdateGoods(){
        GYGoods gyGoods = new GYGoods();
        gyGoods.setCode("1014hot");
        gyGoods.setName("芬达饮料update");
        List<GYSku> gySkus = new ArrayList<>();
        GYSku gySku = new GYSku();
        gySku.setSkuCode("testskucode");
        gySku.setSkuName("testskuname");
        gySkus.add(gySku);
        gyGoods.setSkus(gySkus);

        gyGoodsHandler.updateGoods(gyGoods,mockGySysData);
    }
}
