/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by allan on 2015/7/13.
 */
@Configuration
//@ComponentScan({"com.huobanplus.erpprovider.edb", "com.huobanplus.erpservice.eventhandler"})
public class EDBConfig {
    @Autowired
    private ERPRegister register;

    @Autowired
    private EDBHandlerBuilder edbHandlerBuilder;

    @PostConstruct
    public void init() {
        register.addBuilders(edbHandlerBuilder);
    }
}
