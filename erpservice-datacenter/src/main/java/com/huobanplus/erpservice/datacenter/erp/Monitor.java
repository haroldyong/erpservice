package com.huobanplus.erpservice.datacenter.erp;

/**
 * 进度查询
 *
 * @author CJ
 */
public interface Monitor<T> {

    /**
     * 抓取结果
     * @return 相应结果
     * @throws IllegalStateException 当结果还未知
     */
    T fetchResult() throws IllegalStateException;

    boolean finished();
    boolean failed();
}
