package com.huobanplus.erpprovider.lgj.handler;

import com.huobanplus.erpprovider.lgj.LGJTestBase;
import com.huobanplus.erpprovider.lgj.common.LGJSysData;
import com.huobanplus.erpprovider.lgj.handler.LGJBaseHandler;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

/**
 * Created by admin on 2016/5/3.
 */
public class LGJBaseHandlerTest extends LGJTestBase {


    private LGJSysData mockSysData;


    @Autowired
    private LGJBaseHandler baseHandler;
    @Before
    public void setUp(){
        mockSysData = new LGJSysData();
        mockSysData.setHost("http://www.liguanjia.com/index.php/api");
        mockSysData.setUsername("ligoukeji");
        mockSysData.setPassword("111111");
        mockSysData.setApiName("ligoukeji");
        mockSysData.setApiSecret("111111");
    }

    @Test
    public void TestGetSecretStr() throws UnsupportedEncodingException {
        System.out.println(" secretKey :"+baseHandler.getSecretStr(mockSysData));
    }

    @Test
    public void TestGetToken() throws UnsupportedEncodingException {
        System.out.println(" Token :"+baseHandler.getToken(mockSysData));
    }
}
