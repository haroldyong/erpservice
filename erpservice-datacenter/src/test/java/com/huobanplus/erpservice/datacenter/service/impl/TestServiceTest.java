/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.datacenter.config.DataCenterConfig;
import com.huobanplus.erpservice.datacenter.entity.People;
import com.huobanplus.erpservice.datacenter.entity.Woman;
import com.huobanplus.erpservice.datacenter.repository.PeopleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by liual on 2015-11-07.
 */
@ContextConfiguration(classes = DataCenterConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestServiceTest {
    @Autowired
    private TestService testService;

    @Test
    public void test() throws Exception {
//        testService.save();
        People people = testService.findById(1);
        System.out.println("end");
    }
}