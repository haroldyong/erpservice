/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.support;

import com.huobanplus.erpservice.eventhandler.model.Monitor;

/**
 * Created by Administrator on 2015/7/23.
 */
public class SimpleMonitor<T> implements Monitor<T>{

    private T result;

    public SimpleMonitor(T result) {
        this.result = result;
    }


    @Override
    public void dispose() throws IllegalStateException {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean isDisposed() {
        return false;
    }

    @Override
    public T get() {
        return result;
    }
}
