package com.huobanplus.erpprovider.gy;

import com.huobanplus.erpprovider.gy.config.GYTestConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wuxiongliu on 2016/6/14.
 */

@ActiveProfiles("test")
@ContextConfiguration(classes = {GYTestConfig.class})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class GYTestBase {
}
