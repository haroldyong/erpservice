package com.huobanplus.erpuser.huobanmall.common;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by liual on 2015-10-15.
 */
@Configuration
public class HuobanmallConfig {
    @Autowired
    private ERPRegister erpRegister;

    @Autowired
    private HuobanmallHandlerBuilder huobanmallHandlerBuilder;

    @PostConstruct
    public void register() {
        erpRegister.addUserBuilders(huobanmallHandlerBuilder);
    }
}
