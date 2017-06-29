package com.huobanplus.erpprovider.gjbc.config;

import com.huobanplus.erpprovider.gjbc.handler.GjbcOrderHandler;
import com.huobanplus.erpservice.datacenter.config.DataCenterConfig;
import com.huobanplus.erpservice.datacenter.model.Order;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by hxh on 2017-06-29.
 */
@ActiveProfiles("test")
@Configuration
@ComponentScan({
        "com.huobanplus.erpprovider.gjbc",
        "com.huobanplus.erpservice.eventhandler",
        "com.huobanplus.erpuser.huobanmall",
        "com.huobanplus.erpuser.hotsupplier"
})
@Import({DataCenterConfig.class})
public class GjbcTestConfig {

    @Autowired
    private GjbcOrderHandler gjbcOrderHandler;
    @Test
    public void testPushOrder() {
        Order order = new Order();

    }
}
