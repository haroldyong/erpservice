package com.huobanplus.erpprovider.gjbc.handler.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomOrderHead;
import com.huobanplus.erpprovider.dtw.util.AESUtil;
import com.huobanplus.erpprovider.dtw.util.Arith;
import com.huobanplus.erpprovider.dtw.util.DtwUtil;
import com.huobanplus.erpprovider.dtw.util.RSAUtil;
import com.huobanplus.erpprovider.gjbc.common.GjbcData;
import com.huobanplus.erpprovider.gjbc.common.GjbcEnum;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomBody;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomGoodsPurchaser;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomHead;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomOrder;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomOrderDetail;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomOrderInfo;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomOrderInfoList;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomSign;
import com.huobanplus.erpprovider.gjbc.formatgjbc.GjbcAllOrderStatus;
import com.huobanplus.erpprovider.gjbc.formatgjbc.GjbcGoods;
import com.huobanplus.erpprovider.gjbc.formatgjbc.GjbcOrder;
import com.huobanplus.erpprovider.gjbc.formatgjbc.WeiXinCustom;
import com.huobanplus.erpprovider.gjbc.handler.GjbcOrderHandler;
import com.huobanplus.erpprovider.gjbc.util.GjbcConstant;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by montage on 2017/6/26.
 */

@Component
public class GjbcOrderHandlerImpl implements GjbcOrderHandler{

    private final static Log log = LogFactory.getLog(GjbcOrderHandlerImpl.class);

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    @Override
    public EventResult sendOrder(PushNewOrderEvent pushNewOrderEvent) {
        Order order = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
        GjbcData gjbcData = JSON.parseObject(erpInfo.getSysDataJson(), GjbcData.class);
        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();

        List<OrderItem> orderItems = order.getOrderItems();
        List<GjbcGoods> gjbcGoodsList = new ArrayList<>();
        orderItems.forEach(orderItem -> {
            GjbcGoods gjbcGoods = new GjbcGoods();
            gjbcGoods.setGoods_seq(orderItem.getGoodId());
            gjbcGoods.setGoods_barcode("");//商品条形码
            gjbcGoods.setGoods_ptcode("");//税号
            gjbcGoods.setGoods_size("");//020
            gjbcGoods.setGoods_unit("");//020
            gjbcGoods.setGoods_hg_num(orderItem.getNum());
            gjbcGoods.setGoods_gweight(orderItem.getWeight());
            gjbcGoods.setGoods_name(orderItem.getName());
            gjbcGoods.setBrand(orderItem.getProductBn());
            gjbcGoods.setGoods_spec(orderItem.getStandard());
            gjbcGoods.setGoods_num(String.valueOf(orderItem.getSendNum()));
            gjbcGoods.setGoods_price(orderItem.getAmount());
            gjbcGoods.setYcg_code("");//原产国代码
            gjbcGoods.setHs_code("");//商品HS编码
            gjbcGoods.setCurr("");//商品单价币制
            gjbcGoods.setGoods_hg_num2(orderItem.getNum());
            double total = orderItem.getNum() * orderItem.getAmount();
            gjbcGoods.setGoods_total(total);
            gjbcGoods.setGoods_commonid(orderItem.getProductId());
            gjbcGoodsList.add(gjbcGoods);
        });

        GjbcOrder gjbcOrder = new GjbcOrder();
        gjbcOrder.setOrder_sn(order.getOrderId());
        gjbcOrder.setSender_name(order.getUserNickname());
        gjbcOrder.setBuyer_name(order.getShipName());
        gjbcOrder.setBuyer_phone(order.getShipMobile());
        gjbcOrder.setOrder_name(order.getBuyerName());
        gjbcOrder.setOrder_idcard(order.getBuyerPid());
        gjbcOrder.setOrder_phone(order.getUserLoginName());
        gjbcOrder.setCustoms_discount(order.getPmtAmount());
        gjbcOrder.setOrder_uname(order.getUserNickname());
        gjbcOrder.setBuyer_address(order.getShipAddr());
        gjbcOrder.setP_name(order.getPaymentName());
        gjbcOrder.setP_time(Integer.valueOf(order.getPayTime()));
        gjbcOrder.setSh_fee(order.getCostFreight());
        gjbcOrder.setCus_tax(order.getTaxAmount());
        gjbcOrder.setExpress_code(order.getLogiCode());
        gjbcOrder.setOrder_amount(order.getFinalAmount());
       /* EventResult eventResult = orderPush(gjbcData, gjbcOrder, order.getOrderId());*/
       /* if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            saveLog(order, erpUserInfo, erpInfo, pushNewOrderEvent, true, null);
        } else {
            saveLog(order, erpUserInfo, erpInfo, pushNewOrderEvent, false, eventResult.getResultMsg());
        }*/

        return null;
    }



   /* public EventResult orderPush(GjbcData gjbcData , GjbcOrder gjbcOrder ,String originOrderId){
        try {
            String GjbcJson = JSON.toJSONString(gjbcOrder);
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("seller_name",Base64.encode(gjbcData.getSeller_name().getBytes("utf-8")));
            requestMap.put("api_key",Base64.encode(gjbcData.getApi_key().getBytes("utf-8")));
            requestMap.put("mark",Base64.encode(gjbcData.getMark().getBytes("utf-8")));
            requestMap.put("confirm",Base64.encode(gjbcData.getConfirm().getBytes("utf-8")));
            requestMap.put("order",Base64.encode(Base64.encode(GjbcJson.getBytes("utf-8")).getBytes("utf-8")));

            HttpResult httpResult = HttpClientUtil.getInstance().post(GjbcConstant.REQUEST_URL,requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject resultJson = JSON.parseObject(httpResult.getHttpContent());
                if (resultJson.getString("sts").equals("Y")) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                } else {
                    return EventResult.resultWith(EventResultEnum.ERROR, resultJson.getString("res"), null);
                }
            } else {
                return EventResult.resultWith(EventResultEnum.SYSTEM_BAD_REQUEST, httpResult.getHttpContent(), null);
            }
        }catch (Exception e){
            log.info("跨境易购推送订单异常（" + originOrderId + "）-----" + e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }

    }*/


    /**
     * 推送支付单 和 海关推送
     *
     * @param order
     * @param gjbcData
     * @param gjbcAllOrderStatus
     */
    private EventResult pushFourOrder(Order order , GjbcData gjbcData, GjbcAllOrderStatus gjbcAllOrderStatus){
        StringBuilder errorMsg = new StringBuilder();
        //支付单推送
        if(!gjbcAllOrderStatus.isPayOrderSyncStatus()){
            EventResult PayResult = null;
            OrderEnum.PaymentOptions enumTypeOptions = EnumHelper.getEnumType(OrderEnum.PaymentOptions.class, order.getPayType());
            if (enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_MOBILE_WEB || enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_MOBILE || enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_PC){
                PayResult = PushOrderAliPay(order, gjbcData);
            } else if (enumTypeOptions == OrderEnum.PaymentOptions.WEIXINPAY_V3 || enumTypeOptions == OrderEnum.PaymentOptions.WEIXINPAY || enumTypeOptions == OrderEnum.PaymentOptions.WEIXINPAY_APP){
                PayResult = PushOrderWeiXin(order, gjbcData);
            }else {
                PayResult = EventResult.resultWith(EventResultEnum.ERROR, "不支持该支付", null);
            }

            if (PayResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()){
                gjbcAllOrderStatus.setPayOrderSyncStatus(true);
            } else {
                gjbcAllOrderStatus.setPayOrderSyncStatus(false);
                errorMsg.append("支付单:");
                errorMsg.append(PayResult.getResultMsg()).append("|");
            }
        }

        //海关推送
        if (!gjbcAllOrderStatus.isCustomOrderSyncStatus()) {
            EventResult eventResultCustom = PushOrderCustom(order, gjbcData);
            if (eventResultCustom.getResultCode() == EventResultEnum.SUCCESS.getResultCode()){
                gjbcAllOrderStatus.setCustomOrderSyncStatus(true);
            } else {
                gjbcAllOrderStatus.setCustomOrderSyncStatus(false);
                errorMsg.append("海关订单");
                errorMsg.append(eventResultCustom.getResultMsg()).append("|");
            }
        }
        if (gjbcAllOrderStatus.isSyncSuccess()){
            return EventResult.resultWith(EventResultEnum.SUCCESS, gjbcAllOrderStatus);
        }
        return EventResult.resultWith(EventResultEnum.ERROR, errorMsg.toString(), gjbcAllOrderStatus);
    }


    @Override
    public EventResult PushOrderAliPay(Order order, GjbcData gjbcData) {
        try {
            Map<String , Object> requestMap = new TreeMap<>();
            requestMap.put("service", "alipay.acquire.customs");
            requestMap.put("_input_charset", "utf-8");
            requestMap.put("sign_type", "MD5");

            requestMap.put("order_Id",order.getOrderId());
            requestMap.put("payNumber",order.getPayNumber());
            requestMap.put("amount",order.getOnlinePayAmount());
            requestMap.put("partner",gjbcData.getAliPartner());
            requestMap.put("merchant_customs_code",gjbcData.getECommerceCode());
            requestMap.put("merchant_customs_name",gjbcData.getECommerceName());
            requestMap.put("customs_place", GjbcEnum.CustomerEnum.HANGZHOU.toString());

            String sign = DtwUtil.aliBuildSign(requestMap, gjbcData.getAliKey());
            requestMap.put("sign",sign);

            HttpResult httpResult = HttpClientUtil.getInstance().get(GjbcConstant.ALI_PAY_URL, requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK){
                String XmlData = httpResult.getHttpContent();
                Element rootElement = DocumentHelper.parseText(XmlData).getRootElement();
                Element isSuccess= rootElement.element("is_success");
                if (isSuccess.getText().equals("T")){
                    Element aliPay_Element = rootElement.element("response").element("alipay");
                    Element result_code = aliPay_Element.element("result_code");
                    if (result_code.getText().equals("SUCCESS")){
                        log.info("ali pay order push success");
                        return EventResult.resultWith(EventResultEnum.SUCCESS);
                    }else{
                        Element detailErrorDesdes = aliPay_Element.element("detail_error_des");
                        log.info("ali pay order push failed:"+detailErrorDesdes.getText());
                        return EventResult.resultWith(EventResultEnum.ERROR,detailErrorDesdes.getText(),null);
                    }
                }else {
                    Element error_Elem = rootElement.element("error");
                    log.info("ali pay order push failed:" + error_Elem.getText());
                    return EventResult.resultWith(EventResultEnum.ERROR, error_Elem.getText(), null);
                }
            }else {
                log.error("Server Request Failed:" + httpResult.getHttpContent());
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }

    }


    @Override
    public EventResult PushOrderWeiXin(Order order, GjbcData gjbcData) {
        try {
            WeiXinCustom weixinCustom = new WeiXinCustom();
            weixinCustom.setAppid(gjbcData.getWeiXinAppId());
            weixinCustom.setMchId(gjbcData.getWeixinMchId());
            weixinCustom.setOutTradeNo(order.getOrderId());
            weixinCustom.setTransactionId(order.getPayNumber());
            weixinCustom.setCustoms(GjbcEnum.CustomerEnum.HANGZHOU.toString());
            weixinCustom.setMchCustomsNo(gjbcData.getECommerceCode());
            weixinCustom.setSubOrderNo(order.getOrderId());
            weixinCustom.setFeeType("CNY");
            weixinCustom.setCertType("IDCARD");
            weixinCustom.setCertId(order.getBuyerPid());
            weixinCustom.setName(order.getBuyerName());

            //order_fee=transport_fee+product_fee  应付金额=物流费+商品价格
            int orderFee = (int) (order.getOnlinePayAmount() * 100);
            int transportFee = (int) (order.getCostFreight() * 100);
            weixinCustom.setOrderFee(orderFee);// 单位转换成分
            weixinCustom.setTransportFee(transportFee);// 单位转换成分
            weixinCustom.setProductFee(orderFee - transportFee);// 单位转换成分
            weixinCustom.setDuty(0);//关税为0
            Map<String,Object> requestMap = new TreeMap<>();
            requestMap.put("appId",weixinCustom.getAppid());
            requestMap.put("mchId",weixinCustom.getMchId());
            requestMap.put("outTradeNo",weixinCustom.getOutTradeNo());
            requestMap.put("transactionId",weixinCustom.getTransactionId());
            requestMap.put("customs",weixinCustom.getCustoms());
            requestMap.put("mchCustomsNo",weixinCustom.getMchCustomsNo());
            requestMap.put("subOrderNo",weixinCustom.getSubOrderNo());
            requestMap.put("freeType",weixinCustom.getFeeType());
            requestMap.put("certType",weixinCustom.getCertType());
            requestMap.put("certId",weixinCustom.getCertId());
            requestMap.put("name",weixinCustom.getName());
            requestMap.put("orderFee",weixinCustom.getOrderFee());
            requestMap.put("transportFee",weixinCustom.getTransportFee());
            requestMap.put("ProductFee",weixinCustom.getProductFee());
            requestMap.put("duty",weixinCustom.getDuty());
            String sign = DtwUtil.weixinBuildSign(requestMap, gjbcData.getWeixinKey());
            requestMap.put("sign", sign);
            weixinCustom.setSign(sign);

            String requestData = new XmlMapper().writeValueAsString(weixinCustom);
            HttpResult httpResult = HttpClientUtil.getInstance().post(GjbcConstant.WEIXIN_PAY_URL, requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                String httpContent = httpResult.getHttpContent();
                Element rootElement = DocumentHelper.parseText(httpContent).getRootElement();
                Element returnCodeElem = rootElement.element("return_code");
                if (returnCodeElem.getText().equals("SUCCESS")) {
                    Element result_Code = rootElement.element("result_code");
                    if (result_Code.getText().equals("SUCCESS")) {
                        log.info("WeiXin pay order push success");
                        return EventResult.resultWith(EventResultEnum.SUCCESS);
                    } else {
                        Element ErrorMessage= rootElement.element("err_code_des");
                        log.info("WeiXin pay order push failed:" + ErrorMessage.getText());
                        return EventResult.resultWith(EventResultEnum.ERROR, ErrorMessage.getText(), null);
                    }
                } else {
                    Element return_msg = rootElement.element("return_msg");
                    log.info("WeiXin pay order push failed:" + return_msg.getText());
                    return EventResult.resultWith(EventResultEnum.ERROR,return_msg.getText(),null);
                }
            } else {
                log.info("Server Request failed:" + httpResult.getHttpContent());
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        }catch (Exception e) {
            log.error(e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    @Override
    public EventResult PushOrderCustom(Order order, GjbcData gjbcData) {
            try {
                CustomOrder customOrder = converToCustomOrder(order, gjbcData);
                if (customOrder == null){
                    return EventResult.resultWith(EventResultEnum.ERROR,"支付方式暂时不支持",null);
                }
                String valueAsString = new XmlMapper().writeValueAsString(customOrder);
                valueAsString += "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
                int start = valueAsString.indexOf("<jkfOrderDetail>");
                int end = valueAsString.lastIndexOf("</jkfOrderDetail>");
                valueAsString = valueAsString.substring(0,start) + "<jkfOrderDetailList>" + valueAsString.substring(start+16,end)+"</jkfOrderDetailList>"+valueAsString.substring(end+17);

                //海关接口rsa加密
                byte[] bytes = valueAsString.getBytes("utf-8");
                byte[] privateKeyCode = java.util.Base64.getDecoder().decode(gjbcData.getRsaPrivateKey().getBytes("utf-8"));
                byte[] aesKeyCode = Base64.getDecoder().decode(gjbcData.getAesKey().getBytes("utf-8"));
                String sign = new String(Base64.getEncoder().encode(AESUtil.encrypt(bytes, privateKeyCode)),"utf-8");
                String encData = new String(Base64.getEncoder().encode(RSAUtil.sign(bytes,aesKeyCode)),"utf-8");
                String result = DtwUtil.requestCustomWebService(gjbcData.getCustomUrl(), encData, "IMPORTORDER", sign, gjbcData.getECommerceCode());
                Element rootElement= DocumentHelper.parseText(result).getRootElement();
                Element element = rootElement.element("body").element("list").element("jkfResult").element("chkMark");

                //数据无误且成功推送到海关
                if (element.getText().equals("1")) {
                    log.info("customerOrder push success");
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                } else {
                    String xpath = "/mo/body/list/jkfResult/resultList/jkfResultDetail/resultInfo";
                    List<Element> selectNodes = DocumentHelper.parseText(result).selectNodes(xpath);
                    StringBuffer errorMsg = new StringBuffer();
                    selectNodes.stream().forEach(a -> errorMsg.append(a.getText()).append(";"));
                    log.info("customerOrder push failed"+errorMsg);
                    return EventResult.resultWith(EventResultEnum.ERROR, errorMsg.toString(), null);
                }
            }catch (Exception e) {
                log.error(e.getMessage());
                return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
            }
    }


    private CustomOrder converToCustomOrder(Order order,GjbcData gjbcData){
        CustomHead customHead = new CustomHead();
        customHead.setBusinessType("IMPORTORDER");

        CustomSign customSign = new CustomSign();
        customSign.setCompanyCode(gjbcData.getECommerceCode());
        customSign.setBusinessNo(order.getOrderId());//业务编号 使用订单编号
        customSign.setBusinessType("IMPORTORDER");
        customSign.setDeclareType("1");// 固定填写1
        customSign.setNote("");

       CustomOrderHead customOrderHead = new CustomOrderHead();
       customOrderHead.setCommerceCode(gjbcData.getECommerceCode());
       customOrderHead.setCommerceName(gjbcData.getECommerceName());
       customOrderHead.setIeFlag("I");
       customOrderHead.setPayType("03");
        OrderEnum.PaymentOptions paymentOptions = EnumHelper.getEnumType(OrderEnum.PaymentOptions.class, order.getPayType());
        if (paymentOptions == OrderEnum.PaymentOptions.ALIPAY_MOBILE_WEB || paymentOptions == OrderEnum.PaymentOptions.ALIPAY_PC || paymentOptions == OrderEnum.PaymentOptions.ALIPAY_MOBILE) {
            customOrderHead.setCompanyCode(GjbcConstant.ALI_PAY_CUSTOM_CODE);
        }else if (paymentOptions == OrderEnum.PaymentOptions.WEIXINPAY_V3 || paymentOptions == OrderEnum.PaymentOptions.WEIXINPAY
                || paymentOptions == OrderEnum.PaymentOptions.WEIXINPAY_APP) {
            customOrderHead.setPayCompanyCode(GjbcConstant.WEIXIN_PAY_CUSTOM_CODE);
        } else {
            return null;
        }

        customOrderHead.setPayNumber(order.getPayNumber());
        customOrderHead.setOrderTotalAmount(order.getFinalAmount());
        customOrderHead.setOrderNo(order.getOrderId());
        customOrderHead.setFeeAmount(order.getCostFreight());
        customOrderHead.setInsureAmount(0.0);
        customOrderHead.setCompanyCode(gjbcData.getECommerceCode());
        customOrderHead.setCompanyName(gjbcData.getECommerceName());
        customOrderHead.setTradeTime(order.getPayTime());
        customOrderHead.setCurrCode(GjbcEnum.CurrencyEnum.RMB.getCode());
        customOrderHead.setDiscount(Arith.sub(order.getFinalAmount(), order.getOnlinePayAmount()));

        customOrderHead.setConsigneeEmail(order.getShipEmail());
        customOrderHead.setConsigneeTel(order.getShipMobile());
        customOrderHead.setConsignee(order.getShipName());
        customOrderHead.setConsigneeAddress(order.getShipAddr());
        customOrderHead.setTotalCount(order.getItemNum());
        customOrderHead.setSenderCountry(GjbcEnum.CountryEnum.CHINA.getCode());

        String[] split = gjbcData.getSenderInfo().split(",");
        customOrderHead.setSenderName(split[0]);
        customOrderHead.setPurchaserId(String.valueOf(order.getMemberId()));
        customOrderHead.setLogisCompanyCode(GjbcConstant.LOGISTIC_CODE);
        customOrderHead.setLogisCompanyName(GjbcConstant.LOGISTIC_NAME);
        customOrderHead.setZipCode(order.getShipZip());
        customOrderHead.setNote(order.getMemo());
        customOrderHead.setRate("1");//人民币，统一填写1
        customOrderHead.setUserProcotol(GjbcConstant.USER_PROCOTOL);

        List<CustomOrderDetail> customOrderDetails = new ArrayList<>();
        List<OrderItem> orderItems = order.getOrderItems();
        for (int i= 0 ; i<orderItems.size(); i++){
            OrderItem orderItem = orderItems.get(i);
            CustomOrderDetail customOrderDetail = new CustomOrderDetail();
            customOrderDetail.setGoodsOrder(i);
            customOrderDetail.setGoodsName(orderItem.getName());
            customOrderDetail.setGoodsModel(orderItem.getStandard());
            customOrderDetail.setCodeTs(orderItem.getGoodBn());
            customOrderDetail.setOriginCountry(GjbcEnum.CountryEnum.CHINA.getCode());
            customOrderDetail.setUnitPrice(orderItem.getPrice());
            customOrderDetail.setGoodsCount(orderItem.getNum());
            customOrderDetail.setGoodsUnit(GjbcEnum.UnitEnum.JIAN.getCode());
            customOrderDetail.setCurrency(GjbcEnum.CurrencyEnum.RMB.getCode());
            customOrderDetails.add(customOrderDetail);
        }

        CustomGoodsPurchaser customGoodsPurchaser = new CustomGoodsPurchaser();
        customGoodsPurchaser.setId(String.valueOf(order.getMemberId()));
        customGoodsPurchaser.setName(order.getBuyerName());
        customGoodsPurchaser.setEmail("");//注册邮箱 非必填
        customGoodsPurchaser.setTelNumber(order.getUserLoginName());
        customGoodsPurchaser.setPaperType("01");
        customGoodsPurchaser.setPaperNumber(order.getBuyerPid());
        customOrderHead.setOrderTaxAmount(order.getTaxAmount());
        customOrderHead.setOrderGoodsAmount(order.getCostItem());
        customOrderHead.setTotalAmount(order.getCostItem());

        CustomOrderInfoList customOrderInfoList = new CustomOrderInfoList();
        CustomOrderInfo customOrderInfo = new CustomOrderInfo();
        customOrderInfo.setCustomSign(customSign);
        customOrderInfo.setCustomOrderHead(customOrderHead);
        customOrderInfo.setCustomOrderDetailList(customOrderDetails);
        customOrderInfo.setCustomGoodsPurchaser(customGoodsPurchaser);
        customOrderInfoList.setCustomOrderInfo(customOrderInfo);

        CustomBody customBody = new CustomBody();
        customBody.setOrerInfoList(customOrderInfoList);

        CustomOrder customOrder = new CustomOrder();
        customOrder.setBody(customBody);
        customOrder.setHead(customHead);

        System.out.println("\n**************海关订单费用**********************");
        System.out.println("税费:" + order.getTaxAmount());
        System.out.println("贷款：" + customOrderHead.getOrderGoodsAmount());
        System.out.println("成交总价：" + customOrderHead.getTotalAmount());
        System.out.println("订单总金额:" + customOrderHead.getOrderTotalAmount());
        System.out.println("\n**************费用**********************");

        log.info("\n**************CustomerOrder fee info**********************");
        log.info("tax:" + order.getTaxAmount());
        log.info("goodsAmount：" + customOrderHead.getOrderGoodsAmount());
        log.info("totalAmount：" + customOrderHead.getTotalAmount());
        log.info("order totalAmount:" + customOrderHead.getOrderTotalAmount());
        log.info("\n**********************************************************");
        //TODO 海关推送
        return  customOrder;
    }
}
