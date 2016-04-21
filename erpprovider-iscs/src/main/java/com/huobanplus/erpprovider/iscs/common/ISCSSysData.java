/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.iscs.common;

import lombok.Data;

/**
 * Created by allan on 4/19/16.
 */
@Data
public class ISCSSysData {
    /**
     * 网仓服务器地址
     */
    private String host;
    /**
     * 用于标识接入网仓的客户id
     */
    private String appKey;
    /**
     * 用于标识接入网仓的客户接口密钥，加密签名用，不需要提交
     */
    private String appSecret;
    /**
     * 货主id
     */
    private int ownerId;
    /**
     * 店铺id
     */
    private int shopId;
    /**
     * 仓库id
     */
    private int stockId;
    /**
     * 开始同步时间（发货时间）
     */
    private String beginTime;
}
