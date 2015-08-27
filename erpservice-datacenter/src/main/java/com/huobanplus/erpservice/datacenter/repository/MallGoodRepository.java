package com.huobanplus.erpservice.datacenter.repository;

import com.huobanplus.erpservice.datacenter.bean.MallGoodBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

/**
 * Created by liual on 2015-08-26.
 */
public interface MallGoodRepository extends JpaRepository<MallGoodBean, Long>, JpaSpecificationExecutor {
    MallGoodBean findByBn(String bn);
}
