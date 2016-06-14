package com.huobanplus.erpprovider.dtw.common;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Data
public class DtwSysData {

    /**
     *  请求地址
     */
    private String requestUrl;

    /**
     *
     */
    private String passKey;

    /**
     * 电商企业编码(电商企业在跨境平台备案编码)
     */
    private String eCommerceCode;

    /**
     * 电商企业名称
     */
    private String eCommerceName;

}
