/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpuser.huobanmall.common;

import com.huobanplus.erpservice.eventhandler.ERPRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 伙伴商城配置
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
