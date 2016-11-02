package com.huobanplus.erpprovider.wangdian;

import com.huobanplus.erpprovider.wangdian.config.WangDianTestConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wuxiongliu on 2016-11-02.
 */

@ActiveProfiles("test")
@ContextConfiguration(classes = {WangDianTestConfig.class})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class WangDianTestBase {
}
