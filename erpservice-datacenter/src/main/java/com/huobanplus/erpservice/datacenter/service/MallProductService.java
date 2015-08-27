package com.huobanplus.erpservice.datacenter.service;

import com.huobanplus.erpservice.datacenter.bean.MallProductBean;

/**
 * Created by liual on 2015-08-27.
 */
public interface MallProductService {
    MallProductBean findBySku(String bn, String skuId);
}
