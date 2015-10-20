/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service;

import com.huobanplus.erpservice.datacenter.bean.MallProductOutBean;

/**
 * 出库单中的产品业务
 * Created by allan on 2015/8/11.
 */
public interface MallProductOutService {
    /**
     * 保存
     *
     * @param productOutBean
     * @return
     */
    MallProductOutBean save(MallProductOutBean productOutBean);

    /**
     * 得到实体
     *
     * @param id
     * @return
     */
    MallProductOutBean findById(long id);
}
