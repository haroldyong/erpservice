package com.huobanplus.erpservice.commons.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by allan on 2015/7/10.
 */
@Configuration
@ComponentScan(basePackages = "com.huobanplus.erpservice")
@ImportResource({"classpath*:applicationContext-datacenter.xml", "classpath*:applicationContext-providers.xml"})
public class ApplicationConfig {
}
