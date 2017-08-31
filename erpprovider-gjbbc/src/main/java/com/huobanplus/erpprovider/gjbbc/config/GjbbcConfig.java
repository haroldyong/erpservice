package com.huobanplus.erpprovider.gjbbc.config;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by hxh on 2017-08-15.
 */
@Configuration
public class GjbbcConfig {
    @Autowired
    private ERPRegister erpRegister;
    @Autowired
    private GjbbcHandlerBuilder gjbbcHandlerBuilder;

    @PostConstruct
    public void init() {
        erpRegister.addBuilders(gjbbcHandlerBuilder);
    }
}
