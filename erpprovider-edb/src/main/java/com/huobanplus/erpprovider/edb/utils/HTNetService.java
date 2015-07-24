package com.huobanplus.erpprovider.edb.utils;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * http请求实现
 */
public class HTNetService extends BaseService {

    private HttpClient client = null;
    private MultiThreadedHttpConnectionManager connectionManager;

    private static class Holder {
        private static final HTNetService instance = new HTNetService();
    }

    private HTNetService() {
        connectionManager = new MultiThreadedHttpConnectionManager();
        client = new HttpClient();
        initConnection();

    }

    /**
     * 初始化连接
     */
    private void initConnection() {
        connectionManager.setMaxTotalConnections(10);//总的连接数
        connectionManager.setMaxConnectionsPerHost(2);//每个host的最大连接数
        client.setHttpConnectionManager(connectionManager);
    }


    public static final HTNetService getInstance() {
        return Holder.instance;
    }

    /**
     * 封装url
     *
     * @param url
     * @param params
     * @param type   0 get/ 1 post
     */
    private void getUrl(String url, Map params, int type) {

        if (0 == type) {


        } else if (1 == type) {

        }
    }

    /**
     * httpget请求
     *
     * @param url
     * @throws IOException
     */
    public String doGet(String url) throws IOException {
        GetMethod getMethod = null;
        //设置url
        try {
            URI uri = new URI(url);
            getMethod = new GetMethod(url);
            //设置代理，此处无代理
            //设置系统默认回复策略
            getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
            managerParams.setConnectionTimeout(30000);     // 设置连接超时时间(单位毫秒)
            managerParams.setSoTimeout(120000);        //设置读数据超时时间(单位毫秒)
            int statusCode = client.executeMethod(getMethod);// 执行getMethod,返回响应码
            if (HttpStatus.SC_OK == statusCode) {
                //请求成功，读取内容
                return new String(getMethod.getResponseBody(), "UTF-8");
            } else {
                //请求失败，返回null
                return null;
            }
        } catch (URISyntaxException e) {
            logUtil.error(e.getMessage());
            return null;
        } finally {
            getMethod.releaseConnection();
        }

    }

    public String doPost(String url, Map params) throws IOException {
        PostMethod postMethod = new PostMethod(url);
        //设置代理，此处无代理
        NameValuePair[] values = new NameValuePair[params.size()];
        int i = 0;
        for (Object obj : params.keySet()) {
            values[i++] = new NameValuePair((String) obj, (String) params.get(obj));
        }
        postMethod.setRequestBody(values);//将表单的值放入postMethod中
        try {
            HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
            managerParams.setConnectionTimeout(30000);     // 设置连接超时时间(单位毫秒)
            managerParams.setSoTimeout(120000);    // 设置读数据超时时间(单位毫秒)
            int statusCode = client.executeMethod(postMethod);// 执行postMethod
            //HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
            if (HttpStatus.SC_OK == statusCode) {
                //请求成功
                return new String(postMethod.getResponseBody(), "UTF-8");
            } else {
                //请求失败
                return null;
            }
        } catch (IOException e) {
            logUtil.error(e.getMessage());
            return null;
        } finally {
            postMethod.releaseConnection();
        }
    }
}
