/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.common.httputil;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;

/**
 * HTTP工具类
 */
public class HttpUtil {

    private HttpClient client;

    private static class Holder {
        private static final HttpUtil instance = new HttpUtil();
    }

    public static final HttpUtil getInstance() {
        return Holder.instance;
    }

    private HttpUtil() {

    }

    /**
     * httpget请求
     *
     * @param url
     * @throws IOException
     */
    public String doGet(String url) throws IOException {
        GetMethod getMethod = null;
        client = new HttpClient();
        //设置url
        try {
            URI uri = new URI(url);
            getMethod = new GetMethod(url);
            //设置代理，此处无代理
            //设置系统默认回复策略
           /* getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
            managerParams.setConnectionTimeout(30000);     // 设置连接超时时间(单位毫秒)
            managerParams.setSoTimeout(120000);        //设置读数据超时时间(单位毫秒)*/
            int statusCode = client.executeMethod(getMethod);// 执行getMethod,返回响应码
            if (HttpStatus.SC_OK == statusCode) {
                //请求成功，读取内容
                return new String(getMethod.getResponseBody(), "UTF-8");
            } else {
                String msg = new String(getMethod.getResponseBody(), "UTF-8");
                //请求失败，返回null
                return null;
            }
        } catch (URISyntaxException e) {
            return null;
        } finally {
            getMethod.releaseConnection();
        }

    }

    /**
     * get请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return
     */
    public String doGet(String url, Map<String, String> params) throws IOException {
        String finalUrl = url;
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();

            if (index == 0) {
                finalUrl += "?" + entry.getKey() + "=" + entry.getValue();
            } else {
                finalUrl += "&" + entry.getKey() + "=" + entry.getValue();
            }
            index++;
        }
        GetMethod getMethod = null;
        client = new HttpClient();
        try {
            URI uri = new URI(finalUrl);
            getMethod = new GetMethod(finalUrl);
            //设置代理，此处无代理
            //设置系统默认回复策略
           /* getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
            managerParams.setConnectionTimeout(30000);     // 设置连接超时时间(单位毫秒)
            managerParams.setSoTimeout(120000);        //设置读数据超时时间(单位毫秒)*/
            int statusCode = client.executeMethod(getMethod);// 执行getMethod,返回响应码
            if (HttpStatus.SC_OK == statusCode) {
                //请求成功，读取内容
                return new String(getMethod.getResponseBody(), "UTF-8");
            } else {
//                String msg = new String(getMethod.getResponseBody(), "UTF-8");
                //请求失败，返回null
                return null;
            }
        } catch (URISyntaxException e) {
            return null;
        } finally {
            getMethod.releaseConnection();
        }
    }

    public String doPost(String url, Map params) throws IOException {
        PostMethod postMethod = new PostMethod(url);
        client = new HttpClient();
        //设置代理，此处无代理
        NameValuePair[] values = new NameValuePair[params.size()];
        int i = 0;
        for (Object obj : params.keySet()) {
            values[i++] = new NameValuePair((String) obj, (String) params.get(obj));
        }
        postMethod.setRequestBody(values);//将表单的值放入postMethod中
        try {
           /* HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
            managerParams.setConnectionTimeout(30000);     // 设置连接超时时间(单位毫秒)
            managerParams.setSoTimeout(120000);    // 设置读数据超时时间(单位毫秒)*/
            int statusCode = client.executeMethod(postMethod);// 执行postMethod
            //HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
            if (HttpStatus.SC_OK == statusCode) {
                //请求成功
                return new String(postMethod.getResponseBody(), "UTF-8");
            } else {
                //请求失败
                String msg = new String(postMethod.getResponseBody(), "UTF-8");
                return null;
            }
        } catch (IOException e) {
            return null;
        } finally {
            postMethod.releaseConnection();
        }
    }
}
