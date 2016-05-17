package com.huobanplus.erpprovider.kaola.handler;

import com.huobanplus.erpprovider.kaola.KaoLaTestBase;
import com.huobanplus.erpprovider.kaola.common.KaoLaSysData;
import com.huobanplus.erpprovider.kaola.search.KaoLaGoodsInfoSearch;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by wuxiongliu on 2016/5/9.
 */
public class KaoLaGoodsInfoHandlerTest extends KaoLaTestBase {

    private KaoLaSysData mockKaoLaSysData;

    private KaoLaGoodsInfoSearch mockKaoLaGoodsInfoSearch;

    @Autowired
    private KaoLaGoodsInfoHandler kaoLaGoodsInfoHandler;

    @Before
    public void setUp(){
        mockKaoLaSysData = new KaoLaSysData();
        mockKaoLaSysData.setAppKey("bb0b3ad64c9e5eb06c2fb6f163bf179e79051bd5c9b652fc45dc68a2b5dd23c6");
        mockKaoLaSysData.setAppSecret("4ed8b056c32939b9fd66987470b3e9fb720bdded02197e678e516bdcdf810833");
        mockKaoLaSysData.setRequestUrl("http://223.252.220.85/api");//http://thirdpart.kaola.com/api,http://223.252.220.85/api
        mockKaoLaSysData.setV("1.0");
        mockKaoLaSysData.setChannelId(1200L);

        mockKaoLaGoodsInfoSearch = new KaoLaGoodsInfoSearch();
        mockKaoLaGoodsInfoSearch.setChannelId(1L);
        String timeStr = StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN);
        mockKaoLaGoodsInfoSearch.setTimeStamp(timeStr);
        mockKaoLaGoodsInfoSearch.setSkuId("1");
        mockKaoLaGoodsInfoSearch.setQueryType(0);
    }

    @Test
    public void testQueryAllGoodsInfo(){
        EventResult eventResult = kaoLaGoodsInfoHandler.queryAllGoodsInfo(mockKaoLaSysData, mockKaoLaGoodsInfoSearch);
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testQueryAllGoodsId(){
        EventResult eventResult = kaoLaGoodsInfoHandler.queryAllGoodsId(mockKaoLaSysData, mockKaoLaGoodsInfoSearch);
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testQueryGoodsInfoById(){
        EventResult eventResult  = kaoLaGoodsInfoHandler.queryGoodsInfoById(mockKaoLaSysData, mockKaoLaGoodsInfoSearch);
        System.out.println(eventResult.getResultMsg());
    }



}
