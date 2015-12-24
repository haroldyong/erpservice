/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpprovider.edb.EDBConfig;
import com.huobanplus.erpprovider.edb.EDBTestBase;
import com.huobanplus.erpservice.common.util.ClassUtil;
import com.huobanplus.erpservice.datacenter.config.DataCenterConfig;
import com.huobanplus.erpservice.datacenter.entity.MallOrderBean;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by allan on 12/24/15.
 */
//@ActiveProfiles("test")
//@ContextConfiguration(classes = {EDBTestBase.class, DataCenterConfig.class})
//@RunWith(SpringJUnit4ClassRunner.class)
public class ClassUtilTest {
    @Test
    public void testCloneClass() throws Exception {
        MallOrderBean orderBean = new MallOrderBean();

        System.out.println(orderBean.getClass() == MallOrderBean.class);

        orderBean.setOrderId("12312321");
        orderBean.setPayTime(new Date());
        orderBean.setCreateTime(new Date());

        Order order = new Order();
        order.setPayTime(LocalDateTime.now().plusMonths(1).format(DateTimeFormatter.ISO_LOCAL_DATE));
        ClassUtil.cloneClass(orderBean, order);

        System.out.println(order.getOrderId());
        System.out.println(order.getPayTime());
        System.out.println(order.getCreateTime());

    }
}
