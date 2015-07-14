package handler;

import event.ERPEvent;
import event.eventEnum.EventMethod;
import model.EventResult;

import java.io.IOException;

/**
 * 事件处理器
 * <p>由epr-provider具体实现</p>
 * Created by allan on 2015/7/13.
 */
public interface ERPHandler {
    /**
     * 是否支持该事件
     *
     * @param event
     * @return
     */
    boolean eventSupported(ERPEvent event);

    /**
     * 处理事件
     *
     * @param event
     * @param eventMethod 枚举方法
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     */
    EventResult handleEvent(ERPEvent event, EventMethod eventMethod) throws IOException, IllegalAccessException;
}
