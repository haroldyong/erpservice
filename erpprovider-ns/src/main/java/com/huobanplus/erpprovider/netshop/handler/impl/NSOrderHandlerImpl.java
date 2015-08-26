package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.netshop.bean.NSOrderDetailResult;
import com.huobanplus.erpprovider.netshop.bean.NSOrderItemResult;
import com.huobanplus.erpprovider.netshop.bean.NSOrderListResult;
import com.huobanplus.erpprovider.netshop.handler.NSOrderHandler;
import com.huobanplus.erpprovider.netshop.net.HttpUtil;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpprovider.netshop.util.SignBuilder;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 订单信息处理实现类
 */
@Service
public class NSOrderHandlerImpl implements NSOrderHandler {

    @Autowired
    private MallOrderService mallOrderService;

    @Override
    public Monitor<EventResult> commitOrderInfo(HttpServletRequest request) throws IOException {
        //todo 暂时木有
        return null;
    }

    @Override
    public Monitor<EventResult> orderStatusInfo(HttpServletRequest request) throws IOException {
        //todo 暂时木有
        return null;
    }

    @Override
    public Monitor<EventResult> obtainOrderInfoList(HttpServletRequest request) throws IOException {
        String sign = request.getParameter(Constant.SIGN_PARAM);
        String uCode = request.getParameter(Constant.SIGN_U_CODE);
        String orderStatus = request.getParameter("OrderStatus");
        String pageSize = request.getParameter("PageSize");
        String page = request.getParameter("Page");
        Map<String, String> signMap = new TreeMap<>();
        signMap.put(Constant.SIGN_U_CODE, uCode);
        signMap.put(Constant.SIGN_M_TYPE, request.getParameter(Constant.SIGN_M_TYPE));
        signMap.put(Constant.SIGN_TIME_STAMP, request.getParameter(Constant.SIGN_TIME_STAMP));
        signMap.put("OrderStatus", orderStatus);
        signMap.put("PageSize", pageSize);
        signMap.put("Page", page);

        //订单参数
        String signStr = SignBuilder.buildSign(signMap, StringUtil.NETSHOP_SECRET, StringUtil.NETSHOP_SECRET);

        if (null == sign || !signStr.equals(sign)) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>签名不正确</Cause></Order>"));
        } else {
            try {
                Page<MallOrderBean> orderList = mallOrderService.findAll(Integer.parseInt(orderStatus), null, null, Integer.parseInt(page), Integer.parseInt(pageSize));
                NSOrderListResult orderListResult = new NSOrderListResult();
                orderListResult.setOrderCount(String.valueOf(orderList.getTotalElements()));
                orderListResult.setPage(page);
                orderListResult.setResult("1");
                List<String> orderNo = new ArrayList<>();
                orderList.getContent().forEach(order -> orderNo.add(order.getOrderId()));
                orderListResult.setOrderNo(orderNo);
                String orderResultXml = new XmlMapper().writeValueAsString(orderListResult);
                int firstIndex = orderResultXml.indexOf("<OrderNO>");
                int lastIndex = orderResultXml.lastIndexOf("</OrderNO>");
                String firstPanel = orderResultXml.substring(0, firstIndex);
                String orderPanel = orderResultXml.substring(firstIndex + 9, lastIndex);
                String lastPanel = orderResultXml.substring(lastIndex + 10);
                String xmlResult = firstPanel + "<OrderList>" + orderPanel + "</OrderList>" + lastPanel;
                //获取伙伴商城的订单信息
                return new BaseMonitor<>(new EventResult(1, xmlResult));
            } catch (JsonParseException e) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>数据解析失败</Cause></Order>"));
            } catch (JsonMappingException e) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>数据解析失败</Cause></Order>"));
            } catch (IOException e) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>客户端请求失败</Cause></Order>"));
            } catch (Exception e) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>客户端请求失败</Cause></Order>"));
            }
        }
    }

    @Override
    public Monitor<EventResult> obtainOrderInfo(HttpServletRequest request) throws IOException {
        String sign = request.getParameter(Constant.SIGN_PARAM);
        String uCode = request.getParameter(Constant.SIGN_U_CODE);
        String orderNo = request.getParameter("OrderNO");
        Map<String, String> signMap = new TreeMap<>();
        signMap.put(Constant.SIGN_U_CODE, uCode);
        signMap.put(Constant.SIGN_M_TYPE, request.getParameter(Constant.SIGN_M_TYPE));
        signMap.put(Constant.SIGN_TIME_STAMP, request.getParameter(Constant.SIGN_TIME_STAMP));
        signMap.put("OrderNO", orderNo);
        String signStr = SignBuilder.buildSign(signMap, StringUtil.NETSHOP_SECRET, StringUtil.NETSHOP_SECRET);

        if (null == sign || !signStr.equals(sign)) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>签名不正确</Cause></Order>"));
        } else {
            try {
                MallOrderBean orderBean = mallOrderService.findByOrderId(orderNo);
                NSOrderDetailResult orderDetailResult = new NSOrderDetailResult();
                orderDetailResult.setOrderNo(orderBean.getOrderId());
                orderDetailResult.setResult(1);
                orderDetailResult.setDateTime(StringUtil.DateFormat(orderBean.getPayTime(), Constant.TIME_FORMAT_ONE));
                orderDetailResult.setBuyerId(orderBean.getBuyerId());
                orderDetailResult.setBuyerName(orderBean.getReceiverName());
                orderDetailResult.setProvince(orderBean.getProvince());
                orderDetailResult.setCity(orderBean.getCity());
                orderDetailResult.setTown(orderBean.getDistrict());
                orderDetailResult.setAdr(orderBean.getAddress());
                orderDetailResult.setZip(orderBean.getPost());
                orderDetailResult.setEmail(orderBean.getEmail());
                orderDetailResult.setPhone(orderBean.getReceiverMobile());
                orderDetailResult.setTotal(orderBean.getOrderTotalFee());
                orderDetailResult.setPostage(orderBean.getRealPayFreight());
                orderDetailResult.setPayAccount(orderBean.getPayMothed());
                orderDetailResult.setPayID(orderBean.getPayId());
                orderDetailResult.setLogisticsName(orderBean.getSendingType());
                orderDetailResult.setChargetype(orderBean.getChargeType());
                orderDetailResult.setCustomerRemark(orderBean.getBuyerMessage());
                orderDetailResult.setInvoiceTitle(orderBean.getInvoiceTitle());
                orderDetailResult.setRemark(orderBean.getServiceRemarks());
                List<NSOrderItemResult> orderItemResults = new ArrayList<>();
                orderBean.getOrderItems().forEach(item -> {
                    NSOrderItemResult orderItemResult = new NSOrderItemResult();
                    orderItemResult.setGoodsID(item.getProductNo());
                    orderItemResult.setGoodsName(item.getProName());
                    orderItemResult.setGoodsSpec(item.getSpecification());
                    orderItemResult.setCount(item.getProNum());
                    orderItemResult.setPrice(item.getSellPrice());
                    orderItemResults.add(orderItemResult);
                });
                String resultXml = new XmlMapper().writeValueAsString(orderDetailResult);

                return new BaseMonitor<>(new EventResult(1, resultXml));
            } catch (JsonParseException e) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>数据解析失败</Cause></Order>"));
            } catch (JsonMappingException e) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>数据解析失败</Cause></Order>"));
            } catch (IOException e) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>客户端请求失败</Cause></Order>"));
            } catch (Exception e) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>客户端请求失败</Cause></Order>"));
            }

        }
    }
}
