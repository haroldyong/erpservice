package com.huobanplus.erpservice.proxy.controller;

import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>类描述：API对接伙伴商城产品（库存）操作接口</p>
 * 产品相关接口
 * Created by allan on 2015/8/6.
 */
public interface HotProductController {
    /**
     * <p>方法描述：</p>
     * 获取产品库存信息
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
     * @param sign       远程传过来的鉴权参数
     * @param erpInfo    erp关联信息（参数为des加密后的参数）
     * @return 返回库存信息列表
     */
    @RequestMapping(value = "/obtainInventory", method = RequestMethod.POST)
    ApiResult obtainInventory(ERPInfo erpInfo, String sign);
}
