package handler;

import org.springframework.stereotype.Component;
import event.eventEnum.EventMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * erp注册器，提供给erp-provider注册
 * <p>平台将根据事件中携带ERPInfo来分配到某一个erp-provider处理</p>
 * Created by allan on 2015/7/13.
 */
@Component
public class ERPRegister {
    List<ERPHandlerBuilder> handlerBuilders = new ArrayList<>();

    public List<ERPHandlerBuilder> getHandlerBuilders() {
        return handlerBuilders;
    }

    public void setHandlerBuilders(List<ERPHandlerBuilder> handlerBuilders) {
        this.handlerBuilders = handlerBuilders;
    }

    /**
     * 添加(注册)一个erp handler builder
     *
     * @param handlerBuilder
     */
    public void addBuilders(ERPHandlerBuilder handlerBuilder) {
        this.handlerBuilders.add(handlerBuilder);
    }
}
