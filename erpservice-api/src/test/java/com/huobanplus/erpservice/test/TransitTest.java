package com.huobanplus.erpservice.test;

import com.huobanplus.erpservice.commons.config.ApplicationConfig;
import com.huobanplus.erpservice.commons.config.WebConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, ApplicationConfig.class})
@Transactional
public class TransitTest {


}
