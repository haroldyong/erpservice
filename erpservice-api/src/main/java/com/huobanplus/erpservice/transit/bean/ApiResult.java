package com.huobanplus.erpservice.transit.bean;

/**
 * Created by allan on 2015/8/4.
 */
public class ApiResult {
    private String status;
    private String resultData;
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ApiResult(String status, String resultData, String msg) {
        this.status = status;
        this.resultData = resultData;
        this.msg = msg;
    }

    public ApiResult() {
    }
}
