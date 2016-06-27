package com.huobanplus.erpprovider.gy.service;

import com.huobanplus.erpprovider.gy.GYTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wuxiongliu on 2016/6/22.
 */
public class GYSyncDeliveryTest extends GYTestBase {

    @Autowired
    private GYSyncDelivery gySyncDelivery;

    @Test
    public void testSync(){
        gySyncDelivery.syncOrderShip();
    }
}
