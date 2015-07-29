package com.huobanplus.erpservice.event.model;

/**
 * erp 返回结果
 * Created by allan on 2015/7/13.
 */
public class EventResult {
    /**
     * 返回结果，0表示失败，1表示成功
     */
    private int status;
    /**
     * 结果
     * <p>json格式数据</p>
     */
    private String result;

    public EventResult(int status, String result) {
        this.status = status;
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
