/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.common.ienum;

/**
 * Created by liual on 2015-11-03.
 */
public interface OrderEnum {
    enum OrderStatus implements ICommonEnum {
        ACTIVE(0, "活动"),
        DEAD(-1, "已取消"),
        FINISH(1, "完成");
        private int code;
        private String name;

        OrderStatus(int code, String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 支付状态
     */
    enum PayStatus implements ICommonEnum {
        NOT_PAYED(0, "未支付"),
        PAYED(1, "已支付"),
        PARTY_PAYED(3, "部分付款"),
        PARTY_REFUND(4, "部分退款"),
        ALL_REFUND(5, "全额退款"),
        REFUNDING(6, "售后退款中");

        private int code;
        private String name;

        PayStatus(int code, String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    /**
     * 发货状态
     */
    enum ShipStatus implements ICommonEnum {
        NOT_DELIVER(0, "未发货"),
        DELIVERED(1, "已发货"),
        PARTY_DELIVER(2, "部分发货"),
        PARTY_RETURN(3, "部分退货"),
        RETURNED(4, "已退货");
        private int code;
        private String name;

        ShipStatus(int code, String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    enum PaymentOptions implements ICommonEnum {
        ALIPAY_MOBILE(1, "支付宝手机网页即时到账"),
        WEIXINPAY(2, "微信支付"),
        ALIPAY_PC(7, "支付宝网页即时到账"),
        REDEMPTION(8, "提货券"),
        WEIXINPAY_V3(9, "微信支付V3"),
        UNIONPAY(100, "银联在线支付"),
        BAIDUPAY(200, "百度钱包"),
        WEIXINPAY_APP(300, "微信APP支付"),
        WEIFUTONG(500, "威富通");

        private int code;
        private String name;

        PaymentOptions(int code, String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
