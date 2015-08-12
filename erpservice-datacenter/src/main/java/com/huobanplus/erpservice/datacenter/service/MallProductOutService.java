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
