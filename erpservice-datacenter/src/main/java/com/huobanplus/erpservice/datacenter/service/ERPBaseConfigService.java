/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service;

import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;

/**
 * Created by liual on 2015-10-28.
 */
public interface ERPBaseConfigService {
    /**
     * 保存
     *
     * @param erpBaseConfig
     */
    ERPBaseConfigEntity save(ERPBaseConfigEntity erpBaseConfig);

    /**
     * 根据appKey和token得到商户id
     *
     * @param appKey
     * @param token
     * @return
     */
    ERPBaseConfigEntity findByAppKeyAndToken(String appKey, String token);

    /**
     * 修改token
     *
     * @param customerId
     */
    void updateToken(int customerId);

    /**
     * 修改erp的开通状态
     *
     * @param customerId
     * @param isOpen     0表示关闭，1表示开通
     */
    void updateOpenStatus(int customerId, int isOpen);

    /**
     * 根据customerId得到基本信息
     *
     * @param customerId
     * @return
     */
    ERPBaseConfigEntity findByCustomerId(int customerId);
}
