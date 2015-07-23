package com.huobanplus.erpprovider.edb;


import com.huobanplus.erpservice.event.erpevent.CreateOrderEvent;
import com.huobanplus.erpservice.event.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.event.erpevent.InventoryEvent;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;

import java.io.IOException;

/**
 * com.huobanplus.erpprovider.edb �����¼�����ʵ����
 * Created by allan on 2015/7/13.
 */
public class EDBHandlerBuilder implements ERPHandlerBuilder {

    /**
     * ����erp��Ϣ�ж��Ƿ��ɸ�erp-provider����
     *
     * @param erpInfo
     * @return �޷������ؿգ����Դ����ظ�erp�¼�������
     */
    public ERPHandler buildHandler(ERPInfo erpInfo) {
        if (erpInfo.toString().equals("")) {
            return null;
        }
        return new ERPHandler() {
            public boolean eventSupported(ERPBaseEvent erpEvent) {
                //todo �ж��¼��Ƿ���Դ���
                if (erpEvent instanceof CreateOrderEvent) {
                    return true;
                }
                return false;
            }

            public Monitor<EventResult> handleEvent(ERPBaseEvent erpEvent) throws IOException, IllegalAccessException {
                //todo �����¼������ؽ��
                if (erpEvent instanceof CreateOrderEvent) {
                    OrderInfo orderInfo = ((CreateOrderEvent) erpEvent).getOrderInfo();

                }
                if (erpEvent instanceof InventoryEvent) {

                }
                return null;
            }
        };
    }
}
