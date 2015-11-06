/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpuser.huobanmall.handler;

import com.huobanplus.erpservice.eventhandler.model.BaseInfo;
import com.huobanplus.erpservice.eventhandler.model.OrderSearchInfo;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Created by liual on 2015-11-05.
 */
public class HBOrderHandlerTest {

    @Test
    public void testDeliverInfo() throws Exception {
        BaseInfo orderSearchInfo = new OrderSearchInfo();
        Class test = orderSearchInfo.getClass();
        Field[] fields = test.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }
}