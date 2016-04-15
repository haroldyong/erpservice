package com.huobanplus.erpprovider.sap;

import com.huobanplus.erpprovider.sap.config.SAPTestConfig;
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
@ContextConfiguration(classes = {SAPTestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SAPTestBase {

    protected ERPBaseConfigEntity mockErpBaseConfig;

    protected ERPDetailConfigEntity mockErpDetailConfig;
    @Autowired
    private ERPBaseConfigService erpBaseConfigService;

    @Before
    public void mockData() {

    }

}
