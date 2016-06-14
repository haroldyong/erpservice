package com.huobanplus.erpprovider.gy.config;

import org.springframework.test.context.ActiveProfiles;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.huobanplus.erpservice.datacenter.config.DataCenterConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by wuxiongliu on 2016/6/14.
 */


@ActiveProfiles("test")
@Configuration
@ComponentScan({
        "com.huobanplus.erpprovider.gy",
        "com.huobanplus.erpservice.eventhandler",
        "com.huobanplus.erpuser.huobanmall",
        "com.huobanplus.erpuser.hotsupplier"
})
@Import({DataCenterConfig.class})
public class GYTestConfig {
}
