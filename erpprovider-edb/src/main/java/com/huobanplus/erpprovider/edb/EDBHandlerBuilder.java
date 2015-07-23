package com.huobanplus.erpprovider.edb;


import com.huobanplus.erpprovider.edb.support.SimpleMonitor;
import com.huobanplus.erpservice.event.erpevent.*;
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
        if (!erpInfo.getName().equals("edb")) {
            return null;
        }
        return new ERPHandler() {
            public boolean eventSupported(ERPBaseEvent erpEvent) {
                //todo �ж��¼��Ƿ���Դ���
                if (erpEvent instanceof CreateOrderEvent) {
                    return true;
                }
                else if(erpEvent instanceof InventoryEvent)
                {
                    return true;
                }
                else if(erpEvent instanceof DeliveryInfoEvent)
                {
                    return true;
                }
                else if(erpEvent instanceof OrderStatusInfoEvent)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }

            public Monitor<EventResult> handleEvent(ERPBaseEvent erpEvent) throws IOException, IllegalAccessException {
                //todo �����¼������ؽ��
                if (erpEvent instanceof CreateOrderEvent) {
                    //
                    OrderInfo orderInfo = ((CreateOrderEvent) erpEvent).getOrderInfo();

                    EventResult result = null;// api

                    return new SimpleMonitor<EventResult>(result);
                }
                if (erpEvent instanceof InventoryEvent) {


                }
                if (erpEvent instanceof DeliveryInfoEvent) {

                }
                if (erpEvent instanceof OrderStatusInfoEvent) {

                }
                return null;
            }
        };
    }
}
