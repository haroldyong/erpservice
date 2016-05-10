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
        mockKaoLaSysData.setAppKey("0dd1a2b29d6e4bfebce479450889b4b2");
        mockKaoLaSysData.setAppSecret("3cf1a3ed8556444bbd1fbd8b9381c8bb");
        mockKaoLaSysData.setHost("http://223.252.220.85/api");//http://thirdpart.kaola.com/api,http://223.252.220.85/api
        mockKaoLaSysData.setV("1.0");

        mockKaoLaGoodsInfoSearch = new KaoLaGoodsInfoSearch();
        mockKaoLaGoodsInfoSearch.setChannelId(1L);
        String timeStr = StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN);
        mockKaoLaGoodsInfoSearch.setTimeStamp(timeStr);
    }

    @Test
    public void testQueryAllGoodsInfo(){
        EventResult eventResult   = kaoLaGoodsInfoHandler.queryAllGoodsInfo(mockKaoLaSysData, mockKaoLaGoodsInfoSearch);
        System.out.println(eventResult.getResultMsg());
    }



}
