package com.huobanplus.erpservice;

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
}
