/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.config;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 类描述：网店管家配置服务
 */
@Configuration
@ComponentScan({"com.huobanplus.erpprovider.netshop", "com.huobanplus.erpservice.eventhandler"})
public class NetShopConfig {
    @Autowired
    private ERPRegister register;

    @Bean
    public NetShopHandlerBuilder netShopHandlerBuilder() {
        return new NetShopHandlerBuilder();
    }

    @Autowired
    private NetShopHandlerBuilder netShopHandlerBuilder;

    @PostConstruct
    public void init() {
        register.addBuilders(netShopHandlerBuilder);
    }
}
