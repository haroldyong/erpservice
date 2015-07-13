package com.huobanplus.erpservice.thirdparty.service;

import com.huobanplus.erpservice.thirdparty.bean.EDBBaseBean;
import com.huobanplus.erpservice.thirdparty.bean.ProductClass;

import java.util.Map;

/**
 * Created by Administrator on 2015/7/13.
 */
public interface IProduct extends IEvent {

    /**
     * 更新产品明细信息
     * @param params
     * @return
     */
    EDBBaseBean edbProductDetailInfoUpdate(Map params);

    /**
     * 获取产品分类信息
     * @param params
     * @return
     */
    ProductClass edbProductClassGet(Map params);


}
