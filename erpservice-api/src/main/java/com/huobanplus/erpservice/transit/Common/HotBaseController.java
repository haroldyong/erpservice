package com.huobanplus.erpservice.transit.Common;

import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.transit.utils.DesUtil;

/**
 * Created by allan on 2015/8/6.
 */
public class HotBaseController {
    /**
     * 转换ERPInfo为明文状态
     *
     * @param preInfo 根据不同erp传递不同的必须参数
     * @return 返回密文形式的erp信息
     * @throws Exception
     */
    protected ERPInfo encryptInfo(ERPInfo preInfo) throws Exception {
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(DesUtil.decrypt(preInfo.getName()));
        erpInfo.setType(DesUtil.decrypt(preInfo.getType()));
        erpInfo.setSysDataJson(DesUtil.decrypt(preInfo.getSysDataJson()));
        erpInfo.setValidation(DesUtil.decrypt(preInfo.getValidation()));

        return erpInfo;
    }
}
