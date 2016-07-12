package com.huobanplus.erpprovider.gy.service;

import com.huobanplus.erpprovider.gy.GYTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wuxiongliu on 2016/6/22.
 */
public class GYScheduledServiceTest extends GYTestBase {

    @Autowired
    private GYScheduledService gySyncDelivery;

    @Test
    public void testSyncOrderShip(){
        gySyncDelivery.syncOrderShip();
    }

    @Test
    public void testSyncGoodsStock(){
        gySyncDelivery.syncGoodsStock();
    }
}
