package com.huobanplus.erpprovider.netshop.service;


import com.huobanplus.erpprovider.netshop.dao.NetShopDao;
import com.huobanplus.erpservice.event.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Order;

/**
 * 类描述：网店管家操作接口
 */
public interface NetShopService {

    //---订单信息

    /**
     * 获取订单列表
     * @param authBean 授权安全验证
     * @param orderInfo 查询条件
     * @return 返回订单信息列表
     */
     public Monitor<OrderInfo> obtainOrderList(AuthBean authBean, OrderInfo orderInfo);

    /**
     * 订单详细
     * @param authBean 授权安全验证
     * @param orderId 订单编号
     * @return 返回订单详细信息
     */
    public Monitor<OrderInfo> obtainOrderDetail(AuthBean authBean, String orderId);

    //---发货

    /**
     * 发货通知
     * @param authBean 授权安全验证
     * @param deliveryInfo 发货物流信息
     * @return 返回发货结果（1、发货成功、2、发货失败）
     */
    public Monitor<BaseResult> deliveryNotice(AuthBean authBean, DeliveryInfo deliveryInfo);

    //---商品

    /**
     * 商品查询
     * @param authBean 授权安全验证
     * @param productInfo 商品查询参数
     * @return 返回商品信息
     */
    public Monitor<ProductInfo> obtainGoods(AuthBean authBean, ProductInfo productInfo);

    //---库存同步接口

    /**
     * 库存同步接口
     * @param authBean 授权安全验证
     * @param inventoryInfo 库存查询参数
     * @return 返回同步结果
     */
    public Monitor<BaseResult> syncInventory(AuthBean authBean, InventoryInfo inventoryInfo);
}
