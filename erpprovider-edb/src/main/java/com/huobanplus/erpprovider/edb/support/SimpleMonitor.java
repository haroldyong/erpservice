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
