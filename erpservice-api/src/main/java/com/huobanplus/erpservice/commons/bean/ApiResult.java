package com.huobanplus.erpservice.commons.bean;

import lombok.Data;

/**
 * Created by allan on 2015/8/4.
 */
@Data
public class ApiResult {
    private int resultCode;
    private String resultMsg;
    private Object data;

    public static ApiResult resultWith(ResultCode resultCode, Object data) {
        ApiResult apiResult = new ApiResult();
        apiResult.resultCode = resultCode.getResultCode();
        apiResult.resultMsg = resultCode.getResultMsg();
        apiResult.data = data;
        return apiResult;
    }

    public static ApiResult resultWith(ResultCode resultCode) {
        ApiResult apiResult = new ApiResult();
        apiResult.resultCode = resultCode.getResultCode();
        apiResult.resultMsg = resultCode.getResultMsg();
        return apiResult;
    }

    public static ApiResult resultWith(ResultCode resultCode, String msg, Object data) {
        ApiResult apiResult = new ApiResult();
        apiResult.resultCode = resultCode.getResultCode();
        apiResult.resultMsg = msg;
        apiResult.data = data;
        return apiResult;
    }
}
