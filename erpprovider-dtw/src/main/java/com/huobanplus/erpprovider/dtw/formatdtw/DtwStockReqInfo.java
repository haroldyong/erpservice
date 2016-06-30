package com.huobanplus.erpprovider.dtw.formatdtw;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/30.
 */
@Data
public class DtwStockReqInfo {
    /**
     * 身份认证Key(必填)
     */
    @JSONField(name= "PassKey")
    private String passKey;

    /**
     * 产品编码，实物外包装应该标注的产品编码(最长50个字符)。(必填)
     */
    @JSONField(name= "Partno")
    private String partno;

    /**
     * 电商企业编码(必填),电商企业在跨境平台备案编码
     */
    @JSONField(name= "eCommerceCode")
    private String eCommerceCode;

    /**
     * 电商企业名称(必填),电商企业在跨境平台备名称
     */
    @JSONField(name= "eCommerceName")
    private String eCommerceName;
}
