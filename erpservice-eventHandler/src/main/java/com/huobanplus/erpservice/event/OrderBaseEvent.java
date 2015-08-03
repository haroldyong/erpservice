package com.huobanplus.erpservice.event;

//import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.event.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.event.model.OrderInfo;

/**
 * 订单处理事件
 * Created by allan on 2015/7/13.
 */
public class OrderBaseEvent extends ERPBaseEvent {

    public OrderInfo createOrder() {

        return new OrderInfo();
    }

    /**
     * 更新订单状态
     *
     * @return
     */
    private OrderInfo tradeImportStatusUpdate() {
        return new OrderInfo();
    }

    /**
     * 订单作废
     *
     * @return
     */
    private OrderInfo tradeCancel() {
        return new OrderInfo();
    }

    /**
     * 根据条件得到外部订单信息
     *
     * @return
     */
    private OrderInfo outTradeGet() {
        return new OrderInfo();
    }

    /**
     * 订单批量发  货
     *
     * @return
     */
    private OrderInfo tradeDeliveryBatch() {
        return new OrderInfo();
    }

    /**
     * 未确认订单中添加赠品
     *
     * @return
     */
    private OrderInfo tradeGiftAdd() {
        return new OrderInfo();
    }

    /**
     * 订单业务状态更新
     *
     * @return
     */
    private OrderInfo tradeUpdate() {
        return new OrderInfo();
    }

    /**
     * 批量更改订单信息中的内部便签
     *
     * @return
     */
    private OrderInfo inNoteUpdate() {
        return new OrderInfo();
    }

    /**
     * 获取订单信息
     *
     * @return
     */
    private OrderInfo tradeGet() {
        return new OrderInfo();
    }

    /**
     * 写入订单
     *
     * @return
     */
    private OrderInfo tradeAdd() {
        return new OrderInfo();
    }
}
