package com.huobanplus.test.gjbbc.config;

import com.huobanplus.erpservice.datacenter.config.DataCenterConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * 高捷 测试配置类
 * Created by montage on 2017/6/29.
 */
@ActiveProfiles("test")
@Configuration
@ComponentScan({
        "com.huobanplus.erpprovider.gjbbc",
        "com.huobanplus.erpservice.eventhandler",
        "com.huobanplus.erpuser.huobanmall",
        "com.huobanplus.erpuser.hotsupplier"
})
@Import({DataCenterConfig.class})
public class TestGjbbcConfig {

}
