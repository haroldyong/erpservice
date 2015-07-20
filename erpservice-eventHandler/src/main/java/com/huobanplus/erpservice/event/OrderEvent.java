package com.huobanplus.erpservice.event;

import com.huobanplus.erpservice.event.model.OrderInfo;
import com.huobanplus.erpservice.event.model.ProductClass;
import com.huobanplus.erpservice.event.model.ProductInfo;
import com.huobanplus.erpservice.event.model.ProudctInStock;

/**
 * 订单处理事件
 * Created by allan on 2015/7/13.
 */
public class OrderEvent extends ERPEvent {

    /**
     * 更新订单状态
     * @return
     */
    private OrderInfo edbTradeImportStatusUpdate(){return  new OrderInfo();}

    /**
     * 订单作废
     * @return
     */
    private OrderInfo edbTradeCancel(){return  new OrderInfo();}

    /**
     * 根据条件得到外部订单信息
     * @return
     */
    private OrderInfo edbOutTradeGet(){return new OrderInfo();}

    /**
     * 订单批量发货
     * @return
     */
    private OrderInfo edbTradeDeliveryBatch(){return  new OrderInfo();}

    /**
     * 未确认订单中添加赠品
     * @return
     */
    private OrderInfo edbTradeGiftAdd(){return new OrderInfo();}

    /**
     * 订单业务状态更新
     * @return
     */
    private OrderInfo edbTradeUpdate(){return new OrderInfo();}

    /**
     * 批量更改订单信息中的内部便签
     * @return
     */
    private OrderInfo edbInNoteUpdate(){return new OrderInfo();}

    /**
     * 获取订单信息
     * @return
     */
    private OrderInfo edbTradeGet(){return  new OrderInfo();}

    /**
     * 写入订单
     * @return
     */
    private OrderInfo edbTradeAdd(){return new OrderInfo();}
}
