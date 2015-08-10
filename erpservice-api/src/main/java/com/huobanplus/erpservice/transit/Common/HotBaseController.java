package com.huobanplus.erpservice.transit.Common;

import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.transit.bean.ApiResult;
import com.huobanplus.erpservice.transit.utils.DesUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Map;

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

    /**
     * 创建一个sign签名
     *
     * @param params 代签名参数，key排序的map
     * @param prefix
     * @param suffix
     * @return
     */
    protected String buildSign(Map<String, String> params, String prefix, String suffix) {
        if (prefix == null)
            prefix = "";
        if (suffix == null)
            suffix = "";
        StringBuilder stringBuilder = new StringBuilder(prefix);
//        Collections.sort(new ArrayList(params.entrySet()), new Comparator<Map.Entry<String, String>>() {
//            @Override
//            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
//                return o1.getKey().compareTo(o2.getKey());
//            }
//        });
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            stringBuilder.append(next.getKey()).append(next.getValue());
        }
        stringBuilder.append(suffix);
        return DigestUtils.md5Hex(stringBuilder.toString()).toUpperCase();
    }
}
