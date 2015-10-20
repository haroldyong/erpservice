/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/29.
 */
public class SpringWebTest {
    protected static String signKey = "847385239789";
    
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected ServletContext servletContext;
    @Autowired
    protected MockHttpServletRequest request;
//    @PersistenceContext(unitName = "basePu")
//    protected EntityManager entityManager;
//    @Resource(name = "entityManagerFactory")
//    protected EntityManagerFactory managerFactory;

    protected MockMvc mockMvc;

    @Before
    public void createMockMVC() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = ((DefaultMockMvcBuilder) MockMvcBuilders.webAppContextSetup(this.context)).build();
    }

    /**
     * 创建一个sign签名
     *
     * @param params 代签名参数，key排序的map
     * @param prefix
     * @param suffix
     * @return
     */
    protected String buildSign(Map<String, String> params, String prefix, String suffix) {
        if (prefix == null)
            prefix = "";
        if (suffix == null)
            suffix = "";
        StringBuilder stringBuilder = new StringBuilder(prefix);
//        Collections.sort(new ArrayList(params.entrySet()), new Comparator<Map.Entry<String, String>>() {
//            @Override
//            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
//                return o1.getKey().compareTo(o2.getKey());
//            }
//        });
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            stringBuilder.append(next.getKey()).append(next.getValue());
        }
        stringBuilder.append(suffix);
        return DigestUtils.md5Hex(stringBuilder.toString()).toUpperCase();
    }
}
