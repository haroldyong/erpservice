package com.huobanplus.erpservice.transit.controller;

import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.transit.bean.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>类描述：<p/>
 * API对接伙伴商城订单操作接口
 */
@RequestMapping("/hotClientOrderApi")
public interface HotOrderController {
    /**
     * <p>方法描述：<p/>
     * 创建订单
     * <p>erpInfo:需要传递参数为name，sysDateJson</p>
     * <p>sysDateJson包含：
     * 1、dbhost 软件注册用户，比如edb_aXXXXX（接口调用的唯一标识），用户的主帐号
     * 2、appkey 公钥，你申请的appkey， 以标识来源
     * 3、format 返回值类型：json、xml,默认XML
     * 4、timestamp 时间戳
     * 6、slencry 返回结果加密方式(0:表示不加密，1:表示加密)
     * 7、ip 本机的外网IP地址
     * 9、secret 秘钥
     * </p>
     * <p>参数均为des加密后的字符串</p>
     *
     * @param orderInfo 订单信息
     * @param erpInfo   erp关联信息（参数为des加密后的参数）
     * @return 返回创建订单结果
     */
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    ApiResult createOrder(MallOrderBean orderInfo, ERPInfo erpInfo);

    /**
     * <p>方法描述：<p/>
     * 获取订单列表
     * <p>erpInfo:需要传递参数为name，sysDateJson</p>
     * <p>sysDateJson定义参照方法createOrder<p/>
     * <p>参数均为des加密后的字符串</p>
     *
     * @param erpInfo erp关联信息（参数为des加密后的参数）
     * @return 返回订单信息列表
     */
    @RequestMapping(value = "/obtainOrder", method = RequestMethod.GET)
    ApiResult obtainOrder(ERPInfo erpInfo);

    /**
     * <p>方法描述：<p/>
     * 订单发货
     * <p>sysDateJson定义参照方法createOrder<p/>
     *
     * @param orderInfo 根据不同erp传递不同的必须参数
     * @param erpInfo   erp关联信息（参数为des加密后的参数）
     * @return 返回发货结果信息
     */
    @RequestMapping(value = "/orderDeliver", method = RequestMethod.POST)
    ApiResult orderDeliver(MallOrderBean orderInfo, ERPInfo erpInfo);

    /**
     * <p>方法描述：<p/>
     * 订单业务信息更新
     * <p>sysDateJson定义参照方法createOrder<p/>
     *
     * @param orderInfo 根据不同erp传递不同的必须参数
     * @param erpInfo   erp关联信息（参数为des加密后的参数）
     * @return 返回订单更新结果信息
     */
    @RequestMapping(value = "/orderUpdate", method = RequestMethod.POST)
    ApiResult orderUpdate(MallOrderBean orderInfo, ERPInfo erpInfo);

    /**
     * <p>方法描述：<p/>
     * 订单状态更新
     * <p>sysDateJson定义参照方法createOrder<p/>
     *
     * @param orderInfo 根据不同erp传递不同的必须参数
     * @param erpInfo   erp关联信息（参数为des加密后的参数）
     * @return 返回订单更新结果信息
     */
    @RequestMapping(value = "/orderStatusUpdate", method = RequestMethod.POST)
    ApiResult orderStatusUpdate(MallOrderBean orderInfo, ERPInfo erpInfo);
}
