package com.huobanplus.erpservice.datacenter.erp;

import com.huobanplus.erpservice.datacenter.event.HOTEvent;
import com.huobanplus.erpservice.datacenter.event.HOTEventResult;
import org.springframework.dao.DataAccessException;

import java.io.IOException;

/**
 * 具体的一个ERP事件处理器
 * <p>它可以决定是否响应以及如何响应一个事件</p>
 * <p>事件的响应可能不是实时的，但客户端代码可以检测到这个事件处理的进度。</p>
 * @author CJ
 */
public interface ERPHandler {

    boolean eventSupported(HOTEvent event);

    Monitor<HOTEventResult> handleEvent(HOTEvent event) throws IOException,IllegalAccessException,DataAccessException;

}
