package com.huobanplus.erpprovider.sap.formatsap;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by admin on 2016/4/20.
 */
@Setter
@Getter
public class LogiInfo {

    /**
     * 交货单
     */
    private String zVBELN ;

    /**
     * 物流单号
     */
    private String zWMOrder ;

    /**
     * 导出状态
     */
    private String zType ;

    /**
     * 交货单
     */
    private String yVBELN ;

    /**
     * 微订单
     *
     */
    private String zOrder ;

}
