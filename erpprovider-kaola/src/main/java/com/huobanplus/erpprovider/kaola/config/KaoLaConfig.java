package com.huobanplus.erpprovider.kaola.config;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by wuxiongliu on 2016/5/9.
 */
@Configuration
public class KaoLaConfig {
    @Autowired
    private ERPRegister erpRegister;

    @Autowired
    private KaoLaHandlerBuilder kaoLaHandlerBuilder;

    @PostConstruct
    public void init() {
        erpRegister.addBuilders(kaoLaHandlerBuilder);
    }
}