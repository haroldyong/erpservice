package com.huobanplus.erpprovider.lgj;

import com.huobanplus.erpprovider.lgj.config.LGJTestConfig;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.service.ERPBaseConfigService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by elvis on 2016/4/15.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {LGJTestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LGJTestBase {

    protected ERPBaseConfigEntity mockErpBaseConfig;

    protected ERPDetailConfigEntity mockErpDetailConfig;
    @Autowired
    private ERPBaseConfigService erpBaseConfigService;

    @Before
    public void mockData() {

    }

}
