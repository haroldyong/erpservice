package com.huobanplus.erpprovider.netshop.config;

import com.huobanplus.erpprovider.netshop.bean.*;
import com.huobanplus.erpprovider.netshop.handler.DeliverHandler;
import com.huobanplus.erpprovider.netshop.handler.InventoryHandler;
import com.huobanplus.erpprovider.netshop.handler.OrderHandler;
import com.huobanplus.erpprovider.netshop.service.NetShopService;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpprovider.netshop.util.SignBuilder;
import com.huobanplus.erpprovider.netshop.util.SignStrategy;
import com.huobanplus.erpservice.event.erpevent.*;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.event.model.*;
import com.huobanplus.erpservice.event.model.AuthBean;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * <b>类描述：<b/>具体处理事件
 */
public class NetShopHandlerBuilder implements ERPHandlerBuilder {
    /**
     * @param info 相关信息
     * @return 无法处理返回空，可以处理返回该erp事件处理器
     */
    @Resource
    private NetShopService netShopService;

    @Autowired
    private DeliverHandler deliverHandler;

    @Resource
    private OrderHandler orderHandler;

    @Resource
    private InventoryHandler inventoryHandler;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if (!"netShop".equals(info.getName())) {
            //不是网店管家
            return null;
        }
        return new ERPHandler() {

            @Override
            public boolean eventSupported(Class<? extends ERPBaseEvent> baseEventClass) {

                if (baseEventClass == CreateOrderEvent.class) {
                    return true;
                }
                else if(baseEventClass == DeliveryInfoEvent.class)
                {
                    return true;
                }
                else if(baseEventClass == InventoryEvent.class)
                {
                    return true;
                }
                else if(baseEventClass == OrderStatusInfoEvent.class)
                {
                    return true;
                }
                else if(baseEventClass == ProductInfoEvent.class)
                {
                    return true;
                }
                else if(baseEventClass == ObtainOrderEvent.class)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }

            @Override
            public Monitor handleEvent(Class<? extends ERPBaseEvent> baseEventClass, Object data) throws IOException, IllegalAccessException, DataAccessException, IllegalArgumentException {
                HttpServletRequest request = (HttpServletRequest) data;
                if (baseEventClass == CreateOrderEvent.class) {
                    return orderHandler.commitOrderInfo(request);
                } else if (baseEventClass == InventoryEvent.class) {
                    return inventoryHandler.synsInventory(request);
                } else if (baseEventClass == DeliveryInfoEvent.class) {
                    return deliverHandler.deliverInform(request);
                }
                else if(baseEventClass == OrderStatusInfoEvent.class)
                {
                    return orderHandler.orderStatusInfo(request);
                }
                else if(baseEventClass == ProductInfoEvent.class)
                {
                    //商品信息
                    ProductInfo productInfo;
                    //构造Auth对象
                    AuthBean authBean = new AuthBean();
                    String sign = (String) request.getAttribute("sign");
                    String mType = (String) request.getAttribute("mType");
                    String secret = (String) request.getAttribute("signType");
                    String signType = (String) request.getAttribute("signType");
                    String timeStamp = (String) request.getAttribute("timeStamp");
                    String uCode = (String) request.getAttribute("uCode");
                    authBean.setSign(sign);
                    authBean.setmType(mType);
                    authBean.setSecret(secret);
                    authBean.setSignType(signType);
                    authBean.setTimeStamp(timeStamp);
                    authBean.setuCode(uCode);
                    String signStr = SignStrategy.getInstance().buildSign(authBean).getSign();
                    if (null != signStr && signStr.equals(sign)) {
                        productInfo = new ProductInfo();
                        productInfo.setResultMsg("sign验证失败");
                        productInfo.setResultCode(Constant.REQUEST_SING_ERROR);
                        return new BaseMonitor<ProductInfo>(productInfo);
                    } else {
                        productInfo = new ProductInfo();
                        return netShopService.obtainGoods(authBean, productInfo);
                    }
                }
                else if(baseEventClass == ObtainOrderEvent.class)
                {
                    return orderHandler.obtainOrderInfo(request);
                }
                else
                {
                    return null;
                }
            }

            @Override
            public Monitor handleException(Class<? extends ERPBaseEvent> baseEventClass, FailedBean failedBean) {

                if (baseEventClass == CreateOrderEvent.class)
                {
                    return netShopService.notifyFailedEvent(failedBean);
                } else if(baseEventClass == DeliveryInfoEvent.class)
                {
                    return netShopService.notifyFailedEvent(failedBean);
                }
                else if(baseEventClass == InventoryEvent.class)
                {
                    return netShopService.notifyFailedEvent(failedBean);
                }
                else if(baseEventClass == OrderStatusInfoEvent.class)
                {
                    return netShopService.notifyFailedEvent(failedBean);
                }
                else if(baseEventClass == ProductInfoEvent.class)
                {
                    return netShopService.notifyFailedEvent(failedBean);
                }
                else if(baseEventClass == ObtainOrderEvent.class)
                {
                    return netShopService.notifyFailedEvent(failedBean);
                }
                else
                {
                    return  null;
                }

            }
        };
    }
}
