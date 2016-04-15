package com.huobanplus.erpprovider.sap.util;

import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.ext.DestinationDataProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by wuxiongliu on 2016/4/14.
 */
public class SAPConnector {


    private JCoDestination destination;

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

    public SAPConnector(SAPSysData sapSysData) throws JCoException {

        destination =  JCoDestinationManager.getDestination(ABAP_AS_POOLED);
    }

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
        JCoDestination destination = connect();
        JCoFunction function = null;
        try {
            function = destination.getRepository().getFunction("Z_SY_WM_MATNR");

            function.execute(destination);



//            function.getImportParameterList().setValue("name","wuxiongliu");
//            function.execute(destination);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
