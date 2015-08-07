package com.huobanplus.erpservice.transit.controller;

import com.huobanplus.erpservice.commons.utils.ResponseCode;
import com.huobanplus.erpservice.commons.utils.SecurityUtils;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.bean.MallPaymentBean;
import com.huobanplus.erpservice.datacenter.bean.MallProductBean;
import com.huobanplus.erpservice.datacenter.repository.MallOrderRepository;
import com.huobanplus.erpservice.datacenter.repository.MallPaymentRepository;
import com.huobanplus.erpservice.datacenter.repository.MallProductRepository;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.event.erpevent.CreateOrderEvent;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.event.handler.ERPRegister;
import com.huobanplus.erpservice.event.model.ERPInfo;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>和伙伴客交互的控制层</p>
 */
@Controller
@RequestMapping("/app")
public class ObtainDataController {

    @Resource
    private ERPRegister erpRegister;
    @Autowired
    private MallOrderService orderService;
    @Resource
    private MallPaymentRepository paymentRepository;
    @Resource
    private MallProductRepository productRepository;

    /**
     * 获取伙伴客的订单数据，并插入数据库
     *
     * @param orderId 订单编号
     * @param syncStatus 同步标记，1表示已抓取
     * @param memberId 会员ID
     * @param orderStatus 订单状态
     * @param payStatus 支付状态
     * @param shipStatus 发货状态
     * @param memberStatus 会员状态
     * @param isDelivery 是否需要发货
     * @param deliverMethodId 配送方式id
     * @param deliverMethod 配送方式
     * @param deliverArea 配送区域
     * @param weight 重量
     * @param orderName 订单名称
     * @param itemNum 商品数量
     * @param actTime 活动时间
     * @param createTime 创建时间
     * @param createIP 创建ip
     * @param shipName 收货人
     * @param shipAddr 收货人地址
     * @param shipZip 收货人邮编
     * @param shipTel 收货人电话
     * @param shipEmail 收货人邮箱
     * @param shipTime 收货人建议送货时间
     * @param shipMobile 收货人手机
     * @param costItem 商品总金额
     * @param isTax 是否开发票
     * @param costTax 税金
     * @param taxCompany 开票公司抬头
     * @param costFreight 配送费用
     * @param isProtect 是否保价
     * @param costProtect 保价费用
     * @param costPayment 支付手续费
     * @param scoreU 抵用积分
     * @param discount 折扣省下的金额
     * @param usePmt 是否使用优惠
     * @param totalAmount 订单总金额
     * @param finalAmount 最终金额
     * @param pmtAmount 优惠金额
     * @param payed 用余额支付的金额
     * @param memo 订单备注
     * @param lastChangeTime 最后变动时间
     * @param customerId 商户Id
     * @param cashOnDly 是否货到付款
     * @param onlinePayType 在线支付方式
     * @param scoreUAmount 积分抵用金额
     * @param payAgentId 代付人ID
     * @param payAgentScore 代付积分
     * @param payAgentScoreAmount 代付积分金额
     * @param payAgentPayed 余额代付
     * @param hasPayed 订单已付金额
     * @param hasPayedScore 订单已付积分
     * @param onlineAmount 在线支付金额
     * @param hongbaoAmount 红包抵用金额
     * @param payTime 支付时间
     * @param virtualRecMobile 虚拟商品收货手机
     * @param sign 鉴权
     * @param appKey appkey
     * @param operation 操作平台
     * @param capCode 平台码
     * @param timeStamp 时间戳
     * @param erpName erp名称
     * @return 返回map数据
     */
    @ResponseBody
    @RequestMapping(value = "/commitOrderInfo", method = RequestMethod.POST)
    public Map obtainOrderInfo(String orderId,
                               @RequestParam(value = "syncStatus",required = false, defaultValue = "0") int syncStatus,
                               String memberId,
                               String orderStatus,
                               String payStatus,
                               String shipStatus,
                               String memberStatus,
                               int isDelivery,
                               @RequestParam(value = "deliverMethodId", required = false, defaultValue = "0") String deliverMethodId,
                               String deliverMethod,
                               String deliverArea,
                               String weight,
                               String orderName,
                               int itemNum,
                               @RequestParam(value = "actTime", required = false, defaultValue = "0") long actTime,
                               @RequestParam(value = "createTime", required = false, defaultValue = "0") long createTime,
                               @RequestParam(value = "createIP", required = false, defaultValue = "") String createIP,
                               String shipName,
                               String shipAddr,
                               @RequestParam(value = "shipZip", required = false, defaultValue = "") String shipZip,
                               String shipTel,
                               @RequestParam(value = "shipEmail", required = false, defaultValue = "") String shipEmail,
                               @RequestParam(value = "shipTime", required = false, defaultValue = "") long shipTime,
                               String shipMobile,
                               float costItem,
                               @RequestParam(value = "isTax", required = false, defaultValue = "0") int isTax,
                               @RequestParam(value = "costTax", required = false, defaultValue = "0") float costTax,
                               @RequestParam(value = "taxCompany", required = false, defaultValue = "") String taxCompany,
                               @RequestParam(value = "costFreight", required = false, defaultValue = "0") float costFreight,
                               @RequestParam(value = "isProtect", required = false, defaultValue = "0") String isProtect,
                               @RequestParam(value = "costProtect", required = false, defaultValue = "0") float costProtect,
                               String costPayment,
                               @RequestParam(value = "scoreU", required = false, defaultValue = "0") float scoreU,
                               @RequestParam(value = "discount", required = false, defaultValue = "0") float discount,
                               @RequestParam(value = "usePmt", required = false, defaultValue = "") String usePmt,
                               float totalAmount,
                               float finalAmount,
                               @RequestParam(value = "pmtAmount", required = false, defaultValue = "0") float pmtAmount,
                               @RequestParam(value = "payed", required = false, defaultValue = "0") float payed,
                               @RequestParam(value = "memo", required = false, defaultValue = "") String memo,
                               @RequestParam(value = "lastChangeTime", required = false, defaultValue = "0") long lastChangeTime,
                               int customerId,
                               @RequestParam(value = "cashOnDly", required = false, defaultValue = "0") int cashOnDly,
                               String onlinePayType,
                               @RequestParam(value = "scoreUAmount", required = false, defaultValue = "0") float scoreUAmount,
                               @RequestParam(value = "payAgentId", required = false, defaultValue = "0") String payAgentId,
                               @RequestParam(value = "payAgentScore", required = false, defaultValue = "0") float payAgentScore,
                               @RequestParam(value = "payAgentScoreAmount", required = false, defaultValue = "0") float payAgentScoreAmount,
                               @RequestParam(value = "payAgentPayed", required = false, defaultValue = "0") float payAgentPayed,
                               float hasPayed,
                               @RequestParam(value = "hasPayedScore", required = false, defaultValue = "0") float hasPayedScore,
                               float onlineAmount,
                               @RequestParam(value = "hongbaoAmount", required = false, defaultValue = "0") float hongbaoAmount,
                               @RequestParam(value = "payTime", required = false, defaultValue = "0") long payTime,
                               @RequestParam(value = "virtualRecMobile", required = false, defaultValue = "") String virtualRecMobile,
                               String sign,
                               String appKey,
                               String operation,
                               String capCode,
                               String timeStamp,
                               String erpName) {
        Map<String, String> resultMap;
        MallOrderBean order;
        if (!SecurityUtils.getInstance().validateSign(sign, appKey, operation, capCode, timeStamp)) {
            resultMap = new HashMap<String, String>();
            resultMap.put("resut_message", "sign验证失败");
            resultMap.put("resut_code", ResponseCode.SING_ERROR);
            return resultMap;
        } else {
            order = new MallOrderBean();
            order.setOrderCode(orderId);
            order.setSyncStatus(syncStatus);
            order.setCustomerId(memberId);
            order.setOrderStatus(orderStatus);
            order.setPayStatus(payStatus);
            order.setDeliveryStatus(shipStatus);
            order.setCustomerStatus(memberStatus);
            order.setIsDelivery(isDelivery);
            order.setSendingTypeId(deliverMethodId);
            order.setSendingType(deliverMethod);
            order.setSendingArea(deliverArea);
            order.setWeight(weight);
            order.setOrderName(orderName);
            order.setItemNum(itemNum);
            order.setBeginTime(new Date(actTime));
            order.setOrderTime(new Date(createTime));
            order.setCreateIP(createIP);
            order.setReceiverName(shipName);
            order.setAddress(shipAddr);
            order.setPost(shipZip);
            order.setPhone(shipTel);
            order.setEmail(shipEmail);
            order.setCargoTime(new Date(shipTime));
            order.setReceiverMobile(shipMobile);
            order.setProTotalFee(costItem);
            order.setInvoiceIsopen(isTax);
            order.setCostTax(costTax);
            order.setInvoiceTitle(taxCompany);
            order.setCostFreight(costFreight);
            order.setIsProtect(isProtect);
            order.setCostProtect(costProtect);
            order.setOtherFee(costPayment);
            order.setScoreU(scoreU);
            order.setDiscount(discount);
            order.setUsePmt(usePmt);
            order.setOrderTotalFee(totalAmount);
            order.setFinalAmount(finalAmount);
            order.setDiscountFee(pmtAmount);
            order.setPayed(payed);
            order.setMemo(memo);
            order.setLastRefundTime(new Date(lastChangeTime));
            order.setCustomerId(memberId);
            order.setIsCod(cashOnDly);
            order.setOnlinePayType(onlinePayType);
            order.setScoreUAmount(scoreUAmount);
            order.setPayAgentId(payAgentId);
            order.setPayAgentScore(payAgentScore);
            order.setPayAgentScoreAmount(payAgentScoreAmount);
            order.setPayAgentPayed(payAgentPayed);
            order.setHasPayed(hasPayed);
            order.setHasPayedScore(hasPayedScore);
            order.setOnlineAmount(onlineAmount);
            order.setHongbaoAmount(hongbaoAmount);
            order.setPayTime(new Date(payTime));
            order.setVirtualRecMobile(virtualRecMobile);
            order = orderService.save(order);
            if (null == order) {

                //插入数据失败
                resultMap = new HashMap<String, String>();
                resultMap.put("resut_message", "订单数据保存失败");
                resultMap.put("resut_code", ResponseCode.DATABASE_ERROR);
                return resultMap;
            } else {
                if (null == erpName || !"".equals(erpName.trim())) {
                    //erp信息错误
                    resultMap = new HashMap<String, String>();
                    resultMap.put("resut_message", "ERP信息出错");
                    resultMap.put("resut_code", ResponseCode.ERP_INFO_ERROR);
                    return resultMap;
                } else {
                    ERPInfo info = new ERPInfo();
                    info.setName(erpName);
                    //处理edb模块
                    ERPHandler edbHandler = erpRegister.getERPHandler(info);
                    if (null != edbHandler) {
                        if (edbHandler.eventSupported(CreateOrderEvent.class)) {
//                            try {
//                                edbHandler.handleEvent(CreateOrderEvent.class,new Orde);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } catch (IllegalAccessException e) {
//                                e.printStackTrace();
//                            } catch (DocumentException e) {
//                                e.printStackTrace();
//                            }
                        }
                    }

                    //返回操作成功结果
                    resultMap = new HashMap<String, String>();
                    resultMap.put("resut_message", "订单接收成功");
                    resultMap.put("resut_code", ResponseCode.SUCCESS);
                    return resultMap;
                }
            }

        }
    }

    /**
     * 获取伙伴商城支付信息
     *
     * @param paymentId 支付编号
     * @param orderId 订单编号
     * @param memberId 会员编号
     * @param account 账户
     * @param bank 银行
     * @param payAccount 支付账户
     * @param currency 币种
     * @param money 钱币
     * @param payCost 支付金额
     * @param curMoney 当前钱币
     * @param payType 支付类型
     * @param payMethod 支付方法
     * @param ip ip
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param status 状态
     * @param memo 备注
     * @param tradeNo 订单编号
     * @param customerId 客户编号
     * @param wxOpenId 微信开放编号
     * @param wxIsSubscribe
     * @param onlinePayType
     * @param payAgentId
     * @param sign
     * @param appKey
     * @param operation
     * @param capCode
     * @param timeStamp
     * @param erpName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/commitPayMentInfo", method = RequestMethod.POST)
    public Map obtainPaymentInfo(String paymentId, String orderId,
                                 @RequestParam(value = "memberId", required = false, defaultValue = "0") int memberId,
                                 String account,
                                 String bank,
                                 String payAccount,
                                 String currency,
                                 float money,
                                 @RequestParam(value = "payCost", required = false, defaultValue = "0") float payCost,
                                 @RequestParam(value = "curMoney", required = false, defaultValue = "0") float curMoney,
                                 int payType,
                                 String payMethod,
                                 @RequestParam(value = "ip", required = false, defaultValue = "") String ip,
                                 long beginTime,
                                 long endTime,
                                 String status,
                                 @RequestParam(value = "memo", required = false, defaultValue = "") String memo,
                                 @RequestParam(value = "tradeNo", required = false, defaultValue = "") String tradeNo,
                                 int customerId,
                                 String wxOpenId,
                                 @RequestParam(value = "wxIsSubscribe", required = false, defaultValue = "0") int wxIsSubscribe,
                                 int onlinePayType,
                                 @RequestParam(value = "payAgentId", required = false, defaultValue = "0") int payAgentId,
                                 String sign,
                                 String appKey,
                                 String operation,
                                 String capCode,
                                 String timeStamp,
                                 String erpName) {
        Map<String, String> resultMap;
        MallPaymentBean payMent;
        if (!SecurityUtils.getInstance().validateSign(sign, appKey, operation, capCode, timeStamp)) {
            resultMap = new HashMap<String, String>();
            resultMap.put("resut_message", "sign验证失败");
            resultMap.put("resut_code", ResponseCode.SING_ERROR);
            return resultMap;
        } else {

            payMent = new MallPaymentBean();
            payMent.setPaymentId(paymentId);
            payMent.setOrderId(orderId);
            payMent.setMemberId(memberId);
            payMent.setAccount(account);
            payMent.setBank(bank);
            payMent.setPayAccount(payAccount);
            payMent.setCurrency(currency);
            payMent.setMoney(money);
            payMent.setPayCost(payCost);
            payMent.setCurMoney(curMoney);
            payMent.setPayType(payType);
            payMent.setPayMethod(payMethod);
            payMent.setIp(ip);
            payMent.setBeginTime(new Date(beginTime));
            payMent.setEndTime(new Date(endTime));
            payMent.setStatus(status);
            payMent.setMemo(memo);
            payMent.setTradeNo(tradeNo);
            payMent.setCustomerId(customerId);
            payMent.setWxOpenId(wxOpenId);
            payMent.setWxIsSubscribe(wxIsSubscribe);
            payMent.setOnlinePayType(onlinePayType);
            payMent.setPayAgentId(payAgentId);
            payMent = paymentRepository.save(payMent);
            if (null == payMent) {

                //插入数据失败
                resultMap = new HashMap<String, String>();
                resultMap.put("resut_message", "支付订单数据保存失败");
                resultMap.put("resut_code", ResponseCode.DATABASE_ERROR);
                return resultMap;
            } else {
                if (null == erpName || !"".equals(erpName.trim())) {
                    //erp信息错误
                    resultMap = new HashMap<String, String>();
                    resultMap.put("resut_message", "ERP信息出错");
                    resultMap.put("resut_code", ResponseCode.ERP_INFO_ERROR);
                    return resultMap;
                } else {
                    //遍历
                    for (ERPHandlerBuilder builder : erpRegister.getHandlerBuilders()) {

                    }

                    //返回操作成功结果
                    resultMap = new HashMap<String, String>();
                    resultMap.put("resut_message", "支付订单接收成功");
                    resultMap.put("resut_code", ResponseCode.SUCCESS);
                    return resultMap;
                }
            }

        }
    }

    @ResponseBody
    @RequestMapping(value = "/commitProductInfo", method = RequestMethod.POST)
    public Map obtainProductInfo(long id,
                                 String orderId,
                                 int productId,
                                 String deliverStatus,
                                 int typeId,
                                 String bn,
                                 String name,
                                 float cost,
                                 float price,
                                 float amount,
                                 int nums,
                                 int sendNum,
                                 String isType,
                                 int supplierId,
                                 int customerId,
                                 int goodId,
                                 String sign,
                                 String appKey,
                                 String operation,
                                 String capCode,
                                 String timeStamp,
                                 String erpName) {
        Map<String, String> resultMap;
        MallProductBean product;
        if (!SecurityUtils.getInstance().validateSign(sign, appKey, operation, capCode, timeStamp)) {
            resultMap = new HashMap<String, String>();
            resultMap.put("resut_message", "sign验证失败");
            resultMap.put("resut_code", ResponseCode.SING_ERROR);
            return resultMap;
        } else {

            product = new MallProductBean();
            product.setId(id);
            product.setOrder(orderId);
            product.setProductId(productId);
            product.setDeliverStatus(deliverStatus);
            product.setTypeId(typeId);
            product.setBn(bn);
            product.setName(name);
            product.setCost(cost);
            product.setPrice(price);
            product.setAmount(amount);
            product.setNums(nums);
            product.setSendNum(sendNum);
            product.setIsType(isType);
            product.setSupplierId(supplierId);
            product.setCustomerId(customerId);
            product.setGoodId(goodId);
            product = productRepository.save(product);
            if (null == product) {

                //插入数据失败
                resultMap = new HashMap<String, String>();
                resultMap.put("resut_message", "产品信息数据保存失败");
                resultMap.put("resut_code", ResponseCode.DATABASE_ERROR);
                return resultMap;
            } else {
                if (null == erpName || !"".equals(erpName.trim())) {
                    //erp信息错误
                    resultMap = new HashMap<String, String>();
                    resultMap.put("resut_message", "ERP信息出错");
                    resultMap.put("resut_code", ResponseCode.ERP_INFO_ERROR);
                    return resultMap;
                } else {
                    //遍历
                    for (ERPHandlerBuilder builder : erpRegister.getHandlerBuilders()) {

                    }

                    //返回操作成功结果
                    resultMap = new HashMap<String, String>();
                    resultMap.put("resut_message", "产品信息接收成功");
                    resultMap.put("resut_code", ResponseCode.SUCCESS);
                    return resultMap;
                }
            }

        }
    }

    public void Test(ERPInfo erpInfo) {

    }
}
