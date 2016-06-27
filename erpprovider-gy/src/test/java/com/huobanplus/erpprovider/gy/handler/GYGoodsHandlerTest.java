package com.huobanplus.erpprovider.gy.handler;

import com.huobanplus.erpprovider.gy.GYTestBase;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.formatgy.goods.*;
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

        mockGySysData.setRequestUrl("https://demo.guanyierp.com/erpapi/rest/erp_open");
        mockGySysData.setAppKey("143158");
        mockGySysData.setSessionkey("58b9c91e195e4a28be107e1485264890");
        mockGySysData.setSecret("a4384907606e435bbf594c420760d29a");
    }

    @Test
    public void testGoodsQuery(){
        GYGoodsSearch gyGoodsSearch = new GYGoodsSearch();
        gyGoodsSearch.setCode("hotcode");
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

    @Test
    public void testDeleteGoods(){
        GYDeleteGoods gyDeleteGoods = new GYDeleteGoods();
        gyDeleteGoods.setCode("20161025");
        gyGoodsHandler.deleteGoods(gyDeleteGoods,mockGySysData);
    }

    @Test
    public void testPushGoodsSku(){
        GYGoodsSku gyGoodsSku = new GYGoodsSku();
        gyGoodsSku.setItemId("1021942961");
        gyGoodsSku.setCode("testskucode");
        gyGoodsSku.setName("testsku");
        gyGoodsSku.setStockStatusCode("");
        gyGoodsSku.setWeight(100.2);
        gyGoodsSku.setSalesPoint(5);
        gyGoodsSku.setPackagePoint(5);
        gyGoodsSku.setPurchasePrice(5);
        gyGoodsSku.setSalesPoint(7);
        gyGoodsSku.setAgentPrice(6);
        gyGoodsSku.setCostPrice(3);
        gyGoodsSku.setNote("test");

        gyGoodsHandler.pushGoodsSku(gyGoodsSku,mockGySysData);

    }

    @Test
    public void testUpdateGoodsSku(){
        GYGoodsSku gyGoodsSku = new GYGoodsSku();
        gyGoodsSku.setItemId("1021942961");
        gyGoodsSku.setCode("testskucode");
        gyGoodsSku.setName("testskuUpdate");
        gyGoodsSku.setStockStatusCode("");
        gyGoodsSku.setWeight(100.2);
        gyGoodsSku.setSalesPoint(5);
        gyGoodsSku.setPackagePoint(5);
        gyGoodsSku.setPurchasePrice(5);
        gyGoodsSku.setSalesPoint(7);
        gyGoodsSku.setAgentPrice(6);
        gyGoodsSku.setCostPrice(3);
        gyGoodsSku.setNote("test");
        gyGoodsHandler.updateGoodsSku(gyGoodsSku,mockGySysData);
    }

    @Test
    public void testDeleteGoodsSku(){
        GYDeleteSku gyDeleteSku = new GYDeleteSku();
        gyDeleteSku.setItemId("1021942961");
        gyDeleteSku.setCode("testskucode");

        gyGoodsHandler.deleteGoodsSku(gyDeleteSku,mockGySysData);

    }

    @Test
    public void testPushGoodsBarCode(){
        GYGoodsBarCode gyGoodsBarCode = new GYGoodsBarCode();
        gyGoodsBarCode.setBarCode("testbarcode");
        gyGoodsBarCode.setItemCode("hotcode");
        gyGoodsBarCode.setSkuCode("testskucode");

        gyGoodsHandler.pushGoodsBarCode(gyGoodsBarCode,mockGySysData);
    }
}
