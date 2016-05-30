package com.huobanplus.erpprovider.iscs.service;

import com.huobanplus.erpprovider.iscs.ISCSTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hzbc on 2016/5/3.
 */
public class ISCSScheduledServiceTest extends ISCSTestBase {

    @Autowired
    private ISCSScheduledService iscsScheduledService;

    @Test
    public void testSyncOrderShip(){
        iscsScheduledService.syncOrderShip();
    }

}
