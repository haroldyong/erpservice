package com.huobanplus.erpprovider.edb;/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by allan on 12/24/15.
 */
@ActiveProfiles("test")
@Configuration
@ComponentScan({"com.huobanplus.erpprovider.edb", "com.huobanplus.erpservice.eventhandler"})
@Import({EDBConfig.class})
public class EDBTestBase {
}
