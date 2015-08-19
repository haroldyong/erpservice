package com.huobanplus.erpprovider.edb;

import com.huobanplus.erpservice.event.handler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by allan on 2015/7/13.
 */
@Configuration
@ComponentScan({"com.huobanplus.erpprovider.edb", "com.huobanplus.erpservice.event"})
public class EDBConfig {
    @Autowired
    private ERPRegister register;

    @Bean
    public EDBHandlerBuilder edbHandlerBuilder() {
        return edbHandlerBuilder;
    }

    @Autowired
    private EDBHandlerBuilder edbHandlerBuilder;

    @PostConstruct
    public void init() {
        register.addBuilders(edbHandlerBuilder);
    }
}
