/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.util;

import com.huobanplus.erpprovider.sap.common.SAPConstant;
import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by elvis on 2016/4/15.
 */
public class ConnectHelper {
    private static final Logger logger = LoggerFactory.getLogger(ConnectHelper.class);

    private static final String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";


    public static JCoFunction getRfcFunction(SAPSysData sysData, ERPUserInfo erpUserInfo, String rfcName) throws Exception {
        JCoDestination destination = connect(sysData, erpUserInfo);
        //如果有数据则进行填充，没有直接返回JCoFunction
        //    if(datamap!=null && datamap.size()>0){
//            JCoFunction function=fillData(destination.getRepository().getFunction(rfcName),datamap);
//            return function;
//        }
        return destination.getRepository().getFunction(rfcName);
    }


//    /**
//     * 填充数据
//     */
//    public static JCoFunction fillData(JCoFunction jcoFunc,Map<String, Object> datamap){
//        JCoTable jCoTable = null;
//        jCoTable = jcoFunc.getTableParameterList().getTable("ZTABLE");
//
//        if(jCoTable==null){
//            return jcoFunc;
//        }
////      填充传入值
//        for(String key:datamap.keySet()){
//            Object value=datamap.get(key);
//            jCoTable.setValue(key, value);
//        }
//        return jcoFunc;
//    }

    public static JCoDestination connect(SAPSysData sysData, ERPUserInfo erpUserInfo) throws JCoException, IOException {
        JCoDestination destination = null;
        String fileBaseName = ABAP_AS_POOLED + erpUserInfo.getCustomerId();
        createDataFile(fileBaseName, "jcoDestination", sysData);
        destination = JCoDestinationManager.getDestination(fileBaseName);
        logger.info("JCO SAP 连接成功");
        return destination;
    }

    /**
     * @param name    创建连接属性文件
     * @param suffix
     * @param sysData
     */
    private static void createDataFile(String name, String suffix, SAPSysData sysData) throws IOException {
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, sysData.getHost());//服务器
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, sysData.getSysNo());        //系统编号
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, sysData.getClient());       //SAP集团
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, sysData.getJcoUser());  //SAP用户名
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, sysData.getJcoPass());     //密码
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, SAPConstant.LANGUAGE);        //登录语言
        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, SAPConstant.JCO_POOL_CAPACITY + "");  //最大连接线程
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, SAPConstant.JCO_PEAK_LIMIT + "");     //最大连接数
        connectProperties.setProperty(DestinationDataProvider.JCO_SAPROUTER, sysData.getSapRouter()); //路由
        File cfg = new File(name + "." + suffix);
        if (cfg.exists()) {
            cfg.deleteOnExit();
        }
        FileOutputStream fos = new FileOutputStream(cfg, false);
        connectProperties.store(fos, "for sap !");
        fos.close();
    }

}
