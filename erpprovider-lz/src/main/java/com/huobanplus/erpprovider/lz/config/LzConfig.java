package com.huobanplus.erpprovider.lz.config;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class LzConfig {
    @Autowired
    private ERPRegister erpRegister;

    @Autowired
    private LzHandlerBuilder lzHandlerBuilder;

    @PostConstruct
    public void init() {
        erpRegister.addBuilders(lzHandlerBuilder);
    }
}
