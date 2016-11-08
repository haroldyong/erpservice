/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.wangdian.common;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Data
public class WangDianSysData {

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * ERP为卖家分配的帐号
     */
    private String sellerId;

    /**
     * ERP为外部接口分配的帐号
     */
    private String interfaceId;

    /**
     * ERP给外部接口的授权字段
     */
    private String appKey;

    /**
     * 仓库编码
     */
    private String warehouseNo;

    /**
     * 店铺名称
     */
    private String shopName;

}
