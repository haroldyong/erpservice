package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.datacenter.bean.MallOutStoreBean;
import com.huobanplus.erpservice.datacenter.repository.MallOutStoreRepository;
import com.huobanplus.erpservice.datacenter.service.MallOutStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by allan on 2015/8/11.
 */
@Service
public class MallOutStoreServiceImpl implements MallOutStoreService {
    @Autowired
    private MallOutStoreRepository outStoreRepository;

    @Override
    public MallOutStoreBean save(MallOutStoreBean outStoreBean) {
        return outStoreRepository.save(outStoreBean);
    }

    @Override
    public MallOutStoreBean findByNo(String storageNo) {
        return outStoreRepository.findOne(storageNo);
    }
}
