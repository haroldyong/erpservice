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
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            stringBuilder.append(next.getKey()).append(next.getValue());
        }
        stringBuilder.append(suffix);
        return DigestUtils.md5Hex(stringBuilder.toString()).toUpperCase();
    }
}
