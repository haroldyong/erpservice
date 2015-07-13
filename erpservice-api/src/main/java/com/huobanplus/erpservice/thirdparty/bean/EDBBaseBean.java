package com.huobanplus.erpservice.thirdparty.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * EDB基础类
 */
@XmlRootElement
public class EDBBaseBean extends BaseBean {
    private String warning;
    private String operatorStatus;

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getOperatorStatus() {
        return operatorStatus;
    }

    public void setOperatorStatus(String operatorStatus) {
        this.operatorStatus = operatorStatus;
    }
}
