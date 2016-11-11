/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.common;

import com.huobanplus.erpservice.common.ienum.ICommonEnum;

/**
 * Created by wuxiongliu on 2016-08-30.
 */
public interface SurSungEnum {

    enum OrderStatus {
        WAIT_BUYER_PAY,//等待买家付款
        WAIT_SELLER_SEND_GOODS,//等待卖家发货
        WAIT_BUYER_CONFIRM_GOODS,//等待买家确认收货
        TRADE_FINISHED,//交易成功
        TRADE_CLOSED,//付款后交易关闭
        TRADE_CLOSED_BY_TAOBAO//付款前交易关闭
    }

    enum SourceShop implements ICommonEnum {
        OTHER(0, "其他"),
        YIHAODIAN(1, "1号店2.0"),
        ALIBABA(2, "阿里巴巴"),
        CHUCHUJIE(3, "楚楚街"),
        DANGDANG(4, "当当"),
        DULIWANGDIAN(5, "独立网店"),
        GONGSHANGYINHANG(6, "工商银行"),
        JINGDONG2(7, "京东2"),
        KOUDAITONG(8, "口袋通"),
        KUBA(9, "库巴"),
        MEILISHUO(10, "美丽说"),
        MOGUJIE(11, "蘑菇街"),
        PAIPAI(12, "拍拍"),
        SUNING(13, "苏宁"),
        TAOBAO(14, "淘宝"),
        TAOBAOFENXIAO(15, "淘宝分销"),
        TESHEHUI(16, "特奢汇"),
        TIANMAO(17, "天猫"),
        WANGYIKAOLA(18, "网易考拉"),
        YAMAXUN(19, "亚马逊");

        private int code;
        private String name;

        SourceShop(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public void setName(String name) {
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


    enum GoodsStatus implements ICommonEnum {
        BUYER_NOT_RECEIVED(0, "买家未收到货"),
        BUYER_RECEIVED(1, "买家已收到货"),
        BUYER_RETURNED_GOODS(2, "买家已退货"),
        SELLER_RECEIVED(3, "卖家已收到退货");

        private int code;
        private String name;

        GoodsStatus(int code, String name) {
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
