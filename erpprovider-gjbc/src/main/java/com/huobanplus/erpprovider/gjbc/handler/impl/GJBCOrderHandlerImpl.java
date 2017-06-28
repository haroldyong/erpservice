package com.huobanplus.erpprovider.gjbc.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.gjbc.common.GJBCEnum;
import com.huobanplus.erpprovider.gjbc.common.GJBCSysData;
import com.huobanplus.erpprovider.gjbc.formatgjbc.GJBCGoodsItemsInfo;
import com.huobanplus.erpprovider.gjbc.formatgjbc.GJBCOrderInfo;
import com.huobanplus.erpprovider.gjbc.formatgjbc.GjbcAllOrderStatus;
import com.huobanplus.erpprovider.gjbc.handler.BaseHandler;
import com.huobanplus.erpprovider.gjbc.handler.GJBCOrderHandler;
import com.huobanplus.erpprovider.gjbc.util.GJBCconstant;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hxh on 2017-06-26.
 */
@Component
public class GJBCOrderHandlerImpl extends BaseHandler implements GJBCOrderHandler {
    private static final Log log = LogFactory.getLog(GJBCOrderHandlerImpl.class);

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        try {
            Order order = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
            ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
            GJBCSysData gjbcSysdata = JSON.parseObject(erpInfo.getSysDataJson(), GJBCSysData.class);
            ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
            GJBCOrderInfo gjbcOrderInfo = new GJBCOrderInfo();
            gjbcOrderInfo.setOrder_sn(order.getOrderId());
            /*发件人和收件人信息*/
            String[] senderAndAddresseeInfo = gjbcSysdata.getSenderAndAddresseeInfo().split(",");
            gjbcOrderInfo.setSender_name(senderAndAddresseeInfo[0]);
            gjbcOrderInfo.setSender_city(senderAndAddresseeInfo[1]);
            gjbcOrderInfo.setSender_address(senderAndAddresseeInfo[2]);
            gjbcOrderInfo.setSender_phone(senderAndAddresseeInfo[3]);
            gjbcOrderInfo.setSender_country_code(senderAndAddresseeInfo[4]);
            gjbcOrderInfo.setBuyer_idcard(senderAndAddresseeInfo[5]);
            gjbcOrderInfo.setBuyer_name(order.getShipName());
            gjbcOrderInfo.setBuyer_phone(order.getShipMobile());
            gjbcOrderInfo.setOrder_name(order.getBuyerName());
            gjbcOrderInfo.setOrder_idcard(order.getBuyerPid());
            gjbcOrderInfo.setCustoms_discount(order.getPmtAmount());
            gjbcOrderInfo.setOrder_phone(order.getUserLoginName());
            gjbcOrderInfo.setProvince_code(order.getProvince());
            gjbcOrderInfo.setBuyer_address(order.getShipAddr());
            gjbcOrderInfo.setCurr(Integer.parseInt(order.getCurrency()));
            gjbcOrderInfo.setP_no(order.getPayNumber());
            String payTime = order.getPayTime();
            if (!StringUtils.isEmpty(payTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat(GJBCconstant.TIME_PATTERN);
                try {
                    Date payDate = sdf.parse(payTime);
                    gjbcOrderInfo.setP_time((int) payDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            gjbcOrderInfo.setSh_fee(order.getCostFreight());
            gjbcOrderInfo.setCus_tax(order.getTaxAmount());
            gjbcOrderInfo.setPweb(gjbcSysdata.getPWeb());
            gjbcOrderInfo.setWeb_name(gjbcSysdata.getWebName());
            /*商家备案*/
            String[] reInfo = gjbcSysdata.getReInfo().split(",");
            gjbcOrderInfo.setRe_no(reInfo[0]);
            gjbcOrderInfo.setRe_no_qg(reInfo[1]);
            gjbcOrderInfo.setRe_name(reInfo[2]);
            gjbcOrderInfo.setExpress_code(order.getLogiName());
            gjbcOrderInfo.setOrder_amount(order.getFinalAmount());
            List<OrderItem> orderItems = order.getOrderItems();
            GJBCGoodsItemsInfo[] goodsItemsInfos = new GJBCGoodsItemsInfo[orderItems.size()];
            for (int i = 0; i < orderItems.size(); i++) {
                GJBCGoodsItemsInfo gjbcGoodsItemsInfo = new GJBCGoodsItemsInfo();
                gjbcGoodsItemsInfo.setGoods_seq(orderItems.get(i).getGoodId());
                gjbcGoodsItemsInfo.setGoods_barcode(orderItems.get(i).getGoodBn());
                gjbcGoodsItemsInfo.setGoods_unit(GJBCEnum.UnitEnum.JIAN.getCode());
                gjbcGoodsItemsInfo.setGoods_size(GJBCEnum.UnitEnum.KG.getCode());
                gjbcGoodsItemsInfo.setGoods_hg_num(10);
                gjbcGoodsItemsInfo.setGoods_gweight(orderItems.get(i).getWeight());
                gjbcGoodsItemsInfo.setGoods_name(orderItems.get(i).getName());
                gjbcGoodsItemsInfo.setBrand(orderItems.get(i).getProductBn());
                gjbcGoodsItemsInfo.setGoods_spec(orderItems.get(i).getBrief());
                gjbcGoodsItemsInfo.setGoods_num(orderItems.get(i).getNum() + "");
                gjbcGoodsItemsInfo.setGoods_price(orderItems.get(i).getPrice());
                gjbcGoodsItemsInfo.setYcg_code(GJBCEnum.CountryEnum.CHINA.getCode());
                gjbcGoodsItemsInfo.setGoods_total(orderItems.get(i).getPrice() * orderItems.get(i).getNum());
                gjbcGoodsItemsInfo.setGoods_commonid(Integer.parseInt(orderItems.get(i).getGoodBn()));
                goodsItemsInfos[i] = gjbcGoodsItemsInfo;
            }
            gjbcOrderInfo.setOrder_goods(goodsItemsInfos);
            GJBCOrderInfo[] gjbcOrderInfos = {gjbcOrderInfo};
            String gjbcOrderInfosJson = JSON.toJSONString(gjbcOrderInfos);
            Map<String, Object> requestData = null;
            try {
                requestData = getSysRequestData(gjbcSysdata);
                requestData.put("order", Base64.encode(Base64.encode(gjbcOrderInfosJson
                        .getBytes("utf-8")).getBytes("utf-8")));
            } catch (UnsupportedEncodingException e) {
                log.info("Exception：" + e.getMessage());
                return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
            }
            HttpResult httpResult = HttpClientUtil.getInstance().post(gjbcSysdata.getRequestUrl(), requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject jsonObject = JSON.parseObject(httpResult.getHttpContent());
                if ("ok".equals(jsonObject.get("flag"))) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }
                return EventResult.resultWith(EventResultEnum.ERROR, jsonObject.getString("info"), null);
            }
            return null;
        } catch (Exception e) {
            log.info("Exception：" + e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    @Override
    public EventResult pushAliPayOrder(Order order, GJBCSysData gjbcSysData) {
        return null;
    }

    /**
     * 推送个人订单，订单报文和支付订单报文
     *
     * @return
     */
    private EventResult pushThreeOrder(Order order, GJBCSysData gjbcSysData, GjbcAllOrderStatus gjbcAllOrderStatus) {

        return null;
    }
}
