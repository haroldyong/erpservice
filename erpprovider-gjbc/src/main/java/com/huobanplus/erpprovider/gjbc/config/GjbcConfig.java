package com.huobanplus.erpprovider.gjbc.config;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by montage on 2017/6/26.
 */

@Configuration
public class GjbcConfig {

    @Autowired
    private ERPRegister erpRegister;

    @Autowired
    private GjbcHandlerBuilder gjbcHandlerBuilder;

    @PostConstruct
    public void init(){
        erpRegister.addBuilders(gjbcHandlerBuilder);
    }

}
