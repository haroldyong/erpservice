/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

import java.io.IOException;

/**
 * 货品相关
 * Created by allan on 2015/7/28.
 */
public interface EDBProductHandler {
    /**
     * 获取货品库存信息
     *
     * @param sysData erp信息
     * @return 返回结果
     * @throws IOException IO 异常
     */
    EventResult getProInventoryInfo(EDBSysData sysData, String productNo) throws IOException;
}
