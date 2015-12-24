package com.huobanplus.erpservice.sandbox.controller;

import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.util.ClassUtil;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.datacenter.entity.MallOrderBean;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.datacenter.model.OrderSearchInfo;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.hotapi.common.HotApiConstant;
import com.huobanplus.erpservice.hotapi.jsonmodel.OrderList;
import com.huobanplus.erpservice.sandbox.common.SBBootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by allan on 12/10/15.
 */
@Controller
@RequestMapping("/erpService/sandbox/rest")
public class SBHotApiController {
    @Autowired
    private MallOrderService orderService;

    @RequestMapping(value = "/order/index", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> orderIndex(String eventType, HttpServletRequest request) {
        Map<String, Object> requestMap = (Map<String, Object>) request.getAttribute("requestMap");
        Map<String, Object> responseData = new HashMap<>();
        switch (eventType) {
            case HotApiConstant.OBTAIN_ORDER_LIST:
                Integer pageIndex = StringUtil.getWithDefault(request.getParameter("pageIndex"), 1);
                Integer pageSize = StringUtil.getWithDefault(request.getParameter("pageSize"), 10);
                Integer orderStatus = StringUtil.getWithDefault(request.getParameter("orderStatus"), -2);
                Integer shipStatus = StringUtil.getWithDefault(request.getParameter("shipStatus"), -1);
                Integer payStatus = StringUtil.getWithDefault(request.getParameter("payStatus"), -1);
                String beginTime = request.getParameter("beginTime");
                String endTime = request.getParameter("endTime");
                String beginPayTime = request.getParameter("beginPayTime");
                String endPayTime = request.getParameter("endPayTime");
                String beginUpdateTime = request.getParameter("beginUpdateTime");
                String endUpdateTime = request.getParameter("endUpdateTime");
                String orderBy = StringUtil.getWithDefault(request.getParameter("orderBy"), "createTime");
                String orderType = StringUtil.getWithDefault(request.getParameter("orderType"), "desc");

                OrderSearchInfo orderSearchInfo = new OrderSearchInfo();
                orderSearchInfo.setOrderStatus(EnumHelper.getEnumType(OrderEnum.OrderStatus.class, orderStatus));
                orderSearchInfo.setShipStatus(EnumHelper.getEnumType(OrderEnum.ShipStatus.class, shipStatus));
                orderSearchInfo.setPayStatus(EnumHelper.getEnumType(OrderEnum.PayStatus.class, payStatus));
                orderSearchInfo.setBeginTime(beginTime);
                orderSearchInfo.setEndTime(endTime);
                orderSearchInfo.setBeginPayTime(beginPayTime);
                orderSearchInfo.setEndPayTime(endPayTime);
                orderSearchInfo.setBeginUpdateTime(beginUpdateTime);
                orderSearchInfo.setEndUpdateTime(endUpdateTime);
                orderSearchInfo.setOrderBy(orderBy);
                orderSearchInfo.setOrderType(orderType);

                Page<MallOrderBean> orderBeans = orderService.findAll(orderSearchInfo, pageIndex, pageSize);
                List<Order> orders = new ArrayList<>();
                orderBeans.getContent().forEach(o -> {
                    Order order = new Order();
                    try {
                        ClassUtil.cloneClass(o, order);
                        order.setLastUpdateTime(StringUtil.DateFormat(o.getLastUpdateTime(), StringUtil.TIME_PATTERN));
                        order.setCreateTime(StringUtil.DateFormat(o.getCreateTime(), StringUtil.TIME_PATTERN));
                        order.setPayTime(StringUtil.DateFormat(o.getPayTime(), StringUtil.TIME_PATTERN));
                    } catch (ReflectiveOperationException e) {
                        e.printStackTrace();
                    }
                    orders.add(order);
                });
                OrderList orderList = new OrderList();
                orderList.setRecordCount(orders.size());
                orderList.setPageIndex(pageIndex);
                orderList.setPageSize(pageSize);
                orderList.setOrderBy(orderBy);
                orderList.setOrderType(orderType);
                orderList.setOrders(orders);
                responseData.put("requestData", requestMap);
                responseData.put("responseData", ApiResult.resultWith(ResultCode.SUCCESS, orderList));
                break;
        }
        return responseData;
    }
}
