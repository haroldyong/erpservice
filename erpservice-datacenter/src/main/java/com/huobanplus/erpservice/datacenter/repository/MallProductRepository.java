package com.huobanplus.erpservice.datacenter.repository;

import com.huobanplus.erpservice.datacenter.bean.MallProductBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by liual on 2015-08-27.
 */
public interface MallProductRepository extends JpaRepository<MallProductBean, Long> {
    @Query("select product from MallProductBean product where product.goodBean.bn=?1 and product.skuId=?2")
    MallProductBean findBySku(String bn, String skuId);
}
