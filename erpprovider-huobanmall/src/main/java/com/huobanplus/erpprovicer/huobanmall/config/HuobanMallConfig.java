package com.huobanplus.erpprovicer.huobanmall.config;

import com.huobanplus.erpservice.event.handler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 伙伴Mall模块的系统配置文件
 * 将该模块注册到系统中
 */
@Configuration
@ComponentScan({"com.huobanplus.erpprovicer.huobanmall", "com.huobanplus.erpservice.event"})
public class HuobanMallConfig {

    /**
     * erp注册对象
     */
    @Resource
    private ERPRegister erpRegister;

    @Bean
    public HuobanMallHandlerBuilder huobanMallHandlerBuilder() {
        return new HuobanMallHandlerBuilder();
    }

    @Resource
    private HuobanMallHandlerBuilder edbHandlerBuilder;

    @PostConstruct
    public void init() {
        erpRegister.addBuilders(new HuobanMallHandlerBuilder());
    }

}
