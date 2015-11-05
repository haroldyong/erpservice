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
import com.huobanplus.erpservice.datacenter.entity.MallGoodEntity;
import com.huobanplus.erpservice.datacenter.entity.MallProductBean;
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

    private MallGoodEntity mockGood;

    @Before
    public void setUp() throws Exception {
    }

    /**
     * 商品查询接口
     * @throws Exception
     */
    @Test
    public void testMGetGoods() throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis());
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("uCode", "123456");
        signMap.put("mType", "mGetGoods");
        signMap.put("TimeStamp", timestamp);
        signMap.put("GoodsType", "1");
        signMap.put("OuterID", "123456");
        signMap.put("GoodsName", "123456");
        signMap.put("PageSize", "10");
        signMap.put("Page", "1");

        String sign = buildSign(signMap, StringUtil.NETSHOP_SECRET, StringUtil.NETSHOP_SECRET);

        mockMvc.perform(post("/providerApi/rest/1/0")
                .param("uCode", "123456")
                .param("mType", "mGetGoods")
                .param("TimeStamp", timestamp)
                .param("GoodsType", "1")
                .param("OuterID", "123456")
                .param("GoodsName", "123456")
                .param("PageSize", "10")
                .param("Page", "1")
                .param("sign", sign))
                .andDo(print());
    }

    /**
     * 库存同步
     * @throws Exception
     */
    @Test
    public void testMSysGoods() throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis());
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("uCode", "123456");
        signMap.put("mType", "mSysGoods");
        signMap.put("TimeStamp", timestamp);
        signMap.put("ItemID", "123456");
        signMap.put("SkuID", "123456");
        signMap.put("Quantity", "12");

        String sign = buildSign(signMap, StringUtil.NETSHOP_SECRET, StringUtil.NETSHOP_SECRET);

        mockMvc.perform(post("/providerApi/rest/1/0")
                .param("uCode", "123456")
                .param("mType", "mSysGoods")
                .param("TimeStamp", timestamp)
                .param("ItemID", "1")
                .param("SkuID", "123456")
                .param("Quantity", "123456")
                .param("sign", sign))
                .andDo(print());
    }
}