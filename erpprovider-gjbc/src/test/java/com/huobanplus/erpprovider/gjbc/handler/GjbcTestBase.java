package com.huobanplus.erpprovider.gjbc.handler;

import com.huobanplus.erpprovider.gjbc.config.GjbcTestConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hxh on 2017-06-29.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {GjbcTestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class GjbcTestBase {
}
