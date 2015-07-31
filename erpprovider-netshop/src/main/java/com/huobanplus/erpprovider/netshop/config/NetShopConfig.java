package com.huobanplus.erpprovider.netshop.config;

import com.huobanplus.erpservice.event.handler.ERPRegister;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 类描述：网店管家配置服务
 */
@Configuration
public class NetShopConfig {
    //
    @Resource
    private ERPRegister register;

    @PostConstruct
    public void init() {
        register.addBuilders(new NetShopHandlerBuilder());
    }
}
