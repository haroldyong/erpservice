package com.huobanplus.erpprovider.dtw.config;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Configuration
public class DtwConfig {

    @Autowired
    private ERPRegister erpRegister;

    @Autowired
    private DtwHandlerBuilder dtwHandlerBuilder;

    @PostConstruct
    public void init() {
        erpRegister.addBuilders(dtwHandlerBuilder);
    }
}
