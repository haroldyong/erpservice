/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service;

import com.huobanplus.erpservice.datacenter.bean.MallOutStoreBean;

/**
 * 出库业务
 * Created by allan on 2015/8/11.
 */
public interface MallOutStoreService {
    /**
     * 保存出库单实体
     *
     * @param outStoreBean
     * @return
     */
    MallOutStoreBean save(MallOutStoreBean outStoreBean);

    /**
     * 根据出库单号得到出库单实体
     *
     * @param storageNo
     * @return
     */
    MallOutStoreBean findByNo(String storageNo);
}
