/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>类描述：API对接伙伴商城订单操作接口</p>
 */
@RequestMapping("/hotClientOrderApi")
public interface HotOrderController {
    /**
     * <p>方法描述：</p>
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
     * @param orderInfoJson 订单信息,有MallOrderBean对象序列化成的json格式字符串
     * @param erpInfo       erp关联信息（参数为des加密后的参数）
     * @param sign          远程传过来的鉴权参数
     * @return 返回创建订单结果
     */
//    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
//    ApiResult createOrder(String orderInfoJson, ERPInfo erpInfo, String sign);
//
//    /**
//     * <p>方法描述：</p>
//     * 获取订单列表
//     * <p>erpInfo:需要传递参数为name，sysDateJson</p>
//     * <p>sysDateJson定义参照方法createOrder</p>
//     * <p>参数均为des加密后的字符串</p>
//     *
//     * @param orderSearchJson 订单搜索条件json
//     * @param sign            远程传过来的鉴权参数
//     * @param erpInfo         erp关联信息（参数为des加密后的参数）
//     * @return 返回订单信息列表
//     */
////    @RequestMapping(value = "/obtainOrder", method = RequestMethod.POST)
////    ApiResult obtainOrder(String orderSearchJson, ERPInfo erpInfo, String sign);
//
//    /**
//     * <p>方法描述：</p>
//     * 订单发货
//     * <p>sysDateJson定义参照方法createOrder</p>
//     *
//     * @param sign          远程传过来的鉴权参数
//     * @param orderInfoJson 根据不同erp传递不同的必须参数
//     * @param erpInfo       erp关联信息（参数为des加密后的参数）
//     * @return 返回发货结果信息
//     */
//    @RequestMapping(value = "/orderDeliver", method = RequestMethod.POST)
//    ApiResult orderDeliver(String orderInfoJson, ERPInfo erpInfo, String sign);
//
//    /**
//     * <p>方法描述：</p>
//     * 订单业务信息更新
//     * <p>sysDateJson定义参照方法createOrder</p>
//     *
//     * @param sign          远程传过来的鉴权参数
//     * @param orderInfoJson 根据不同erp传递不同的必须参数
//     * @param erpInfo       erp关联信息（参数为des加密后的参数）
//     * @return 返回订单更新结果信息
//     */
//    @RequestMapping(value = "/orderUpdate", method = RequestMethod.POST)
//    ApiResult orderUpdate(String orderInfoJson, ERPInfo erpInfo, String sign);
//
//    /**
//     * <p>方法描述：订单状态更新</p>
//     * <p>更新订单导入标记为 已导入，可更新条件为</p>
//     * <p>1、	订单导入标记为未导入</p>
//     * <p>2、	订单非货到付款，其状态为 已确认   已付款（订单）</p>
//     * <p>3、	订单为货到付款，其确认状态为  已确认（订单）</p>
//     * <p>参数：</p>
//     * <p>单据号,可以输入一个,可以输入多个,多个单据号以逗号(,)隔开.如果某个单据号不导入,但需要批注,则在该单据号后加冒号(:),加上批注内容</p>
//     * <p></p>
//     * <p>sysDateJson定义参照方法createOrder</p>
//     *
//     * @param sign          远程传过来的鉴权参数
//     * @param orderInfoJson 根据不同erp传递不同的必须参数
//     * @param erpInfo       erp关联信息（参数为des加密后的参数）
//     * @return 返回订单更新结果信息
//     */
//    @RequestMapping(value = "/orderStatusUpdate", method = RequestMethod.POST)
//    ApiResult orderStatusUpdate(String orderInfoJson, ERPInfo erpInfo, String sign);
}
