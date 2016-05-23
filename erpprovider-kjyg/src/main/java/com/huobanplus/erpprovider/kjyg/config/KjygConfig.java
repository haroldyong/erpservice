package com.huobanplus.erpprovider.kjyg.config;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Configuration
public class KjygConfig {

    @Autowired
    private ERPRegister erpRegister;

    @Autowired
    private KjygHandlerBuilder kjzyHandlerBuilder;

    @PostConstruct
    public void init() {
        erpRegister.addBuilders(kjzyHandlerBuilder);
    }
}
