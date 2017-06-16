package com.huobanplus.erpprovider.pineapple.config;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by hxh on 2017-06-06.
 */
public class BLPConfig {
    @Autowired
    private ERPRegister erpRegister;

    @Autowired
    private BLPHandlerBuilder blpHandlerBuilder;

    @PostConstruct
    public void init() {
        erpRegister.addBuilders(blpHandlerBuilder);
    }
}
