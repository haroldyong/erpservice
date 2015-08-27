package com.huobanplus.erpservice.datacenter.service;

import com.huobanplus.erpservice.datacenter.bean.MallGoodBean;
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
    MallGoodBean save(MallGoodBean goodBean);

    MallGoodBean findByBn(String bn);

    Page<MallGoodBean> findAll(String goodName, String bn, String sysData, int pageIndex, int pageSize);
}
