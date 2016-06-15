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



    /**
     *  appkey: bb0b3ad64c9e5eb06c2fb6f163bf179e79051bd5c9b652fc45dc68a2b5dd23c6
     *  appkey zs : ff438be07823f990fc6eef7f1a3171d05512031e704803949b36fc13fc05b493
     *  appkey: 0dd1a2b29d6e4bfebce479450889b4b2
     *  secretKey: 4ed8b056c32939b9fd66987470b3e9fb720bdded02197e678e516bdcdf810833
     *  secretKey: 3cf1a3ed8556444bbd1fbd8b9381c8bb
     *  secretKey zs: ea2358dda586ed69e51812d0e6e107af5f3c741d5cde7c14e97f265eba9ebcdc
     */

    @Before
    public void setUp(){
        mockKaoLaSysData = new KaoLaSysData();
        mockKaoLaSysData.setAppKey("bb0b3ad64c9e5eb06c2fb6f163bf179e79051bd5c9b652fc45dc68a2b5dd23c6");
        mockKaoLaSysData.setAppSecret("4ed8b056c32939b9fd66987470b3e9fb720bdded02197e678e516bdcdf810833");
        mockKaoLaSysData.setRequestUrl("http://223.252.220.85/api");//http://thirdpart.kaola.com/api,http://223.252.220.85/api
        mockKaoLaSysData.setV("1.0");
        mockKaoLaSysData.setChannelId(1200L);

        mockKaoLaGoodsInfoSearch = new KaoLaGoodsInfoSearch();
        mockKaoLaGoodsInfoSearch.setChannelId(1200L);
        String timeStr = StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN);
        mockKaoLaGoodsInfoSearch.setTimeStamp(timeStr);
        mockKaoLaGoodsInfoSearch.setSkuId("3873113-ecc4090b639c47f89b453980923afb8e");
        mockKaoLaGoodsInfoSearch.setQueryType(0);
    }


    @Test
    public void testQueryAllGoodsId(){
        EventResult eventResult = kaoLaGoodsInfoHandler.queryAllGoodsId(mockKaoLaSysData);
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testQueryGoodsInfoById(){
        EventResult eventResult  = kaoLaGoodsInfoHandler.queryGoodsInfoById(mockKaoLaSysData, "");
        System.out.println(eventResult.getResultMsg());
    }



}
