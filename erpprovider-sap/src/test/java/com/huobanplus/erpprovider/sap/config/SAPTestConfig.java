package com.huobanplus.erpprovider.sap.config;

import com.huobanplus.erpservice.datacenter.config.DataCenterConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by elvis on 2016/4/15.
 */
@ActiveProfiles("test")
@Configuration
@ComponentScan({
        "com.huobanplus.erpprovider.sap",
        "com.huobanplus.erpservice.eventhandler",
        "com.huobanplus.erpuser.huobanmall",
        "com.huobanplus.erpuser.hotsupplier"
})
@Import({DataCenterConfig.class})
public class SAPTestConfig {
}
