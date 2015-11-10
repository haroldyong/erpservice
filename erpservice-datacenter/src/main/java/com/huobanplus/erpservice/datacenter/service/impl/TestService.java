/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.datacenter.entity.People;
import com.huobanplus.erpservice.datacenter.repository.ManRepository;
import com.huobanplus.erpservice.datacenter.repository.PeopleRepository;
import com.huobanplus.erpservice.datacenter.repository.WomanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liual on 2015-11-07.
 */
@Service
public class TestService {
    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private ManRepository manRepository;
    @Autowired
    private WomanRepository womanRepository;

    public People save() {
        People people = new People();
        people.setName("sjdjd");
        people.setSex(0);
        return peopleRepository.saveAndFlush(people);
    }

    public People findById(int id) {
        return peopleRepository.findOne(id);
    }
}
