package com.huobanplus.erpprovider.netshop.service;

import com.huobanplus.erpservice.event.model.*;


/**
 * 类描述：网店管家操作接口
 */
public interface NetShopService {

    //---订单信息

    /**
     * 提交新建订单信息
     * @param authBean 授权安全验证
     * @param orderInfo 订单信息
     * @return 返回提交新订单结果（成功、失败）
     */
    public Monitor<OrderInfo> commitOrderInfo(AuthBean authBean, OrderInfo orderInfo);
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
    public Monitor<EventResult> deliveryNotice(AuthBean authBean, DeliveryInfo deliveryInfo);

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
    public Monitor<EventResult> syncInventory(AuthBean authBean, InventoryInfo inventoryInfo);

    /**
     * 修改订单状态
     * @param authBean 授权安全验证
     * @param orderInfo 库存查询参数
     * @return 返回修改订单结果
     */
    public Monitor<EventResult> modifyOrderInfo(AuthBean authBean, OrderInfo orderInfo);

    /**
     * 处理异常产生的错误，将事件异常提交给远程访问者
     * @param failedBean 失败事件信息
     * @return 返回提交失败事件的结果（成功、失败）
     */
    public Monitor<EventResult> notifyFailedEvent(FailedBean failedBean);
}
