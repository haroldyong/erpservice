package com.huobanplus.erpprovider.pineapple.config;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by hxh on 2017-06-06.
 */
@Component
public class BLPConfig {
    @Autowired
    private ERPRegister erpRegister;

    @Bean
    public BLPHandlerBuilder blpHandlerBuilder(){return new BLPHandlerBuilder();}

    @Autowired
    private BLPHandlerBuilder blpHandlerBuilder;

    @PostConstruct
    public void init() {
        erpRegister.addBuilders(blpHandlerBuilder);
    }
}
