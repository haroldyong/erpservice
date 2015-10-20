/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.datacenter.bean.MallInventoryBean;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.repository.MallInventoryRepository;
import com.huobanplus.erpservice.datacenter.service.MallInventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>类描述：<p/>
 * 库存信息业务实现
 */
@Service
public class MallInventoryServiceImpl implements MallInventoryService {


    @Resource
    private MallInventoryRepository mallInventoryRepository;

    @Override
    public MallInventoryBean save(MallInventoryBean mallInventoryBean) {
        return mallInventoryRepository.save(mallInventoryBean);
    }

    @Override
    public MallInventoryBean findInventoryById(String inventoryId) {
        return mallInventoryRepository.findOne(inventoryId);
    }

    @Override
    public Page<MallInventoryBean> findAll(Integer storageStatus, String inventoryNo, int pageIndex, int pageSize) {
        Specification<MallInventoryBean> specification = new Specification<MallInventoryBean>() {
            @Override
            public Predicate toPredicate(Root<MallInventoryBean> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (storageStatus != null) {
                    list.add(cb.equal(root.get("storageStatus").as(Integer.class), storageStatus));
                }
                if (!StringUtils.isEmpty(inventoryNo)) {
                    list.add(cb.equal(root.get("inventoryNo").as(String.class), inventoryNo));
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return mallInventoryRepository.findAll(specification, new PageRequest(pageIndex - 1, pageSize));
    }
}
