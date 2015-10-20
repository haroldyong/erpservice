/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.datacenter.bean.MallGoodBean;
import com.huobanplus.erpservice.datacenter.repository.MallGoodRepository;
import com.huobanplus.erpservice.datacenter.service.MallGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liual on 2015-08-26.
 */
@Service
@Transactional
public class MallGoodServiceImpl implements MallGoodService {
    @Autowired
    private MallGoodRepository goodRepository;

    @Override
    public MallGoodBean save(MallGoodBean goodBean) {
        return goodRepository.save(goodBean);
    }

    @Override
    public MallGoodBean findByBn(String bn) {
        return goodRepository.findByBn(bn);
    }

    @Override
    public Page<MallGoodBean> findAll(String goodName, String bn, String sysData, int pageIndex, int pageSize) {
        Specification<MallGoodBean> specification = (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(goodName)) {
                list.add(cb.like(root.get("goodName").as(String.class), "%" + goodName + "%"));
            }
            if (!StringUtils.isEmpty(bn)) {
                list.add(cb.like(root.get("bn").as(String.class), "%" + bn + "%"));
            }
            list.add(cb.equal(root.get("erpSysData").as(String.class), sysData));
            return cb.and(list.toArray(new Predicate[list.size()]));
        };
        return goodRepository.findAll(specification, new PageRequest(pageIndex - 1, pageSize));
    }
}
