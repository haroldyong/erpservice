package com.huobanplus.erpservice.proxy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.common.util.DxDESCipher;
import com.huobanplus.erpservice.commons.config.ApplicationConfig;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.bean.MallOrderItem;
import com.huobanplus.erpservice.datacenter.bean.MallOutStoreBean;
import com.huobanplus.erpservice.datacenter.bean.MallProductOutBean;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.datacenter.service.MallOutStoreService;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by allan on 2015/8/11.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {ApplicationConfig.class, WebConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HotStorageControllerTest extends SpringWebTest {

    private ERPInfo mockERP;

    private MallOrderBean mockOrder;

    private MallOutStoreBean mockOutStore;
    @Autowired
    private MallOrderService orderService;
    @Autowired
    private MallOutStoreService outStoreService;

    @Before
    public void setUp() throws Exception {
        mockERP = new ERPInfo();
        mockERP.setName("edb");
        mockERP.setType("mockType");
        mockERP.setValidation("mockValidation");
        EDBSysData sysData = new EDBSysData();
        sysData.setRequestUrl(Constant.REQUEST_URI);
        sysData.setDbHost(Constant.DB_HOST);
        sysData.setAppKey(Constant.APP_KEY);
        sysData.setAppSecret(Constant.APP_SECRET);
        sysData.setToken(Constant.TOKEN);
        sysData.setFormat(Constant.FORMAT);
        sysData.setV(Constant.V);
        sysData.setSlencry(Constant.SLENCRY);
        sysData.setIp(Constant.IP);
        ObjectMapper objectMapper = new ObjectMapper();

        mockERP.setSysDataJson(objectMapper.writeValueAsString(sysData));

        mockOrder = new MallOrderBean();
        mockOrder.setOutTid("123212322334");
        mockOrder.setShopId("12");
        mockOrder.setStorageId("1");
        mockOrder.setExpress("dddd");
        mockOrder.setTidTime(new Date());
        mockOrder.setOrderId("123212322334");
        mockOrder.setTid("123212322334");

        MallOrderItem orderItem = new MallOrderItem();
        orderItem.setBarcode("12312344");
        orderItem.setProName("方便面");
        orderItem.setSpecification("大碗");
        orderItem.setOutTid("123212322334");
        orderItem.setProNum(1);
        mockOrder.setOrderItems(Arrays.asList(orderItem));

        mockOrder = orderService.save(mockOrder);

        mockOutStore = new MallOutStoreBean();
        mockOutStore.setOutStorageNo("12312321123");
        mockOutStore.setOutStorageType(120);
        mockOutStore.setStorageNo("1");
        mockOutStore.setSupplierNo("10");
        mockOutStore.setFreight("10");
        mockOutStore.setRelateOrderNo(mockOrder.getOrderId());

        MallProductOutBean productOutBean = new MallProductOutBean();
        productOutBean.setProductItemNo("5540");
        productOutBean.setLocationNo("1");
        productOutBean.setStorageNo("1");
        productOutBean.setOutStorageNum(1);
        productOutBean.setOutStoragePrice(1);
        productOutBean.setBatch("1");
        productOutBean.setBarCode("123123222");
        mockOutStore.setMallProductOutBeans(Arrays.asList(productOutBean));
        mockOutStore = outStoreService.save(mockOutStore);
    }

    @Test
    public void testOutStoreAdd() throws Exception {
        MallOutStoreBean outStoreBean = new MallOutStoreBean();
        outStoreBean.setOutStorageNo("1231232111");
        outStoreBean.setOutStorageType(120);
        outStoreBean.setStorageNo("1");
        outStoreBean.setSupplierNo("10");
        outStoreBean.setFreight("10");
        outStoreBean.setRelateOrderNo(mockOrder.getOrderId());

        MallProductOutBean productOutBean = new MallProductOutBean();
        productOutBean.setProductItemNo("5540");
        productOutBean.setLocationNo("1");
        productOutBean.setStorageNo("1");
        productOutBean.setOutStorageNum(1);
        productOutBean.setOutStoragePrice(1);
        productOutBean.setBatch("1");
        productOutBean.setBarCode("123123222");
        outStoreBean.setMallProductOutBeans(Arrays.asList(productOutBean));

        String outStoreJson = new ObjectMapper().writeValueAsString(outStoreBean);

        mockERP.setTimestamp(String.valueOf(new Date().getTime()));

        Map<String, String> signMap = new TreeMap<>();
        signMap.put("outStoreJson", outStoreJson);
        signMap.put("name", mockERP.getName());
        signMap.put("type", mockERP.getType());
        signMap.put("validation", mockERP.getValidation());
        signMap.put("sysDataJson", mockERP.getSysDataJson());
        signMap.put("timestamp", mockERP.getTimestamp());
        String sign = buildSign(signMap, signKey, null);

        mockMvc.perform(post("/hotClientStorageApi/outStoreAdd")
                .param("outStoreJson", outStoreJson)
                .param("name", DxDESCipher.encrypt(mockERP.getName()))
                .param("type", DxDESCipher.encrypt(mockERP.getType()))
                .param("validation", DxDESCipher.encrypt(mockERP.getValidation()))
                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson()))
                .param("timestamp", mockERP.getTimestamp())
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testOutStoreConfirm() throws Exception {
        MallOutStoreBean outStoreBean = new MallOutStoreBean();
        outStoreBean.setOutStorageNo(mockOutStore.getOutStorageNo());
        outStoreBean.setFreight("111");
        outStoreBean.setFreightAvgWay("123");
        String outStoreJson = new ObjectMapper().writeValueAsString(outStoreBean);

        mockERP.setTimestamp(String.valueOf(new Date().getTime()));

        Map<String, String> signMap = new TreeMap<>();
        signMap.put("outStoreJson", outStoreJson);
        signMap.put("name", mockERP.getName());
        signMap.put("type", mockERP.getType());
        signMap.put("validation", mockERP.getValidation());
        signMap.put("sysDataJson", mockERP.getSysDataJson());
        signMap.put("timestamp", mockERP.getTimestamp());
        String sign = buildSign(signMap, signKey, null);

        mockMvc.perform(post("/hotClientStorageApi/outStoreConfirm")
                .param("outStoreJson", outStoreJson)
                .param("name", DxDESCipher.encrypt(mockERP.getName()))
                .param("type", DxDESCipher.encrypt(mockERP.getType()))
                .param("validation", DxDESCipher.encrypt(mockERP.getValidation()))
                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson()))
                .param("timestamp", mockERP.getTimestamp())
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testOutStoreWriteBack() throws Exception {
        MallProductOutBean productOutBean = new MallProductOutBean();
        productOutBean.setOutStoreBean(mockOutStore);
        productOutBean.setOutStorageNum(1);
        productOutBean.setBarCode(mockOutStore.getMallProductOutBeans().get(0).getBarCode());
        productOutBean.setProductOutId(mockOutStore.getMallProductOutBeans().get(0).getProductOutId());
        String proOutJson = new ObjectMapper().writeValueAsString(productOutBean);

        mockERP.setTimestamp(String.valueOf(new Date().getTime()));

        Map<String, String> signMap = new TreeMap<>();
        signMap.put("proOutJson", proOutJson);
        signMap.put("name", mockERP.getName());
        signMap.put("type", mockERP.getType());
        signMap.put("validation", mockERP.getValidation());
        signMap.put("sysDataJson", mockERP.getSysDataJson());
        signMap.put("timestamp", mockERP.getTimestamp());
        String sign = buildSign(signMap, signKey, null);

        mockMvc.perform(post("/hotClientStorageApi/outStoreWriteBack")
                .param("proOutJson", proOutJson)
                .param("name", DxDESCipher.encrypt(mockERP.getName()))
                .param("type", DxDESCipher.encrypt(mockERP.getType()))
                .param("validation", DxDESCipher.encrypt(mockERP.getValidation()))
                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson()))
                .param("timestamp", mockERP.getTimestamp())
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk());
    }
}