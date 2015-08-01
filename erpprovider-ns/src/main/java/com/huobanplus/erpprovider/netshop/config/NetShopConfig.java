package com.huobanplus.erpprovider.netshop.config;

import com.huobanplus.erpservice.event.handler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 类描述：网店管家配置服务
 */
@Configuration
@ComponentScan({"com.huobanplus.erpprovider.netshop", "com.huobanplus.erpservice.event"})
public class NetShopConfig {
    //
    @Resource
    private ERPRegister register;

    @Bean
    public NetShopHandlerBuilder netShopHandlerBuilder() {
        return new NetShopHandlerBuilder();
    }

    @Resource
    private NetShopHandlerBuilder edbHandlerBuilder;

    @PostConstruct
    public void init() {
        register.addBuilders(new NetShopHandlerBuilder());
    }
}
