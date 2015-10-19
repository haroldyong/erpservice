package com.huobanplus.erpprovider.huobanmall.config;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 伙伴Mall模块的系统配置文件
 * 将该模块注册到系统中
 */
@Configuration
@ComponentScan({"com.huobanplus.erpprovicer.huobanmall", "com.huobanplus.erpservice.eventhandler"})
public class HuobanMallConfig {

    /**
     * erp注册对象
     */
    @Autowired
    private ERPRegister erpRegister;

    @Bean
    public HuobanMallHandlerBuilder huobanMallHandlerBuilder() {
        return new HuobanMallHandlerBuilder();
    }

    @Autowired
    private HuobanMallHandlerBuilder huobanMallHandlerBuilder;

    @PostConstruct
    public void init() {
        erpRegister.addBuilders(huobanMallHandlerBuilder);
    }

}
