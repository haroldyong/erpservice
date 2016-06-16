package com.huobanplus.erpprovider.dtw;

import com.huobanplus.erpprovider.dtw.config.DtwTestConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wuxiongliu on 2016/6/16.
 */

@ActiveProfiles("test")
@ContextConfiguration(classes = {DtwTestConfig.class})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class DtwTestBase {
}
