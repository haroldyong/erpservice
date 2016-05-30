package com.huobanplus.erpprovider.kjyg.common;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Data
public class KjygSysData {

    /**
     *  请求地址
     */
    private String requestUrl;

    /**
     *  client key
     */
    private String clientKey;

    /**
     *  销售商代码
     */
    private String clientCode;

    /**
     * 伙伴商城在跨境易购的备案码
     */
    private String website="0001";
}
