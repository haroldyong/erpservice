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
