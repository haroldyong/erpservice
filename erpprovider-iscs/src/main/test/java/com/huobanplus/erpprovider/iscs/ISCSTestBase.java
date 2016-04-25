package com.huobanplus.erpprovider.iscs;


import com.huobanplus.erpprovider.iscs.config.ISCSTestConfig;
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
 * Created by wuxiongliu on 2016/4/25.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {ISCSTestConfig.class})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class ISCSTestBase {

    protected ERPBaseConfigEntity mockErpBaseConfig;

    protected ERPDetailConfigEntity mockErpDetailConfig;
    @Autowired
    private ERPBaseConfigService erpBaseConfigService;

    @Before
    public void mockData() {

    }

}
