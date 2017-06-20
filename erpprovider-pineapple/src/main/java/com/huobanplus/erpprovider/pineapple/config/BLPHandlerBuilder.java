package com.huobanplus.erpprovider.pineapple.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.pineapple.bean.BLPSysData;
import com.huobanplus.erpprovider.pineapple.handler.BLPOrderHandler;
import com.huobanplus.erpprovider.pineapple.handler.BLPProductHandler;
import com.huobanplus.erpprovider.pineapple.util.BLPConstant;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPSysDataInfo;
import com.huobanplus.erpservice.datacenter.service.ERPBaseConfigService;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.ERPSysDataInfoService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by hxh on 2017-06-06.
 */
@Component
public class BLPHandlerBuilder implements ERPHandlerBuilder {

    @Autowired
    private ERPSysDataInfoService sysDataInfoService;

    @Autowired
    private ERPDetailConfigService detailConfigService;

    @Autowired
    private BLPOrderHandler blpOrderHandler;

    @Autowired
    private BLPProductHandler blpProductHandler;

    @Autowired
    private ERPBaseConfigService erpBaseConfigService;

    /**
     * 根据erp信息判断是否由该erp-provider处理
     *
     * @param erpInfo
     * @return 无法处理返回空，可以处理返回该erp事件处理器
     */
    @Override
    public ERPHandler buildHandler(ERPInfo erpInfo) {
        if (erpInfo.getErpType() == ERPTypeEnum.ProviderType.BLP) {
            return new ERPHandler() {
                @Override
                public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                    return null;
                }

                @Override
                public EventResult handleRequest(HttpServletRequest request, ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType) {
                    String method = request.getParameter("method");
                    String requestSign = request.getParameter("sign");
                    if (StringUtils.isEmpty(requestSign)) {
                        return EventResult.resultWith(EventResultEnum.NO_SIGN,"sign签名未传");
                    }
                    //签名验证
                    Map<String, String[]> paramMap = request.getParameterMap();
                    paramMap.remove("sign");
                    Map<String, Object> signMap = new TreeMap<>(new Comparator() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            String str1 = (String) o1;
                            String str2 = (String) o2;
                            return str1.toLowerCase().compareTo(str2.toLowerCase());
                        }
                    });
                    //根据akkKey查询指定erp信息
                    List<ERPSysDataInfo> sysDataInfos = sysDataInfoService.findByErpTypeAndErpUserTypeAndParamNameAndParamVal(providerType, erpUserType, "appkey", paramMap.get("appkey")[0]);
                    ERPDetailConfigEntity erpDetailConfig = detailConfigService.findBySysData(sysDataInfos, providerType, erpUserType);
                    BLPSysData blpSysData = JSON.parseObject(erpDetailConfig.getErpSysData(), BLPSysData.class);
                    String appSecret = blpSysData.getAppSecret();
                    String sign;
                    try {
                        sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, appSecret);
                    } catch (UnsupportedEncodingException e) {
                        return EventResult.resultWith(EventResultEnum.WRONG_SIGN,"签名格式不对");
                    }
                    if (sign.toLowerCase().equals(requestSign)) {
                        //开始处理
                        //得到erpUserInfo
                        ERPUserInfo erpUserInfo = new ERPUserInfo(erpUserType, erpDetailConfig.getCustomerId());
                        String requestData = "";
                        JSONObject json = null;
                        switch (method) {
                            case BLPConstant.OBTAIN_ORDER_LIST:
                                /**获取请求参数*/
                                requestData = request.getParameter("bizcontent");
                                json = JSONObject.parseObject(requestData);
                                int orderStatus = Integer.parseInt(json.getString("OrderStatus"));
                                int pageSize = Integer.parseInt(json.getString("PageSize"));
                                Integer pageIndex = Integer.parseInt(json.getString("PageIndex"));
                                String platOrderNo = json.getString("PlatOrderNo");
                                String startTime = json.getString("StartTime");
                                String endTime = json.getString("EndTime");
                                return blpOrderHandler.obtainOrderInfoList(platOrderNo, orderStatus, pageSize, pageIndex, startTime, method, erpUserInfo, endTime);
                            case BLPConstant.SYNC_STOCK:
                                ERPBaseConfigEntity erpBaseConfigEntity = erpBaseConfigService.findByCustomerId(erpDetailConfig.getCustomerId(), erpUserType);
                                /**判断是否开启库存同步*/
                                if (erpBaseConfigEntity.getIsSyncInventory() == 1) {
                                    /**获取请求参数*/
                                    requestData = request.getParameter("bizcontent");
                                    json = JSONObject.parseObject(requestData);
                                    String platProductId = json.getString("PlatProductID");
                                    String skuId = json.getString("PlatProductID");
                                    String outId = json.getString("OuterID");
                                    int quantity = Integer.parseInt(json.getString("Quantity"));
                                    String outSkuId = json.getString("OutSkuID");
                                    return blpProductHandler.syncStock(platProductId, skuId, outId, outSkuId, quantity, erpUserInfo, method);
                                } else {
                                    return EventResult.resultWith(EventResultEnum.ERROR, "平台未开启库存同步");
                                }
                            case BLPConstant.DELIVER_INFO:
                                /**获取请求参数*/
                                requestData = request.getParameter("bizcontent");
                                json = JSONObject.parseObject(requestData);
                                String orderId = json.getString("PlatOrderNo");
                                String logiName = json.getString("LogisticName");
                                String logiNo = json.getString("LogisticNo");
                                if (StringUtils.isEmpty(orderId)) {
                                    return EventResult.resultWith(EventResultEnum.BAD_REQUEST_PARAM,"订单号未传");
                                }
                                return blpOrderHandler.deliverOrder(orderId, logiName, logiNo, erpUserInfo, method);
                        }
                        return EventResult.resultWith(EventResultEnum.NO_DATA,"未找到数据源信息");
                    } else {
                        return EventResult.resultWith(EventResultEnum.WRONG_SIGN,"签名错误");
                    }
                }
            };
        }
        return null;
    }
}
