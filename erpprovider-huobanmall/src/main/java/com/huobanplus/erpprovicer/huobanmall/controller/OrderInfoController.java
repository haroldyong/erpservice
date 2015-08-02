package com.huobanplus.erpprovicer.huobanmall.controller;

import com.huobanplus.erpprovicer.huobanmall.common.AuthBean;
import com.huobanplus.erpprovicer.huobanmall.common.SimpleMonitor;
import com.huobanplus.erpprovicer.huobanmall.service.OrderEventService;
import com.huobanplus.erpprovicer.huobanmall.util.Constant;
import com.huobanplus.erpprovicer.huobanmall.util.DateUtil;
import com.huobanplus.erpprovicer.huobanmall.util.SignStrategy;
import com.huobanplus.erpservice.event.model.BaseResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 类描述：订单事件处理类
 *
 * @author aaron
 * @version V1.0
 * @since 2015年7月25日 下午4:30:43
 */
@Controller
public class OrderInfoController {

    @Resource
    private OrderEventService orderEventService;

    /**
     * 方法描述：提交（创建）单个订单信息
     *
     * @param orderId             订单编号
     * @param syncStatus          同步标记，1表示已抓取
     * @param memberId            会员id
     * @param orderStatus         订单状态
     * @param payStatus           支付状态
     * @param shipStatus          发货状态
     * @param memberStatus        用户状态
     * @param isDelivery          是否需要发货
     * @param deliverMethodId     配送方式id
     * @param deliverMethod       配送方式
     * @param deliverArea         配送区域
     * @param weight              重量
     * @param orderName           订单名称
     * @param itemNum             商品数量
     * @param actTime             活动时间
     * @param createTime          创建时间
     * @param createIP            创建ip
     * @param shipName            收货人
     * @param shipArea            收货人区域
     * @param shipAddr            收货人地址
     * @param shipZip             收货人邮编
     * @param shipTel             收货人电话
     * @param shipEmail           收货人邮箱
     * @param shipTime            收货人建议送货时间
     * @param shipMobile          收货人手机
     * @param costItem            商品总金额
     * @param isTax               是否开发票
     * @param costTax             税金
     * @param taxCompany          开票公司抬头
     * @param costFreight         配送费用
     * @param isProtect           是否保价
     * @param costProtect         保价费用
     * @param costPayment         支付手续费
     * @param scoreU              抵用积分
     * @param discount            折扣省下的金额
     * @param usePmt              是否使用优惠
     * @param totalAmount         订单总金额
     * @param finalAmount         最终金额
     * @param pmtAmount           优惠金额
     * @param payed               用余额支付的金额
     * @param memo                订单备注
     * @param lastChangeTime      最后变动时间
     * @param customerId          商户Id
     * @param cashOnDly           是否货到付款
     * @param onlinePayType       在线支付方式
     * @param scoreUAmount        积分抵用金额
     * @param payAgentId          代付人ID
     * @param payAgentScore       代付积分
     * @param payAgentScoreAmount 代付积分金额
     * @param payAgentPayed       余额代付
     * @param hasPayed            订单已付金额
     * @param hasPayedScore       订单已付积分
     * @param onlineAmount        在线支付金额
     * @param hongbaoAmount       红包抵用金额
     * @param payTime             支付时间
     * @param virtualRecMobile    虚拟商品收货手机
     * @param sign                授权签名
     * @param uCode               接入码，用于验证请求的有效性。主要用于区分店铺
     * @param secret              密钥
     * @param timeStamp           时间戳
     * @param mType               方法名，不同接口分别传入不同的方法值。
     * @param signType            加密方式，默认为MD5
     * @return 处理结果
     */
    public Monitor commitOrder(String orderId, int syncStatus,
                                           String memberId,
                                           String orderStatus,
                                           String payStatus,
                                           String shipStatus,
                                           String memberStatus,
                                           int isDelivery,
                                           String deliverMethodId,
                                           String deliverMethod,
                                           String deliverArea,
                                           float weight,
                                           String orderName,
                                           int itemNum,
                                           long actTime,
                                           long createTime,
                                           String createIP,
                                           String shipName,
                                           String shipArea,
                                           String shipAddr,
                                           String shipZip,
                                           String shipTel,
                                           String shipEmail,
                                           long shipTime,
                                           String shipMobile,
                                           double costItem,
                                           int isTax,
                                           float costTax,
                                           String taxCompany,
                                           float costFreight,
                                           String isProtect,
                                           float costProtect,
                                           double costPayment,
                                           double scoreU,
                                           float discount,
                                           String usePmt,
                                           float totalAmount,
                                           float finalAmount,
                                           float pmtAmount,
                                           float payed,
                                           String memo,
                                           long lastChangeTime,
                                           String customerId,
                                           int cashOnDly,
                                           String onlinePayType,
                                           double scoreUAmount,
                                           String payAgentId,
                                           double payAgentScore,
                                           double payAgentScoreAmount,
                                           double payAgentPayed,
                                           double hasPayed,
                                           double hasPayedScore,
                                           double onlineAmount,
                                           double hongbaoAmount,
                                           long payTime,
                                           String virtualRecMobile,
                                           String sign,
                                           String uCode,
                                           String secret,
                                           String timeStamp,
                                           String mType,
                                           String signType) {
        BaseResult result;
        AuthBean authBean = new AuthBean();
        authBean.setSign(sign);
        authBean.setmType(mType);
        authBean.setSecret(secret);
        authBean.setSignType(signType);
        authBean.setTimeStamp(timeStamp);
        authBean.setuCode(uCode);

        OrderInfo orderInfo;
        String signStr = SignStrategy.getInstance().buildSign(authBean).getSign();
        if (null != signStr && signStr.equals(sign)) {
            result = new BaseResult();
            result.setResultMsg("sign验证失败");
            result.setResultCode(Constant.REQUEST_SING_ERROR);
            return new SimpleMonitor<BaseResult>(result);
        } else {
            orderInfo = new OrderInfo();
            orderInfo.setOrderCode(orderId);
            orderInfo.setSyncStatus(syncStatus);
            orderInfo.setBuyerId(memberId);
            orderInfo.setOrderStatus(orderStatus);
            orderInfo.setPayStatus(payStatus);
            orderInfo.setDeliveryStatus(shipStatus);
            orderInfo.setCustomerStatus(memberStatus);
            orderInfo.setIsDelivery(isDelivery);
            orderInfo.setSendingTypeId(deliverMethodId);
            orderInfo.setSendingType(deliverMethod);
            orderInfo.setSendingArea(deliverArea);
            orderInfo.setTotalWeight(weight);
            orderInfo.setOrderName(orderName);
            orderInfo.setItemNum(itemNum);
            orderInfo.setAdvDistributTime(new Date(actTime));
            orderInfo.setOrderTime(new Date(createTime));
            orderInfo.setCreateIP(createIP);
            orderInfo.setConsignee(shipName);
            orderInfo.setArea(shipArea);
            orderInfo.setAddress(shipAddr);
            orderInfo.setPost(shipZip);
            orderInfo.setPhone(shipTel);
            orderInfo.setEmail(shipEmail);
            orderInfo.setBookDeliveryTime(new Date(shipTime));
            orderInfo.setReceiverMobile(shipMobile);
            orderInfo.setProTotalFee(costItem);
            orderInfo.setInvoiceIsopen(isTax);
            orderInfo.setCostTax(costTax);
            orderInfo.setInvoiceTitle(taxCompany);
            orderInfo.setCostFreight(costFreight);
            orderInfo.setIsProtect(isProtect);
            orderInfo.setCostProtect(costProtect);
            orderInfo.setPayCommission(costPayment);
            orderInfo.setCostPoint(scoreU);
            orderInfo.setDiscount(discount);
            orderInfo.setUsePmt(usePmt);
            orderInfo.setOrderTotalFee(totalAmount);
            orderInfo.setFinalAmount(finalAmount);
            orderInfo.setDiscountFee(pmtAmount);
            orderInfo.setPayed(payed);
            orderInfo.setMemo(memo);
            orderInfo.setEndTime(new Date(lastChangeTime));
            orderInfo.setCustomerId(customerId);
            orderInfo.setIsCod(cashOnDly);
            orderInfo.setPayMothed(onlinePayType);
            orderInfo.setScoreUAmount(scoreUAmount);
            orderInfo.setPayAgentId(payAgentId);
            orderInfo.setPayAgentScore(payAgentScore);
            orderInfo.setPayAgentScoreAmount(payAgentScoreAmount);
            orderInfo.setPayAgentPayed(payAgentPayed);
            orderInfo.setHasPayed(hasPayed);
            orderInfo.setHasPayedScore(hasPayedScore);
            orderInfo.setOnlineAmount(onlineAmount);
            orderInfo.setHongbaoAmount(hongbaoAmount);
            orderInfo.setPayDate(new Date(payTime));
            orderInfo.setVirtualRecMobile(virtualRecMobile);

            return orderEventService.commitOrder(authBean, orderInfo);
        }
    }

    /**
     * 根据订单编号获取单个订单信息
     * 该接口包含批量获取订单信息功能
     * {1、orderIds只包含一个orderId时，只获取一个订单信息 2、orderIds包含多个orderId时，批量获取订单信息}
     * orderIds包含多个orderId时 每个orderId以逗号隔开
     *
     * @param sign      授权签名
     * @param uCode     接入码，用于验证请求的有效性。主要用于区分店铺
     * @param secret    密钥
     * @param timeStamp 时间戳
     * @param mType     方法名，不同接口分别传入不同的方法值。
     * @param signType  加密方式，默认为MD5
     * @param orderIds  订单编号
     * @return 返回订单详细
     */
    public Monitor obtainOrder(String sign,
                                          String uCode,
                                          String secret,
                                          String timeStamp,
                                          String mType,
                                          String signType,
                                          String orderIds) {

        AuthBean authBean = new AuthBean();
        authBean.setSign(sign);
        authBean.setmType(mType);
        authBean.setSecret(secret);
        authBean.setSignType(signType);
        authBean.setTimeStamp(timeStamp);
        authBean.setuCode(uCode);
        OrderInfo orderInfo;

        String signStr = SignStrategy.getInstance().buildSign(authBean).getSign();
        if (null != signStr && signStr.equals(sign)) {
            orderInfo = new OrderInfo();
            orderInfo.setResultMsg("sign验证失败");
            orderInfo.setResultCode(Constant.REQUEST_SING_ERROR);
            return new SimpleMonitor<OrderInfo>(orderInfo);
        } else {
            if (StringUtils.isEmpty(orderIds)) {
                orderInfo = new OrderInfo();
                orderInfo.setResultMsg("传入订单编号无效");
                orderInfo.setResultCode(Constant.REQUEST_INAVLID_PARAMETER);
                return new SimpleMonitor<OrderInfo>(orderInfo);
            } else {
                return orderEventService.obtainOrder(authBean, orderIds);
            }
        }
    }


    /**
     * 获取所有的订单信息
     *
     * @param sign      授权签名
     * @param uCode     接入码，用于验证请求的有效性。主要用于区分店铺
     * @param secret    密钥
     * @param timeStamp 时间戳
     * @param mType     方法名，不同接口分别传入不同的方法值。
     * @param signType  加密方式，默认为MD5
     * @return 返回订单列表
     */
    public Monitor<OrderInfo> obtainOrder(String sign,
                                          String uCode,
                                          String secret,
                                          String timeStamp,
                                          String mType,
                                          String signType) {

        AuthBean authBean = new AuthBean();
        authBean.setSign(sign);
        authBean.setmType(mType);
        authBean.setSecret(secret);
        authBean.setSignType(signType);
        authBean.setTimeStamp(timeStamp);
        authBean.setuCode(uCode);
        OrderInfo orderInfo;

        String signStr = SignStrategy.getInstance().buildSign(authBean).getSign();
        if (null != signStr && signStr.equals(sign)) {
            orderInfo = new OrderInfo();
            orderInfo.setResultMsg("sign验证失败");
            orderInfo.setResultCode(Constant.REQUEST_SING_ERROR);
            return new SimpleMonitor<OrderInfo>(orderInfo);
        } else {
            return orderEventService.obtainOrders(authBean);

        }
    }


    /**
     * 方法描述：更新单个订单信息
     *
     * @param orderId             订单编号
     * @param syncStatus          同步标记，1表示已抓取
     * @param memberId            会员id
     * @param orderStatus         订单状态
     * @param payStatus           支付状态
     * @param shipStatus          发货状态
     * @param memberStatus        用户状态
     * @param isDelivery          是否需要发货
     * @param deliverMethodId     配送方式id
     * @param deliverMethod       配送方式
     * @param deliverArea         配送区域
     * @param weight              重量
     * @param orderName           订单名称
     * @param itemNum             商品数量
     * @param actTime             活动时间
     * @param createTime          创建时间
     * @param createIP            创建ip
     * @param shipName            收货人
     * @param shipArea            收货人区域
     * @param shipAddr            收货人地址
     * @param shipZip             收货人邮编
     * @param shipTel             收货人电话
     * @param shipEmail           收货人邮箱
     * @param shipTime            收货人建议送货时间
     * @param shipMobile          收货人手机
     * @param costItem            商品总金额
     * @param isTax               是否开发票
     * @param costTax             税金
     * @param taxCompany          开票公司抬头
     * @param costFreight         配送费用
     * @param isProtect           是否保价
     * @param costProtect         保价费用
     * @param costPayment         支付手续费
     * @param scoreU              抵用积分
     * @param discount            折扣省下的金额
     * @param usePmt              是否使用优惠
     * @param totalAmount         订单总金额
     * @param finalAmount         最终金额
     * @param pmtAmount           优惠金额
     * @param payed               用余额支付的金额
     * @param memo                订单备注
     * @param lastChangeTime      最后变动时间
     * @param customerId          商户Id
     * @param cashOnDly           是否货到付款
     * @param onlinePayType       在线支付方式
     * @param scoreUAmount        积分抵用金额
     * @param payAgentId          代付人ID
     * @param payAgentScore       代付积分
     * @param payAgentScoreAmount 代付积分金额
     * @param payAgentPayed       余额代付
     * @param hasPayed            订单已付金额
     * @param hasPayedScore       订单已付积分
     * @param onlineAmount        在线支付金额
     * @param hongbaoAmount       红包抵用金额
     * @param payTime             支付时间
     * @param virtualRecMobile    虚拟商品收货手机
     * @param sign                授权签名
     * @param uCode               接入码，用于验证请求的有效性。主要用于区分店铺
     * @param secret              密钥
     * @param timeStamp           时间戳
     * @param mType               方法名，不同接口分别传入不同的方法值。
     * @param signType            加密方式，默认为MD5
     * @return 处理结果
     */
    public Monitor modifyOrder(String orderId, int syncStatus,
                                           String memberId,
                                           String orderStatus,
                                           String payStatus,
                                           String shipStatus,
                                           String memberStatus,
                                           int isDelivery,
                                           String deliverMethodId,
                                           String deliverMethod,
                                           String deliverArea,
                                           float weight,
                                           String orderName,
                                           int itemNum,
                                           long actTime,
                                           long createTime,
                                           String createIP,
                                           String shipName,
                                           String shipArea,
                                           String shipAddr,
                                           String shipZip,
                                           String shipTel,
                                           String shipEmail,
                                           long shipTime,
                                           String shipMobile,
                                           double costItem,
                                           int isTax,
                                           float costTax,
                                           String taxCompany,
                                           float costFreight,
                                           String isProtect,
                                           float costProtect,
                                           double costPayment,
                                           double scoreU,
                                           float discount,
                                           String usePmt,
                                           float totalAmount,
                                           float finalAmount,
                                           float pmtAmount,
                                           float payed,
                                           String memo,
                                           long lastChangeTime,
                                           String customerId,
                                           int cashOnDly,
                                           String onlinePayType,
                                           double scoreUAmount,
                                           String payAgentId,
                                           double payAgentScore,
                                           double payAgentScoreAmount,
                                           double payAgentPayed,
                                           double hasPayed,
                                           double hasPayedScore,
                                           double onlineAmount,
                                           double hongbaoAmount,
                                           long payTime,
                                           String virtualRecMobile,
                                           String sign,
                                           String uCode,
                                           String secret,
                                           String timeStamp,
                                           String mType,
                                           String signType) {
        BaseResult result;
        AuthBean authBean = new AuthBean();
        authBean.setSign(sign);
        authBean.setmType(mType);
        authBean.setSecret(secret);
        authBean.setSignType(signType);
        authBean.setTimeStamp(timeStamp);
        authBean.setuCode(uCode);

        OrderInfo orderInfo;
        String signStr = SignStrategy.getInstance().buildSign(authBean).getSign();
        if (null != signStr && signStr.equals(sign)) {
            result = new BaseResult();
            result.setResultMsg("sign验证失败");
            result.setResultCode(Constant.REQUEST_SING_ERROR);
            return new SimpleMonitor<BaseResult>(result);
        } else {
            orderInfo = new OrderInfo();
            orderInfo.setOrderCode(orderId);
            orderInfo.setSyncStatus(syncStatus);
            orderInfo.setBuyerId(memberId);
            orderInfo.setOrderStatus(orderStatus);
            orderInfo.setPayStatus(payStatus);
            orderInfo.setDeliveryStatus(shipStatus);
            orderInfo.setCustomerStatus(memberStatus);
            orderInfo.setIsDelivery(isDelivery);
            orderInfo.setSendingTypeId(deliverMethodId);
            orderInfo.setSendingType(deliverMethod);
            orderInfo.setSendingArea(deliverArea);
            orderInfo.setTotalWeight(weight);
            orderInfo.setOrderName(orderName);
            orderInfo.setItemNum(itemNum);
            orderInfo.setAdvDistributTime(new Date(actTime));
            orderInfo.setOrderTime(new Date(createTime));
            orderInfo.setCreateIP(createIP);
            orderInfo.setConsignee(shipName);
            orderInfo.setArea(shipArea);
            orderInfo.setAddress(shipAddr);
            orderInfo.setPost(shipZip);
            orderInfo.setPhone(shipTel);
            orderInfo.setEmail(shipEmail);
            orderInfo.setBookDeliveryTime(new Date(shipTime));
            orderInfo.setReceiverMobile(shipMobile);
            orderInfo.setProTotalFee(costItem);
            orderInfo.setInvoiceIsopen(isTax);
            orderInfo.setCostTax(costTax);
            orderInfo.setInvoiceTitle(taxCompany);
            orderInfo.setCostFreight(costFreight);
            orderInfo.setIsProtect(isProtect);
            orderInfo.setCostProtect(costProtect);
            orderInfo.setPayCommission(costPayment);
            orderInfo.setCostPoint(scoreU);
            orderInfo.setDiscount(discount);
            orderInfo.setUsePmt(usePmt);
            orderInfo.setOrderTotalFee(totalAmount);
            orderInfo.setFinalAmount(finalAmount);
            orderInfo.setDiscountFee(pmtAmount);
            orderInfo.setPayed(payed);
            orderInfo.setMemo(memo);
            orderInfo.setEndTime(new Date(lastChangeTime));
            orderInfo.setCustomerId(customerId);
            orderInfo.setIsCod(cashOnDly);
            orderInfo.setPayMothed(onlinePayType);
            orderInfo.setScoreUAmount(scoreUAmount);
            orderInfo.setPayAgentId(payAgentId);
            orderInfo.setPayAgentScore(payAgentScore);
            orderInfo.setPayAgentScoreAmount(payAgentScoreAmount);
            orderInfo.setPayAgentPayed(payAgentPayed);
            orderInfo.setHasPayed(hasPayed);
            orderInfo.setHasPayedScore(hasPayedScore);
            orderInfo.setOnlineAmount(onlineAmount);
            orderInfo.setHongbaoAmount(hongbaoAmount);
            orderInfo.setPayDate(new Date(payTime));
            orderInfo.setVirtualRecMobile(virtualRecMobile);

            return orderEventService.modifyOrderInfo(authBean, orderInfo);
        }
    }

}
