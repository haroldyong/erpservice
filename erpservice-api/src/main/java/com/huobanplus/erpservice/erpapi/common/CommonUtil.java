package com.huobanplus.erpservice.erpapi.common;

import com.huobanplus.erpservice.common.util.DxDESCipher;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;

/**
 * Created by liual on 2015-10-14.
 */
public class CommonUtil {
    public static String signKey = "847385239789";

    public static ERPInfo encryptInfo(ERPInfo preInfo){
        preInfo.setName(DxDESCipher.decrypt(preInfo.getName()));
        preInfo.setType(DxDESCipher.decrypt(preInfo.getType()));
        preInfo.setSysDataJson(DxDESCipher.decrypt(preInfo.getSysDataJson()));
        preInfo.setValidation(DxDESCipher.decrypt(preInfo.getValidation()));

        return preInfo;
    }
}
