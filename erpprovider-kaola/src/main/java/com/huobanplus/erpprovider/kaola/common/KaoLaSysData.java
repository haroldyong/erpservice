package com.huobanplus.erpprovider.kaola.common;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/5/9.
 */
@Data
public class KaoLaSysData {

    /**
     * 考拉服务器地址
     */
    private String host;

    /**
     * 分配给应用的AppKey ，创建应用时可获得
     */
    private String appKey;

    /**
     * 用于标识接入考拉的客户接口密钥，加密签名用，不需要提交
     */
    private String appSecret;

    /**
     * api 版本
     */
    private String v = "1.0";


}
