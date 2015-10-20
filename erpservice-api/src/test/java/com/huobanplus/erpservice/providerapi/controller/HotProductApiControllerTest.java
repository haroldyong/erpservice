/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.providerapi.controller;

import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.commons.config.ApplicationConfig;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.bean.MallGoodBean;
import com.huobanplus.erpservice.datacenter.bean.MallProductBean;
import com.huobanplus.erpservice.datacenter.service.MallGoodService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * Created by liual on 2015-08-26.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class, ApplicationConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class HotProductApiControllerTest extends SpringWebTest {
    @Autowired
    private MallGoodService goodService;

    private MallGoodBean mockGood;

    @Before
    public void setUp() throws Exception {
        mockGood = new MallGoodBean();
        mockGood.setGoodId(1);
        mockGood.setBn("123123");
        mockGood.setGoodName("mockName");
        mockGood.setNum(10);
        mockGood.setPrice(12.4);
        mockGood.setIsSku(1);
        mockGood.setErpSysData("123456");
        List<MallProductBean> productBeans = new ArrayList<>();

        MallProductBean productBean = new MallProductBean();
        productBean.setSkuName("ddd");
        productBean.setSkuId("122");
        productBean.setNum(5);
        MallProductBean productBean1 = new MallProductBean();
        productBean.setSkuName("aaa");
        productBean.setSkuId("123");
        productBean.setNum(5);
        productBeans.add(productBean);
        productBeans.add(productBean1);
        mockGood.setProductBeans(productBeans);
        mockGood = goodService.save(mockGood);
    }

    @Test
    public void testObtainGood() throws Exception {
        String timestamp = String.valueOf(new Date().getTime());
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("uCode", "123456");
        signMap.put("mType", "mGetGoods");
        signMap.put("TimeStamp", timestamp);
        signMap.put("GoodsType", "");
        signMap.put("OuterID", "");
        signMap.put("GoodsName", "");
        signMap.put("PageSize", "10");
        signMap.put("Page", "1");

        String sign = buildSign(signMap, StringUtil.NETSHOP_SECRET, StringUtil.NETSHOP_SECRET);

        mockMvc.perform(post("/hotErpApi/netShop/obtainGood")
                .param("uCode", "123456")
                .param("mType", "mGetGoods")
                .param("TimeStamp", timestamp)
                .param("GoodsType", "")
                .param("OuterID", "")
                .param("GoodsName", "")
                .param("PageSize", "10")
                .param("Page", "1")
                .param("Sign", sign))
                .andDo(print());
    }
}