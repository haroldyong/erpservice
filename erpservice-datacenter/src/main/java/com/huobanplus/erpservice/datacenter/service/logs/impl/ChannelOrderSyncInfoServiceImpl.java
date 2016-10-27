/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.logs.impl;

import com.huobanplus.erpservice.datacenter.entity.logs.ChannelOrderSyncInfo;
import com.huobanplus.erpservice.datacenter.repository.logs.ChannelOrderSyncInfoRepository;
import com.huobanplus.erpservice.datacenter.service.logs.ChannelOrderSyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiongliu on 2016-10-11.
 */
@Service
public class ChannelOrderSyncInfoServiceImpl implements ChannelOrderSyncInfoService {

    @Autowired
    private ChannelOrderSyncInfoRepository channelOrderSyncInfoRepository;

    @Override
    public Page<ChannelOrderSyncInfo> findAll(int pageIndex, int pageSize, long logSyncId, String orderId) {
        Specification<ChannelOrderSyncInfo> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("channelOrderSyncLog").get("id").as(Long.class), logSyncId));
            if (!StringUtils.isEmpty(orderId)) {
                predicates.add(cb.like(root.get("orderId").as(String.class), orderId));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return channelOrderSyncInfoRepository.findAll(specification, new PageRequest(pageIndex - 1, pageSize));
    }

    @Override
    public ChannelOrderSyncInfo findById(Long id) {
        return channelOrderSyncInfoRepository.findOne(id);
    }

    @Override
    public void batchSave(List<ChannelOrderSyncInfo> channelOrderSyncInfoList) {
        channelOrderSyncInfoRepository.save(channelOrderSyncInfoList);
    }

    @Override
    public ChannelOrderSyncInfo save(ChannelOrderSyncInfo channelOrderSyncInfo) {
        return channelOrderSyncInfoRepository.save(channelOrderSyncInfo);
    }
}
