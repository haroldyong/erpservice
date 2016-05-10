package com.huobanplus.erpprovider.kaola.search;

import lombok.Data;

/**
 * Created by hzbc on 2016/5/9.
 */
@Data
public class BaseSearch {

    /**
     * 时间戳
     */
    protected String timeStamp;

    /**
     * api 版本
     */
    protected String v;

    /**
     *  签名加密方法
     */
    protected String signMethod;

    /**
     *  appKey
     */
    protected String appKey;


}
