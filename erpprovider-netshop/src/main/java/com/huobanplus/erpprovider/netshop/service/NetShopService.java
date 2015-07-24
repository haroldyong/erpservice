package com.huobanplus.erpprovider.netshop.service;

import com.huobanplus.erpprovider.netshop.dao.NetShopDao;
import com.huobanplus.erpservice.event.model.EventResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class NetShopService {

    @Resource
    private NetShopDao netShopDao;
    //---订单信息
    //订单查询
     public EventResult obtainOrderList()
     {
         return null;
     }

    //订单详细
    public EventResult obtainOrderDetail()
    {
        return null;
    }

    //---发货通知
    //发货通知
    public EventResult deliveryNotice()
    {
        return null;
    }

    //---商品
    //商品查询
    public EventResult obtainGoods()
    {
        return null;
    }

    //---库存同步接口
    public EventResult syncInventory()
    {
        return null;
    }
}
