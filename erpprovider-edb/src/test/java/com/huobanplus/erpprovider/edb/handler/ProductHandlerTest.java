package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpprovider.edb.EDBConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by allan on 2015/7/28.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {EDBConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductHandlerTest {
    @Autowired
    private ProductHandler productHandler;

    @Test
    public void testGetProductInfo() throws Exception {
        productHandler.getProductInfo();
    }
}