package com.huobanplus.erpprovider.netshop.config;

import com.huobanplus.erpprovider.netshop.bean.*;
import com.huobanplus.erpprovider.netshop.handler.DeliverHandler;
import com.huobanplus.erpprovider.netshop.service.NetShopService;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
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

                    //创建订单信息
                    OrderInfo orderInfo;
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
                        orderInfo = new OrderInfo();
                        orderInfo.setResultMsg("sign验证失败");
                        orderInfo.setResultCode(Constant.REQUEST_SING_ERROR);
                        return new BaseMonitor<OrderInfo>(orderInfo);
                    } else {
                        orderInfo = new OrderInfo();
                        return  netShopService.commitOrderInfo(authBean, orderInfo);
                    }

                } else if (baseEventClass == InventoryEvent.class) {
                    //库存信息
                    InventoryInfo inventoryInfo;
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
                        inventoryInfo = new InventoryInfo();
                        inventoryInfo.setResultMsg("sign验证失败");
                        inventoryInfo.setResultCode(Constant.REQUEST_SING_ERROR);
                        return new BaseMonitor<>(inventoryInfo);
                    } else {
                        inventoryInfo = new InventoryInfo();
                        return netShopService.syncInventory(authBean, inventoryInfo);
                    }

                } else if (baseEventClass == DeliveryInfoEvent.class) {

                    //物流信息
                    DeliveryInfo deliveryInfo;
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
                        deliveryInfo = new DeliveryInfo();
                        deliveryInfo.setResultMsg("sign验证失败");
                        deliveryInfo.setResultCode(Constant.REQUEST_SING_ERROR);
                        return new BaseMonitor<DeliveryInfo>(deliveryInfo);
                    } else {
                        deliveryInfo = new DeliveryInfo();
                        return netShopService.deliveryNotice(authBean, deliveryInfo);
                    }
                }
                else if(baseEventClass == OrderStatusInfoEvent.class)
                {
                    //订单信息
                    OrderInfo orderInfo;
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
                        orderInfo = new OrderInfo();
                        orderInfo.setResultMsg("sign验证失败");
                        orderInfo.setResultCode(Constant.REQUEST_SING_ERROR);
                        return new BaseMonitor<OrderInfo>(orderInfo);
                    } else {
                        orderInfo = new OrderInfo();
                        return netShopService.modifyOrderInfo(authBean, orderInfo);
                    }
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
                    //商品信息
                    OrderInfo orderInfo;
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
                        orderInfo = new OrderInfo();
                        orderInfo.setResultMsg("sign验证失败");
                        orderInfo.setResultCode(Constant.REQUEST_SING_ERROR);
                        return new BaseMonitor<OrderInfo>(orderInfo);
                    } else {
                        orderInfo = new OrderInfo();
                        return netShopService.obtainOrderDetail(authBean, orderInfo.getOrderCode());
                    }
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
