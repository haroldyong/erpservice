package com.huobanplus.erpservice.commons.config;

import com.huobanplus.erpservice.datacenter.config.DataCenterConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by allan on 2015/7/10.
 */
@Configuration
@ComponentScan(basePackages = "com.huobanplus.erpservice")
@ImportResource({"classpath*:applicationContext-providers.xml"})
@EnableScheduling
@Import(DataCenterConfig.class)
public class ApplicationConfig {
}
