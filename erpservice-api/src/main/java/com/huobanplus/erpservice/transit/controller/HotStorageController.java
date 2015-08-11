package com.huobanplus.erpservice.transit.controller;

import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.transit.bean.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by allan on 2015/8/7.
 */
@RequestMapping("/hotClientStorageApi")
public interface HotStorageController {
    /**
     * <p>方法描述：<p/>
     * 新增出库单
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
     * @param outStoreJson json格式，出库单信息,有MallOutStoreBean对象序列化成的json格式字符串
     * @param erpInfo      erp关联信息（参数为des加密后的参数）
     * @param sign         签名
     * @return 返回结果
     */
    @RequestMapping(value = "/outStoreAdd", method = RequestMethod.POST)
    ApiResult outStoreAdd(String outStoreJson, ERPInfo erpInfo, String sign);

    /**
     * <p>方法描述：</p>
     * 根据出库单号，对出库单进行确认。出库单确认后系统库存会减少
     * <p>包含参数：</p>
     * <p>outStorageNo,freight,freightAvgWay</p>
     * <p>sysDateJson定义参照方法createOrder</p>
     *
     * @param outStoreJson json格式，根据不同erp传递不同的必须参数(e.g. edb:outStorage_no,freight,freight_avgway)
     * @param erpInfo      erp关联信息（参数为des加密后的参数）
     * @param sign         签名
     * @return 返回结果
     */
    @RequestMapping(value = "/outStoreConfirm", method = RequestMethod.POST)
    ApiResult outStoreConfirm(String outStoreJson, ERPInfo erpInfo, String sign);

    /**
     * <p>方法描述：</p>
     * 根据出库单号，对出库单进行确认。出库单确认后系统库存会减少
     * <p>sysDateJson定义参照方法createOrder</p>
     *
     * @param proOutJson json格式，根据MallProductOutBean，所需参数（bar_code，outstorage_no，outstorage_num,edb为例）
     * @param erpInfo    erp关联信息（参数为des加密后的参数）
     * @param sign       签名
     * @return 返回结果
     */
    @RequestMapping(value = "/outStoreWriteBack", method = RequestMethod.POST)
    ApiResult outStoreWriteBack(String proOutJson, ERPInfo erpInfo, String sign);
}
