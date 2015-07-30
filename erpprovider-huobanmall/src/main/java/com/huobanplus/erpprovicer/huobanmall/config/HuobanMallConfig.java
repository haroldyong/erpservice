package com.huobanplus.erpprovicer.huobanmall.config;

import com.huobanplus.erpservice.event.handler.ERPRegister;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 伙伴Mall模块的系统配置文件
 * 将该模块注册到系统中
 */
@Configuration
public class HuobanMallConfig {

    /**
     * erp注册对象
     */
    @Resource
    private ERPRegister erpRegister;

    @PostConstruct
    public void init() {
        erpRegister.addBuilders(new HuobanMallHandlerBuilder());
    }

}
