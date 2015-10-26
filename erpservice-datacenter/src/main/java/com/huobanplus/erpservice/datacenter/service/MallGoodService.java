/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service;

import com.huobanplus.erpservice.datacenter.entity.MallGoodEntity;
import org.springframework.data.domain.Page;

/**
 * 商品业务
 * Created by liual on 2015-08-26.
 */
public interface MallGoodService {
    /**
     * 保存
     *
     * @param goodBean
     * @return
     */
    MallGoodEntity save(MallGoodEntity goodBean);

    MallGoodEntity findByBn(String bn);

    Page<MallGoodEntity> findAll(String goodName, String bn, String sysData, int pageIndex, int pageSize);
}
