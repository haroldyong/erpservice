package com.huobanplus.erpservice.event.model;

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
