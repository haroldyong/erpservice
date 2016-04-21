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
 * 订单同步状态
 * Created by allan on 4/21/16.
 */
public interface OrderSyncStatus {
    /**
     * 订单信息同步状态
     */
    enum DetailSyncStatus implements ICommonEnum {
        SYNC_SUCCESS(0, "同步成功"),
        SYNC_FAILURE(1, "同步失败");
        private int code;
        private String name;

        DetailSyncStatus(int code, String name) {
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
     * 订单发货同步
     */
    enum ShipSyncStatus implements ICommonEnum {
        SYNC_SUCCESS(0, "同步成功"),
        SYNC_PARTY_SUCCESS(1, "同步部分成功"),
        SYNC_FAILURE(2, "同步失败");

        private int code;
        private String name;

        ShipSyncStatus(int code, String name) {
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
