/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
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
        ALIPAY_MOBILE_WEB(11, "支付宝移动网站支付"),
        WEIXINPAY(2, "微信支付"),
        PAY_ON_DELIVERY(6, "货到付款"),
        ALIPAY_PC(7, "支付宝网页即时到账"),
        REDEMPTION(8, "提货券"),
        WEIXINPAY_V3(9, "微信支付V3"),
        UNIONPAY(100, "银联在线支付"),
        BAIDUPAY(200, "百度钱包"),
        WEIXINPAY_APP(300, "微信APP支付"),
        WEIFUTONG(500, "威富通"),
        HUIJINBAO(600, "汇金宝"),
        AD_PAYMENT(700, "预付款");

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

    enum AuditStatus implements ICommonEnum {
        UNAUDITED(0, "未审核"),
        AUDITED(1, "已审核");

        private int code;
        private String name;


        AuditStatus(int code, String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    /**
     * 售后状态
     */
    enum AfterSalesStatus implements ICommonEnum {
        NORMAL(-1, "正常"),
        APPLYING(0, "售后申请中"),
        REFUNDING(1, "退款中"),
        REFUSED(2, "拒绝售后"),
        REFUND_SUCCESS(3, "退款成功"),
        CANCELED(4, "售后已取消"),
        WAITING_RETURN(5, "等待买家退货"),
        WAITING_CONFIRM_RETURN(6, "买家退货等待确认");

        private int code;
        private String name;


        AfterSalesStatus(int code, String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
