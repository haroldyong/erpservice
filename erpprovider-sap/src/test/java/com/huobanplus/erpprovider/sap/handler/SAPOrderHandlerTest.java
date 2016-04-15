package com.huobanplus.erpprovider.sap.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.sap.SAPTestBase;
import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.eventhandler.erpevent.AddOutStoreEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by elvis on 2016/4/15.
 */

public class SAPOrderHandlerTest extends SAPTestBase {

    @Autowired
    private SAPOrderHandler sapOrderHandler;


    private Order order;
    private ERPUserInfo erpUserInfo;
    private SAPSysData sysData;

    @Before
    public void setUp() throws Exception {
        SAPSysData sysData = new SAPSysData();

        ObjectMapper objectMapper = new ObjectMapper();

    }

    @Test
    public void testPushOrder() throws Exception {



    }



}
