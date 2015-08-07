package com.huobanplus.erpservice.event.model;

/**
 * erp 返回结果
 * Created by allan on 2015/7/13.
 */
public class EventResult extends BaseResult{
    /**
     * 返回结果，0表示失败，1表示成功
     */
    private int systemStatus;
    /**
     * 结果
     * <p>json格式数据</p>
     */
    private String systemResult;

    public EventResult()
    {

    }
    public int getSystemStatus() {
        return systemStatus;
    }

    public EventResult(int systemStatus, String systemResult) {
        this.systemStatus = systemStatus;
        this.systemResult = systemResult;
    }

    public void setSystemStatus(int systemStatus) {
        this.systemStatus = systemStatus;
    }

    public String getSystemResult() {
        return systemResult;
    }

    public void setSystemResult(String systemResult) {
        this.systemResult = systemResult;
    }
}
