package com.huobanplus.erpprovider.edb;

import com.huobanplus.erpservice.event.handler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by allan on 2015/7/13.
 */
@Configuration
public class EDBConfig {
    @Autowired
    private ERPRegister register;

    @PostConstruct
    public void init() {
        register.addBuilders(new EDBHandlerBuilder());
    }
}
