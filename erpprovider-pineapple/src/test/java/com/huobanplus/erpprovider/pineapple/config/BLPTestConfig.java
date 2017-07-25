package com.huobanplus.erpprovider.pineapple.config;

import com.huobanplus.erpservice.datacenter.config.DataCenterConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by hxh on 2017-06-19.
 */
@ActiveProfiles("test")
@Configuration
@ComponentScan({
        "com.huobanplus.erpprovider.pineapple",
        "com.huobanplus.erpservice.eventhandler",
        "com.huobanplus.erpuser.huobanmall",
        "com.huobanplus.erpuser.hotsupplier"
})
@Import({DataCenterConfig.class})
public class BLPTestConfig {
}
