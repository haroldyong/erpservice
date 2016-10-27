/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.common.httputil;

import com.huobanplus.erpservice.common.util.StringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wuxiongliu on 2016-10-10.
 */
public class HttpClientUtil2 {
    private static HttpClientUtil2 httpClientUtil = new HttpClientUtil2();
    private CloseableHttpClient httpClient;

    private HttpClientUtil2() {
    }

    public static HttpClientUtil2 getInstance() {
        return httpClientUtil;
    }

    public HttpResult post(String url, String requestData) {
        String msg = null;
        CloseableHttpResponse response = null;
        try {
            StringEntity stringEntity = new StringEntity(requestData, "utf-8");
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpResult httpResult = new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
            EntityUtils.consume(response.getEntity());
            return httpResult;
        } catch (IOException e) {
            msg = e.getMessage();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

            } catch (IOException e) {
            }
        }
        return new HttpResult(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public HttpResult post(String url, Map<String, Object> requestMap) {
        String msg = null;
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        CloseableHttpResponse response = null;
        try {
            requestMap.forEach((key, value) -> {
                if (value != null) {
                    nameValuePairs.add(new BasicNameValuePair(key, String.valueOf(value)));
                }
            });
            HttpPost httpPost = new HttpPost(url);
            HttpEntity httpEntity = new UrlEncodedFormEntity(nameValuePairs, StringUtil.UTF8);

            httpPost.setEntity(httpEntity);
            response = httpClient.execute(httpPost);
            HttpResult httpResult = new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
            EntityUtils.consume(response.getEntity());
            return httpResult;
        } catch (IOException e) {
            msg = e.getMessage();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

            } catch (IOException e) {
            }
        }
        return new HttpResult(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public void initHttpClient() {
        httpClient = HttpClients.createDefault();
    }

    public void close() throws IOException {
        if (httpClient != null) {
            httpClient.close();
        }
    }
}
