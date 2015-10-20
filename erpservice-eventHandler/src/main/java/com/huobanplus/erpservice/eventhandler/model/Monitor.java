/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.model;

import java.util.function.Supplier;

/**
 * 期待一个事件的结果
 *
 * @author CJ
 */
public interface Monitor<T> extends Supplier<T> {

    /**
     * 取消这个延时工作，但并不保证成功。
     *
     * @throws IllegalStateException 当状态为完成时。
     */
    void dispose() throws IllegalStateException;

    /**
     * @return true for finished
     */
    boolean isFinished();

    /**
     * @return true for disposed
     */
    boolean isDisposed();
}
