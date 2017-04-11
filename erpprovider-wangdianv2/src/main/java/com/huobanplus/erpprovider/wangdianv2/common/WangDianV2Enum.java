/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.wangdianv2.common;

import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.ICommonEnum;
import com.huobanplus.erpservice.common.ienum.OrderEnum;

public interface WangDianV2Enum {
    enum TradeStatus implements ICommonEnum {
        NOT_CONFIRM(10, "未确认"),
        WAITING_PAY(20, "待尾款"),
        PAYED(30, "已付款待发货"),
        PARTY_DELIVER(40, "部分发货"),
        DELIVERED(50, "已发货"),
        RECEIVED(60, "已签收"),
        COMPLETE(70, "已完成"),
        REFUNDED(80, "已退款"),
        CLOSED(90, "已关闭");

        private int code;
        private String name;

        TradeStatus(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public static TradeStatus toWangDianV2(OrderEnum.PaymentOptions paymentOptions, OrderEnum.PayStatus payStatus) {
            if (paymentOptions == OrderEnum.PaymentOptions.PAY_ON_DELIVERY) {
                return TradeStatus.PAYED;
            } else {
                switch (payStatus) {
                    case PAYED:
                        return TradeStatus.PAYED;
                    default:
                        return TradeStatus.PAYED;
                }
            }
        }

        public static TradeStatus toWangDianV2(int paymentOptionsInt, int payStatusInt) {
            OrderEnum.PaymentOptions paymentOptions = EnumHelper.getEnumType(OrderEnum.PaymentOptions.class, paymentOptionsInt);
            OrderEnum.PayStatus payStatus = EnumHelper.getEnumType(OrderEnum.PayStatus.class, payStatusInt);
            return toWangDianV2(paymentOptions, payStatus);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    enum PayStatus implements ICommonEnum {
        NOT_PAY(0, "未付款"),
        PARTY_PAYED(1, "部分付款"),
        PAYED(2, "已付款");

        private int code;
        private String name;

        PayStatus(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public static PayStatus toWangDianV2(OrderEnum.PayStatus payStatus) {
            switch (payStatus) {
                case NOT_PAYED:
                    return PayStatus.NOT_PAY;
                case PARTY_PAYED:
                    return PayStatus.PARTY_PAYED;
                case PAYED:
                    return PayStatus.PAYED;
                default:
                    return PayStatus.NOT_PAY;
            }
        }

        public static PayStatus toWangDianV2(int payStatusInt) {
            OrderEnum.PayStatus payStatus = EnumHelper.getEnumType(OrderEnum.PayStatus.class, payStatusInt);
            return toWangDianV2(payStatus);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 退款状态
     */
    enum RefundStatus implements ICommonEnum {
        NORMAL(0, "无退款"),
        CANCELD(1, "取消退款"),
        APPLYED(2, "已申请退款"),
        WAITING_RETURN(3, "等待退货"),
        WAITING_RECEIVE(4, "等待收货"),
        REFUND_SUCCESS(5, "退款成功");

        private int code;
        private String name;

        RefundStatus(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public static RefundStatus toWangDianV2(OrderEnum.AfterSalesStatus afterSalesStatus) {
            switch (afterSalesStatus) {
                case NORMAL:
                    return RefundStatus.NORMAL;
                case APPLYING:
                    return RefundStatus.APPLYED;
                case CANCELED:
                    return RefundStatus.CANCELD;
                case WAITING_RETURN:
                    return RefundStatus.WAITING_RETURN;
                case WAITING_CONFIRM_RETURN:
                    return RefundStatus.WAITING_RECEIVE;
                case REFUND_SUCCESS:
                    return RefundStatus.REFUND_SUCCESS;
                default:
                    return RefundStatus.NORMAL;
            }
        }

        public static RefundStatus toWangDianV2(int afterSalesStatusInt) {
            OrderEnum.AfterSalesStatus afterSalesStatus = EnumHelper.getEnumType(OrderEnum.AfterSalesStatus.class, afterSalesStatusInt);

            return toWangDianV2(afterSalesStatus);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}