package com.huobanplus.erpprovider.wangdian.handler;

import com.huobanplus.erpprovider.wangdian.WangDianTestBase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wuxiongliu on 2016-11-02.
 */
public class WangDianOrderHandlerTest extends WangDianTestBase{

    @Autowired
    private WangDianOrderHandler wangDianOrderHandler;

    @Test
    public void test(){
        Assert.assertNotNull(wangDianOrderHandler);
    }
}
