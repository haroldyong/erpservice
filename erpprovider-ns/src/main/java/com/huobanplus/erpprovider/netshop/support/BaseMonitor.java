package com.huobanplus.erpprovider.netshop.support;

import com.huobanplus.erpservice.event.model.Monitor;

/**
 * 处理事件
 */
public class BaseMonitor<T> implements Monitor<T> {

    T result;

    public BaseMonitor(T result)
    {
        this.result = result;
    }

    @Override
    public void dispose() throws IllegalStateException {

    }

    @Override
    public boolean isFinished() {
        return false;
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
