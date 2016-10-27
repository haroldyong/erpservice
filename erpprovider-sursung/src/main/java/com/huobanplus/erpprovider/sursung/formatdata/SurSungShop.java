/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-10-15.
 */
@Data
public class SurSungShop {

    /**
     * 店铺编号
     */
    @JSONField(name = "shop_id")
    private int shopId;

    /**
     * 店铺名称
     */
    @JSONField(name = "shop_name")
    private String shopName;

    /**
     * 站点
     */
    @JSONField(name = "shop_site")
    private String shopSite;

    /**
     * 店铺昵称
     */
    @JSONField(name = "nick")
    private String nick;

    /**
     * 创建时间
     */
    @JSONField(name = "created")
    private String createTime;

}
