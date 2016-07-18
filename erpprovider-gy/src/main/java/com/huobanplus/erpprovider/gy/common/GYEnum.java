/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.common;

/**
 * Created by wuxiongliu on 2016/6/24.
 */
public interface GYEnum {

    enum PaymentOptions {
        GIFTCART("giftcart", "礼品卡"),
        YINLIAN("yinlian", "银联支付"),
        BALANCE("balance", "预存款"),
        WEIXIN("weixin", "微信支付"),
        CASH("cash", "现金支付"),
        COD("cod", "货到付款"),
        HUIKUAN("huikuan", "邮局汇款"),
        CAIFUTONG("caifutong", "财付通"),
        WANGYIN("wangyin", "网银在线"),
        ZHIFUBAO("zhifubao", "支付宝");

        private String code;
        private String name;

        PaymentOptions(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
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
