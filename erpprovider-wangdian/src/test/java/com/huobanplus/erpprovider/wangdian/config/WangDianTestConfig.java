package com.huobanplus.erpprovider.wangdian.config;

import com.huobanplus.erpservice.datacenter.config.DataCenterConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by wuxiongliu on 2016-11-02.
 */

@ActiveProfiles("test")
@Configuration
@ComponentScan({
        "com.huobanplus.erpprovider.wangdian",
        "com.huobanplus.erpservice.eventhandler",
        "com.huobanplus.erpuser.huobanmall",
        "com.huobanplus.erpuser.hotsupplier"
})
@Import({DataCenterConfig.class})
public class WangDianTestConfig {
}
