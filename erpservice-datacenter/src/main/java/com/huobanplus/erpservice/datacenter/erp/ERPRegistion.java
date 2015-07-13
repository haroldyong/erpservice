package com.huobanplus.erpservice.datacenter.erp;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ERP注册信息，提供给ERP-Provider
 * @author CJ
 */
@Component
public class ERPRegistion {

    private List<ERPHandlerBuilder> builderList = new ArrayList<>();

    public List<ERPHandlerBuilder> getBuilderList() {
        return builderList;
    }

    public void setBuilderList(List<ERPHandlerBuilder> builderList) {
        this.builderList = builderList;
    }
}
