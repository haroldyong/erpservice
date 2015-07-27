package com.huobanplus.erpprovider.edb.eventResult;

import com.huobanplus.erpservice.event.model.EventResult;

/**
 * 创建订单结果
 * Created by allan on 2015/7/27.
 */
public class CreateOrderResult extends EventResult {
    private String result;
    private String state;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
