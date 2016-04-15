/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.hotapi.controller;

import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.service.ERPBaseConfigService;
import com.huobanplus.erpservice.hotapi.common.HotApiConstant;
import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by liual on 2015-10-19.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class OrderApiControllerTest extends SpringWebTest {

    private static final String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";

    static {
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "193.168.9.15");//服务器
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");        //系统编号
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "500");       //SAP集团
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, "DEV3");  //SAP用户名
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "800sap");     //密码
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "zh");        //登录语言
        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");  //最大连接数
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "10");     //最大连接线程
        connectProperties.setProperty(DestinationDataProvider.JCO_SAPROUTER, "/H/202.107.243.45/H/"); //路由

        createDataFile(ABAP_AS_POOLED, "jcoDestination", connectProperties);
    }

    @Autowired
    private ERPBaseConfigService baseConfigService;
    private ERPBaseConfigEntity mockHbBaseConfig;
    private ERPBaseConfigEntity mockSupBaseConfig;
    private String mockAppKey = "fhcpam1w";
    private String mockToken = "975733031175ed399a48702d51012879";
    private int mockCustomerId = 3677;
    private int mockSupplierId = 6340;

    /**
     * 创建SAP接口属性文件。
     *
     * @param name       ABAP管道名称
     * @param suffix     属性文件后缀
     * @param properties 属性文件内容
     */
    private static void createDataFile(String name, String suffix, Properties properties) {



        File cfg = new File(name + "." + suffix);
        if (cfg.exists()) {
            cfg.deleteOnExit();
        }
        try {
            FileOutputStream fos = new FileOutputStream(cfg, false);
            properties.store(fos, "for tests only !");
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
        }
    }

    /**
     * 获取SAP连接
     *
     * @return SAP连接对象
     */
    public static JCoDestination connect() {
        JCoDestination destination = null;
        try {
            destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
        } catch (JCoException e) {
//            log.error("Connect SAP fault, error msg: " + e.toString());
        }
        return destination;
    }

    public static void main(String[] args) {
        System.out.println(111);
        JCoDestination destination = OrderApiControllerTest.connect();
        JCoFunction function = null;
        try {
            function = destination.getRepository().getFunction("Z_SY_WM_MATNR");
            JCoRecord record =function.getImportParameterList();

            record.setValue("I_BUKRS", "sd");

            function.execute(destination);
        } catch (Exception e) {

        }
    }

    @Before
    public void setUp() throws Exception {
        mockHbBaseConfig = new ERPBaseConfigEntity();
        mockHbBaseConfig.setIsOpen(1);
        mockHbBaseConfig.setAppKey(StringUtil.createRandomStr(8));
        mockHbBaseConfig.setToken(StringUtil.createRandomStr32());
        mockHbBaseConfig.setCustomerId(mockCustomerId);
        mockHbBaseConfig.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockHbBaseConfig.setSecretKey("123456");
        mockHbBaseConfig = baseConfigService.save(mockHbBaseConfig);

        mockSupBaseConfig = new ERPBaseConfigEntity();
        mockSupBaseConfig.setIsOpen(1);
        mockSupBaseConfig.setAppKey(StringUtil.createRandomStr(8));
        mockSupBaseConfig.setToken(StringUtil.createRandomStr32());
        mockSupBaseConfig.setCustomerId(mockSupplierId);
        mockSupBaseConfig.setErpUserType(ERPTypeEnum.UserType.HUOBAN_SUPPLIER);
        mockSupBaseConfig.setSecretKey("123456");
        mockSupBaseConfig = baseConfigService.save(mockSupBaseConfig);
    }


    /**
     * 发货通知测试
     *
     * @throws Exception
     */
    @Test
    public void testDeliverInfo() throws Exception {

        Map<String, String> signMap = new TreeMap<>();
        signMap.put("appKey", mockHbBaseConfig.getAppKey());
        signMap.put("token", mockHbBaseConfig.getToken());
        signMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        signMap.put("orderId", "1231232222");
        signMap.put("logiName", "中国邮政");
        signMap.put("logiNo", "12312321");
        signMap.put("remark", "");
        String sign = buildSign(signMap, null, mockHbBaseConfig.getSecretKey());
        mockMvc.perform(post("/hotApi/rest/index/hotDeliveryInfo")
                .param("appKey", "123123")
                .param("token", "123213")
                .param("sign", sign)
                .param("timestamp", String.valueOf(System.currentTimeMillis()))
                .param("orderId", "1231232222")
                .param("logiName", "中国邮政")
                .param("logiNo", "12312321")
                .param("remark", ""))
                .andDo(print());
    }

    @Test
    public void testObtainOrderList() throws Exception {
        Date now = new Date();
        //使用者：伙伴商城
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("appKey", mockHbBaseConfig.getAppKey());
        signMap.put("token", mockHbBaseConfig.getToken());
        signMap.put("timestamp", String.valueOf(now.getTime()));
//        signMap.put("beginUpdateTime", "2016-01-16 10:39:00");
//        signMap.put("endUpdateTime", "2016-01-16 10:40:00");
        signMap.put("pageIndex", "1");
        signMap.put("pageSize", "200");
        signMap.put("eventType", HotApiConstant.OBTAIN_ORDER_LIST);

        String sign = buildSign(signMap, null, mockHbBaseConfig.getSecretKey());
        ResultActions result = mockMvc.perform(post("/erpService/hotApi/rest/order/index")
                .param("pageIndex", "1")
                .param("pageSize", "200")
                .param("eventType", HotApiConstant.OBTAIN_ORDER_LIST)
                .param("sign", sign)
//                .param("beginUpdateTime", "2016-01-16 10:39:00")
//                .param("endUpdateTime", "2016-01-16 10:40:00")
                .param("appKey", mockHbBaseConfig.getAppKey())
                .param("token", mockHbBaseConfig.getToken())
                .param("timestamp", String.valueOf(now.getTime())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(ResultCode.SUCCESS.getResultCode()));
        MockHttpServletResponse response = result.andReturn().getResponse();
        response.setCharacterEncoding("utf-8");
        System.out.println(response.getContentAsString());
        //使用者：供应商
        Map<String, Object> supSignMap = new TreeMap<>();
        supSignMap.put("appKey", mockSupBaseConfig.getAppKey());
        supSignMap.put("token", mockSupBaseConfig.getToken());
        supSignMap.put("timestamp", String.valueOf(now.getTime()));
        supSignMap.put("beginUpdateTime", "2016-01-10 10:39:00");
        supSignMap.put("endUpdateTime", "2016-01-20 10:40:00");
        supSignMap.put("pageIndex", 1);
        supSignMap.put("pageSize", 10);
        supSignMap.put("eventType", HotApiConstant.OBTAIN_ORDER_LIST);

        String supSign = SignBuilder.buildSignIgnoreEmpty(supSignMap, null, mockSupBaseConfig.getSecretKey());
        ResultActions supResult = mockMvc.perform(post("/erpService/hotApi/rest/order/index")
                .param("pageIndex", "1")
                .param("pageSize", "10")
                .param("eventType", HotApiConstant.OBTAIN_ORDER_LIST)
                .param("sign", supSign)
                .param("beginUpdateTime", "2016-01-10 10:39:00")
                .param("endUpdateTime", "2016-01-20 10:40:00")
                .param("appKey", mockSupBaseConfig.getAppKey())
                .param("token", mockSupBaseConfig.getToken())
                .param("timestamp", String.valueOf(now.getTime())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(ResultCode.SUCCESS.getResultCode()));
        MockHttpServletResponse supResponse = supResult.andReturn().getResponse();
        supResponse.setCharacterEncoding("utf-8");
        System.out.println(supResponse.getContentAsString());
    }

    @Test
    public void testObtainOrderDetail() throws Exception {
        Date now = new Date();
        //使用者：伙伴商城
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderId", "2015120726126556");
        signMap.put("timestamp", String.valueOf(now.getTime()));
        signMap.put("appKey", mockHbBaseConfig.getAppKey());
        signMap.put("token", mockHbBaseConfig.getToken());
        signMap.put("eventType", "hbpOrderDetail");
        String sign = buildSign(signMap, null, mockHbBaseConfig.getSecretKey());
        MockHttpServletResponse response = mockMvc.perform(post("/erpService/hotApi/rest/order/index")
                .param("orderId", "2015120726126556")
                .param("timestamp", String.valueOf(now.getTime()))
                .param("appKey", mockHbBaseConfig.getAppKey())
                .param("token", mockHbBaseConfig.getToken())
                .param("eventType", "hbpOrderDetail")
                .param("sign", sign))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(ResultCode.SUCCESS.getResultCode()))
                .andReturn().getResponse();
        response.setCharacterEncoding("utf-8");
        System.out.println(response.getContentAsString());
        //使用者：供应商平台
        Map<String, Object> supSignMap = new TreeMap<>();
        supSignMap.put("orderId", "2015120726126556");
        supSignMap.put("timestamp", String.valueOf(now.getTime()));
        supSignMap.put("appKey", mockSupBaseConfig.getAppKey());
        supSignMap.put("token", mockSupBaseConfig.getToken());
        supSignMap.put("eventType", "hbpOrderDetail");

        String supSign = SignBuilder.buildSignIgnoreEmpty(supSignMap, null, mockSupBaseConfig.getSecretKey());
        MockHttpServletResponse supResponse = mockMvc.perform(post("/erpService/hotApi/rest/order/index")
                .param("orderId", "2015120726126556")
                .param("timestamp", String.valueOf(now.getTime()))
                .param("appKey", mockSupBaseConfig.getAppKey())
                .param("token", mockSupBaseConfig.getToken())
                .param("eventType", "hbpOrderDetail")
                .param("sign", supSign))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(ResultCode.SUCCESS.getResultCode()))
                .andReturn().getResponse();
        response.setCharacterEncoding("utf-8");
        System.out.println(supResponse.getContentAsString());
    }

    @Test
    public void testDeliveryInfo() throws Exception {
        Date now = new Date();
        //分销商
//        Map<String, Object> signMap = new TreeMap<>();
//        signMap.put("timestamp", String.valueOf(now.getTime()));
//        signMap.put("appKey", mockHbBaseConfig.getAppKey());
//        signMap.put("token", mockHbBaseConfig.getToken());
//        signMap.put("orderId", "2016010785229941");
//        signMap.put("logiName", "申通快递");
//        signMap.put("logiNo", "12312331");
//        signMap.put("remark", "大家立刻就开始");
//        signMap.put("deliverItemsStr", "3677HHUVBwYO,1");
//        String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, mockHbBaseConfig.getSecretKey());
//
//        mockMvc.perform(post("/hotApi/rest/order/index/hbpDeliveryInfo")
//                .param("timestamp", String.valueOf(now.getTime()))
//                .param("appKey", mockHbBaseConfig.getAppKey())
//                .param("token", mockHbBaseConfig.getToken())
//                .param("orderId", "2015011719995195")
//                .param("logiName", "申通快递")
//                .param("logiNo", "12312331")
//                .param("remark", "大家立刻就开始")
//                .param("deliverItemsStr", "3677HHUVBwYO,1")
//                .param("sign", sign))
//                .andDo(print());
        //供应商
        Map<String, Object> supSignMap = new TreeMap<>();
        supSignMap.put("timestamp", String.valueOf(now.getTime()));
        supSignMap.put("appKey", mockSupBaseConfig.getAppKey());
        supSignMap.put("token", mockSupBaseConfig.getToken());
        supSignMap.put("orderId", "2016033018932132");
        supSignMap.put("logiName", "申通快递");
        supSignMap.put("logiNo", "12312331");
        supSignMap.put("remark", "大家立刻就开始");
        supSignMap.put("eventType", HotApiConstant.DELIVERY_INFO);
        String supSign = SignBuilder.buildSignIgnoreEmpty(supSignMap, null, mockSupBaseConfig.getSecretKey());

        MockHttpServletResponse supResponse = mockMvc.perform(post("/erpService/hotApi/rest/order/index")
                .param("timestamp", String.valueOf(now.getTime()))
                .param("appKey", mockSupBaseConfig.getAppKey())
                .param("token", mockSupBaseConfig.getToken())
                .param("orderId", "2016033018932132")
                .param("logiName", "申通快递")
                .param("logiNo", "12312331")
                .param("remark", "大家立刻就开始")
                .param("eventType", HotApiConstant.DELIVERY_INFO)
                .param("sign", supSign))
                .andDo(print()).andReturn().getResponse();
        supResponse.setCharacterEncoding(StringUtil.UTF8);
        System.out.println(supResponse.getContentAsString());
    }

    @Test
    public void testReturnInfo() throws Exception {
        Date now = new Date();
        String returnAddr = "mockAddr";
        String returnMobile = "12877876656";
        String returnName = "mockReturnName";
        String remark = "jdjdjdjd记得记得";
        String logiName = "申通快递";
        //供应商
        Map<String, Object> supSignMap = new TreeMap<>();
        supSignMap.put("timestamp", String.valueOf(now.getTime()));
        supSignMap.put("appKey", mockSupBaseConfig.getAppKey());
        supSignMap.put("token", mockSupBaseConfig.getToken());
        supSignMap.put("orderId", "2016011183564599");
        supSignMap.put("logiName", logiName);
        supSignMap.put("logiNo", "12312331");
        supSignMap.put("remark", remark);
        supSignMap.put("eventType", HotApiConstant.RETURN_INFO);
        supSignMap.put("returnAddr", returnAddr);
        supSignMap.put("returnMobile", returnMobile);
        supSignMap.put("returnName", returnName);
        String supSign = SignBuilder.buildSignIgnoreEmpty(supSignMap, null, mockSupBaseConfig.getSecretKey());

        MockHttpServletResponse supResponse = mockMvc.perform(post("/erpService/hotApi/rest/order/index")
                .param("timestamp", String.valueOf(now.getTime()))
                .param("appKey", mockSupBaseConfig.getAppKey())
                .param("token", mockSupBaseConfig.getToken())
                .param("orderId", "2016011183564599")
                .param("logiName", logiName)
                .param("logiNo", "12312331")
                .param("remark", remark)
                .param("eventType", HotApiConstant.RETURN_INFO)
                .param("sign", supSign)
                .param("returnAddr", returnAddr)
                .param("returnMobile", returnMobile)
                .param("returnName", returnName))
                .andReturn().getResponse();
        supResponse.setCharacterEncoding(StringUtil.UTF8);
        System.out.println(supResponse.getContentAsString());
    }

    @Test
    public void testOnlineObtainOrderList() throws Exception {
        Date now = new Date();
        String url = "http://test.erp.51flashmall.com:8081/erpService/hotApi/rest/order/index";
        Map<String, Object> signMap = new TreeMap<>();
        signMap.put("appKey", "v4mx0dc4");
        signMap.put("token", "a648959dae990babc726dc0e591e90f4");
//        signMap.put("timestamp", now.getTime());
        signMap.put("pageSize", 20);
        signMap.put("eventType", HotApiConstant.OBTAIN_ORDER_LIST);
        signMap.put("orderStatus", 0);
        signMap.put("payStatus", 1);
        signMap.put("shipStatus", 0);
        signMap.put("beginUpdateTime", "2015-01-18 10:39:00");
        signMap.put("endUpdateTime", "2016-05-20 10:40:00");


        String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, "123456");
        signMap.put("sign", sign);
        HttpResult httpResult = HttpClientUtil.getInstance().post(url, signMap);
        System.out.println(httpResult.getHttpContent());
        //退货

    }

    @Test
    public void testOnlineDeliver() throws Exception {
        String url = "http://erpservice.olquan.cn/erpService/hotApi/rest/order/index";

        Date now = new Date();
        //发货
        Map<String, Object> deliverSignMap = new TreeMap<>();
        deliverSignMap.put("timestamp", String.valueOf(now.getTime()));
        deliverSignMap.put("appKey", "wmv2dl4l");
        deliverSignMap.put("token", "072a47bfeeaccb2ca7fbe7f265d1eb7d");
        deliverSignMap.put("orderId", "2016012016272417");
        deliverSignMap.put("logiName", "全峰");
        deliverSignMap.put("logiCode", "quanfengkuaidi");
        deliverSignMap.put("logiNo", "123123");
        deliverSignMap.put("eventType", HotApiConstant.DELIVERY_INFO);
        String deliverSign = SignBuilder.buildSignIgnoreEmpty(deliverSignMap, null, "123456");
        deliverSignMap.put("sign", deliverSign);
        HttpResult httpResult1 = HttpClientUtil.getInstance().post(url, deliverSignMap);
        System.out.println(httpResult1.getHttpContent());
    }


}