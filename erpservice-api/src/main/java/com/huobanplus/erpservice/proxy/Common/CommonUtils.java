/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.common;

import com.huobanplus.erpservice.common.util.DxDESCipher;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;

/**
 * Created by liual on 2015-10-19.
 */
public class CommonUtils {
    public static final String SECRET_KEY = "66668888";

    /**
     * 转换ERPInfo为明文状态
     *
     * @param preInfo 根据不同erp传递不同的必须参数
     * @return 返回密文形式的erp信息
     * @throws Exception
     */
    public static ERPInfo encryptInfo(ERPInfo preInfo) throws Exception {
        preInfo.setErpName(DxDESCipher.decrypt(preInfo.getErpName()));
        preInfo.setSysDataJson(DxDESCipher.decrypt(preInfo.getSysDataJson()));

        return preInfo;
    }
}
