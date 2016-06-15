package com.huobanplus.erpprovider.kaola.service;

import com.huobanplus.erpprovider.kaola.KaoLaTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wuxiongliu on 2016/6/12.
 */
public class KaolaScheduledServiceTest extends KaoLaTestBase {

    @Autowired
    private KaolaScheduledService kaolaScheduledService;

    @Test
    public void testSyncOrderShip(){
        kaolaScheduledService.syncOrderShip();
    }

    @Test
    public void testSyncGoodsInfo(){
        kaolaScheduledService.syncGoodsInfo();
    }
}
