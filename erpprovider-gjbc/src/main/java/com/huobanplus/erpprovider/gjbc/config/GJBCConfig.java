package com.huobanplus.erpprovider.gjbc.config;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by hxh on 2017-06-26.
 */
@Configuration
public class GJBCConfig {
    @Autowired
    private ERPRegister erpRegister;

    @Autowired
    private GJBCHandlerBuilder gjbcHandlerBuilder;

    @PostConstruct
    public void init() {
        erpRegister.addBuilders(gjbcHandlerBuilder);
    }


}
