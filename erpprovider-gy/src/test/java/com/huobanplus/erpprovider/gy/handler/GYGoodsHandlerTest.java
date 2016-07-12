package com.huobanplus.erpprovider.gy.handler;

import com.huobanplus.erpprovider.gy.GYTestBase;
import com.huobanplus.erpprovider.gy.common.GYConstant;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.formatgy.goods.*;
import com.huobanplus.erpprovider.gy.search.GYGoodsSearch;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        mockGySysData.setSessionkey("48df26a5f53d47f2bf7085376ba39164");
        mockGySysData.setSecret("a4384907606e435bbf594c420760d29a");
    }

    @Test
    public void testGoodsQuery(){
        GYGoodsSearch gyGoodsSearch = new GYGoodsSearch();
//        gyGoodsSearch.setCode("1014hot");
        gyGoodsSearch.setPageSize(GYConstant.PAGE_SIZE);
        gyGoodsSearch.setPageNo(1);
        EventResult eventResult = gyGoodsHandler.goodsQuery(gyGoodsSearch,mockGySysData);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());

    }

    @Test
    public void testPushGoods(){
        GYGoods gyGoods = new GYGoods();
        gyGoods.setCode(UUID.randomUUID().toString());
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
        EventResult eventResult = gyGoodsHandler.pushGoods(gyGoods,mockGySysData);
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());

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

        EventResult eventResult = gyGoodsHandler.updateGoods(gyGoods,mockGySysData);
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }

    @Test
    public void testDeleteGoods(){
        GYDeleteGoods gyDeleteGoods = new GYDeleteGoods();
        gyDeleteGoods.setCode("1014hot");
        EventResult eventResult = gyGoodsHandler.deleteGoods(gyDeleteGoods,mockGySysData);
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }

    @Test
    public void testPushGoodsSku(){
        GYGoodsSku gyGoodsSku = new GYGoodsSku();
        gyGoodsSku.setItemId("10252204162222");
        gyGoodsSku.setCode("testskucode2233");
        gyGoodsSku.setName("testsku2233");
        gyGoodsSku.setStockStatusCode("");
        gyGoodsSku.setWeight(100.2);
        gyGoodsSku.setSalesPoint(5);
        gyGoodsSku.setPackagePoint(5);
        gyGoodsSku.setPurchasePrice(5);
        gyGoodsSku.setSalesPoint(7);
        gyGoodsSku.setAgentPrice(6);
        gyGoodsSku.setCostPrice(3);
        gyGoodsSku.setNote("test");

        EventResult eventResult = gyGoodsHandler.pushGoodsSku(gyGoodsSku,mockGySysData);
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());

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
        EventResult eventResult = gyGoodsHandler.updateGoodsSku(gyGoodsSku,mockGySysData);
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }

    @Test
    public void testDeleteGoodsSku(){
        GYDeleteSku gyDeleteSku = new GYDeleteSku();
        gyDeleteSku.setItemId("1021942961");
        gyDeleteSku.setCode("testskucode");

        EventResult eventResult = gyGoodsHandler.deleteGoodsSku(gyDeleteSku,mockGySysData);
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());

    }

    @Test
    public void testPushGoodsBarCode(){
        GYGoodsBarCode gyGoodsBarCode = new GYGoodsBarCode();
        gyGoodsBarCode.setBarCode("testbarcode22");
        gyGoodsBarCode.setItemCode("hotcode");
        gyGoodsBarCode.setSkuCode("testskucode");

        EventResult eventResult = gyGoodsHandler.pushGoodsBarCode(gyGoodsBarCode,mockGySysData);
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }
}
