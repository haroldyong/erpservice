package com.huobanplus.erpprovicer.huobanmall.common;

import com.huobanplus.erpservice.event.model.Monitor;

/**
 * 一个简单的监控返回值的类
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
