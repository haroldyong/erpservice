/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.model;
import java.io.Serializable;

/**
 * 类描述：基本的接口返回实体
 * @author aaron
 * @since  2015年7月27日 上午10:24:35
 * @version V1.0
 */
public class BaseResult implements Serializable {

    /**
     * 请求的结果码
     */
    private String resultCode;
    /**
     * 请求的结果说明
     */
    private String resultMsg;
    /**
     * 系统级别的结果码
     */
    private String systemCode;
    /**
     * 系统级别的结果说明
     */
    private String systemMsg;
    /**
     * 重发机制(是否重发)
     */
    private boolean repeat;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * 请求方法名称
     */
    private String methodName;
    /**
     * 重发次数
     */
    private int repeatNum;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemMsg() {
        return systemMsg;
    }

    public void setSystemMsg(String systemMsg) {
        this.systemMsg = systemMsg;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public int getRepeatNum() {
        return repeatNum;
    }

    public void setRepeatNum(int repeatNum) {
        this.repeatNum = repeatNum;
    }
}
